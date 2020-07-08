package com.fd.starter;

import config.GlobalExceptionHandler;
import config.WebConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcFdConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "global",name = "exception",havingValue = "true",matchIfMissing = true)
    public GlobalExceptionHandler globalExceptionHandler(){
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnProperty(prefix = "global",name = "webconfig",havingValue = "true",matchIfMissing = true)
    public WebConfig webConfig(){
        return new WebConfig();
    }
}
