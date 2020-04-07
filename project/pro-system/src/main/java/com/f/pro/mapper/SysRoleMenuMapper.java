package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.pro.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    @Select("SELECT rm.menu_id FROM sys_role_menu rm,sys_user_role ur,sys_user u WHERE u.user_id = #{userId} AND u.user_id = ur.user_id AND rm.role_id = ur.role_id")
    List<Integer> getMenuIdByUserId(Integer userId);

    @Select("SELECT count(1) FROM sys_role_menu where menu_id = #{menuId}")
    Boolean isBindingRole(Serializable menuId);
}
