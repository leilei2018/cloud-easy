package com.fd.servicesentinel;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ServiceSentinelApplication {

    /*RestTemplate restTemplate;

    @Bean
    @SentinelRestTemplate(fallback = "fallback", fallbackClass = ExceptionUtil.class, blockHandler="handleException",blockHandlerClass=ExceptionUtil.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String fallback(){
        return "系统繁忙";
    }

    @GetMapping
    @SentinelResource(value = "haha",fallback = "fallback")
    public String haha(){
        //restTemplate.getForObject("/api/user-service/hello",String.class);
        return "qs";
    }*/

    public static void main(String[] args) {
        SpringApplication.run(ServiceSentinelApplication.class);
    }
}
