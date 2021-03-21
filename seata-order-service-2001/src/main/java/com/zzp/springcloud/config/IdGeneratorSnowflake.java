package com.zzp.springcloud.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class IdGeneratorSnowflake {

    private long workerId = 0;
    private long datacenterId = 1;
    private Snowflake snowflake = IdUtil.createSnowflake(workerId,datacenterId);

    /**
     * 加载初始化数据  @PostConstruct
     */
    @PostConstruct
    public void init(){
        try
        {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的workerId: {}",workerId);
        }catch (Exception e){
            log.warn("当前机器的workerId获取失败：{}",e);
            workerId = NetUtil.getLocalhost().hashCode();
            log.info("当前机器的workerId.NetUtil.getLocalhost().hashCode(): {}",workerId);
        }
    }

    public synchronized long snowflake(){
        return snowflake.nextId();
    }

    /**
     * 指定机器位数
     * @param workerId 0到31
     * @param datacenterId 0到31
     * @return
     */
    public synchronized long snowflake(long workerId, long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workerId,datacenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        final long snowflake = new IdGeneratorSnowflake().snowflake();
        System.out.println(snowflake + "\t" + String.valueOf(snowflake).length());
    }
}
