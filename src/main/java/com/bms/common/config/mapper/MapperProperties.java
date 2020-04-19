package com.bms.common.config.mapper;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mapper属性.
 *
 * @author luojimeng
 * @date 2020/4/19
 */
@ConfigurationProperties(prefix = "mapper")
public class MapperProperties {
    private String basePackages;

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
}
