package com.zzp.springcloud.controller;

import com.zzp.springcloud.entities.CommonResult;
import com.zzp.springcloud.entities.Payment;
import com.zzp.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author zzp
 * @create 2021-02-12
 */
@RestController
@Slf4j
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancer loadBalancer;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/consumer/payment/create")
    public ResponseEntity<CommonResult> create(Payment payment){
        return restTemplate.postForEntity(PAYMENT_URL + "/payment/create",payment, CommonResult.class);
    }

    @GetMapping(value = "/consumer/payment/get/{id}")
    public ResponseEntity<CommonResult> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id,CommonResult.class);
    }

    // 返回对象为响应体中数据转化成的对象，基本上可以理解为josn
    @GetMapping(value = "/consumer/payment/getForObject/{id}")
    public CommonResult<Payment> getForObject(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id,CommonResult.class);
    }

    // 返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、响应状态码，响应体等
    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public Object getForEntity(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            return "404 响应失败";
        }else {
            log.info("*******getStatusCode:{}",responseEntity.getStatusCode());
            log.info("*******getHeaders:{}",responseEntity.getHeaders());
            log.info("*******getBody:{}",responseEntity.getBody());
            return responseEntity.getBody();
        }

    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if(null == instances || 0 >= instances.size()){
            return "无服务";
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri + "/payment/lb",String.class);
    }


    @GetMapping(value = "/consumer/payment/zipkin")
    public String paymentZipkin(){
        return restTemplate.getForObject("http://localhost:8001" + "/payment/zipkin/",String.class);
    }

}
