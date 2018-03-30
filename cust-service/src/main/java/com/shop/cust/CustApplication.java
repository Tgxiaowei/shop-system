package com.shop.cust;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class CustApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CustApplication.class).web(true).run(args);
    }

}