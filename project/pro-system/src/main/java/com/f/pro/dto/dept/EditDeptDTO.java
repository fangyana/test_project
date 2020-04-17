package com.f.pro.dto.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class EditDeptDTO extends AddDeptDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门id")
    private Integer deptId;
}
