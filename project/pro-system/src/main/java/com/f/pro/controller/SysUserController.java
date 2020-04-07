package com.f.pro.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.pro.common.constant.ProConstant;
import com.f.pro.common.exception.BaseException;
import com.f.pro.common.util.R;
import com.f.pro.domain.SysUser;
import com.f.pro.dto.user.UserDTO;
import com.f.pro.log.annotation.SysLog;
import com.f.pro.security.util.EmailUtil;
import com.f.pro.security.util.ProUtil;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "3.用户信息接口对接控制器")
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 保存用户包括角色和部门
     *
     * @param userDto
     * @return
     */
    @ApiOperation(value = "保存用户包括角色和部门", notes = "保存用户包括角色和部门")
    @SysLog(descrption = "保存用户包括角色和部门")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    public R insert(UserDTO userDto, @RequestParam(value = "file", required = false) MultipartFile file) {
        return R.ok(userService.insertUser(userDto, file));
    }


    /**
     * 获取用户列表集合
     *
     * @param page
     * @param userDTO
     * @return
     */
    @ApiOperation(value = "查询用户集合", notes = "查询用户集合")
    @SysLog(descrption = "查询用户集合")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:view')")
    public R getList(Page page, UserDTO userDTO) {
        return R.ok(userService.getUsersWithRolePage(page, userDTO));
    }

    /**
     * 更新用户包括角色和部门
     *
     * @param userDto
     * @return
     */
    @ApiOperation(value = "更新用户包括角色和部门", notes = "更新用户包括角色和部门")
    @SysLog(descrption = "更新用户包括角色和部门")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:update')")
    public R update(UserDTO userDto, @RequestParam(value = "file", required = false) MultipartFile file) {
        return R.ok(userService.updateUser(userDto, file));
    }

    /**
     * 删除用户包括角色和部门
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id删除用户包括角色和部门", notes = "根据用户id删除用户包括角色和部门")
    @SysLog(descrption = "根据用户id删除用户包括角色和部门")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@PathVariable("userId") Integer userId) {
        if (SecurityUtil.getUser().getUserId().equals(userId))
            return R.error("无法删除当前登录用户");
        return R.ok(userService.removeUser(userId));
    }


    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "重置密码", notes = "重置密码(默认为123456)")
    @SysLog(descrption = "重置密码")
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:rest')")
    public R restPass(@PathVariable("userId") Integer userId) {
        return R.ok(userService.restPass(userId));
    }


    /**
     * 获取个人信息
     *
     * @return
     */
    @ApiOperation(value = "获取个人信息", notes = "获取个人信息")
    @SysLog(descrption = "获取个人信息")
    @GetMapping("/info")
    public R getUserInfo() {
        return R.ok(userService.findByUserInfoName(SecurityUtil.getUser().getUsername()));
    }

    /**
     * 修改密码
     *
     * @return
     */
    @ApiOperation(value = "修改当前登录用户密码", notes = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "oldPass", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "newPass", value = "新密码", required = true, dataType = "String")
    })
    @SysLog(descrption = "修改密码")
    @PutMapping("updatePass")
    @PreAuthorize("hasAuthority('sys:user:updatePass')")
    public R updatePass(@RequestParam String oldPass, @RequestParam String newPass) {
        // 校验密码流程
        SysUser sysUser = userService.findSecurityUserByUser(new SysUser().setUsername(SecurityUtil.getUser().getUsername()));
        if (!ProUtil.validatePass(oldPass, sysUser.getPassword())) {
            throw new BaseException("原密码错误");
        }
        if (StrUtil.equals(oldPass, newPass)) {
            throw new BaseException("新密码不能与旧密码相同");
        }
        // 修改密码流程
        SysUser user = new SysUser();
        user.setUserId(sysUser.getUserId());
        user.setPassword(ProUtil.encode(newPass));
        return R.ok(userService.updateUserInfo(user));
    }

    /**
     * 检测用户名是否存在 避免重复
     *
     * @param userName
     * @return
     */
    @ApiOperation(value = "检测用户名是否存在", notes = "检测用户名是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", required = true, dataType = "String")
    })
    @PostMapping("/vailUserName")
    public R vailUserName(@RequestParam(required = false) String userId, @RequestParam String userName) {
        return R.ok(userService.verifyUniqueUserName(userId, userName));
    }

    /**
     * 发送邮箱验证码
     *
     * @param to
     * @param request
     * @return
     */
    @PostMapping("/sendMailCode")
    public R sendMailCode(@RequestParam String to, HttpServletRequest request) {
        emailUtil.sendSimpleMail(to, request);
        return R.ok();
    }

    /**
     * 修改密码
     *
     * @return
     */
    @SysLog(descrption = "修改邮箱")
    @PutMapping("updateEmail")
    @PreAuthorize("hasAuthority('sys:user:updateEmail')")
    public R updateEmail(@RequestParam String mail, @RequestParam String code, @RequestParam String pass, HttpServletRequest request) {
        // 校验验证码流程
        String cCode = (String) request.getSession().getAttribute(ProConstant.RESET_MAIL);
        if (ObjectUtil.isNull(cCode)) {
            throw new BaseException("验证码已过期");
        }
        if (!StrUtil.equals(code.toLowerCase(), cCode)) {
            throw new BaseException("验证码错误");
        }
        // 校验密码流程
        SysUser sysUser = userService.findSecurityUserByUser(new SysUser().setUsername(SecurityUtil.getUser().getUsername()));
        if (!ProUtil.validatePass(pass, sysUser.getPassword())) {
            throw new BaseException("密码错误");
        }
        // 修改邮箱流程
        SysUser user = new SysUser();
        user.setUserId(sysUser.getUserId());
        user.setEmail(mail);
        return R.ok(userService.updateUserInfo(user));
    }


}
