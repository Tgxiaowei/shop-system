package com.shop.redis.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.base.constant.ApiUrlConstant;
import com.shop.redis.client.RedisClient;

@RestController
@RequestMapping(ApiUrlConstant.REDIS)
public class RedisProvider {

    private static Logger logger = LoggerFactory.getLogger(RedisProvider.class);

    @Autowired
    private RedisClient   redisClient;

    @PostMapping("/set")
    public void set(String key, Object value) {
        logger.info("redis recive set:key={},value={}", key, value);
        redisClient.set(key, value);
    }

    @PostMapping("/setex")
    public void setex(String key, int seconds, Object value) {
        logger.info("redis recive setex:key={},seconds={},value={}", key, seconds, value);
        redisClient.setex(key, seconds, value);
    }

    @PostMapping("/get")
    public String get(String key) {
        logger.info("redis recive get:key={}", key);
        return redisClient.get(key);
    }

    @PostMapping("/getObj")
    public <T> T getObj(String key, T t) {
        logger.info("redis recive get:key={},t={}", key, t);
        return redisClient.getObj(key, t);
    }

    @PostMapping("/del")
    public void del(String key) {
        logger.info("redis recive del:key={}", key);
        redisClient.del(key);
    }

    @PostMapping("/exists")
    public boolean exists(String key) {
        logger.info("redis recive exists:key={}", key);
        return redisClient.exists(key);
    }

    @PostMapping("/ttl")
    public long ttl(String key) {
        logger.info("redis recive ttl:key={}", key);
        return redisClient.ttl(key);
    }

    @PostMapping("/expire")
    public long expire(String key, int seconds) {
        logger.info("redis recive expire:key={},seconds={}", key, seconds);
        return redisClient.expire(key, seconds);
    }

}
