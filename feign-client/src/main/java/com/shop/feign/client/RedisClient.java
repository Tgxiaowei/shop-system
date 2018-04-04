package com.shop.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.shop.base.constant.ApiUrlConstant;

@FeignClient(value = ApiUrlConstant.REDIS)
public interface RedisClient {

    @PostMapping("/set")
    void set(String key, Object value);

    @PostMapping("/setex")
    void setex(String key, int seconds, Object value);

    @PostMapping("/get")
    String get(String key);

    @PostMapping("/getObj")
    <T> T getObj(String key, T t);

    @PostMapping("/del")
    void del(String key);

    @PostMapping("/exists")
    boolean exists(String key);

    @PostMapping("/ttl")
    long ttl(String key);

    @PostMapping("/expire")
    long expire(String key, int seconds);
}
