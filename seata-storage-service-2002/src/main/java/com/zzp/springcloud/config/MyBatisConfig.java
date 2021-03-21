package com.zzp.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzp
 *
 */
@Configuration
@MapperScan({"com.zzp.springcloud.dao"})
public class MyBatisConfig {
}
