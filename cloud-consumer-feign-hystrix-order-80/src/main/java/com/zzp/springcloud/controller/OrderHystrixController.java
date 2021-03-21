package com.zzp.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zzp.springcloud.feign.PaymentHystrixFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zzp
 * @create 2021-02-26
 */
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixFeign paymentHystrixFeign;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    String paymentHystrixOk(@PathVariable("id") Integer id){
        String result = paymentHystrixFeign.paymentHystrixOk(id);
        return result;
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentHystrixTimtoutFallbackMethod",
//            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")})
    @HystrixCommand
    String paymentHystrixTimtout(@PathVariable("id") Integer id){
        int l = 8/0;
        String result = paymentHystrixFeign.paymentHystrixTimtout(id);
        return result;
    }
    public String paymentHystrixTimtoutFallbackMethod(Integer id){
        return "消费者80运行异常，请求8001失败...";
    }

    // 下面是全局fallback方法
    public String paymentGlobalFallbackMethod(){
        return "GlobalFallback异常处理,等待....";
    }
}
