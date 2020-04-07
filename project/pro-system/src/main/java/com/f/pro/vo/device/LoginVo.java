package com.f.pro.vo.device;

import lombok.Data;

/**
 * @Description 设备登录返回Vo类
 * @Author FangYN
 * @Date 2020/1/13
 **/
@Data
public class LoginVo {
    private Integer userId;
    private Integer deptId;
    private String deptName;
}
