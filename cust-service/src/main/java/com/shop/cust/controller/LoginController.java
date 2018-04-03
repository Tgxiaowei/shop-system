package com.shop.cust.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.base.dto.Result;
import com.shop.cust.dto.req.LoginReq;
import com.shop.cust.dto.resp.LoginResp;
import com.shop.cust.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService  loginService;

    public Result<LoginResp> login(@RequestBody @Valid LoginReq req, BindingResult bindingResult) {

        logger.info("客户登陆，req={}", req);
        LoginResp resp = loginService.login(req);
        logger.info("客户{}登陆结果，resp={}", req.getMobile(), resp);

        return Result.success(resp);
    }

}
