package com.atguigu.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 自定义日志过滤器
 * @author: guojd
 * @date: 2021年01月14日10:06:59
 */
@Component
@Slf4j
public class MyLogGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("*****come in 全局过滤器"+new Date());
        //类似spring中的httprequest
        //获取request中的参数  名字为num的
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if(null == uname){
            log.info(">>>>用户名为空,非法用户！！o(╥﹏╥)o");
            //如果为空 返回状态码
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        //如果不为空则放行，继续传给filter中的exchange  交给下一个filter
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
