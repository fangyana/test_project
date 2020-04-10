package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.domain.SysUser;
import com.f.pro.security.swagger.PublicApi;
import com.f.pro.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 设备对接入口
 */
@RestController
@RequestMapping("/device")
@Api(tags = "0.设备接口对接控制器")
public class DeviceController {
    @Autowired
    private ISysUserService userService;

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     */
//    @ApiOperation(value = "登录接口", notes = "设备登录")
    @PublicApi(value = "登录接口", notes = "设备登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String")
    })
    @GetMapping("login")
    public R login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return R.ok(userService.deviceLogin(username, password));
    }


//    @ApiOperation(value = "设备同步批量上传数据", notes = "设备同步批量上传数据")
    @PublicApi(value = "设备同步批量上传数据", notes = "设备同步批量上传数据")
    @ApiImplicitParam(paramType = "path", name = "deviceNo", value = "设备号", required = true, dataType = "String")
    @PostMapping("syncUpload/{deviceNo}")
    public R syncUpload(@RequestBody List<SysUser> dtoList, @PathVariable("deviceNo") String deviceNo) {
        return R.ok();
    }
}
