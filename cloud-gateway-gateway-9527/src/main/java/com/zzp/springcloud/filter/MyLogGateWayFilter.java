package com.zzp.springcloud.filter;

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
 * @author zzp
 * @create
 */
//@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("*********全局过滤器：时间" + new Date());
        //获得请求参数username属性
        String uname = exchange.getRequest().getQueryParams().getFirst("username");
        //如果不包含该属性，则过滤器对请求进行拦截
        if (uname == null) {
            log.info("****************用户名为null，错误");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * 加载过滤器的顺序 数字越小 优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
