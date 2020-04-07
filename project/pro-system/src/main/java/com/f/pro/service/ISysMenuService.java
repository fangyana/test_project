package com.f.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysMenu;
import com.f.pro.dto.menu.EditMenuDTO;

import java.io.Serializable;
import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {
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
     * @return
     */
    List<SysMenu> selectMenuTree(Integer uid);

    /**
     * @Author 李号东
     * @Description 根据父id查询菜单
     * @Date 18:43 2019-05-12
     **/
    SysMenu getMenuById(Integer parentId);

    /**
     * @Description 根据用户id查询权限
     **/
    List<String> findPermsByUserId(Integer userId);
}
