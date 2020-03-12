package com.bms.common.config.session;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 会话信息.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
public class SessionInfo implements Serializable {

    public static final ThreadLocal<SessionInfo> SESSION = new ThreadLocal<>();
    /**
     * 存放到缓存的key.
     */
    public static final String CACHE_SESSION_KEY = "session";

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
    @JSONField(name = "org_id")
    private Long orgId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
