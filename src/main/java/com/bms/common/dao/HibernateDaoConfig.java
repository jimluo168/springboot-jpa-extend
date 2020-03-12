package com.bms.common.dao;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * Hibernate配置.
 *
 * @author luojimeng
 * @date 2020/3/13
 */
@Configuration
@ConditionalOnClass({Session.class, EntityManager.class})
public class HibernateDaoConfig {
    private static final Logger logger = LoggerFactory.getLogger(HibernateDaoConfig.class);
    /**
     * 约定俗成 规定Mapper xml的位置.
     */
    public static final String mapperLocation = "classpath:mapper/*.xml";

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public HibernateDao hibernateDao() {
        HibernateDao dao = new HibernateDao(entityManager);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            dao.setQueryFiles(resolver.getResources(mapperLocation));
        } catch (ConfigurationException | IOException e) {
            logger.error("HibernateDAO get resources from [" + mapperLocation + "] error ", e);
        }
        return dao;
    }
}
