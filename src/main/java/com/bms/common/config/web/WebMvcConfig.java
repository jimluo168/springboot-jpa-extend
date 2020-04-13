package com.bms.common.config.web;

import com.bms.common.config.session.ISessionManager;
import com.bms.common.config.web.interceptor.AuthenticationInterceptor;
import com.bms.common.config.web.interceptor.PermissionInterceptor;
import com.bms.common.util.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    public static final String DATE_FMT_DEFAULT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FMT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


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
        mapper.setDateFormat(new StdDateFormat());
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
                    return DateUtils.parseDate(source, DATE_FMT_UTC);
                } catch (ParseException e) {
                    logger.error("Parse Date error,source:" + source + " date format:" + DATE_FMT_UTC, e);
                }
                return null;
            }
        });
    }
}
