package com.bms.common.config.mapper;

import com.bms.common.dao.IDao;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Mapper配置.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@Configuration
@EnableConfigurationProperties(MapperProperties.class)
public class MapperConfig {
    private final static Logger logger = LoggerFactory.getLogger(MapperConfig.class);

    //    private BeanDefinitionRegistry registry;
    @Autowired
    private MapperProperties mapperProperties;
    @Autowired
    private IDao dao;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    @PostConstruct
    public void init() {
        String basePackages = mapperProperties.getBasePackages();
        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Mapper.class);
        if (annotated == null || annotated.isEmpty()) {
            logger.info("无Mapper注解的接口");
            return;
        }
        annotated.forEach(mapperInterface -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(mapperInterface);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(mapperInterface);
            definition.getConstructorArgumentValues().addGenericArgumentValue(dao, IDao.class.getName());
            definition.setBeanClass(MapperFactory.class);

            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//            registry.registerBeanDefinition(mapperInterface.getSimpleName(), definition);
//            InvocationHandler handler = new MapperProxy(mapperInterface, dao);

            defaultListableBeanFactory.registerBeanDefinition(mapperInterface.getSimpleName(), definition);
//            defaultListableBeanFactory.registerSingleton(mapperInterface.getSimpleName(), handler);

        });
    }
//
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
////        this.registry = registry;
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//
//    }
}
