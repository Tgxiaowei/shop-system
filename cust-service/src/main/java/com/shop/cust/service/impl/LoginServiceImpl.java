package com.shop.cust.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.constant.CacheKeyConstant;
import com.shop.base.enums.CustStatusEnum;
import com.shop.base.enums.MsgSendEnum;
import com.shop.base.exception.BizException;
import com.shop.base.util.Assert;
import com.shop.base.util.UUIDUtil;
import com.shop.cust.dao.CustDAO;
import com.shop.cust.dto.req.LoginReq;
import com.shop.cust.dto.req.RegisterReq;
import com.shop.cust.dto.resp.LoginResp;
import com.shop.cust.mapper.CustMapper;
import com.shop.cust.service.CustService;
import com.shop.cust.service.LoginService;
import com.shop.feign.client.RedisClient;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisClient   redisClient;
    @Autowired
    private CustMapper    custMapper;
    @Autowired
    private CustService   custService;
    @Value("${login.token.expiry}")
    private int           loginExpiry;
    @Value("${msg.code.expiry}")
    private int           msgExpiry;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResp register(RegisterReq req) {

        String mobile = req.getMobile();
        String psw = req.getPsw();
        // 先校验密码是否一致，不一致直接抛异常，如此可不必重发短信
        Assert.equals(psw, req.getPswRept(), "两次密码输入不一致！");
        // check msgCode
        validMsgCode(mobile, req.getMsgCode(), MsgSendEnum.REGISTSER);
        // 校验客户是否已注册，验证码校验通过之后才让客户知晓该手机号是否已注册过
        CustDAO cust = custService.selectCustByMobile(mobile);
        Assert.isNull(cust, "手机号码已注册！");

        cust = new CustDAO();
        cust.setMobile(mobile);
        cust.setLoginPsw(DigestUtils.md5Hex(psw));
        cust.setStatus(CustStatusEnum.NORMAL.getIndex());
        cust.setGmt_create(new Date());
        custMapper.insert(cust);

        return login(new LoginReq(mobile, psw));
    }

    @Override
    public LoginResp login(LoginReq req) {

        String mobile = req.getMobile();
        String psw = req.getPsw();
        String msgCode = req.getMsgCode();
        CustDAO cust = custService.selectCustByMobile(mobile);
        Assert.notNull(cust, "客户不存在！");

        if (StringUtils.isNotBlank(psw)) {
            Assert.equals(psw, DigestUtils.md5Hex(psw), "密码错误！");
        } else if (StringUtils.isNotBlank(req.getMsgCode())) {
            validMsgCode(mobile, msgCode, MsgSendEnum.LOGIN);
        } else {
            logger.error("非法登陆请求！req={}", req);
            throw new BizException("非法登陆请求！");
        }
        String token = UUIDUtil.getUUID();
        redisClient.setex(CacheKeyConstant.token + mobile, loginExpiry, token);

        return new LoginResp(token, cust.getName());
    }

    @Override
    public boolean sendMsg(String mobile, int type) {
        // 校验
        CustDAO cust = custService.selectCustByMobile(mobile);
        if (type == MsgSendEnum.REGISTSER.getIndex()) {
            Assert.isNull(cust, "客户已注册！");
        } else {
            Assert.notNull(cust, "客户不存在！");
            Assert.isTrue(cust.getStatus() == CustStatusEnum.NORMAL.getIndex(), "非法状态！");
        }
        // TODO 生成短信验证码
        String code = "";
        // TODO 发送短信验证码成功后，塞缓存
        String cacheKey = getMsgCacheKey(mobile, MsgSendEnum.getEnumByIndex(type));
        redisClient.setex(cacheKey, msgExpiry, code);

        return true;
    }

    /**
     * 校验短信验证码
     */
    private void validMsgCode(String mobile, String code, MsgSendEnum msgSendEnum) {

        String cacheKey = getMsgCacheKey(mobile, msgSendEnum);
        String cacheMsgCode = redisClient.get(cacheKey);
        redisClient.del(cacheKey);

        Assert.isTrue(StringUtils.isNotEmpty(cacheMsgCode), "短信验证码已失效！");
        Assert.isTrue(StringUtils.equals(cacheMsgCode, code), "短信验证码错误！");
    }

    /**
     * 获取短信验证码缓存key
     */
    private String getMsgCacheKey(String mobile, MsgSendEnum msgSendEnum) {

        switch (msgSendEnum) {
            case REGISTSER:
                return CacheKeyConstant.registMsg + mobile;
            case LOGIN:
                return CacheKeyConstant.loginMsg + mobile;
            case RESET_LOGIN_PSW:
                return CacheKeyConstant.resetLoginPsw + mobile;
            case RESET_PAY_PSW:
                return CacheKeyConstant.resetPayPsw + mobile;
            default:
                throw new BizException("错误的短信类型！");
        }
    }

}
