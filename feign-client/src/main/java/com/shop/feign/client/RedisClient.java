package com.shop.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.base.constant.ApiUrlConstant;

@FeignClient(value = ApiUrlConstant.REDIS)
public interface RedisClient {

    @PostMapping("/set")
    void set(@RequestParam(value = "key") String key, @RequestParam(value = "value") Object value);

    @PostMapping("/setex")
    void setex(@RequestParam(value = "key") String key,
               @RequestParam(value = "seconds") int seconds,
               @RequestParam(value = "value") Object value);

    @PostMapping("/get")
    String get(String key);

    @PostMapping("/getObj")
    <T> T getObj(@RequestParam(value = "key") String key, @RequestParam(value = "t") T t);

    @PostMapping("/del")
    void del(String key);

    @PostMapping("/exists")
    boolean exists(String key);

    @PostMapping("/ttl")
    long ttl(String key);

    @PostMapping("/expire")
    long expire(@RequestParam(value = "key") String key,
                @RequestParam(value = "seconds") int seconds);
}
