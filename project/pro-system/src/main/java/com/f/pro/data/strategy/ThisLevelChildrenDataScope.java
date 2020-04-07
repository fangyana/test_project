package com.f.pro.data.strategy;

import com.f.pro.data.enums.DataScopeTypeEnum;
import com.f.pro.dto.role.RoleDTO;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysDeptService;
import com.f.pro.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("3")
public class ThisLevelChildrenDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;


    @Override
    public List<Integer> getDeptIds(RoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        Integer deptId = userService.findByUserInfoName(SecurityUtil.getUser().getUsername()).getDeptId();
        return deptService.selectDeptIds(deptId);
    }
}
