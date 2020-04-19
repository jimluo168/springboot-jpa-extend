package com.springboot.jpa.demo.core.config.session;

import com.springboot.jpa.demo.core.config.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Session配置.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@Configuration
@EnableConfigurationProperties(SessionProperties.class)
@ConditionalOnClass(RedisClient.class)
public class SessionConfig {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private SessionProperties sessionProperties;

    @Bean
    public RedisSessionClient redisSessionClient() {
        return new RedisSessionClient(redisClient);
    }

    @Bean
    public RedisSessionManager redisSessionManager() {
        return new RedisSessionManager(redisSessionClient(), sessionProperties);
    }
}
