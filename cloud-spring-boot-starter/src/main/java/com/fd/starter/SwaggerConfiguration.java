package com.fd.starter;

import config.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "global",name = "swaggerConfig",havingValue = "true")
@Conditional(SwaggerConfiguration.SwaggerCondition.class)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfiguration {
        SwaggerProperties swaggerProperties;

        public SwaggerConfiguration(SwaggerProperties swaggerProperties){
            this.swaggerProperties = swaggerProperties;
        }

        @Bean
        public Docket createRestApi() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .pathMapping("/")
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                    .paths(PathSelectors.any())
                    .build().apiInfo(new ApiInfoBuilder()
                            .title("SpringBoot整合Swagger")
                            .description("SpringBoot整合Swagger，详细信息......")
                            .version("9.0")
                            .contact(new Contact("sad","blog.csdn.net","aaa@gmail.com"))
                            .license("The Apache License")
                            .licenseUrl("http://www.baidu.com")
                            .build());
        }

    static class SwaggerCondition extends AnyNestedCondition {
        SwaggerCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix = "swagger", name = "basePackage")
        static class ExplicitType {

        }
    }
}
