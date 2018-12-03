package com.shop.cust.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.base.dto.Result;
import com.shop.cust.dto.req.LoginReq;
import com.shop.cust.dto.req.RegisterReq;
import com.shop.cust.dto.resp.LoginResp;
import com.shop.cust.service.LoginService;
import com.shop.feign.client.RedisClient;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService  loginService;

    @Autowired
    private RedisClient   redisClient;

    @PostMapping("/register")
    public Result<LoginResp> register(@RequestBody RegisterReq req) {

        logger.info("register，req={}", req);
        LoginResp resp = loginService.register(req);
        logger.info("register，resp={}", resp);

        return Result.ok(resp);
    }

    @PostMapping("/login")
    public Result<LoginResp> login(@RequestBody LoginReq req) {

        logger.info("login，req={}", req);
        LoginResp resp = loginService.login(req);
        logger.info("login，resp={}", resp);

        return Result.ok(resp);
    }

    @RequestMapping("/test")
    public Result<Boolean> test(@RequestBody List<LoginReq> list) {
        System.out.println(list);
        return Result.ok(true);
    }

}
