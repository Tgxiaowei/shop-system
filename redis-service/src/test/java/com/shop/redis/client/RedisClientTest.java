package com.shop.redis.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shop.redis.RedisApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisClientTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void mytest() {
        redisClient.set("key", "value");
        System.out.println(redisClient.get("key"));
    }

}
