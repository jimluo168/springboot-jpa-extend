package com.springboot.jpa.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**
 * Main App.
 *
 * @author luojimeng
 * @date 2020/3/09
 */
@EnableScheduling
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
@ServletComponentScan(basePackages = "com.springboot.jpa.demo.core")
public class Application {

    public static void main(String[] args) {
        System.setProperty("user.timezone", "GMT+8");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(Application.class, args);
    }
}
