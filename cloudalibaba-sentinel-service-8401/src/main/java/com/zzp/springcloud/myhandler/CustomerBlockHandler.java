package com.zzp.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zzp.springcloud.entities.CommonResult;

/**
 * @author zzp
 * @create
 */
public class CustomerBlockHandler {

    //这里方法必须是 static


    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(500,"按客户自定义,异常 global handlerException----1");
    }

    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(500,"按客户自定义,异常 global handlerException-----2");
    }
}
