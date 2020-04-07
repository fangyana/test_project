package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.dto.dept.AddDeptDTO;
import com.f.pro.dto.dept.EditDeptDTO;
import com.f.pro.log.annotation.SysLog;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "4.部门信息接口对接控制器")
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService deptService;

    @ApiOperation(value = "获取部门信息", notes = "获取部门信息")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:dept:view')")
    public R getDeptList() {
        return R.ok(deptService.selectDeptList());
    }

    @ApiOperation(value = "获取部门树", notes = "获取部门树")
    @GetMapping("/tree")
    public R getDeptTree() {
        return R.ok(deptService.getDeptTree());
    }

    @ApiOperation(value = "保存部门信息", notes = "保存部门信息")
    @SysLog(descrption = "保存部门信息")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:dept:add')")
    public R save(@RequestBody AddDeptDTO dto) {
        return R.ok(deptService.insert(dto));
    }

    @ApiOperation(value = "更新部门信息", notes = "更新部门信息")
    @SysLog(descrption = "更新部门信息")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:dept:update')")
    public R update(@RequestBody EditDeptDTO dto) {
        return R.ok(deptService.updateDeptById(dto));
    }

    @ApiOperation(value = "根据id逻辑删除部门信息", notes = "根据id逻辑删除部门信息")
    @SysLog(descrption = "根据id删除部门信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public R delete(@PathVariable("id") Integer id) {
        Integer loginDeptId = SecurityUtil.getUser().getDeptId();
        if (loginDeptId == id) return R.error("不能删除当前登录用户所在机构");
        return R.ok(deptService.removeById(id));
    }

    @ApiOperation(value = "获取机构类型部门树（不包含学校类型）", notes = "获取机构类型部门树（不包含学校类型）")
    @GetMapping("/tree/org")
    public R getDeptOrgTree() {
        return R.ok(deptService.getDeptOrgTree());
    }
}
