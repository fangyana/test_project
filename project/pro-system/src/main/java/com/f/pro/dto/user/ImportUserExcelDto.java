package com.f.pro.dto.user;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description: 批量上传
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImportUserExcelDto {

    @ExcelProperty(value = {"姓名"})
    private String realName;
    // 性别  1：男  0：女
    @ExcelProperty(value = {"性别"})
    private String sex;
    @ExcelProperty(value = {"头像"})
    private String avatar;
}
