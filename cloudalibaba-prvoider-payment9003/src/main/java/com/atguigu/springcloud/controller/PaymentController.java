package com.atguigu.springcloud.controller;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<Long, Payment>() {
        {
            put(1L, new Payment(1L, "dba0575d414d4aab88d27606a799223f"));
            put(2L, new Payment(2L, "72c87794306b46019b6d1a4d40ebf965"));
            put(3L, new Payment(3L, "ed78f93e76bc4dd59cc9133bcd7adeec"));
        }
    };

    @GetMapping(value = "/payment/SQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {

        Payment payment = hashMap.get(id);
        return new CommonResult(200, serverPort, payment);

    }
}
