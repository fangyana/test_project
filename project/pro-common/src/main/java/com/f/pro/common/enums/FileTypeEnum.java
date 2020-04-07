package com.f.pro.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *@Author: FangYN
 *@Date: 2020/4/3
 *@Description: ftp文件类型保存目录枚举
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    IMG_TYPE(0,"img/"),
    IMG_USER(1,"img/user/"),
    IMG_STUDENT(2,"img/student/");

    private int code;
    private String name;

    public static String getName(int code) {
        for(FileTypeEnum typeVar : FileTypeEnum.values()) {
            if(typeVar.getCode() == code) {
                return typeVar.getName();
            }
        }
        return "";
    }
}
