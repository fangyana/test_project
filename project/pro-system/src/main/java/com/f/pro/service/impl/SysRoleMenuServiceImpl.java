package com.f.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.domain.SysRoleMenu;
import com.f.pro.mapper.SysRoleMenuMapper;
import com.f.pro.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    @Override
    public List<Integer> getMenuIdByUserId(Integer userId) {
        return baseMapper.getMenuIdByUserId(userId);
    }

    @Override
    public Boolean isBindingRole(Serializable menuId) {
        return baseMapper.isBindingRole(menuId);
    }
}
