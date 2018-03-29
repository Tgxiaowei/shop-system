package com.shop.base;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BaseApplication.class)
                    .web(true).run(args);
    }
}
