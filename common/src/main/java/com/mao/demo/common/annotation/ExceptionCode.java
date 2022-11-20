package com.mao.demo.common.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExceptionCode {

    /**
     * 响应码
     */
    int value() default 405;

    /**
     * 响应信息
     */
    String message() default  "参数校验错误";
}
