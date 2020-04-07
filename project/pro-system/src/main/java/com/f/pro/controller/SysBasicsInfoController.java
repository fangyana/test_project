package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.dto.AreaDTO;
import com.f.pro.service.ISysCountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 系统基础信息查询控制入口
 */
@Api(tags = "2.基础公共信息接口对接控制器")
@RestController
@RequestMapping("/basics")
public class SysBasicsInfoController {

    @Autowired
    private ISysCountryService countryService;

    @ApiOperation(value = "获取地区列表接口", notes = "获取地区列表")
    @GetMapping("/getAreaList")
    public R getAreaList(AreaDTO areaDTO) {
        return R.ok(countryService.getAreaList(areaDTO));
    }


}
