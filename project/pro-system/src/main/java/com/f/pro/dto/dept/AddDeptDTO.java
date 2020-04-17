package com.f.pro.dto.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class AddDeptDTO {
    @ApiModelProperty(value = "部门名称")
    private String name;
    @ApiModelProperty(value = "上级部门id")
    private Integer parentId;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "省id")
    private String provinceId;
    @ApiModelProperty(value = "市id")
    private String cityId;
    @ApiModelProperty(value = "区/县id")
    private String countyId;
    @ApiModelProperty(value = "部门类型（1：机构，2：学校）")
    private String deptType;
    @ApiModelProperty(value = "部门编号")
    private String deptNo;

}
