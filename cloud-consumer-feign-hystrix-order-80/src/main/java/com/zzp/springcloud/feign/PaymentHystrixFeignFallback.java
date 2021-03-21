package com.zzp.springcloud.feign;

import org.springframework.stereotype.Component;

/**
 * @author zzp
 * @create
 */
@Component
public class PaymentHystrixFeignFallback implements PaymentHystrixFeign {

    @Override
    public String paymentHystrixOk(Integer id) {
        return " fallback >>>>  服务降级 paymentHystrixOk接口";
    }

    @Override
    public String paymentHystrixTimtout(Integer id) {
        return "fallback >>>>  服务降级 paymentHystrixTimtout接口";
    }
}
