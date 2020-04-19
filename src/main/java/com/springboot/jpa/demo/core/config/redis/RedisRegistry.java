package com.springboot.jpa.demo.core.config.redis;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * Redis配置.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public class RedisRegistry {

    private boolean isCluster;

    private JedisCluster cluster;

    private JedisPool pool;

    RedisRegistry(JedisCluster cluster) {
        this.isCluster = true;
        this.cluster = cluster;
    }

    RedisRegistry(JedisPool pool) {
        this.isCluster = false;
        this.pool = pool;
    }

    public boolean isCluster() {
        return isCluster;
    }

    public JedisCluster getCluster() {
        return cluster;
    }

    public JedisPool getPool() {
        return pool;
    }
}
