package com.f.pro.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String message) {
        super(message);
    }
}
