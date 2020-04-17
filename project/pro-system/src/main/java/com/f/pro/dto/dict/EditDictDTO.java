package com.f.pro.dto.dict;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class EditDictDTO extends AddDictDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典id", required = true)
    private Integer id;
}
