package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.myhandler.ConsumerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping(value = "/byResource")//Blocked by Sentinel (flow limiting)
    @SentinelResource(value = "byResource", blockHandler = "handelException")//资源名称会走配置的
    public CommonResult byResource() {

        return new CommonResult(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public CommonResult handelException(BlockException exception) {

        return new CommonResult(444, exception.getClass().getCanonicalName() + "服务不可用！！");

    }

    @GetMapping(value = "/rateLimit/byUrl")//Blocked by Sentinel (flow limiting)
    @SentinelResource(value = "byUrl")//报错Whitelabel Error Page
    public CommonResult byUrl() {

        return new CommonResult(200, "按Url限流测试OK", new Payment(2020L, "serial001"));
    }

    //todo 按客户自定义
    @GetMapping(value = "/rateLimit/consumerBlockHandler")
    @SentinelResource(value = "consumerBlockHandler",blockHandlerClass = ConsumerBlockHandler.class,blockHandler = "handlerException2")
    public CommonResult consumerBlockHandler() {

        return new CommonResult(200, "按客户自定义", new Payment(2020L, "serial003"));
    }
}
