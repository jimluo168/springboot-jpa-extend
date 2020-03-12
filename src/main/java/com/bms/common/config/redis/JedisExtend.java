package com.bms.common.config.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Jedis扩展类.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public class JedisExtend implements ExtendCommands {

    private RedisRegistry registry;

    JedisExtend(RedisRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Object getConnection() {
        return registry.isCluster() ? registry.getCluster() : registry.getPool();
    }

    @Override
    public Set<JedisPool> getMasterJedisPools() {
        if (registry.isCluster()) {
            JedisCluster cluster = registry.getCluster();
            Set<JedisPool> masters = new HashSet<>();
            if (cluster != null) {
                cluster.getClusterNodes().forEach((s, pool) -> {
                    try (Jedis jedis = pool.getResource()) {
                        if (jedis.info("Replication").contains("role:master")) {
                            masters.add(pool);
                        }
                    }
                });
            }
            return masters;
        } else {
            JedisPool pool = registry.getPool();
            return pool != null ? new HashSet<JedisPool>() {{
                add(pool);
            }} : new HashSet<>();

        }
    }

    @Override
    public boolean isCluster() {
        return registry.isCluster();
    }

}
