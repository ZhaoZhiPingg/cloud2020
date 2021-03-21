package com.zzp.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author zzp
 * @create 2021-02-22
 */
public interface LoadBalancer {

    //收集活着的机器
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
