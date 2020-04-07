package com.f.pro.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddDictDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "数据值")
    private String value;
    @ApiModelProperty(value = "标签名")
    private String label;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;
    @ApiModelProperty(value = "备注信息")
    private String remark;
}
