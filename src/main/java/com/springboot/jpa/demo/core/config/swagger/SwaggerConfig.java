package com.springboot.jpa.demo.core.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置.
 *
 * @author luojimeng
 * @date 2020/3/12
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger.enabled:true}")
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("API文档")
                .description("API文档")
                .version("1.0")
                .build();

        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        ticketPar.name("Authorization")
                .description("AccessToken令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        params.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.springboot.jpa.demo"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params);
    }

}
