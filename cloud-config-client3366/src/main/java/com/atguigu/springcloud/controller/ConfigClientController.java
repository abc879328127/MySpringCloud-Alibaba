package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope//配置中心 动态更新
@Slf4j
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;//本地配置文件中没有，在配置中心github上面有config.info

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/configInfo")
    public String getConfigInfo(){

        return "serverPort:  "+serverPort+"\t\n\n configInfo:  "+configInfo;
    }
}
