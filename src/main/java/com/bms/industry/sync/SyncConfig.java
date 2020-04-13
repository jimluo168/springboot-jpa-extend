package com.bms.industry.sync;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * sync config.
 *
 * @author luojimeng
 * @date 2020/4/13
 */
@Configuration
@EnableConfigurationProperties({SyncProperties.class})
@RequiredArgsConstructor
public class SyncConfig {
    private final SyncProperties syncProperties;
}
