package com.shop.redis.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.redis.client.RedisClient;

@RestController
@RequestMapping(value = "redis")
public class RedisProvider {

    private static Logger logger = LoggerFactory.getLogger(RedisProvider.class);

    @Autowired
    private RedisClient   redisClient;

    @PostMapping("/set")
    public void set(@RequestParam(value = "key") String key,
                    @RequestParam(value = "value") Object value) {
        logger.info("redis recive set:key={},value={}", key, value);
        redisClient.set(key, value);
    }

    @PostMapping("/setex")
    public void setex(@RequestParam(value = "key") String key,
                      @RequestParam(value = "seconds") int seconds,
                      @RequestParam(value = "value") Object value) {
        logger.info("redis recive setex:key={},seconds={},value={}", key, seconds, value);
        redisClient.setex(key, seconds, value);
    }

    @PostMapping("/get")
    public String get(@RequestParam(value = "key") String key) {
        logger.info("redis recive get:key={}", key);
        return redisClient.get(key);
    }

    @PostMapping("/getObj")
    public <T> T getObj(@RequestParam(value = "key") String key, @RequestParam(value = "t") T t) {
        logger.info("redis recive get:key={},t={}", key, t);
        return redisClient.getObj(key, t);
    }

    @PostMapping("/del")
    public void del(@RequestParam(value = "key") String key) {
        logger.info("redis recive del:key={}", key);
        redisClient.del(key);
    }

    @PostMapping("/exists")
    public boolean exists(@RequestParam(value = "key") String key) {
        logger.info("redis recive exists:key={}", key);
        return redisClient.exists(key);
    }

    @PostMapping("/ttl")
    public long ttl(@RequestParam(value = "key") String key) {
        logger.info("redis recive ttl:key={}", key);
        return redisClient.ttl(key);
    }

    @PostMapping("/expire")
    public long expire(@RequestParam(value = "key") String key,
                       @RequestParam(value = "seconds") int seconds) {
        logger.info("redis recive expire:key={},seconds={}", key, seconds);
        return redisClient.expire(key, seconds);
    }

}
