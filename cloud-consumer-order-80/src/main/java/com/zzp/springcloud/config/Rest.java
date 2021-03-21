package com.zzp.springcloud.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public interface Rest {

    /**
     * chose one alive server from lb.allServers or
     * lb.upServers according to key
     *
     * @return choosen Server object. NULL is returned if none
     *  server is alailable
     */
    Server choose(Object var1);

    void setLoadBalancer(ILoadBalancer var1);

    ILoadBalancer getLoadBalancer();


}
