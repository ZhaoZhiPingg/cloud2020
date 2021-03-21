package com.zzp.springcloud.service;

import com.zzp.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author zzp
 * @create 2021-02-10
 */
public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
