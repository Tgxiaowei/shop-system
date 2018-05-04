package com.shop.cust;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = "com.shop")
@EnableFeignClients(basePackages = "com.shop.feign.client")
//@MapperScan(basePackages = "com.shop.cust.mapper")
public class CustApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CustApplication.class).web(true).run(args);
    }

}