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

    ISession createSession() throws SessionException;

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
