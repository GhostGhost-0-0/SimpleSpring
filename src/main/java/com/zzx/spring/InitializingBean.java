package com.zzx.spring;

/**
 * @author: 那个小楠瓜
 * @Description: 初始化 bean
 * @Date: 2022/11/7 20:01
 */
public interface InitializingBean {

    /**
     * 在依赖注入后进行的方法
     * @throws Exception 异常
     */
    void afterPropertiesSet() throws Exception;
}
