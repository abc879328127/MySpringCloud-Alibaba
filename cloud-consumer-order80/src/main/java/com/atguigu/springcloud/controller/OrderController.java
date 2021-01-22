package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.LoadBalanced;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class OrderController {

    //    public static final String PAYMENT_URL = "http://localhost:8001/";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE/";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalanced loadBalanced;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {

        return restTemplate.postForObject(PAYMENT_URL + "payment/create", payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {

//        return restTemplate.getForObject(PAYMENT_URL+"payment/get/"+id,CommonResult.class);
        return restTemplate.getForObject(PAYMENT_URL + "payment/get/" + id, CommonResult.class);
    }
    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id) {

        ResponseEntity<CommonResult> forEntity = restTemplate.getForEntity(PAYMENT_URL + "payment/get/" + id, CommonResult.class);
        if(forEntity.getStatusCode().is2xxSuccessful()){
            log.info(forEntity.getHeaders().toString());
            return forEntity.getBody();
        }else {
            return new CommonResult<Payment>(444,"操作失败");
        }
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getLB(){
        //获取所有服务提供者实例
        List<ServiceInstance> cloud_payment_service = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        //进行自己写的轮询计算
        ServiceInstance instance = loadBalanced.instance(cloud_payment_service);
        URI uri = instance.getUri();
        String forObject = restTemplate.getForObject(uri + "/payment/lb", String.class);
        return forObject;
    }

    //todo 测试zipkin 链路调用
    @GetMapping(value = "/consumer/payment/zipkin")
    public String paymentZipkin(){
       return restTemplate.getForObject(PAYMENT_URL + "payment/zipkin/", String.class);
    }

}
