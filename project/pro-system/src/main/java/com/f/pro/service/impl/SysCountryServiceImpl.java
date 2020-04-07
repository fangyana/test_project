package com.f.pro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.domain.SysCountry;
import com.f.pro.dto.AreaDTO;
import com.f.pro.mapper.SysCountryMapper;
import com.f.pro.service.ISysCountryService;
import com.f.pro.vo.AreaVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
@Service
public class SysCountryServiceImpl extends ServiceImpl<SysCountryMapper, SysCountry> implements ISysCountryService {

    @Override
    public List<AreaVo> getAreaList(AreaDTO areaDTO) {
        List<AreaVo> list = new ArrayList<>();
        if (areaDTO.getType() == AreaDTO.PROVINCE)
            list = baseMapper.getProvinceList();
        if (areaDTO.getType() == AreaDTO.CITY)
            list = baseMapper.getCityList(areaDTO.getParentId());
        if (areaDTO.getType() == AreaDTO.COUNTY)
            list = baseMapper.getCountyList(areaDTO.getParentId());
        return list;
    }

}
