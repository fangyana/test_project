package com.f.pro.dto.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class AddJobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位名称", required = true)
    private String jobName;
    @ApiModelProperty(value = "所属部门id", required = true)
    private Integer deptId;
    @ApiModelProperty(value = "排序")
    private Integer sort;
}

