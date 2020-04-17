package com.f.pro.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: FangYN
 * @Date: 2020/4/16
 * @Description: 编辑用户对象
 */
@ApiModel
@Data
public class EditUserDTO extends AddUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id", required = true)
    private Integer userId;
    @ApiModelProperty(value = "密码")
    private String password;
}
