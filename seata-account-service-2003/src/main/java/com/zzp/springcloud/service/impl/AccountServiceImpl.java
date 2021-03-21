package com.zzp.springcloud.service.impl;

import com.zzp.springcloud.dao.AccountDao;
import com.zzp.springcloud.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;


/**
 * @author zzp
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        LOGGER.info("----->account-service中扣减余额--start");
//        // 暂停几秒设置
//        try
//        {
//            TimeUnit.SECONDS.sleep(20);
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
        accountDao.decrease(userId,money);
        LOGGER.info("----->account-service中扣减余额--end");
    }

}
