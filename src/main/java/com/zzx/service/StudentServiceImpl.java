package com.zzx.service;

import com.zzx.spring.BeanNameAware;
import com.zzx.spring.annotation.Autowired;
import com.zzx.spring.annotation.Component;

/**
 * @author: 那个小楠瓜
 * @Description: 接口实现类
 * @Date: 2022/11/7 20:58
 */
@Component("studentService")
public class StudentServiceImpl implements StudentService, BeanNameAware {

    /**
     * 自动注入的测试订单业务
     */
    @Autowired
    private OrderService orderService;

    /**
     * 名称
     */
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void test() {
        System.out.println(orderService);
        System.out.println(name);
    }

    @Override
    public void setBeanName(String name) {
        setName(name);
    }
}
