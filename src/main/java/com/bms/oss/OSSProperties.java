package com.bms.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OSS配置.
 *
 * @author luojimeng
 * @date 2020/3/13
 */
@ConfigurationProperties(prefix = "oss")
public class OSSProperties {

    /**
     * 仓库位置.
     */
    private String repo;

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }
}
