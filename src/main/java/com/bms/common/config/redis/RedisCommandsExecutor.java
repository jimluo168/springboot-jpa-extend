package com.bms.common.config.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Redis执行类.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public class RedisCommandsExecutor implements InvocationHandler {

    private RedisRegistry registry;

    private JedisExtend jedisExtend;

    public RedisCommandsExecutor(RedisRegistry registry) {
        this.registry = registry;
        this.jedisExtend = new JedisExtend(registry);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Method proxyMethod;
        try {
            proxyMethod = JedisExtend.class.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException ex) {
            proxyMethod = null;
        }
        if (proxyMethod != null) {
            return proxyMethod.invoke(this.jedisExtend, args);
        }
        if (registry.isCluster()) {
            proxyMethod = JedisCluster.class.getMethod(methodName, parameterTypes);
            return proxyMethod.invoke(registry.getCluster(), args);
        } else {
            proxyMethod = Jedis.class.getMethod(methodName, parameterTypes);
            try (Jedis jedis = registry.getPool().getResource()) {
                return proxyMethod.invoke(jedis, args);
            }
        }
    }
}
