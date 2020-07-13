package com.fd.starter;

import config.GlobalExceptionHandler;
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

}
