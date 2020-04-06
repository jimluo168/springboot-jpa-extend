package com.bms.industry.sync;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO(类的简要说明)
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
    }
}
