package com.zzp.springcloud.service;

import com.zzp.springcloud.domain.Order;

/**
 * @author zzp
 *
 */
public interface OrderService {

    // 1 创建订单
    void create(Order order);

}
