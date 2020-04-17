package com.f.pro.data.strategy;

import com.f.pro.data.enums.DataScopeTypeEnum;
import com.f.pro.domain.SysDept;
import com.f.pro.dto.role.AddRoleDTO;
import com.f.pro.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("1")
public class AllDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysDeptService deptService;


    @Override
    public List<Integer> getDeptIds(AddRoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        List<SysDept> sysDeptList= deptService.list();
        return sysDeptList.stream().map(SysDept::getDeptId).collect(Collectors.toList());
    }
}
