package com.f.pro.data.strategy;

import com.f.pro.data.enums.DataScopeTypeEnum;
import com.f.pro.dto.role.AddRoleDTO;

import java.util.List;

public interface AbstractDataScopeHandler {

    /**
     * @param roleDto
     * @param dataScopeTypeEnum
     * @return
     */
    List<Integer> getDeptIds(AddRoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum);
}
