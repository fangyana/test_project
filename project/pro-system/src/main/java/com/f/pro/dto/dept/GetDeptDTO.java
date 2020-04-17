package com.f.pro.dto.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author FangYN
 * @Description
 * @Date 2020/03/23
 */
@ApiModel
@Data
public class GetDeptDTO implements Serializable {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "机构id")
    private Integer deptId;
    @ApiModelProperty(value = "机构名")
    private String name;
    @ApiModelProperty(value = "部门类型（1：机构，2：学校）")
    private String deptType;
    @ApiModelProperty(value = "参数不必传")
    private List<Integer> deptList;
    @ApiModelProperty(value = "部门编号")
    private String deptNo;
}
