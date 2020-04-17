package com.f.pro.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class EditRoleDTO extends AddRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
