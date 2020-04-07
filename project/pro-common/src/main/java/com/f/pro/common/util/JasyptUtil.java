package com.f.pro.common.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 *@Author: FangYN
 *@Date: 2020/4/3
 *@Description: 加密解密工具类
 */
public class JasyptUtil {

    public static void main(String[] args) {

        BasicTextEncryptor textEnc = new BasicTextEncryptor();
        textEnc.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ1");

//        String encryptedText = textEnc.encrypt("orange");
        String encryptedText = textEnc.encrypt("jdbc:mysql://192.168.31.221:45017/football_gate?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai");
        System.out.println(encryptedText);

        String plainText = textEnc.decrypt("JFPXkjDgB572ULYnOezm0w==");
        System.out.println(plainText);

    }
}
