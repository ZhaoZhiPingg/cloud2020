package com.zzp.springcloud.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.zzp.springcloud.entities.CommonResult;
import com.zzp.springcloud.entities.Payment;
import com.zzp.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zzp
 * @create 2021-02-10
 */
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String servicePort;

    @Resource
    private DiscoveryClient discoveryClient;


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
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询结果:{}",payment);


        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if(null != payment ){
            return new CommonResult(200,"查询成功,servicePort: " + servicePort,payment);
        }else {
            return new CommonResult(404,"没有对应记录,查询ID: "+id,null);
        }

    }


    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for(String element : services){
            log.info(">>>>>>>(element)服务名称: {}",element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances){
            log.info("InstanceId: " + instance.getInstanceId() + "\t" + "Host: " + instance.getHost() + "\t"
             + "Port: "+ instance.getPort() + "Uri: "+ instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return servicePort;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try
        {
            // 睡眠3秒
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return servicePort;
    }

    @GetMapping(value = "/payment/zipkin")
    public String paymentZipkin(){
        return "******链路追踪********";
    }


}
