package com.zzx.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: 那个小楠瓜
 * @Description: 组件注解
 * @Date: 2022/11/4 20:38
 */
@Retention(RetentionPolicy.RUNTIME) // 生效时间
@Target(ElementType.TYPE)  // 只能写在类上
public @interface Component {

    /**
     * bean 的名称
     * @return String
     */
    String value() default "";

    /**
     * bean 的作用域
     * @return String
     */
    String scope() default "singleton";
}
