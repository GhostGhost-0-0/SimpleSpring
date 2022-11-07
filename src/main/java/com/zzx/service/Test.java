package com.zzx.service;

import com.zzx.spring.ApplicationContext;

/**
 * @author: 那个小楠瓜
 * @Description: 测试类
 * @Date: 2022/11/4 20:40
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        StudentService studentService = (StudentService) applicationContext.getBean("studentService");
        studentService.test();
    }
}
