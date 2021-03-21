package com.zzp.springcloud.service.impl;

import com.zzp.springcloud.dao.OrderDao;
import com.zzp.springcloud.domain.Order;
import com.zzp.springcloud.feign.AccountFeign;
import com.zzp.springcloud.feign.StorageFeign;
import com.zzp.springcloud.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zzp
 *
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountFeign accountFeign;
    @Resource
    private StorageFeign storageFeign;

    /**
     * 创建订单-》调用库存服务扣减库存-》调用账户扣减账户余额-》修改订单状态
     * @param order
     */
    @Override
    @GlobalTransactional(name = "zzp-creata-order",rollbackFor = Exception.class)
    public void create(Order order) {

        log.info("----->开始新建订单");
        // 1 新建订单
        orderDao.create(order);

        log.info("----->订单服务调用库存服务，做扣减 --- start");
        // 2 扣减库存
        storageFeign.decrease(order.getProductId(),order.getCount());
        log.info("----->订单服务调用库存服务，做扣减 --- end");

        log.info("----->订单服务调用账户服务，做扣减 --- start");
        // 3 扣减账户
        accountFeign.decrease(order.getUserId(),order.getMoney());
        log.info("----->订单服务调用账户服务，做扣减 --- end");

        // 4 修改订单的状态 订单状态；0 创建中 1 已完成
        log.info("----->修改订单状态 --- start");
        orderDao.update(order.getUserId(),0);
        log.info("----->修改订单状态 --- end");


        log.info("----->下订单结束了，-----> end");

    }


}
