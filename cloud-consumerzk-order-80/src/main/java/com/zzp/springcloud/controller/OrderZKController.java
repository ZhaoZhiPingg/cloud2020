package com.zzp.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author zzp
 * @create 2021-02-15
 */
@RestController
@Slf4j
public class OrderZKController {

    public static final String PAYMENT_URL = "http://cloud-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/zk")
    public String paymentInfo(){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/zk",String.class);
    }

}
