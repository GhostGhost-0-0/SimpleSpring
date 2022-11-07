package com.zzx.spring;

/**
 * @author: 那个小楠瓜
 * @Description: bean 的定义
 * @Date: 2022/11/4 20:48
 */
public class BeanDefinition {

    /**
     * class 类型
     */
    private Class<?> type;

    /**
     * 作用域
     */
    private String scope;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
