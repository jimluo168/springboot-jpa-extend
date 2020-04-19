package com.springboot.jpa.demo.core.config.web.interceptor;

import com.springboot.jpa.demo.ErrorCodes;
import com.springboot.jpa.demo.core.config.session.ISession;
import com.springboot.jpa.demo.core.config.session.ISessionManager;
import com.springboot.jpa.demo.core.config.session.SessionInfo;
import com.springboot.jpa.demo.core.util.IPUtils;
import com.springboot.jpa.demo.core.web.annotation.RequiresAuthentication;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 授权访问拦截器.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    /**
     * Http Authorization头.
     */
    public static final String HTTP_HEAD_AUTH = "Authorization";

    private final ISessionManager sessionManager;

    public AuthenticationInterceptor(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;

        // 错误控制不用处理令牌问题
        if (method.getBean() instanceof ErrorController) {
            return true;
        }
        String token = request.getHeader(HTTP_HEAD_AUTH);
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("<--- {} {} {} token:{}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), token);
        }

        // 获取方法及类级别上的权限过滤标示,方法级别上的大于类级别上的
        RequiresAuthentication auth = method.getMethodAnnotation(RequiresAuthentication.class);
        if (auth == null) {
            auth = method.getBeanType().getAnnotation(RequiresAuthentication.class);
        }
        if (auth != null) {
            if (StringUtils.isBlank(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            ISession session = sessionManager.getSession(token);
            if (session == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            SessionInfo info = session.getAttribute(SessionInfo.CACHE_SESSION_KEY, SessionInfo.class);
            if (info.getStatus() != null && info.getStatus() == 0) {
                throw ErrorCodes.build(ErrorCodes.USER_STATUS_DISABLED);
            }
            info.setSessionId(token);
            info.setIp(IPUtils.getClinetIpByRequest(request));
            info.setRequestUrl(request.getRequestURI());

            String requestMethod = request.getMethod();
            String params = "";
            String contentType = request.getContentType();
            boolean iswrapper = contentType != null && contentType.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)
                    && ("POST".equals(requestMethod) || "PUT".equals(requestMethod));
            if (iswrapper) {
                params = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            } else {
                params = request.getQueryString();
            }

            info.setRequestParams(params);
            SessionInfo.createSession(info);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionInfo.removeSession();
    }
}
