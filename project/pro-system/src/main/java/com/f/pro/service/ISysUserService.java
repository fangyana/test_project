package com.f.pro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.f.pro.domain.SysUser;
import com.f.pro.dto.user.AddUserDTO;
import com.f.pro.dto.user.EditUserDTO;
import com.f.pro.dto.user.RegisterUserDTO;
import com.f.pro.dto.user.UserDTO;
import com.f.pro.vo.device.LoginVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ISysUserService extends IService<SysUser> {
    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param page    分页对象
     * @param userDTO 参数列表
     * @return
     */
    IPage<SysUser> getUsersWithRolePage(Page page, UserDTO userDTO);

    /**
     * 保存用户以及角色部门等信息
     *
     * @param userDto
     * @return
     */
    boolean insertUser(AddUserDTO userDto, MultipartFile file);

    /**
     * 更新用户以及角色部门等信息
     *
     * @param userDto
     * @return
     */
    boolean updateUser(EditUserDTO userDto, MultipartFile file);

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    boolean removeUser(Integer userId);

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    boolean restPass(Integer userId);

    /**
     * 通过用户名查找用户个人信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUser findByUserInfoName(String username);

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    Set<String> findPermsByUserId(Integer userId);

    /**
     * 通过用户id查询角色集合
     *
     * @param userId
     * @return
     */
    Set<String> findRoleIdByUserId(Integer userId);

    /**
     * 账户密码登录
     *
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);


    /**
     * 注册用户
     *
     * @return
     */
    boolean register(RegisterUserDTO userDTO);

    /**
     * 修改用户信息
     *
     * @param sysUser
     * @return
     */
    boolean updateUserInfo(SysUser sysUser);

    /**
     * 通过用户去查找用户(id/用户名/手机号)
     *
     * @param sysUser
     * @return
     */
    SysUser findSecurityUserByUser(SysUser sysUser);

    /**
     * 根据部门id数组查询部门下的人员数量
     *
     * @param deptIds
     * @return
     */
    Integer getIrsByDeptIds(List<Integer> deptIds);

    /**
     * 验证登录名是否重复（排除userId）
     *
     * @param userId
     * @param userName
     * @return
     */
    Boolean verifyUniqueUserName(String userId, String userName);

    /**
     * 账户密码登录(设备登录)
     *
     * @param username
     * @param password
     * @return
     */
    LoginVo deviceLogin(String username, String password);

}
