package com.shop.cust.service;

import com.shop.cust.dto.req.LoginReq;
import com.shop.cust.dto.req.RegisterReq;
import com.shop.cust.dto.resp.LoginResp;

public interface LoginService {

    /**
     * 注册并登陆
     */
    LoginResp register(RegisterReq req);

    /**
     * 登陆
     */
    LoginResp login(LoginReq req);

    /**
     * 发送短信验证码
     */
    boolean sendMsg(String mobile, int type);

}
