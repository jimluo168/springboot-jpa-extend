package com.bms.common.config.session;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Session配置属性.
 *
 * @author luojimeng
 * @date 2020/3/11
 */
@ConfigurationProperties(prefix = "session")
public class SessionProperties {
    /**
     * 过期时间(秒),默认30天.
     */
    private int expiryTime = 60 * 60 * 24 * 30;

    public int getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(int expiryTime) {
        this.expiryTime = expiryTime;
    }
}
