package com.zzp.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zzp.springcloud.entities.CommonResult;
import com.zzp.springcloud.entities.Payment;
import com.zzp.springcloud.myhandler.CustomerBlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzp
 * @create
 */
@RestController
@Slf4j
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "by_resource",blockHandler = "handlerException")
    public CommonResult byResource() {
        return new CommonResult(200,"按资源名称限流测试OK",new Payment(200L,"serial-001"));
    }
    public CommonResult handlerException(BlockException exception) {
        return new CommonResult(500,exception.getClass().getCanonicalName() + "\t 服务不可用");
    }


    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "by_url")
    public CommonResult byUrl() {
        return new CommonResult(200,"按url流测试OK",new Payment(200L,"serial-002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customer_blockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler="handlerException2")
    public CommonResult customerBlockHandler() {
        return new CommonResult(200,"按客户自定义流测试OK",new Payment(200L,"serial-003"));
    }

}
