package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class CirleBreakerController {

    @Value("${service-url.nacos-user-service}")
    private String serviceUrl;
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback") // 没有配置
//    @SentinelResource(value = "fallback",fallback = "handlerFallback")//fallback只负责运行时异常，服务降级
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler")//只负责sentinel配置控制台异常
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler")
    public CommonResult<Payment> fallback(@PathVariable("id") Long id) {

        CommonResult<Payment> result = restTemplate.getForObject(serviceUrl + "/payment/SQL/" + id, CommonResult.class);
        if (id == 4) {

            throw new IllegalArgumentException("IllegalArgumentException  非法参数异常");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException  改ID没有对应记录，控制住异常");
        }
        return result;
    }
    //todo 配置fallback
    public CommonResult<Payment> handlerFallback(@PathVariable("id") Long id, Throwable e){
        Payment payment = new Payment(id,null);
        return new CommonResult<>(444,"兜底异常，handlerFallback，，"+e.getMessage(),payment);
    }

    //todo 配置blockHandler
    public CommonResult<Payment> blockHandler(@PathVariable("id") Long id, BlockException exception){
        Payment payment = new Payment(id,null);
        return new CommonResult<>(445,"blockHandler-sentinel限流，无此流水：blockException***"+exception.getMessage(),payment);
    }

    @Resource
    private PaymentService paymentService;
    //todo openfeign测试
    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){

        return paymentService.paymentSQL(id);
    }
}