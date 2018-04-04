package com.shop.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.redis.client.RedisClient;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisClient redisClient;

    //    @GetMapping("/set")
    //    public void set() {
    //        redisClient.set("key", "value");
    //        System.out.println(redisClient.get("key"));
    //    }

}
