package com.springboot.jpa.demo.core.config.mapper;

import com.springboot.jpa.demo.core.dao.IDao;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

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

    @Autowired
    private MapperProperties mapperProperties;
    @Autowired
    private IDao dao;

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

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

            defaultListableBeanFactory.registerBeanDefinition(mapperInterface.getSimpleName(), definition);

        });
    }
}
