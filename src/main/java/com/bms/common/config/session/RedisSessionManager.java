package com.bms.common.config.session;

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
    public ISession createSession() throws SessionException {
        String sessionId = SessionIdGenerator.generate();
        String key = RedisSession.buildSessionIdKey(sessionId);
        long timestamp = System.currentTimeMillis();
        redisSessionClient.set(key, timestamp, sessionProperties.getExpiryTime());
        return new RedisSession(sessionId, redisSessionClient);
    }

    @Override
    public SessionProperties getSessionConfig() throws SessionException {
        return this.sessionProperties;
    }
}
