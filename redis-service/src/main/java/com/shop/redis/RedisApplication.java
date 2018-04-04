package com.shop.redis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RedisApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RedisApplication.class).web(true).run(args);
    }

}