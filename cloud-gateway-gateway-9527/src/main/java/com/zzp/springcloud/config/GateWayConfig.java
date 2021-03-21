package com.zzp.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzp
 * @create
 */
@Configuration
public class GateWayConfig {

    /**
     * 配置了一个id为pathRoute1的路由规则，
     * 当访问地址 http://localhost:9527/guonei时会自动转发到地址： http://news.baidu.com/guonei
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("pathRoute1",
                r -> r.path("/guonei")
                        .uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }
    @Bean
    public RouteLocator customRouteLocator1(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
        builder.route("pathRoute2",r->r.path("/sports")
                .uri("http://news.baidu.com/sports")).build();
        return builder.build();
    }

}
