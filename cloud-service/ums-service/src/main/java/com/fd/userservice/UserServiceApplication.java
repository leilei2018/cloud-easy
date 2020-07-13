package com.fd.userservice;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Random;

@SpringBootApplication
@EnableEurekaClient //#没什么作用，标识而已
@MapperScan("com.fd.userservice.dao.mapper")
@EnableFeignClients(basePackages = "com.fd.cloud.serviceapi.**.inter")
public class UserServiceApplication{
    static Random r = new Random();
    static{
        FlowRule fr = new FlowRule();
        fr.setResource("qaz");
        fr.setCount(4);
        //FlowRuleManager.loadRules(Lists.newArrayList(fr));
    }

    public static void main(String[] args)  {
       SpringApplication.run(UserServiceApplication.class, args);
    }
}
