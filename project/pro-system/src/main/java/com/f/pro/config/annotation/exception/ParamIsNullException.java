package com.f.pro.config.annotation.exception;

/**
 * @Author: FangYN
 * @Date: 2020/5/18
 * @Description: 参数为空时，异常处理
 */
public class ParamIsNullException extends RuntimeException {
    private final String parameterName;
    private final String parameterType;

    public ParamIsNullException(String parameterName, String parameterType) {
        super("");
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }

    @Override
    public String getMessage() {
        return "Required " + this.parameterType + " parameter \'" + this.parameterName + "\' must be not null !";
    }

    public final String getParameterName() {
        return this.parameterName;
    }

    public final String getParameterType() {
        return this.parameterType;
    }
}
