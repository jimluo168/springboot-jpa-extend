package com.bms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import java.util.TimeZone;

/**
 * Main App.
 *
 * @author luojimeng
 * @date 2020/3/09
 */
@EnableJpaAuditing
@SpringBootApplication
@ServletComponentScan(basePackages = "com.bms.common")
public class Application {

    public static void main(String[] args) {
//        System.setProperty("user.timezone", "GMT+8");
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Application.class, args);
    }
}
