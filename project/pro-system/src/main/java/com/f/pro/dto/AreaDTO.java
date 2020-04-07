package com.f.pro.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AreaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    public static Integer PROVINCE = 0;
    public static Integer CITY = 1;
    public static Integer COUNTY = 2;

    /**
     * 类型 0省份 1市 2区/县
     */
    @ApiModelProperty(value = "类型 0-省份 1-市 2-区/县")
    private Integer type;

    /**
     * 上级地区id
     */
    @ApiModelProperty(value = "上级地区id")
    private String parentId;

}
