package com.f.pro.common.util;

import java.util.UUID;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 获取uuid
 */
public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
