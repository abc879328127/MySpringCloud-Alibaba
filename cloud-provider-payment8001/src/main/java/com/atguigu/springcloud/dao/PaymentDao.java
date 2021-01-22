package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author: guojd
* @date: 2021/1/7
*/
@Mapper
public interface PaymentDao {

    //写操作
    public int create(Payment payment);
    //读操作
    public Payment getPaymentById(@Param("id") Long id);
}
