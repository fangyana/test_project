package com.f.pro.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/16
 * @Description: 分页接口查询
 */
@ApiModel
@Data
public class UserDTO implements Serializable {
    @ApiModelProperty(value = "登录名")
    private String username;
    @ApiModelProperty(value = "真实名称")
    private String realName;
    @ApiModelProperty(value = "所属机构id")
    private Integer deptId;
    @ApiModelProperty(value = "参数不用传")
    private List<Integer> deptList;
}
