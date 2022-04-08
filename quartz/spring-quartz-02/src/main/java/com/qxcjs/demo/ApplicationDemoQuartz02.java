package com.qxcjs.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.qxcjs.demo.mapper")
public class ApplicationDemoQuartz02 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemoQuartz02.class, args);
    }
}
