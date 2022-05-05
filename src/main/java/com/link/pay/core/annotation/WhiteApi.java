package com.link.pay.core.annotation;


import java.lang.annotation.*;


/**
 * 白名单接口
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WhiteApi {
}
