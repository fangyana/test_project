package com.f.pro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.pro.common.exception.BaseException;
import com.f.pro.common.util.FtpFileUtil;
import com.f.pro.common.util.UUIDUtils;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysDept;
import com.f.pro.domain.SysUser;
import com.f.pro.domain.SysUserRole;
import com.f.pro.dto.user.AddUserDTO;
import com.f.pro.dto.user.EditUserDTO;
import com.f.pro.dto.user.RegisterUserDTO;
import com.f.pro.dto.user.UserDTO;
import com.f.pro.mapper.SysUserMapper;
import com.f.pro.security.domaim.ProSecurityUser;
import com.f.pro.security.util.JwtUtil;
import com.f.pro.security.util.ProUtil;
import com.f.pro.security.util.SecurityUtil;
import com.f.pro.service.*;
import com.f.pro.vo.device.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysJobService jobService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private FtpFileUtil ftpFileUtil;

    @Override
    public IPage<SysUser> getUsersWithRolePage(Page page, UserDTO userDTO) {
        if (ObjectUtil.isNotNull(userDTO) && userDTO.getDeptId() != 0)
            userDTO.setDeptList(deptService.selectDeptIds(userDTO.getDeptId()));
        return baseMapper.getUserVosPage(page, userDTO, new DataScope());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertUser(AddUserDTO userDto, MultipartFile file) {
        if (file != null) userDto.setAvatar(saveAvatar(file));
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setCreateBy(SecurityUtil.getUser().getUserId());
        // 默认密码 123456
        String passWord = userDto.getPassword();
        if (StringUtils.isEmpty(passWord)) passWord = "123456";
        sysUser.setPassword(ProUtil.encode(passWord));
        this.save(sysUser);
        return buildUserRole(userDto.getRoleList(), sysUser.getUserId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(EditUserDTO userDto, MultipartFile file) {
        if (file != null) userDto.setAvatar(saveAvatar(file));
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        if (!StringUtils.isEmpty(userDto.getPassword())) sysUser.setPassword(ProUtil.encode(userDto.getPassword()));
        baseMapper.updateById(sysUser);
        userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));
        return buildUserRole(userDto.getRoleList(), sysUser.getUserId());
    }

    /**
     * 构建保存用户和角色的关联关系
     *
     * @param roleList
     * @param userId
     * @return
     */
    private boolean buildUserRole(List<Integer> roleList, Integer userId) {
        List<SysUserRole> userRoles = roleList.stream().map(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(userId);
            return sysUserRole;
        }).collect(Collectors.toList());
        return userRoleService.saveBatch(userRoles);
    }

    /**
     * 头像信息保存
     *
     * @param file
     * @return
     */
    private String saveAvatar(MultipartFile file) {
        String avatar = "";
        if (file == null) return avatar;
        String fileName = file.getOriginalFilename();
        String newFileName = UUIDUtils.getUUID() + fileName.substring(fileName.lastIndexOf("."));
        //上传
        try {
            Boolean resultBoolean = ftpFileUtil.uploadFile(ftpFileUtil.getFtpProjectPath(1), "", newFileName, file.getInputStream());
            //判断是否上传成功
            if (resultBoolean)
                avatar = ftpFileUtil.getAccessIp(1) + newFileName;//上传成功
            return avatar;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("头像保存失败");
        }
        return avatar;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeUser(Integer userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setDelFlag("1");
        int i = baseMapper.updateById(sysUser);
        return i == 0 ? false : true;
//        baseMapper.deleteById(userId);
//        return userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
    }

    @Override
    public boolean restPass(Integer userId) {
        return baseMapper.updateById(new SysUser().setPassword(ProUtil.encode("123456")).setUserId(userId)) > 0;
    }

    @Override
    public SysUser findByUserInfoName(String username) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getRealName, SysUser::getPhone, SysUser::getEmail, SysUser::getPassword, SysUser::getDeptId, SysUser::getJobId, SysUser::getAvatar)
                .eq(SysUser::getUsername, username));
        // 获取部门
        SysDept sysDept = deptService.getById(sysUser.getDeptId());
        if (sysDept != null) {
            sysUser.setDeptName(sysDept.getName());
            sysUser.setDeptType(sysDept.getDeptType());
        }
        // 获取岗位
        if (!ObjectUtils.isEmpty(sysUser.getJobId()))
            sysUser.setJobName(jobService.selectJobNameByJobId(sysUser.getJobId()));
        // 获取角色
        sysUser.setRoleList(userRoleService.queryUserRoleListByUserId(sysUser.getUserId()));
        return sysUser;
    }

    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return menuService.findPermsByUserId(userId).stream().filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
    }

    @Override
    public Set<String> findRoleIdByUserId(Integer userId) {
        return userRoleService
                .selectUserRoleListByUserId(userId)
                .stream()
                .map(sysUserRole -> "ROLE_" + sysUserRole.getRoleId())
                .collect(Collectors.toSet());
    }


    @Override
    public String login(String username, String password) {
        //用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到security 的 context中
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new BaseException("用户名或密码错误", 402);
            } else if (e instanceof DisabledException) {
                throw new BaseException("账户被禁用", 402);
            } else if (e instanceof AccountExpiredException) {
                throw new BaseException("账户过期无法验证", 402);
            } else {
                throw new BaseException("账户被锁定,无法登录", 402);
            }
        }
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        ProSecurityUser userDetail = (ProSecurityUser) authentication.getPrincipal();
        return JwtUtil.generateToken(userDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean register(RegisterUserDTO userDTO) {
        // 查询用户名是否存在
        SysUser byUserInfoName = findSecurityUser(userDTO.getUsername());
        if (ObjectUtil.isNotNull(byUserInfoName))
            throw new BaseException("账户名已被注册");
        SysUser securityUser = findSecurityUser(userDTO.getPhone());
        if (ObjectUtil.isNotNull(securityUser))
            throw new BaseException("手机号已被注册");
        // todo 注册部分信息暂时写死
        userDTO.setDeptId(6);
        userDTO.setJobId(4);
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(userDTO, sysUser);// 对象拷贝
        sysUser.setPassword(ProUtil.encode(userDTO.getPassword()));// 加密后的密码
        this.save(sysUser);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(14);
        sysUserRole.setUserId(sysUser.getUserId());
        return userRoleService.save(sysUserRole);
    }

    @Override
    public boolean updateUserInfo(SysUser sysUser) {
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public SysUser findSecurityUserByUser(SysUser sysUser) {
        LambdaQueryWrapper<SysUser> select = Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPassword, SysUser::getDeptId);
        if (StrUtil.isNotEmpty(sysUser.getUsername())) {
            select.eq(SysUser::getUsername, sysUser.getUsername());
        } else if (StrUtil.isNotEmpty(sysUser.getPhone())) {
            select.eq(SysUser::getPhone, sysUser.getPhone());
        } else if (ObjectUtil.isNotNull(sysUser.getUserId()) && sysUser.getUserId() != 0) {
            select.eq(SysUser::getUserId, sysUser.getUserId());
        }


        return baseMapper.selectOne(select);
    }

    private SysUser findSecurityUser(String userIdOrUserNameOrPhone) {
        LambdaQueryWrapper<SysUser> select = Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPassword)
                .eq(SysUser::getUsername, userIdOrUserNameOrPhone)
                .or()
                .eq(SysUser::getPhone, userIdOrUserNameOrPhone)
                .or()
                .eq(SysUser::getUserId, userIdOrUserNameOrPhone);
        return baseMapper.selectOne(select);
    }

    @Override
    public Integer getIrsByDeptIds(List<Integer> deptIds) {
        return baseMapper.getIrsByDeptIds(deptIds);
    }

    @Override
    public Boolean verifyUniqueUserName(String userId, String userName) {
        return baseMapper.verifyUniqueUserName(userId, userName);
    }

    @Override
    public LoginVo deviceLogin(String username, String password) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPassword, SysUser::getLockFlag, SysUser::getDelFlag, SysUser::getDeptId)
                .eq(SysUser::getUsername, username));
        if (sysUser == null || ObjectUtils.isEmpty(sysUser.getUserId())) throw new BaseException("用户名或密码错误");
        if (!ProUtil.validatePass(password, sysUser.getPassword())) throw new BaseException("密码错误");
        if (sysUser.getDelFlag().equals("1")) throw new BaseException("账户被禁用");
        if (sysUser.getLockFlag().equals("1")) throw new BaseException("账户被锁定,无法登录");
        LoginVo vo = new LoginVo();
        vo.setUserId(sysUser.getUserId());
        SysDept sysDept = deptService.getById(sysUser.getDeptId());
        if (!sysDept.getDeptType().equals("2")) throw new BaseException("当前账户无权限登录该设备");
        vo.setDeptId(sysUser.getDeptId());
        vo.setDeptName(sysDept.getName());
        return vo;
    }

}
