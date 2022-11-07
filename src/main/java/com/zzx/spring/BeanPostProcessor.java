package com.zzx.spring;

/**
 * @author: 那个小楠瓜
 * @Description: 后置处理器
 * @Date: 2022/11/7 20:05
 */
public interface BeanPostProcessor {
    /**
     * 初始化前的处理
     * @param bean 被拦截的未初始化 bean
     * @param beanName bean 的名称
     * @return 处理后的 bean
     */
    Object postProcessBeforeInitialization(Object bean, String beanName);

    /**
     * 初始化后的处理
     * @param bean 被拦截的初始化后的 bean
     * @param beanName bean 的名称
     * @return 处理后的bean
     */
    Object postProcessAfterInitialization(Object bean, String beanName);
}
