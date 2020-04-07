package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.log.annotation.SysLog;
import com.f.pro.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "日志信息接口对接控制器")
@RestController
@RequestMapping("/log")
public class SysLogController {

    @Resource
    private ISysLogService logService;

    @ApiOperation(value = "分页查询日志列表", notes = "分页查询日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "条数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "日志类型: 1 操作记录2异常记录", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userName", value = "操作人", required = true, dataType = "String")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('sys:log:view')")
    public R selectLog(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("type") Integer type, @RequestParam String userName) {
        return R.ok(logService.selectLogList(page, pageSize, type, userName));
    }

    @ApiOperation(value = "逻辑删除日志", notes = "逻辑删除日志")
    @ApiImplicitParam(paramType = "path", name = "logId", value = "日志id", required = true, dataType = "Integer")
    @SysLog(descrption = "删除日志")
    @DeleteMapping("/{logId}")
    @PreAuthorize("hasAuthority('sys:log:delete')")
    public R delete(@PathVariable("logId") Integer logId) {
        return R.ok(logService.removeById(logId));
    }
}
