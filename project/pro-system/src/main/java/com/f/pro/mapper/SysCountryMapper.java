package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.pro.domain.SysCountry;
import com.f.pro.vo.AreaVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
@Repository
public interface SysCountryMapper extends BaseMapper<SysCountry> {
    List<AreaVo> getProvinceList();

    List<AreaVo> getCityList(@Param("provinceId") String provinceId);

    List<AreaVo> getCountyList(@Param("cityId") String cityId);
}
