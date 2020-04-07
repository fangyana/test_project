package com.f.pro.dto.dept;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author FangYN
 * @Description
 * @Date 2020/03/23
 */
@Data
public class GetDeptDTO implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer deptId;

    private String name;

    // 部门类型（1：机构，2：学校）
    private String deptType;

    private List<Integer> deptList;

    //部门编号
    private String deptNo;
}
