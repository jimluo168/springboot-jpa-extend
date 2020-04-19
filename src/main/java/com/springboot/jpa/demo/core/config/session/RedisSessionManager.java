package com.springboot.jpa.demo.core.config.session;

import java.util.Set;

/**
 * Redis Session会话管理.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
public class RedisSessionManager implements ISessionManager {

    private final RedisSessionClient redisSessionClient;
    private final SessionProperties sessionProperties;

    public RedisSessionManager(RedisSessionClient redisSessionClient, SessionProperties sessionProperties) {
        this.redisSessionClient = redisSessionClient;
        this.sessionProperties = sessionProperties;
    }

    @Override
    public ISession getSession(String sessionId) throws SessionException {
        String key = RedisSession.buildSessionIdKey(sessionId);
        Long timestamp = redisSessionClient.get(key, Long.class);
        if (timestamp == null) {
            return null;
        }
        long exp = sessionProperties.getExpiryTime() * 1000L;
        long now = System.currentTimeMillis();
        if ((timestamp + exp) < now) {
            return null;
        }
        return new RedisSession(sessionId, redisSessionClient);
    }

    @Override
    public String getSessionId(Long userId) throws SessionException {
        String key = RedisSession.buildSessionIdKey(Long.toString(userId));
        String sessionId = redisSessionClient.get(key);
        return sessionId;
    }

    @Override
    public ISession createSession() throws SessionException {
        String sessionId = SessionIdGenerator.generate();
        String key = RedisSession.buildSessionIdKey(sessionId);
        long timestamp = System.currentTimeMillis();
        redisSessionClient.set(key, timestamp, sessionProperties.getExpiryTime());
        return new RedisSession(sessionId, redisSessionClient);
    }

    @Override
    public ISession createSession(Long userId) throws SessionException {
        ISession session = createSession();
        String key = RedisSession.buildSessionIdKey(Long.toString(userId));
        String sessionId = session.getSessionId();
        redisSessionClient.set(key, sessionId, sessionProperties.getExpiryTime());
        return session;
    }

    @Override
    public void removeSession(String sessionId) throws SessionException {
        ISession session = getSession(sessionId);
        if (session == null) {
            return;
        }
        Set<String> keys = session.getAllAttributeKeys();
        if (keys != null && !keys.isEmpty()) {
            keys.forEach(k -> {
                redisSessionClient.del(k);
            });
        }
        String key = RedisSession.buildSessionIdKey(sessionId);
        redisSessionClient.del(key);
    }

    @Override
    public SessionProperties getSessionConfig() throws SessionException {
        return this.sessionProperties;
    }
}
