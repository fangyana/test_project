package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.config.MultiRequestBody;
import com.f.pro.domain.SysUser;
import com.f.pro.dto.dept.GetDeptDTO;
import com.f.pro.dto.user.UserDTO;
import com.f.pro.service.ISysUserService;
import com.f.pro.swagger.PublicApi;
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
@RequestMapping("/app")
@Api(tags = "0.设备接口对接控制器")
public class AppController {
    @Autowired
    private ISysUserService userService;

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


    @PublicApi(value = "测试接口", notes = "测试接口")
    @PostMapping("test")
    public R test(@RequestBody UserDTO userList, GetDeptDTO deptList) {
        System.out.println(userList);
        System.out.println(deptList);
        return R.ok();
    }

    @PublicApi(value = "测试接口", notes = "测试接口")
    @PostMapping("test1")
    public R test1(@MultiRequestBody UserDTO userList, @MultiRequestBody GetDeptDTO deptList) {
        System.out.println(userList);
        System.out.println(deptList);
        return R.ok();
    }
}
