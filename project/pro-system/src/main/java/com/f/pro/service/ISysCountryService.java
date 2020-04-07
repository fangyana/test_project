package com.f.pro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysCountry;
import com.f.pro.dto.AreaDTO;
import com.f.pro.vo.AreaVo;

import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
public interface ISysCountryService extends IService<SysCountry> {
    List<AreaVo> getAreaList(AreaDTO areaDTO);
}
