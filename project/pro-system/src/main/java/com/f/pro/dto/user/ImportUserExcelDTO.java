package com.f.pro.dto.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 批量上传
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class ImportUserExcelDTO {

    @ExcelProperty(value = {"姓名"})
    private String realName;
    // 性别  1：男  0：女
    @ExcelProperty(value = {"性别"})
    private String sex;
    @ExcelProperty(value = {"头像"})
    private String avatar;
    @ApiModelProperty(value = "生日,格式yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
}
