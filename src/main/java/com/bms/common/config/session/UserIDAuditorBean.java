package com.bms.common.config.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 实现AuditorAware，对使用了@CreatedBy @LastModifiedBy 字段自动填充用户ID.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Configuration
public class UserIDAuditorBean implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        SessionInfo session = SessionInfo.getCurrentSession();
        if (session == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(session.getId());
    }
}
