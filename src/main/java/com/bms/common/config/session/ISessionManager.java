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
     */
    ISession getSession(String sessionId) throws SessionException;

    ISession createSession() throws SessionException;

    /**
     * 获取会话配置属性.
     *
     * @return
     */
    SessionProperties getSessionConfig() throws SessionException;
}
