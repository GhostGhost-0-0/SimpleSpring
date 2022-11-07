package com.zzx.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 那个小楠瓜
 * @Description: 组件扫描注解
 * @Date: 2022/11/4 20:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentScan {
    /**
     * bean 的名称
     * @return String
     */
    String value() default "";
}
