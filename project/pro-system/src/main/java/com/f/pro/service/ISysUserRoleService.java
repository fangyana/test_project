package com.f.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysRole;
import com.f.pro.domain.SysUserRole;

import java.util.List;

public interface ISysUserRoleService extends IService<SysUserRole> {
    /**
     * 根据用户id查询用户角色关系
     *
     * @param userId
     * @return
     */
    List<SysUserRole> selectUserRoleListByUserId(Integer userId);

    /**
     * 根据用户id查询用户角色
     *
     * @param userId
     * @return
     */
    List<SysRole> queryUserRoleListByUserId(Integer userId);
}
