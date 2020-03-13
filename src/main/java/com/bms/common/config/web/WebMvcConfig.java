package com.bms.common.config.web;

import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.bms.common.config.session.ISessionManager;
import com.bms.common.config.web.interceptor.AccessFilter;
import com.bms.common.config.web.interceptor.AuthenticationInterceptor;
import com.bms.common.config.web.interceptor.PermissionInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * WebMvc的配置.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final ISessionManager sessionManager;

    public WebMvcConfig(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        String tz = TimeZone.getDefault().getID();
        String format = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        if ("Etc/UTC".equals(tz) || "UTC".equals(tz) || "GMT".equals(tz) || "GMT-00:00".equals(tz) || "GMT+00:00".equals(tz)) {
            format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        }

        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(StandardCharsets.UTF_8);
        config.setDateFormat(format);
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);

        config.setSerializeFilters((ContextValueFilter) (beanContext, o, s, o1) -> {
            if (o1 instanceof Long) {
                return String.valueOf(o1);
            }
            return o1;
        });

        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(config);

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        converter.setSupportedMediaTypes(mediaTypes);
        return converter;
    }

    public StringHttpMessageConverter stringHttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        mediaTypes.add(new MediaType("application", "javascript", StandardCharsets.UTF_8));
        mediaTypes.add(new MediaType("text", "javascript", StandardCharsets.UTF_8));
        mediaTypes.add(new MediaType("text", "xml", StandardCharsets.UTF_8));
        mediaTypes.add(new MediaType("text", "html", StandardCharsets.UTF_8));

        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(mediaTypes);
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, stringHttpMessageConverter());
        converters.add(0, fastJsonHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(sessionManager));
        registry.addInterceptor(new PermissionInterceptor(sessionManager));
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new AccessFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
