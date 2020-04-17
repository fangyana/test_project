package com.f.pro.data.strategy;

import com.f.pro.data.enums.DataScopeTypeEnum;
import com.f.pro.dto.role.AddRoleDTO;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("2")
public class ThisLevelDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysUserService userService;

    @Override
    public List<Integer> getDeptIds(AddRoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        // 用于存储部门id
        List<Integer> deptIds = new ArrayList<>();
        deptIds.add(userService.findByUserInfoName(SecurityUtil.getUser().getUsername()).getDeptId());
        return deptIds;
    }
}
