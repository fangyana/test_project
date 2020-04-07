package com.f.pro.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.common.constant.MenuConstant;
import com.f.pro.common.exception.BaseException;
import com.f.pro.domain.SysMenu;
import com.f.pro.dto.menu.EditMenuDTO;
import com.f.pro.mapper.SysMenuMapper;
import com.f.pro.security.util.ProUtil;
import com.f.pro.service.ISysMenuService;
import com.f.pro.service.ISysRoleMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private ISysRoleMenuService roleMenuService;

    @Override
    public boolean save(SysMenu sysMenu) {
        // 菜单校验
        verifyForm(sysMenu);
        return super.save(sysMenu);
    }

    @Override
    public boolean updateMenuById(EditMenuDTO dto) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(dto, sysMenu);
        // 菜单校验
        verifyForm(sysMenu);
        return this.updateById(sysMenu);
    }

    @Override
    public List<SysMenu> selectMenuTree(Integer uid) {

        LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = Wrappers.<SysMenu>query().lambda();
        sysMenuLambdaQueryWrapper.select(SysMenu::getMenuId, SysMenu::getName, SysMenu::getPerms, SysMenu::getPath, SysMenu::getParentId, SysMenu::getComponent, SysMenu::getIsFrame, SysMenu::getIcon, SysMenu::getSort, SysMenu::getType, SysMenu::getDelFlag)
                .eq(SysMenu::getDelFlag, "0");
        // 所有人有权限看到 只是没有权限操作而已 暂定这样
        if (uid != 0) {
            List<Integer> menuIdList = roleMenuService.getMenuIdByUserId(uid);
            sysMenuLambdaQueryWrapper.in(SysMenu::getMenuId, menuIdList);
        }
        List<SysMenu> sysMenus = new ArrayList<>();
        List<SysMenu> menus = baseMapper.selectList(sysMenuLambdaQueryWrapper);
        menus.forEach(menu -> {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                if (ProUtil.exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
            }
        });
        sysMenus.sort((o1, o2) -> o1.getSort().compareTo(o2.getSort()));
        ProUtil.findChildren(sysMenus, menus, 0);
        return sysMenus;
    }

    @Override
    public SysMenu getMenuById(Integer parentId) {
        return baseMapper.selectOne(Wrappers.<SysMenu>lambdaQuery().select(SysMenu::getType).eq(SysMenu::getMenuId, parentId));
    }

    @Override
    public List<String> findPermsByUserId(Integer userId) {
        return baseMapper.findPermsByUserId(userId);
    }

    @Override
    public boolean removeMenuById(Serializable id) {
        List<Integer> idList =
                this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id).eq(SysMenu::getDelFlag, "0")).stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(idList))
            throw new BaseException("菜单含有下级不能删除");
        // 判断菜单是否被角色表引用绑定，绑定的不允许删除，提示先解除绑定
        if (roleMenuService.isBindingRole(id))
            throw new BaseException("该菜单已被相关角色绑定, 请先解除角色引用");
        SysMenu entity = new SysMenu();
        entity.setMenuId(id.hashCode());
        entity.setDelFlag("1");
        return super.saveOrUpdate(entity);
//        return this.removeById(id);
    }

    /**
     * 验证菜单参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        //上级菜单类型
        int parentType = MenuConstant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = getMenuById(menu.getParentId());
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (menu.getType() == MenuConstant.MenuType.CATALOG.getValue() ||
                menu.getType() == MenuConstant.MenuType.MENU.getValue()) {
            if (parentType != MenuConstant.MenuType.CATALOG.getValue()) {
                throw new BaseException("上级菜单只能为目录类型");
            }
            return;
        }
        //按钮
        if (menu.getType() == MenuConstant.MenuType.BUTTON.getValue()) {
            if (parentType != MenuConstant.MenuType.MENU.getValue()) {
                throw new BaseException("上级菜单只能为菜单类型");
            }
        }
    }
}
