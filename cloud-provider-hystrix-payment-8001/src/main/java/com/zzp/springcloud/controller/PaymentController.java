package com.zzp.springcloud.controller;

import com.zzp.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zzp
 * @create 2021-02-25
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String servicePort;

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    public String paymentHystrixOk(@PathVariable("id") Integer id){
        String result = paymentService.paymentHystrixOk(id);
        log.info("******响应结果result: " + result);
        return result;
    }

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    public String paymentHystrixTimtout(@PathVariable("id") Integer id){
        String result = paymentService.paymentHystrixTimtout(id);
        log.info("******响应结果result: " + result);
        return result;
    }

    /**       -------服务熔断--------          */
    @GetMapping(value = "/payment/circuit/{id}")
    public String paymentCircuitbreaker(@PathVariable("id") Integer id){
        return paymentService.paymentCircuitbreaker(id);
    }
}
