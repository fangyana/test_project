package com.f.pro.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dept")
public class SysDept {
    /**
     * 部门主键ID
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;

    /**
     * 部门名称
     */
    private String name;


    /**
     * 上级部门
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private String delFlag;

    /**
     * 省
     */
    private String provinceId;

    /**
     * 市
     */
    private String cityId;

    /**
     * 区/县
     */
    private String countyId;

    /**
     * 部门类型（0：超级管理机构，1：机构，2：学校）
     */
    private String deptType;

    /**
     * 机构编号
     */
    private String deptNo;

    /**
     * 创建者id
     */
    private Integer createBy;

    /**
     * 非数据库字段
     * 上级部门
     */
    @TableField(exist = false)
    private String parentName;
    /**
     * 非数据库字段
     * 等级
     */
    @TableField(exist = false)
    private Integer level;

    /**
     * 非数据库字段
     * 子部门
     */
    @TableField(exist = false)
    private List<SysDept> children;

    /**
     * 省
     */
    @TableField(exist = false)
    private String province;

    /**
     * 市
     */
    @TableField(exist = false)
    private String city;

    /**
     * 区/县
     */
    @TableField(exist = false)
    private String county;
}
