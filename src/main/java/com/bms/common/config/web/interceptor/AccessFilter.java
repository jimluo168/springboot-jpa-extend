package com.bms.common.config.web.interceptor;

import com.bms.common.config.web.HttpRequestBodyWrapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 打印访问记录的过滤器.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "accessFilter", urlPatterns = "/*")
public class AccessFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("<--- AccessFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest httpReq = (HttpServletRequest) request;
        String token = httpReq.getHeader(AuthenticationInterceptor.HTTP_HEAD_AUTH);
        String xUserAgent = httpReq.getHeader("X-User-Agent");
        try {
            if (logger.isDebugEnabled()) {
                String contentType = request.getContentType();
                if (contentType != null && contentType.toLowerCase().contains(MediaType.APPLICATION_JSON_VALUE)
                        && ("POST".equalsIgnoreCase(httpReq.getMethod()) || "PUT".equalsIgnoreCase(httpReq.getMethod()))) {
                    httpReq = new HttpRequestBodyWrapper(httpReq);
                    String body = IOUtils.toString(httpReq.getInputStream(), StandardCharsets.UTF_8);
                    logger.debug("<--- request:{} {} {}?{} token:{} X-User-Agent:{} body:{}", httpReq.getRemoteAddr(), httpReq.getMethod(), httpReq.getRequestURI(), httpReq.getQueryString(), token, xUserAgent, body);
                } else {
                    logger.debug("<--- request:{} {} {}?{} token:{} X-User-Agent:{} body:{}", httpReq.getRemoteAddr(), httpReq.getMethod(), httpReq.getRequestURI(), httpReq.getQueryString(), token, xUserAgent, "");
                }
            }
            chain.doFilter(httpReq, response);
        } catch (Exception ex) {
            String errMsg = "<--- request:" + httpReq.getRemoteAddr() + " " + httpReq.getMethod() + " " + httpReq.getRequestURI() + "?" + httpReq.getQueryString() + " token:" + token + " X-User-Agent:" + xUserAgent;
            logger.error(errMsg, ex);
        } finally {
            HttpServletResponse resp = (HttpServletResponse) response;
            logger.info("<--- request:{} {} {} {} {}ms", httpReq.getRemoteAddr(), httpReq.getMethod(), httpReq.getRequestURI(), resp.getStatus(), (System.currentTimeMillis() - start));
        }
    }

    @Override
    public void destroy() {
        logger.info("<--- AccessFilter destroy");
    }
}
