package com.shop.cust.service;

import com.shop.cust.dto.req.LoginReq;
import com.shop.cust.dto.req.RegisterReq;
import com.shop.cust.dto.resp.LoginResp;

public interface LoginService {

    LoginResp register(RegisterReq req);

    LoginResp login(LoginReq req);

    boolean sendMsg(String mobile);

}
