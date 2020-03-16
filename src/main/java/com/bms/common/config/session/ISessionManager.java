package com.bms.common.config.session;

/**
 * 会话管理接口.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
public interface ISessionManager {

    /**
     * 根据sessionId获取相应的会话对象.
     *
     * @param sessionId 会话ID
     * @return null表示过期
     * @throws SessionException
     */
    ISession getSession(String sessionId) throws SessionException;

    /**
     * 根据用户ID获取自己的SessionID.
     *
     * @param userId 用户ID
     * @return SessionID
     * @throws SessionException
     */
    String getSessionId(Long userId) throws SessionException;

    ISession createSession() throws SessionException;

    /**
     * 根据用户ID或唯一标示创建Session.
     *
     * @param userId
     * @return
     * @throws SessionException
     */
    ISession createSession(Long userId) throws SessionException;

    /**
     * 从缓存中移除当前会话.
     *
     * @param sessionId 会话ID
     * @throws SessionException
     */
    void removeSession(String sessionId) throws SessionException;

    /**
     * 获取会话配置属性.
     *
     * @return
     */
    SessionProperties getSessionConfig() throws SessionException;
}
