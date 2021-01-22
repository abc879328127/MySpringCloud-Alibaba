package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.atguigu.springcloud.service.PaymentService;

import javax.annotation.Resource;
import java.time.ZonedDateTime;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {

        int result = paymentService.create(payment);

        log.info("插入结果:" + result);

        if (result > 0) {
            return new CommonResult(200, "插入数据成功,serverPort:"+serverPort, result);
        } else {
            return new CommonResult(444, "插入数据失败,serverPort:"+serverPort);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {

        Payment payment = paymentService.getPaymentById(id);

        if (payment != null) {
            return new CommonResult(200, "查询成功成功,serverPort:"+serverPort, payment);
        } else {
            return new CommonResult(444, "没有对应记录,serverPort:"+serverPort+"查询记录:" + id);
        }
    }


    @GetMapping(value = "/payment/lb")
    public String getPaymentLb() {
        return serverPort;
    }

    // TODO: 2021/1/13 时间格式，路由转发用到 after
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}