package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置类，controller  rest方式访问
 * @author: guojd
 * @date: 2021年01月14日15:24:02
 */
@RestController
@RefreshScope//具备动态刷新
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
