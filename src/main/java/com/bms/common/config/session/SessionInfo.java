package com.bms.common.config.session;

import com.bms.ErrorCodes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.collections.functors.ExceptionFactory;

import java.io.Serializable;

/**
 * 会话信息.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@Data
public class SessionInfo implements Serializable {

    public static final ThreadLocal<SessionInfo> SESSION = new ThreadLocal<>();
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
     * 机构ID.
     */
    private Long orgId;
    /**
     * 机构名称.
     */
    private String orgName;
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

    public static Long getCurrentUserId() {
        SessionInfo info = SESSION.get();
        if (info == null) {
            throw ErrorCodes.build(ErrorCodes.SESSION_INVALID);
        }
        return info.getId();
    }

    public static SessionInfo getCurrentSession() {
        return SESSION.get();
    }
}
