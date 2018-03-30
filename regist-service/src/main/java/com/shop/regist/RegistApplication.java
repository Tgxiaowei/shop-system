package com.shop.regist;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RegistApplication.class)
                    .web(true).run(args);
    }
}