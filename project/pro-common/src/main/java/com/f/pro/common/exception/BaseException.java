package com.f.pro.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: FangYN
 * @Date: 2020/4/3
 * @Description:
 */
public class BaseException extends RuntimeException implements Serializable {
    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private int code = 500;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BaseException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
