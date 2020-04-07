package com.f.pro.security.code.sms.service.impl;

import com.f.pro.security.code.sms.SendMsg;
import com.f.pro.security.code.sms.service.SmsCodeService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsCodeServiceImpl implements SmsCodeService {

    /**
     * 随机数
     */
    private static int randNum = (int) ((Math.random() * 9 + 1) * 100000);
    /**
     * 登录短信
     */
    private final static String LOGIN_TEMPLATE = "登录验证码：" + randNum + "，如非本人操作，请忽略此短信。";


    @Override
    public Map<String, Object> sendCode(String phone) {
        return SendMsg.sendSMSG(phone, LOGIN_TEMPLATE, randNum);
    }
}
