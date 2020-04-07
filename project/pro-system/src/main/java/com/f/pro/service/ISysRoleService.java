package com.f.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysMenu;
import com.f.pro.domain.SysRole;
import com.f.pro.dto.role.RoleDTO;

import java.io.Serializable;
import java.util.List;

public interface ISysRoleService extends IService<SysRole> {

    /**
     * 保存角色和菜单
     *
     * @param dto
     * @return
     */
    boolean saveRoleMenu(RoleDTO dto);

    /**
     * 更新角色和菜单
     *
     * @param dto
     * @return
     */
    boolean updateRoleMenu(RoleDTO dto);

    /**
     * 根据主键删除角色
     *
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 获取角色列表
     *
     * @return
     */
    List<SysRole> selectRoleList(String roleName);

    /**
     * 根据角色id获取菜单
     *
     * @param roleId
     * @return
     */
    List<SysMenu> findMenuListByRoleId(int roleId);

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Integer userId);

    /**
     * 通过角色ID,查询用户是否引用了角色信息
     *
     * @param roleId
     * @return
     */
    Boolean isUserCiteRole(Integer roleId);

}
