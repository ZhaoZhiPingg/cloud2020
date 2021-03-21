package com.zzp.springcloud.controller;

import com.zzp.springcloud.entities.CommonResult;
import com.zzp.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author zzp
 * @create
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    // 这里为了偷懒 就不连接数据库了
    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L,"zzp001"));
        hashMap.put(2L,new Payment(2L,"zzp002"));
        hashMap.put(3L,new Payment(3L,"zzp003"));
    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult(200,"from mysql,serverPort: "+ serverPort,payment);
        return result;
    }


}
