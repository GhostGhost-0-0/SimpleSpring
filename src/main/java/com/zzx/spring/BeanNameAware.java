package com.zzx.spring;

/**
 * @author: 那个小楠瓜
 * @Description: Aware 回调
 * @Date: 2022/11/7 19:56
 */
public interface BeanNameAware {
    /**
     * 设置 bean 的名称
     * @param name bean 的名称
     */
    void setBeanName(String name);
}
