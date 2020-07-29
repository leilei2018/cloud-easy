package com.fd.helper.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.fd.helper.dao.mongo.entity")
public class CloudHelperApplication {
    public static void main(String[] args)  {
        SpringApplication.run(CloudHelperApplication.class, args);
    }
}
