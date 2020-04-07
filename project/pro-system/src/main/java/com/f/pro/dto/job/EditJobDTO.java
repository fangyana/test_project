package com.f.pro.dto.job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EditJobDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "岗位id", required = true)
    private Integer id;
    @ApiModelProperty(value = "岗位名称")
    private String jobName;
    @ApiModelProperty(value = "所属部门id")
    private Integer deptId;
    @ApiModelProperty(value = "排序")
    private Integer sort;
}

