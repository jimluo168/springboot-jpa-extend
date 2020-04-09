package com.bms.common.config.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author luojimeng
 * @date 2020/3/10
 */
@Configuration
@ConditionalOnClass({JedisCluster.class, JedisPool.class})
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private final RedisProperties properties;

    private static final GenericObjectPoolConfig DEFAULT_POOL_CONFIG = new JedisPoolConfig();

    public RedisConfig(RedisProperties properties) {
        this.properties = properties;
    }

    public JedisCluster jedisCluster() {
        logger.info("start the initialization of the redis cluster");
        RedisProperties.Cluster cluster = properties.getCluster();
        GenericObjectPoolConfig poolConfig = properties.getPool() == null ? DEFAULT_POOL_CONFIG : jedisPoolConfig();
        if (cluster.getNodes().size() > 0) {
            Set<HostAndPort> clusterNodeSet = new HashSet<>();
            cluster.getNodes().forEach(node -> {
                String[] hp = node.split(":");
                Assert.state(hp.length == 2, "Must be defined as 'host:port'");
                clusterNodeSet.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
            });
            return new JedisCluster(
                    clusterNodeSet,
                    properties.getTimeout(),
                    properties.getTimeout(),
                    properties.getMaxAttempts(),
                    properties.getPassword(),
                    poolConfig);
        } else {
            throw new IllegalArgumentException("redis clusters must be configured at least one node.");
        }
    }

    public JedisPool jedisPool() {
        logger.info("start the initialization of the redis pool");
        return new JedisPool(
                jedisPoolConfig(),
                properties.getHost(),
                properties.getPort(),
                properties.getTimeout(),
                properties.getPassword(),
                properties.getDatabase());
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        RedisProperties.Pool props = properties.getPool();
        config.setMaxTotal(props.getMaxActive());
        config.setMaxIdle(props.getMaxIdle());
        config.setMinIdle(props.getMinIdle());
        config.setMaxWaitMillis(props.getMaxWait());
        return config;
    }

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("Redis-Executor");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public RedisClient redisClient() {
        RedisRegistry redisRegistry = properties.getCluster() != null ? new RedisRegistry(jedisCluster()) : new RedisRegistry(jedisPool());
        return (RedisClient) Proxy.newProxyInstance(RedisClient.class.getClassLoader(),
                new Class[]{RedisClient.class}, new RedisCommandsExecutor(redisRegistry));
    }
}