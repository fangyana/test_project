package com.f.pro.dto.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "菜单id", required = true)
    private Integer menuId;
    @ApiModelProperty(value = "菜单名")
    private String name;
    @ApiModelProperty(value = "菜单权限标识")
    private String perms;
    @ApiModelProperty(value = "前端跳转url")
    private String path;
    @ApiModelProperty(value = "是否为外链")
    private Boolean isFrame;
    @ApiModelProperty(value = "父级菜单id")
    private Integer parentId;
    @ApiModelProperty(value = "菜单组件")
    private String component;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "菜单类型 （类型   0：目录   1：菜单   2：按钮）")
    private Integer type;

}
