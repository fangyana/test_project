package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.pro.domain.SysMenu;
import com.f.pro.domain.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {


    @Insert("insert into sys_role (role_name,role_code,role_desc,ds_type,ds_scope,create_by) values (#{roleName}, #{roleCode},#{roleDesc},#{dsType},#{dsScope},#{createBy})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId", keyColumn = "role_id")
    Boolean insertRole(SysRole sysRole);

    /**
     * @param roleId
     * @return
     */
    @Select("select m.menu_id,m.name,m.type,m.parent_id,m.sort,m.perms from sys_menu m, sys_role_menu rm where rm.role_id = #{roleId} and m.menu_id = rm.menu_id")
    List<SysMenu> findMenuListByRoleId(int roleId);

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    @Select("SELECT r.* FROM sys_role r, sys_user_role ur WHERE r.role_id = ur.role_id AND r.del_flag = 0 and  ur.user_id IN (#{userId})")
    List<SysRole> listRolesByUserId(Integer userId);

    /**
     * 通过角色id，查询是否被用户引用该角色信息
     *
     * @param roleId
     * @return
     */
    @Select("SELECT count(1) FROM sys_user_role ur WHERE ur.role_id=#{roleId}")
    Boolean isUserCiteRole(Integer roleId);
}
