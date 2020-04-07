package com.f.pro.security.code.sms.service;

import java.util.Map;

public interface SmsCodeService {

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    Map<String, Object> sendCode(String phone);
}
