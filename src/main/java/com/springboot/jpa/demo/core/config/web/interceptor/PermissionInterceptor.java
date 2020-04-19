package com.springboot.jpa.demo.core.config.web.interceptor;

import com.springboot.jpa.demo.core.config.session.ISession;
import com.springboot.jpa.demo.core.config.session.ISessionManager;
import com.springboot.jpa.demo.core.config.session.SessionInfo;
import com.springboot.jpa.demo.core.web.annotation.Logical;
import com.springboot.jpa.demo.core.web.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限拦截器.
 *
 * @author luojimeng
 * @date 2020/3/13
 */
public class PermissionInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    private final ISessionManager sessionManager;

    public PermissionInterceptor(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        RequiresPermissions access = method.getMethodAnnotation(RequiresPermissions.class);
        if (access == null) {
            access = method.getBeanType().getAnnotation(RequiresPermissions.class);
        }

        if (access != null) {
            if (access.value().length == 0) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            SessionInfo sessionInfo = SessionInfo.getCurrentSession();
            ISession session = sessionManager.getSession(sessionInfo.getSessionId());
            if (session == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            Set<String> codeSet = session.getAttribute(SessionInfo.CACHE_PERMISSION_KEY, HashSet.class);
            String[] codes = access.value();
            Logical logical = access.logical();
            boolean pass = false;
            if (codeSet != null && !codeSet.isEmpty()) {
                if (Logical.OR == logical) {
                    // or 有一个包含通过
                    for (String code : codes) {
                        if (codeSet.contains(code)) {
                            pass = true;
                            break;
                        }
                    }
                } else if (Logical.AND == logical) {
                    // and 有一个不包含不通过
                    for (String code : codes) {
                        if (!codeSet.contains(code)) {
                            pass = false;
                            break;
                        }
                    }

                }
            }
            if (!pass) {
                if (logger.isDebugEnabled()) {
                    logger.debug("用户:{} 账号:{} 拒绝访问, 返回403状态码.", sessionInfo.getName(), sessionInfo.getAccount());
                }
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }
}
