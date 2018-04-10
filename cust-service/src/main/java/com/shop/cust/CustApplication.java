package com.shop.cust;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
//@ComponentScan(basePackageClasses = CustApplication.class)
@ComponentScan(basePackages = "com.shop.feign")
//@EnableFeignClients(basePackages = "com.shop")
// 再试试上面这个扫描com.shop包
//@ComponentScan(basePackages = "com.cloud")
public class CustApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CustApplication.class).web(true).run(args);
    }

}