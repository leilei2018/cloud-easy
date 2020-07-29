package com.fd.swagger.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class FeignTokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        template.headers().put("token", Collections.singletonList("qwer"));
    }
}
