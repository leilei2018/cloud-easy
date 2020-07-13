package com.fd.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebMvcFdConfiguration.class,
        SwaggerConfiguration.class,
        RedisCacheCustomizerConfiguration.class})
public class CloudFdAutoConfiguration {
}
