package com.zzx.service;

import com.zzx.spring.BeanNameAware;
import com.zzx.spring.annotation.Autowired;
import com.zzx.spring.annotation.Component;

/**
 * @author: 那个小楠瓜
 * @Description: 用户业务
 * @Date: 2022/11/4 20:47
 */
@Component("userService")
public class UserService implements BeanNameAware {

    /**
     * 自动注入的测试订单业务
     */
    @Autowired
    private OrderService orderService;

    /**
     * 用 set 方法注入的 bean 的名称
     */
    private String beanName;

    /**
     * 测试方法
     */
    public void test() {
        System.out.println(orderService);
        System.out.println(beanName);
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }
}
