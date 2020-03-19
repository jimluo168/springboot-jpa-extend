package com.bms.common.config.web.interceptor;

import com.bms.common.config.web.ServletRequestSnakeCaseParameterWrapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 将下划线式参数进行驼峰式字段进行绑定的拦截器.
 *
 * @author luojimeng
 * @date 2020/3/19
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@WebFilter(filterName = "snakeCaseParameterFilter", urlPatterns = "/*")
public class SnakeCaseParameterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new ServletRequestSnakeCaseParameterWrapper((HttpServletRequest) request), response);
    }
}
