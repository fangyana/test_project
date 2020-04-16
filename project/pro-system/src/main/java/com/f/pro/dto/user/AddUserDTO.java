package com.f.pro.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/16
 * @Description: 新增用户对象
 */
@ApiModel
@Data
public class AddUserDTO implements Serializable {
    @ApiModelProperty(value = "登录名", required = true)
    private String username;
    @ApiModelProperty(value = "真实名称")
    private String realName;
    @ApiModelProperty(value = "密码，默认为123456")
    private String password;
    @ApiModelProperty(value = "所属机构id", required = true)
    private Integer deptId;
    @ApiModelProperty(value = "所在岗位id")
    private Integer jobId;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "头像url")
    private String avatar;
    @ApiModelProperty(value = "是否锁定账户 0-正常，1-锁定")
    private String lockFlag;
    @ApiModelProperty(value = "拥有的角色id数组")
    private List<Integer> roleList;
}
