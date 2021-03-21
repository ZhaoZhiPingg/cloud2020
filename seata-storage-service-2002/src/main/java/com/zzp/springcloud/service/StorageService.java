package com.zzp.springcloud.service;


/**
 * @author zzp
 *
 */
public interface StorageService {

    // 扣减库存
    void decrease(Long productId, Integer count);
}
