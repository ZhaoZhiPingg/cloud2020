package com.zzp.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzp
 * @create 2021-02-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

    /**
     * 自增id
     */
    private Long id;

    private String serial;

}
