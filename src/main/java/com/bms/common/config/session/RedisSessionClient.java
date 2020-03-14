package com.bms.common.config.session;

import com.alibaba.fastjson.JSON;
import com.bms.common.config.redis.RedisClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.io.Serializable;
import java.util.Set;

/**
 * Session的 Redis存储方法.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@ConditionalOnClass(RedisClient.class)
public class RedisSessionClient {

    private final RedisClient redisClient;

    public RedisSessionClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    public String get(String key) {
        return redisClient.get(key);
    }

    /**
     * 此处必须为 json存储.
     *
     * @param key
     * @param type
     * @return
     */
    public <T extends Serializable> T get(String key, Class<T> type) {
        String json = get(key);
        if (json == null) {
            return null;
        }
        return JSON.parseObject(json, type);
    }

    /**
     * 存放一个session值，设置过期时间.
     *
     * @param key
     * @param serializable
     * @param expireSeconds 有效期 0 代表永不过期.
     */
    public void set(String key, Serializable serializable, int expireSeconds) {
        String value;
        if (serializable instanceof String) {
            value = (String) serializable;
        } else {
            value = JSON.toJSONString(serializable);
        }
        if (expireSeconds == 0) {
            redisClient.set(key, value);
        } else {
            redisClient.setex(key, expireSeconds, value);
        }
    }

    public void set(String key, String value) {
        set(key, value, 0);
    }

    /**
     * 重新设置session的过期时间.
     *
     * @param key
     * @param expireSeconds
     */
    public void expire(String key, int expireSeconds) {
        redisClient.expire(key, expireSeconds);
    }

    public boolean del(String key) {
        return redisClient.del(key) > 0;
    }

    public boolean exists(String key) {
        return redisClient.exists(key);
    }

    public long ttl(String key) {
        return redisClient.ttl(key);
    }

    public Set<String> keys(String pattern) {
        return redisClient.keys(pattern);
    }
}
