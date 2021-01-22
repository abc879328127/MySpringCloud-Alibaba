package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sun.deploy.security.BlockedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("testA")
    public String getTestA(){
        System.out.println("######");
        return "testA";
    }

    @GetMapping("testB")
    public String getTestB(){
        System.out.println(Thread.currentThread().getName()+"###### \t testB");
        return "testB";
    }

    //todo 异常比例
    @GetMapping("testD")
    public String getTestD(){
      /*  try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        int age = 10/0;

        System.out.println(Thread.currentThread().getName()+"###### \t testD \t 异常比例");
        return "testD";
    }

    //todo 异常数
    @GetMapping("testE")
    public String getTestE(){
        int age = 10/0;
        System.out.println(Thread.currentThread().getName()+"###### \t testD \t 异常数");
        return "testE";
    }

    //todo 热点kay  testHotKey
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")//value 唯一标识  blockHandler兜底的方法
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value = "p2",required = false) String p2
                             ){
        int age = 10 / 0 ;
        return "testHotKey";
    }
    public String deal_testHotKey(String p1, String p2, BlockException b){

        return "deal_testHotKey,o(╥﹏╥)o";//默认的方法是Block By sentinel...

    }

}
