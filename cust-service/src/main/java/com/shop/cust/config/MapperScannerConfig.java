//package com.shop.cust.config;
//
//import org.springframework.context.annotation.Bean;
//
//import tk.mybatis.spring.mapper.MapperScannerConfigurer;
//
////@Configuration
////@AutoConfigureAfter(MybatisConfig.class)
//public class MapperScannerConfig {
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        mapperScannerConfigurer.setBasePackage("com.shop.cust.mapper");
//        return mapperScannerConfigurer;
//    }
//}
