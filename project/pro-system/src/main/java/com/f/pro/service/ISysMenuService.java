package com.f.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysMenu;
import com.f.pro.dto.menu.AddMenuDTO;
import com.f.pro.dto.menu.EditMenuDTO;

import java.io.Serializable;
import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {

    boolean save(AddMenuDTO dto);

    /**
     * 更新菜单信息
     *
     * @param dto
     * @return
     */
    boolean updateMenuById(EditMenuDTO dto);

    /**
     * 删除菜单信息
     *
     * @param id
     * @return
     */
    boolean removeMenuById(Serializable id);

    /**
     * 根据用户id查找菜单树
     *
     * @param uid
     * @return
     */
    List<SysMenu> selectMenuTree(Integer uid);

    /**
     * 根据父id查询菜单
     *
     * @param parentId
     * @return
     */
    SysMenu getMenuById(Integer parentId);

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    List<String> findPermsByUserId(Integer userId);
}
