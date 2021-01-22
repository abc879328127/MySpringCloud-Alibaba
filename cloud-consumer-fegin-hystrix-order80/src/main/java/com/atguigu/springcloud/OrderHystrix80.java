package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: guojd
 * @date: 2021年01月12日16:02:28
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class OrderHystrix80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrix80.class, args);
    }
}
