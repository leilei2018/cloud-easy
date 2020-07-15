package com.fd.servicegateway.config;

import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.stereotype.Component;

/**
 * 解决在全局输出的时候，日期read/write读写的日期时区的问题
 * AbstractErrorWebExceptionHandler#write
 * @see CodecsAutoConfiguration CodecConfigurerFactory
 * @see WebFluxAutoConfiguration$WebFluxAutoConfiguration（WebFluxConfigurer接口实现）
 * webflux也包含了jackson作为消息转换器，并且在application中配置jackson的date-format和time-zone即可
 */
@Component
public class JacksonCodecCustomizerImpl implements CodecCustomizer {
    @Override
    public void customize(CodecConfigurer configurer) {
        System.out.println("start");
    }
}
