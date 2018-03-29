package com.shop.trans;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TransApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TransApplication.class).web(true).run(args);
    }
}