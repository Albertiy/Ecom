package com.ecom.auth;

import java.lang.annotation.*;

/*
* 添加自定义注解 AuthSeller
* */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthUser {
    boolean validate() default true;
}
