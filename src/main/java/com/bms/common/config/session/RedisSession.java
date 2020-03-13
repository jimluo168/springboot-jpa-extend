package com.bms.common.config.session;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Set;

/**
 * Redis的回话管理.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
public class RedisSession implements ISession {
    private static final Logger logger = LoggerFactory.getLogger(RedisSession.class);

    private static final String CACHE_KEY = "cache:session";

    /**
     * KEY "cache:session:sessionId".
     */
    public static final String TEMPLATE_KEY = CACHE_KEY + ":%s";

    protected final String sessionId;

    private RedisSessionClient redisSessionClient;

    public RedisSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public RedisSession(String sessionId, RedisSessionClient redisSessionClient) {
        this(sessionId);
        this.redisSessionClient = redisSessionClient;
    }

    @Override
    public String getSessionId() throws SessionException {
        return this.sessionId;
    }

    @Override
    public Serializable getAttribute(String attrName) throws SessionException {
        String key = buildAttrKey(attrName);
        return redisSessionClient.get(key);
    }

    @Override
    public <T extends Serializable> T getAttribute(String attrName, Class<T> type) throws SessionException {
        String json = (String) this.getAttribute(attrName);
        return JSON.parseObject(json, type);
    }

    @Override
    public boolean removeAttribute(String attrName) throws SessionException {
        return redisSessionClient.del(buildAttrKey(attrName));
    }

    @Override
    public void setAttribute(String attrName, Serializable value) throws SessionException {
        String key = buildSessionIdKey(this.sessionId);
        long expireSeconds = redisSessionClient.ttl(key);
        this.setAttribute(attrName, value, (int) expireSeconds);
    }

    @Override
    public void setAttribute(String attrName, Serializable value, int expireSeconds) throws SessionException {
        redisSessionClient.set(buildAttrKey(attrName), value, expireSeconds);
    }

    @Override
    public Set<String> getAllAttributeKeys() throws SessionException {
        String pattern = buildAttrKey("*");
        return redisSessionClient.keys(pattern);
    }

    protected static String buildSessionIdKey(String sessionId) {
        return String.format(TEMPLATE_KEY, sessionId);
    }

    protected String buildAttrKey(String attrName) {
        return buildSessionIdKey(sessionId) + ":" + attrName;
    }
}
