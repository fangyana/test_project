package com.f.pro.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: FangYN
 * @Date: 2020/5/18
 * @Description: "参数不能为空"注解，作用于方法参数上
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {
    /**
     * 是否非空,默认不能为空
     */
    boolean notNull() default true;
}
