package com.springboot.jpa.demo.core.config.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.springboot.jpa.demo.core.config.session.ISessionManager;
import com.springboot.jpa.demo.core.config.web.interceptor.AuthenticationInterceptor;
import com.springboot.jpa.demo.core.config.web.interceptor.PermissionInterceptor;
import com.springboot.jpa.demo.core.util.DateUtil;
import com.springboot.jpa.demo.core.util.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * WebMvc的配置.
 *
 * @author luojimeng
 * @date 2020/3/10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    private final ISessionManager sessionManager;

    public WebMvcConfig(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
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

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper mapper = builder.createXmlMapper(false).build();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setDateFormat(StdDateFormat.instance);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return mapper;
    }

    @Bean
    @Order(1)
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return JSON.jackson2ObjectMapperBuilderCustomizer();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, stringHttpMessageConverter());
//        converters.add(0, fastJsonHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(sessionManager));
        // 暂时去掉权限检查
        registry.addInterceptor(new PermissionInterceptor(sessionManager));
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new AccessFilter());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return bean;
//    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                try {
                    return DateUtil.parseDate(source);
                } catch (ParseException e) {
                    logger.error("Parse Date error,source:" + source, e);
                }
                return null;
            }
        });
    }
}
