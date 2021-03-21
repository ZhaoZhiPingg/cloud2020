package com.zzp.springcloud.service.impl;

import com.zzp.springcloud.dao.PaymentDao;
import com.zzp.springcloud.entities.Payment;
import com.zzp.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zzp
 * @create 2021-02-10
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
