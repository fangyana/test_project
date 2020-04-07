package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.pro.domain.SysRole;
import com.f.pro.domain.SysUserRole;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    @Override
    int insert(SysUserRole entity);

    @Select("SELECT r.role_name,ur.role_id \n" +
            "FROM (sys_role r LEFT JOIN sys_user_role ur ON r.role_id = ur.role_id ) \n" +
            "LEFT JOIN sys_user u ON u.user_id = ur.user_id WHERE u.user_id = #{userId}")
    List<SysUserRole> selectUserRoleListByUserId(Integer userId);

    @Select("SELECT r.* FROM sys_role r LEFT JOIN sys_user_role ur ON r.role_id = ur.role_id LEFT JOIN sys_user u ON u.user_id = ur.user_id WHERE u.user_id = #{userId} AND r.del_flag = 0")
    List<SysRole> queryUserRoleListByUserId(Integer userId);
}
