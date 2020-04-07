package com.f.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysRoleMenu;

import java.io.Serializable;
import java.util.List;

public interface ISysRoleMenuService extends IService<SysRoleMenu> {
    List<Integer> getMenuIdByUserId(Integer userId);

    /**
     * 判断菜单id是否被角色引用绑定
     *
     * @param menuId
     * @return
     */
    Boolean isBindingRole(Serializable menuId);
}
