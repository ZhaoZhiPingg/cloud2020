package com.zzp.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzp
 * @create
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @Value("${server.port}")
    private String servicePort;

    @GetMapping("/configInfo")
    public String getConfigInfo() {
        return "port: " + servicePort + "\t " + configInfo;
    }

}
