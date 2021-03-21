package com.zzp.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zzp
 * @create
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {

        return "---------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + " \t " + " ....testB");
        return "---------testB";
    }

    @GetMapping("/testC")
    public String testC() {
        try
        {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        log.info("....testC  慢调用比例");
        return "---------testC  慢调用比例";
    }

    @GetMapping("/testD")
    public String testD() {
        log.info("....testD  异常比例");
        int a = 9/0;
        return "---------testD 异常比例";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("....testE  异常数");
        int a = 9/0;
//        BlockException System.out.println();
        return "---------testE 异常数";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "test_hotKey",blockHandler = "blockHandler_hotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value= "p2",required = false) String p2) {
        int a = 10/0;

        return "---------testHotKey";
    }
    public String blockHandler_hotKey(String p1, String p2,BlockException exception) {
        return "---------testHotKey的blockHandler 处理。。。。。";
    }

}
