package com.f.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.domain.SysRole;
import com.f.pro.domain.SysUserRole;
import com.f.pro.mapper.SysUserRoleMapper;
import com.f.pro.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Override
    public boolean save(SysUserRole entity) {
        return super.save(entity);
    }


    @Override
    public List<SysUserRole> selectUserRoleListByUserId(Integer userId) {
        return baseMapper.selectUserRoleListByUserId(userId);
    }

    @Override
    public List<SysRole> queryUserRoleListByUserId(Integer userId) {
        return baseMapper.queryUserRoleListByUserId(userId);
    }
}
