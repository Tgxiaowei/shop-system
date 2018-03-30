package com.shop.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableConfigServer
@SpringBootApplication
@EnableEurekaClient
public class ConfigApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ConfigApplication.class, args);
        new SpringApplicationBuilder(ConfigApplication.class).web(true).run(args);
    }

}