package com.shop.cust.service.impl;

import java.util.Date;

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
import com.sun.jersey.core.util.Base64;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisClient   redisClient;
    @Autowired
    private CustMapper    custMapper;
    @Autowired
    private CustService   custService;
    @Value("${login.token.seconds}")
    private int           seconds;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResp register(RegisterReq req) {

        // 先校验密码是否一致，不一致直接抛异常，如此可不必重发短信
        Assert.equals(req.getPsw(), req.getPswRept(), "两次密码输入不一致！");
        // check msgCode
        validMsgCode(req.getMobile(), req.getMsgCode(), MsgSendEnum.REGIST);

        CustDAO cust = new CustDAO();
        cust.setMobile(req.getMobile());
        cust.setLoginPsw(req.getPsw());
        cust.setStatus(CustStatusEnum.normal.getIndex());
        cust.setGmt_create(new Date());
        custMapper.insert(cust);

        LoginReq loginReq = new LoginReq();
        loginReq.setMobile(req.getMobile());
        loginReq.setPsw(req.getPsw());
        return login(loginReq);
    }

    @Override
    public LoginResp login(LoginReq req) {

        String mobile = req.getMobile();
        String psw = req.getPsw();
        String msgCode = req.getMsgCode();
        CustDAO cust = custService.selectCustByMobile(mobile);
        Assert.notNull(cust, "客户不存在！");

        if (StringUtils.isNotBlank(psw)) {
            String basePsw = new String(Base64.encode(psw));
            Assert.equals(psw, basePsw, "密码错误！");
        } else if (StringUtils.isNotBlank(req.getMsgCode())) {
            validMsgCode(mobile, msgCode, MsgSendEnum.LOGIN);
        } else {
            logger.error("非法登陆请求！req={}", req);
            throw new BizException("非法登陆请求！");
        }

        String token = UUIDUtil.getUUID();
        redisClient.setex(CacheKeyConstant.token + mobile, seconds, token);

        LoginResp resp = new LoginResp();
        resp.setToken(token);
        resp.setName(StringUtils.isEmpty(cust.getName()) ? "未设置" : cust.getName());
        return resp;
    }

    @Override
    public boolean sendMsg(String mobile) {
        /*
         * TODO 
         * 1、校验手机号是否存在、状态等
         * 2、生成短信验证码
         * 3、发送短信
         * 4、成功后短信验证码塞入缓存
         */
        return false;
    }

    private void validMsgCode(String mobile, String code, MsgSendEnum msgSendEnum) {

        String cacheKey = null;
        switch (msgSendEnum) {
            case REGIST:
                cacheKey = CacheKeyConstant.registMsg;
                break;
            case LOGIN:
                cacheKey = CacheKeyConstant.loginMsg;
                break;
            case RESET_LOGIN_PSW:
                cacheKey = CacheKeyConstant.resetLoginPsw;
                break;
            case RESET_PAY_PSW:
                cacheKey = CacheKeyConstant.resetPayPsw;
                break;
            default:
                break;
        }
        String cacheMsgCode = redisClient.get(cacheKey + mobile);
        redisClient.del(cacheKey + mobile);
        if (StringUtils.isEmpty(cacheMsgCode) || !cacheMsgCode.equals(code)) {
            throw new BizException("验证码错误！");
        }
    }

}
