package com.shop.cust.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.constant.CacheKeyConstant;
import com.shop.base.enums.CustStatusEnum;
import com.shop.base.enums.MsgSendEnum;
import com.shop.base.exception.BizException;
import com.shop.base.redis.RedisClient;
import com.shop.base.util.Assert;
import com.shop.cust.dao.CustDAO;
import com.shop.cust.dto.req.LoginReq;
import com.shop.cust.dto.req.RegisterReq;
import com.shop.cust.dto.resp.LoginResp;
import com.shop.cust.mapper.CustMapper;
import com.shop.cust.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private CustMapper  custMapper;

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

        return null;
    }

    @Override
    public boolean sendMsg() {
        return false;
    }

    private void validMsgCode(String mobile, String code, MsgSendEnum type) {

        String cacheKey = null;
        switch (type) {
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
        if (StringUtils.isEmpty(cacheMsgCode) || !cacheMsgCode.equals(code)) {
            throw new BizException("验证码错误！");
        }
    }

}
