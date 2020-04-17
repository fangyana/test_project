package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.dto.role.AddRoleDTO;
import com.f.pro.dto.role.EditRoleDTO;
import com.f.pro.log.annotation.SysLog;
import com.f.pro.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "6.角色信息接口对接控制器")
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private ISysRoleService roleService;

    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    @ApiImplicitParam(paramType = "query", name = "roleName", value = "角色名", required = true, dataType = "String")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:role:view')")
    public R getRoleList(@RequestParam String roleName) {
        return R.ok(roleService.selectRoleList(roleName));
    }

    @ApiOperation(value = "保存角色以及菜单权限 ", notes = "保存角色以及菜单权限")
    @SysLog(descrption = "保存角色以及菜单权限")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:add')")
    public R save(@RequestBody AddRoleDTO roleDto) {
        return R.ok(roleService.saveRoleMenu(roleDto));
    }

    @ApiOperation(value = "根据角色id获取菜单", notes = "根据角色id获取菜单")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色Id", required = true, dataType = "Integer")
    @SysLog(descrption = "据角色id获取菜单")
    @GetMapping("/findRoleMenus/{roleId}")
    public R findRoleMenus(@PathVariable("roleId") Integer roleId) {
        return R.ok(roleService.findMenuListByRoleId(roleId));
    }

    @ApiOperation(value = "更新角色以及菜单权限", notes = "更新角色以及菜单权限")
    @SysLog(descrption = "更新角色以及菜单权限")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:update')")
    public R update(@RequestBody EditRoleDTO roleDto) {
        return R.ok(roleService.updateRoleMenu(roleDto));
    }

    @ApiOperation(value = "逻辑删除角色以及权限", notes = "逻辑删除角色以及权限")
    @ApiImplicitParam(paramType = "path", name = "roleId", value = "角色Id", required = true, dataType = "Integer")
    @SysLog(descrption = "删除角色以及权限")
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public R delete(@PathVariable("roleId") Integer roleId) {
        if (roleService.isUserCiteRole(roleId.hashCode()))
            return R.error("请先解除绑定的用户");
        return R.ok(roleService.removeById(roleId));
    }

}
