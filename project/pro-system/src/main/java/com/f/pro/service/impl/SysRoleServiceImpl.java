package com.f.pro.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.data.strategy.DataScopeContext;
import com.f.pro.domain.SysMenu;
import com.f.pro.domain.SysRole;
import com.f.pro.domain.SysRoleDept;
import com.f.pro.domain.SysRoleMenu;
import com.f.pro.dto.role.AddRoleDTO;
import com.f.pro.dto.role.EditRoleDTO;
import com.f.pro.mapper.SysRoleMapper;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysRoleDeptService;
import com.f.pro.service.ISysRoleMenuService;
import com.f.pro.service.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private ISysRoleMenuService roleMenuService;
    @Resource
    private ISysRoleDeptService roleDeptService;
    @Autowired
    private DataScopeContext dataScopeContext;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRoleMenu(AddRoleDTO dto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        sysRole.setCreateBy(SecurityUtil.getUser().getUserId().toString());
        // 根据数据权限范围查询部门ids
        StringJoiner dsScope = new StringJoiner(",");
        List<Integer> ids = dataScopeContext.getDeptIdsForDataScope(dto, dto.getDsType());
        ids.forEach(integer -> dsScope.add(Integer.toString(integer)));
        sysRole.setDsScope(dsScope.toString());
        sysRole.setCreateBy(SecurityUtil.getUser().getUserId().toString());
        baseMapper.insertRole(sysRole);

        Integer roleId = sysRole.getRoleId();
        //维护角色菜单
        List<SysRoleMenu> roleMenus = dto.getRoleMenus();
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            List<SysRoleMenu> rms = roleMenus.stream().map(sysRoleMenu -> {
                SysRoleMenu roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(sysRoleMenu.getMenuId());
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuService.saveBatch(rms);
        }
        // 维护角色部门权限
        // 根据数据权限范围查询部门ids
        this.saveRoleAndDeptId(ids, roleId);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleMenu(EditRoleDTO dto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        // 移除原有绑定关系
        roleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, sysRole.getRoleId()));
        roleDeptService.remove(Wrappers.<SysRoleDept>query().lambda().eq(SysRoleDept::getRoleId, sysRole.getRoleId()));
        // 保存新的角色菜单信息
        List<SysRoleMenu> roleMenus = dto.getRoleMenus();
        if (CollectionUtil.isNotEmpty(roleMenus))
            roleMenuService.saveBatch(roleMenus);

        // 根据数据权限范围查询部门ids
        StringJoiner dsScope = new StringJoiner(",");
        List<Integer> ids = dataScopeContext.getDeptIdsForDataScope(dto, dto.getDsType());
        ids.forEach(integer -> dsScope.add(Integer.toString(integer)));
        this.saveRoleAndDeptId(ids, dto.getRoleId());
        sysRole.setDsScope(dsScope.toString());
        baseMapper.updateById(sysRole);
        return true;
    }

    // 保存角色和角色拥有的机构关联表信息
    private void saveRoleAndDeptId(List<Integer> deptIdList, Integer roleId) {
        if (CollectionUtil.isEmpty(deptIdList)) return;
        List<SysRoleDept> roleDeptList = deptIdList.stream().map(integer -> {
            SysRoleDept sysRoleDept = new SysRoleDept();
            sysRoleDept.setDeptId(integer);
            sysRoleDept.setRoleId(roleId);
            return sysRoleDept;
        }).collect(Collectors.toList());
        roleDeptService.saveBatch(roleDeptList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        // 判断角色是否被用户引用
        if (baseMapper.isUserCiteRole(id.hashCode())) {// 逻辑删除
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(id.hashCode());
            sysRole.setDelFlag("1");
            int i = baseMapper.updateById(sysRole);
            return i == 0 ? false : true;
        }
        // 物理删除
        roleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, id));
        roleDeptService.remove(Wrappers.<SysRoleDept>query().lambda().eq(SysRoleDept::getRoleId, id));
        return super.removeById(id);
    }

    @Override
    public List<SysRole> selectRoleList(String roleName) {
        LambdaQueryWrapper<SysRole> sysRoleLambdaQueryWrapper = Wrappers.<SysRole>lambdaQuery().eq(SysRole::getDelFlag, "0");
        if (StrUtil.isNotEmpty(roleName)) {
            sysRoleLambdaQueryWrapper.like(SysRole::getRoleName, roleName);
        }
        List<SysRole> sysRoles = baseMapper.selectList(sysRoleLambdaQueryWrapper);
        return sysRoles.stream().peek(sysRole ->
                sysRole.setRoleDepts(roleDeptService.getRoleDeptIds(sysRole.getRoleId()).stream().map(SysRoleDept::getDeptId).collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }


    @Override
    public List<SysMenu> findMenuListByRoleId(int roleId) {
        return baseMapper.findMenuListByRoleId(roleId);
    }

    @Override
    public List<SysRole> findRolesByUserId(Integer userId) {
        return baseMapper.listRolesByUserId(userId);
    }

    @Override
    public Boolean isUserCiteRole(Integer roleId) {
        return baseMapper.isUserCiteRole(roleId);
    }

}
