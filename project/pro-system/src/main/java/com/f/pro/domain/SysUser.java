package com.f.pro.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {
    /**
     * 主键ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实名字
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 岗位ID
     */
    private Integer jobId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 0-正常，1-锁定
     */
    private String lockFlag;

    /**
     * 0-正常，1-删除
     */
    private String delFlag;

    /**
     * 创建者id
     */
    private Integer createBy;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<SysRole> roleList;
    /**
     * 非数据库字段
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * 非数据库字段
     * 岗位名称
     */
    @TableField(exist = false)
    private String jobName;

    @TableField(exist = false)
    private String key;

    /**
     * 非数据库字段
     * 部门类型 0：超级管理机构，1：机构，2：学校
     */
    @TableField(exist = false)
    private String deptType;
}
