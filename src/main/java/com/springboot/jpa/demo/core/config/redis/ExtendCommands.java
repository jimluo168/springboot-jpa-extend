package com.springboot.jpa.demo.core.config.redis;

import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * Redis扩展命令.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public interface ExtendCommands {

    /**
     * 获取一个redis连接.
     *
     * @return
     */
    Object getConnection();

    /**
     * 获取redis所有主节点.
     *
     * @return
     */
    Set<JedisPool> getMasterJedisPools();

    /**
     * 判断是否集群.
     *
     * @return true:集群环境 false:单实例
     */
    boolean isCluster();
}
