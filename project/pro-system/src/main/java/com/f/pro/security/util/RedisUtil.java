package com.f.pro.security.util;

import com.alibaba.fastjson.JSON;
import com.f.pro.vo.RedisVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 缓存工具类
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 获取所有key value
     *
     * @return
     */
    public List<RedisVo> getAll() {
        List<RedisVo> redisList = new ArrayList<>();
        // 获取所有key
        Set<Object> keys = redisTemplate.keys("*");
        if (keys != null) {
            redisList = keys.stream().map(i -> new RedisVo((String) i, JSON.toJSONString(redisTemplate.opsForValue().get(i)), redisTemplate.getExpire(i))).collect(Collectors.toList());
        }
        return redisList;
    }

    /**
     * 通过key删除value
     *
     * @param keys
     * @return
     */
    public boolean removeKey(List<String> keys) {
        return redisTemplate.delete(keys);
    }


}
