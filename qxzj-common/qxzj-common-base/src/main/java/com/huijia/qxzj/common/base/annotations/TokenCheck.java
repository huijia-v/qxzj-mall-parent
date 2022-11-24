package com.huijia.qxzj.common.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author huijia
 * @Email: 2921523918@qq.com
 * @date 2022/11/22 9:26 下午
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenCheck {
    //是否校验token
    boolean required() default true;
}
