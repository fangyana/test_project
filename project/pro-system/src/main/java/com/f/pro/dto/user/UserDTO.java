package com.f.pro.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel
@Data
public class UserDTO implements Serializable {
//    @ApiModelProperty(value = "查询日期,格式yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Integer userId;
    private String username;
    private String realName;
    private String password;
    private Integer deptId;
    private Integer jobId;
    private String phone;
    private String email;
    private String avatar;
    private String lockFlag;
    private String delFlag;
    private List<Integer> roleList;
    private List<Integer> deptList;
    /**
     * 新密码
     */
    private String newPassword;
    private String smsCode;
}
