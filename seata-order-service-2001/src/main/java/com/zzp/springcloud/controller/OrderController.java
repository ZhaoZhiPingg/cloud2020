package com.zzp.springcloud.controller;

import com.zzp.springcloud.config.IdGeneratorSnowflake;
import com.zzp.springcloud.domain.CommonResult;
import com.zzp.springcloud.domain.Order;
import com.zzp.springcloud.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zzp
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping(value = "/order/create")
    public CommonResult create(Order order){
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }

    @Resource
    private IdGeneratorSnowflake idGeneratorSnowflake;
    @GetMapping(value = "/snowflake")
    public String snowflake(){

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for(int i = 0; i <= 20; i ++){
            threadPool.submit(()->{
                System.out.println(idGeneratorSnowflake.snowflake());
            });
        }

        threadPool.shutdown();

       return "完成 ---";
    }



}
