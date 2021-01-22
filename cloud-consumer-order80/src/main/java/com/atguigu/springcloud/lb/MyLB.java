package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @author: guojd
* @date: 2021年01月13日21:51:24
 * 重新轮询负载均衡算法
*/
@Component
public class MyLB implements LoadBalanced {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //负载均衡算法是通过，第几次访问 % 服务的实例 然后来取第几个
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {

        int index = this.getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }

    public final int getAndIncrement() {
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 20000 ? 0 : current + 1;
        } while (!atomicInteger.compareAndSet(current, next));//用于计算并赋值，不重启一直赋值，第二个值更新第一个值
        System.out.println("*****第几次访问，次数:" + next);
        return next;
    }

}
