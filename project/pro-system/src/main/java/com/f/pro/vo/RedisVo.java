package com.f.pro.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 陈智威
 * @Description
 * @Date 2019/9/9 15:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisVo implements Serializable {

    /**
     * key
     */
    private String key;
    /**
     * value
     */
    private String value;

    /**
     * 过期时间
     */
    private long expireTime;
}
