package com.fd.swagger;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@SpringBootApplication
@Slf4j
@EnableFeignClients(basePackages = "com.fd.cloud.serviceapi.**.inter")
@MapperScan("com.fd.swagger.dao.mapper")
@EnableCaching
public class SwaggerApplication {
    static ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

    @NacosValue(value="author",autoRefreshed=true)
    private String author;

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
