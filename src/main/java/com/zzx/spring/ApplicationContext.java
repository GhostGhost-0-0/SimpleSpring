package com.zzx.spring;

import com.zzx.spring.annotation.Autowired;
import com.zzx.spring.annotation.Component;
import com.zzx.spring.annotation.ComponentScan;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 那个小楠瓜
 * @Description: Spring 容器
 * @Date: 2022/11/4 20:38
 */
public class ApplicationContext {

    /**
     * 配置类
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final Class<?> configClass;

    /**
     * 定义池
     */
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 单例池
     */
    private final ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    /**
     * 后置处理器池
     */
    private final List<BeanPostProcessor> beanPostProcessorsList = new ArrayList<>();

    public ApplicationContext(Class<?> configClass) {
        this.configClass = configClass;

        // 模拟扫描
        scan(configClass);
        // 创建 Bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                // 将单例 bean 放入到单例池中
                Object bean = createBean(beanName, beanDefinition);
                if (bean == null) {
                    throw new RuntimeException("创建 Bean 失败！");
                } else {
                    singletonObjects.put(beanName, bean);
                }
            }
        }
    }

    /**
     * 模拟扫描
     * @param configClass 配置类
     */
    private void scan(Class<?> configClass) {
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotation.value();  // 扫描路径 com.zzx.service

            path = path.replace(".", "/"); // com/zzx/service

            // 使用类加载器获取相对路径
            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            assert resource != null;
            File file = new File(resource.getFile());

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null) {
                    throw new RuntimeException("路径为空");
                } else {
                    for (File f : files) {

                        String fileName = f.getAbsolutePath();
                        if (fileName.endsWith(".class")) {
                            try {
                                String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));

                                className = className.replace("\\", ".");

                                Class<?> clazz = classLoader.loadClass(className);

                                if (clazz.isAnnotationPresent(Component.class)) {
                                    if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                        BeanPostProcessor instance = (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                                        beanPostProcessorsList.add(instance);
                                    }
                                    // BeanDefinition
                                    Component component = clazz.getAnnotation(Component.class);
                                    String beanName = component.value();
                                    String scope = component.scope();

                                    BeanDefinition beanDefinition = new BeanDefinition();
                                    beanDefinition.setType(clazz);
                                    beanDefinition.setScope(scope);

                                    // 将扫描出的 bean 定义对象放入集合中
                                    beanDefinitionMap.put(beanName, beanDefinition);
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 创建 bean
     * @param beanName bean 的名称
     * @param beanDefinition bean 的定义
     * @return bean
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        // bean 的创建
        Class<?> clazz = beanDefinition.getType();

        try {
            Object instance = clazz.getConstructor().newInstance();
            // 依赖注入模拟实现
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(declaredField.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(instance, bean);
                }
            }
            // set 方法依赖注入模拟实现
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }
            // 初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }
            // 初始化机制模拟实现
            if (instance instanceof InitializingBean) {
                try {
                    ((InitializingBean) instance).afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 初始化后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 bean
     * @param beanName bean 的名称
     * @return bean
     */
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                // 单例
                Object bean = singletonObjects.get(beanName);
                if (bean == null) {
                    Object o = createBean(beanName, beanDefinition);
                    if (o == null) {
                        throw new RuntimeException("创建 Bean 失败！");
                    } else {
                        singletonObjects.put(beanName, o);
                        return o;
                    }
                }
                return bean;
            } else {
                // 多例
                return createBean(beanName, beanDefinition);
            }
        }
    }
}
