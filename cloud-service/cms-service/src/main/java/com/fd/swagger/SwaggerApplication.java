package com.fd.swagger;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ClassUtils;

import java.io.IOException;

@SpringBootApplication(
        exclude = {RedissonAutoConfiguration.class, RedisAutoConfiguration.class}
)
@Slf4j
@EnableFeignClients(basePackages = "com.fd.cloud.serviceapi.**.inter")
public class SwaggerApplication {
    static ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

    static void test() throws IOException {
//        Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
//                ClassUtils.convertClassNameToResourcePath("com.fd.swagger.model.entity") + "/**/*.class");


        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                "com/fd/cloud/serviceapi/**/inter/**/*.class";
        Resource[] resources2 = RESOURCE_PATTERN_RESOLVER.getResources(packageSearchPath);
        for (Resource resource : resources2) {
            System.out.println(resource);
        }
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SwaggerApplication.class,args);
    }
}
