package com.zzp.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zzp
 * @create 2021-02-26
 */
@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = PaymentHystrixFeignFallback.class)
public interface PaymentHystrixFeign {

    @GetMapping(value = "/payment/hystrix/ok/{id}")
    String paymentHystrixOk(@PathVariable("id") Integer id);

    @GetMapping(value = "/payment/hystrix/timeout/{id}")
    String paymentHystrixTimtout(@PathVariable("id") Integer id);
}
