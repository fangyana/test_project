package com.f.pro.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.domain.SysRoleDept;
import com.f.pro.mapper.SysRoleDeptMapper;
import com.f.pro.service.ISysRoleDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements ISysRoleDeptService {


    @Override
    public List<SysRoleDept> getRoleDeptIds(int roleId) {
        return baseMapper.selectList(Wrappers.<SysRoleDept>lambdaQuery().select(SysRoleDept::getDeptId).eq(SysRoleDept::getRoleId, roleId));
    }
}
