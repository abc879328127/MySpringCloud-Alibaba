package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
* @author: guojd
* @date: 2021年01月09日21:27:53
*/
public interface LoadBalanced {

    //自己写的负载均衡方法
    ServiceInstance instance(List<ServiceInstance> serviceInstances);
}
