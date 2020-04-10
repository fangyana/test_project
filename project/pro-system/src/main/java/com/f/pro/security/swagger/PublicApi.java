package com.f.pro.security.swagger;

import io.swagger.annotations.Authorization;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.ResponseHeader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: FangYN
 * @Date: 2020/4/10
 * @Description: 分组的注解 -- 无需Authorization验证
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface PublicApi {
    String value();

    String notes() default "";

    String[] tags() default {""};

    Class<?> response() default Void.class;

    String responseContainer() default "";

    String responseReference() default "";

    String httpMethod() default "";

    /** @deprecated */
    @Deprecated
    int position() default 0;

    String nickname() default "";

    String produces() default "";

    String consumes() default "";

    String protocols() default "";

    Authorization[] authorizations() default {@Authorization("")};

    boolean hidden() default false;

    ResponseHeader[] responseHeaders() default {@ResponseHeader(
            name = "",
            response = Void.class
    )};

    int code() default 200;

    Extension[] extensions() default {@Extension(
            properties = {@ExtensionProperty(
                    name = "",
                    value = ""
            )}
    )};

    boolean ignoreJsonView() default false;
}
