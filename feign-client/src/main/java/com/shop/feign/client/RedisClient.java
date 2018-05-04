package com.shop.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.base.constant.ApiUrlConstant;

@FeignClient(value = ApiUrlConstant.REDIS)
public interface RedisClient {

    @PostMapping("/redis/set")
    void set(@RequestParam(value = "key") String key, @RequestParam(value = "value") Object value);

    @PostMapping("/redis/setex")
    void setex(@RequestParam(value = "key") String key,
               @RequestParam(value = "seconds") int seconds,
               @RequestParam(value = "value") Object value);

    @PostMapping("/redis/get")
    String get(@RequestParam(value = "key") String key);

    @PostMapping("/redis/getObj")
    <T> T getObj(@RequestParam(value = "key") String key, @RequestParam(value = "t") T t);

    @PostMapping("/redis/del")
    void del(@RequestParam(value = "key") String key);

    @PostMapping("/redis/exists")
    boolean exists(@RequestParam(value = "key") String key);

    @PostMapping("/redis/ttl")
    long ttl(@RequestParam(value = "key") String key);

    @PostMapping("/redis/expire")
    long expire(@RequestParam(value = "key") String key,
                @RequestParam(value = "seconds") int seconds);
}
