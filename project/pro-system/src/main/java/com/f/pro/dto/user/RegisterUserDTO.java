package com.f.pro.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: FangYN
 * @Date: 2020/4/16
 * @Description: 用户注册
 */
@ApiModel
@Data
public class RegisterUserDTO implements Serializable {
    @ApiModelProperty(value = "登录名", required = true)
    private String username;
    @ApiModelProperty(value = "真实名称")
    private String realName;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "所属机构id")
    private Integer deptId;
    @ApiModelProperty(value = "所在岗位id")
    private Integer jobId;
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "头像url")
    private String avatar;
    //    @ApiModelProperty(value = "拥有的角色id数组")
//    private List<Integer> roleList;
    @ApiModelProperty(value = "短信验证码")
    private String smsCode;
}
