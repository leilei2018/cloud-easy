package com.fd.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebMvcFdConfiguration.class,SwaggerConfig.class})
public class CloudFdAutoConfiguration {
}
