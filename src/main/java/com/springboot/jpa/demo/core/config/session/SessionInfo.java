package com.springboot.jpa.demo.core.config.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.jpa.demo.ErrorCodes;
import lombok.Data;

import java.io.Serializable;

/**
 * 会话信息.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@Data
public class SessionInfo implements Serializable {
    /**
     * session
     */
    private static final ThreadLocal<SessionInfo> SESSION = new ThreadLocal<>();

    /**
     * 存放到缓存的key.
     */
    public static final String CACHE_SESSION_KEY = "session";
    /**
     * 权限的信息.
     */
    public static final String CACHE_PERMISSION_KEY = "permission";

    /**
     * 用户ID.
     */
    private Long id;
    /**
     * 名称.
     */
    private String name;
    /**
     * 账号.
     */
    private String account;
    /**
     * 用户状态(0=禁用 1=启用).
     */
    private Integer status;

    @JsonIgnore
    private String sessionId;
    @JsonIgnore
    private String ip;
    @JsonIgnore
    private String requestUrl;
    @JsonIgnore
    private String requestParams;

    /**
     * 获取当前用户ID.
     *
     * @return Long
     */
    public static Long getCurrentUserId() {
        SessionInfo info = SESSION.get();
        if (info == null) {
            throw ErrorCodes.build(ErrorCodes.SESSION_INVALID);
        }
        return info.getId();
    }

    /**
     * 获取当前session信息.
     *
     * @return SessionInfo
     */
    public static SessionInfo getCurrentSession() {
        return SESSION.get();
    }

    /**
     * 清空session。
     */
    public static void removeSession() {
        SESSION.remove();
    }

    /**
     * 创建一个新的session 保存到当前线程局部变量.
     *
     * @param info SessionInfo
     */
    public static void createSession(SessionInfo info) {
        SESSION.set(info);
    }
}
