package com.f.pro.dto.job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class EditJobDTO extends AddJobDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位id", required = true)
    private Integer id;
}

