package com.f.pro.dto.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditMenuDTO extends AddMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "菜单id", required = true)
    private Integer menuId;
}
