package com.f.pro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.pro.domain.SysMenu;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    @Select("select m.perms from sys_menu m, sys_user u, sys_user_role ur, sys_role_menu rm\n" +
            "        where u.user_id = #{user_id} and u.user_id = ur.user_id\n" +
            "          and ur.role_id = rm.role_id and rm.menu_id = m.menu_id AND m.del_flag=0")
    List<String> findPermsByUserId(Integer userId);
}
