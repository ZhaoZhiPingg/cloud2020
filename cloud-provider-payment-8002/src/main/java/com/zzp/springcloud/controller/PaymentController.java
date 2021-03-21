package com.zzp.springcloud.controller;

import com.zzp.springcloud.entities.CommonResult;
import com.zzp.springcloud.entities.Payment;
import com.zzp.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zzp
 * @create 2021-02-14
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String servicePort;


    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody  Payment payment){
        int count = paymentService.create(payment);
        log.info("*******插入结果:{}",count);
        if(0 < count ){
            return new CommonResult(200,"插入数据库成功,servicePort: " + servicePort,count);
        }else {
            return new CommonResult(404,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询结果:{}",payment);
        if(null != payment ){
            return new CommonResult(200,"查询成功,servicePort: " + servicePort,payment);
        }else {
            return new CommonResult(404,"没有对应记录,查询ID: "+id,null);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return servicePort;
    }

}
