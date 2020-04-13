package com.bms.industry.sync;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * sync属性.
 *
 * @author luojimeng
 * @date 2020/4/6
 */
@Data
@ConfigurationProperties("sync")
public class SyncProperties {

    /**
     * 贵阳公交数据同步接口.
     */
    private Bus bus;

    /**
     * 数据转发.
     */
    private DataForward dataForward;

    @Data
    public static class Bus {
        /**
         * 基础路径.
         */
        private String base;
        /**
         * Cron表达式.
         */
        private String cron;
        /**
         * 用户名.
         */
        private String username;
        /**
         * 密码.
         */
        private String password;
        /**
         * 是否启用该选项 默认禁用.
         */
        private boolean enabled = false;
    }

    @Data
    public static class DataForward {
        /**
         * 主机.
         */
        private String host;
        /**
         * 端口.
         */
        private Integer port;
        /**
         * 授权码.
         */
        private String accessKey;
        /**
         * 是否启用该选项 默认禁用.
         */
        private boolean enabled = false;
    }
}
