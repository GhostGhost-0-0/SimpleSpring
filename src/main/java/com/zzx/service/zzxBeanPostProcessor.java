package com.zzx.service;

import com.zzx.spring.BeanPostProcessor;
import com.zzx.spring.annotation.Component;

import java.lang.reflect.Proxy;

/**
 * @author: 那个小楠瓜
 * @Description: Bean 的后置处理器
 * @Date: 2022/11/7 20:07
 */
@Component("beanPostProcessor")
public class zzxBeanPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前 + " + bean);
        if (beanName.equals("orderService")) {
            ((OrderService) bean).setBeanName(beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后 + " + bean);
        if (beanName.equals("studentService")) {
            return Proxy.newProxyInstance(zzxBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {
                System.out.println("代理逻辑");
                return method.invoke(bean, args);
            });
        }
        return bean;
    }
}
