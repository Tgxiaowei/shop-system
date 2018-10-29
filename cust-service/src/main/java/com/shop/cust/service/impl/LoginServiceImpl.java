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
import com.shop.base.enums.MsgTypeEnum;
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
    @Value("${mock.flage}")
    private int           mockFlage;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResp register(RegisterReq req) {

        registerParamCheck(req);

        String mobile = req.getMobile();
        String psw = req.getPsw();
        // 先校验密码是否一致，不一致直接抛异常，如此可不必重发短信
        Assert.equals(psw, req.getPswRept(), "两次密码输入不一致！");
        // check msgCode
        validMsgCode(mobile, req.getMsgCode(), MsgTypeEnum.REGISTSER);
        // 校验客户是否已注册，验证码校验通过之后才让客户知晓该手机号是否已注册过
        CustDAO cust = custService.selectCustByMobile(mobile);
        Assert.isNull(cust, "手机号码已注册！");

        cust = new CustDAO();
        cust.setCustNo(UUIDUtil.getUUID());
        cust.setMobile(mobile);
        cust.setLoginPsw(DigestUtils.md5Hex(psw));
        cust.setStatus(CustStatusEnum.NORMAL.getIndex());
        cust.setGmtCreate(new Date());
        custMapper.insert(cust);

        return login(new LoginReq(mobile, psw));
    }

    @Override
    public LoginResp login(LoginReq req) {

        Assert.notNull(req, "参数为空");
        Assert.notBlank(req.getMobile(), "手机号为空");

        String mobile = req.getMobile();
        String psw = req.getPsw();
        String msgCode = req.getMsgCode();
        CustDAO cust = custService.selectCustByMobile(mobile);
        Assert.notNull(cust, "客户不存在！");

        if (StringUtils.isNotBlank(psw)) {
            Assert.equals(cust.getLoginPsw(), DigestUtils.md5Hex(psw), "密码错误！");
        } else if (StringUtils.isNotBlank(req.getMsgCode())) {
            Assert.notBlank(msgCode, "密码为空！");
            validMsgCode(mobile, msgCode, MsgTypeEnum.LOGIN);
        } else {
            logger.error("非法登陆请求！req={}", req);
            throw new BizException("非法登陆请求！");
        }
        String token = UUIDUtil.getUUID();
        redisClient.setex(CacheKeyConstant.TOKEN + mobile, loginExpiry, token);

        return new LoginResp(token, cust.getName());
    }

    @Override
    public boolean sendMsg(String mobile, int type) {
        // 校验
        CustDAO cust = custService.selectCustByMobile(mobile);
        if (type == MsgTypeEnum.REGISTSER.getIndex()) {
            Assert.isNull(cust, "客户已注册！");
        } else {
            Assert.notNull(cust, "客户不存在！");
            Assert.isTrue(cust.getStatus() == CustStatusEnum.NORMAL.getIndex(), "非法状态！");
        }
        // 测试mock
        if ("1".equals(mockFlage))
            return true;

        // TODO 调用短信发送渠道(短信验证码由渠道生成)
        return true;
    }

    /**
     * 注册参数校验
     */
    private void registerParamCheck(RegisterReq req) {

        Assert.notNull(req, "参数缺失");
        Assert.notBlank(req.getMobile(), "手机号为空！");
        Assert.notBlank(req.getMsgCode(), "短信验证码为空！");
        Assert.notBlank(req.getPsw(), "密码为空！");
        Assert.notBlank(req.getPswRept(), "密码为空！");

    }

    /**
     * 校验短信验证码
     */
    private void validMsgCode(String mobile, String msgCode, MsgTypeEnum msgTypeEnum) {

        // 测试mock
        if (mockFlage == 1 && StringUtils.equals(msgCode, "8888"))
            return;

        // TODO 调用短信渠道校验短信验证码，校验不通过直接抛出异常
        throw new BizException("验证码错误！");
    }

}
