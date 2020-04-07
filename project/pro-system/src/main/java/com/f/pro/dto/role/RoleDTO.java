package com.f.pro.dto.role;

import com.f.pro.domain.SysRoleMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    @ApiModelProperty(value = "角色标识")
    private String roleCode;
    @ApiModelProperty(value = "描述")
    private String roleDesc;
    @ApiModelProperty(value = "数据权限范围 1 全部 2 本级 3 本级以及子级 4 自定义")
    private int dsType;
    @ApiModelProperty(value = "拥有的角色菜单集合")
    List<SysRoleMenu> roleMenus;
    @ApiModelProperty(value = "拥有的部门id集合")
    List<Integer> roleDepts;


}
