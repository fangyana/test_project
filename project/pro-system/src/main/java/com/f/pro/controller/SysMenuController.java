package com.f.pro.controller;

import com.f.pro.common.util.R;
import com.f.pro.dto.menu.AddMenuDTO;
import com.f.pro.dto.menu.EditMenuDTO;
import com.f.pro.log.annotation.SysLog;
import com.f.pro.security.domaim.ProSecurityUser;
import com.f.pro.security.util.ProUtil;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "5.菜单信息接口对接控制器")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService menuService;

    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @SysLog(descrption = "添加菜单")
    @PostMapping
    public R save(@RequestBody AddMenuDTO dto) {
        return R.ok(menuService.save(dto));
    }

    @ApiOperation(value = "获取当前登录用户的菜单树", notes = "获取菜单树")
    @GetMapping
    public R getMenuTree() {
        ProSecurityUser securityUser = SecurityUtil.getUser();
        return R.ok(menuService.selectMenuTree(securityUser.getUserId()));
    }

    @ApiOperation(value = "获取所有菜单", notes = "获取所有菜单")
    @GetMapping("/getMenus")
    public R getMenus() {
        return R.ok(menuService.selectMenuTree(0));
    }

    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @SysLog(descrption = "修改菜单")
    @PutMapping
    public R updateMenu(@RequestBody EditMenuDTO dto) {
        return R.ok(menuService.updateMenuById(dto));
    }

    @ApiOperation(value = "根据id逻辑删除菜单", notes = "根据id逻辑删除菜单")
    @ApiImplicitParam(paramType = "path", name = "id", value = "菜单id", required = true, dataType = "Integer")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @SysLog(descrption = "删除菜单")
    @DeleteMapping("/{id}")
    public R deleteMenu(@PathVariable("id") Integer id) {
        return R.ok(menuService.removeMenuById(id));
    }

    @ApiOperation(value = "获取路由", notes = "获取路由")
    @GetMapping("/getRouters")
    public R getRouters() {
//        ProSecurityUser securityUser = SecurityUtil.getUser();
//        List<MenuVo> menuVos = ProUtil.buildMenus(menuService.selectMenuTree(securityUser.getUserId()));
        return R.ok(ProUtil.buildMenus(menuService.selectMenuTree(SecurityUtil.getUser().getUserId())));
    }

}
