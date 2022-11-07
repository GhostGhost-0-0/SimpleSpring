package com.zzx.service;

import com.zzx.spring.InitializingBean;
import com.zzx.spring.annotation.Component;

/**
 * @author: 那个小楠瓜
 * @Description: 订单业务
 * @Date: 2022/11/7 19:48
 */
@Component(value = "orderService", scope = "prototype")
public class OrderService implements InitializingBean {

    /**
     * bean 的名称
     */
    private String beanName;

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化 + " + beanName);
    }
}
