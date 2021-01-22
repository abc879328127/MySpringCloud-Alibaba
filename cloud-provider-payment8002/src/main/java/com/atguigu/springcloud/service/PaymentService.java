package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    //写操作
    public int create(Payment payment);
    //读操作
    public Payment getPaymentById(@Param("id") Long id);
}
