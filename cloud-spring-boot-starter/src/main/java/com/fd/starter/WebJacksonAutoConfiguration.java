package com.fd.starter;

import config.WebJacksonConfig;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@Import(WebJacksonConfig.class)
public class WebJacksonAutoConfiguration {

}
