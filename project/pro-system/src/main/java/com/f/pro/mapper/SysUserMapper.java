package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.f.pro.data.datascope.DataScope;
import com.f.pro.domain.SysUser;
import com.f.pro.dto.user.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Insert("insert into sys_user (username,password,dept_id,job_id,phone,email,avatar,lock_flag) values (#{username},#{password},#{deptId},#{jobId},#{phone},#{email},#{avatar},#{lockFlag})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    boolean insertUser(SysUser sysUser);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param page      分页
     * @param userDTO   查询参数
     * @param dataScope
     * @return list
     */
    IPage<SysUser> getUserVosPage(Page page, @Param("query") UserDTO userDTO, DataScope dataScope);

    /**
     * 根据部门id数组查询部门下的人员数量
     *
     * @param deptIds
     * @return
     */
    Integer getIrsByDeptIds(@Param("deptIds") List<Integer> deptIds);

    /**
     * 验证登录名是否重复（排除userId）
     *
     * @param userId
     * @param userName
     * @return
     */
    Boolean verifyUniqueUserName(@Param("userId") String userId, @Param("userName") String userName);
}
