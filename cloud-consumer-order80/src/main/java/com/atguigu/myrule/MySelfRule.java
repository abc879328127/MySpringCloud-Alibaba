package com.atguigu.myrule;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @author: guojd
* @date: 2021年01月09日20:26:29
*/
@Configuration
public class MySelfRule {

    @Bean
    public RandomRule getRandomRule(){
        return new RandomRule();
    }
}
