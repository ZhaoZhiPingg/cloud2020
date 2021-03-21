package com.zzp.springcloud.controller;

import com.zzp.springcloud.entities.CommonResult;
import com.zzp.springcloud.entities.Payment;
import com.zzp.springcloud.feign.PaymentFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zzp
 * @create 2021-02-24
 */
@RestController
@Slf4j
public class OrderFeignController {

    @Resource
    private PaymentFeign paymentFeign;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeign.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //openfeign-ribbon,客户端一般默认等待1秒钟
        return paymentFeign.paymentFeignTimeout();
    }


}
