package com.springboot.jpa.demo.core.config.flake;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FlakeIdProperties.class})
public class FlakeConfig {

    private final FlakeIdProperties properties;

    public FlakeConfig(FlakeIdProperties properties) {
        this.properties = properties;
    }

    @Bean("flakeId")
    public FlakeId flakeId() {
        return new FlakeId(this.properties.getEpoch(), this.properties.getDatacenter(), this.properties.getWorker());
    }
}
