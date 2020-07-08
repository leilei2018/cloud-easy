package com.fd.eurekaserver.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloJob {

    @Scheduled(cron = "0 2,3,5,10,30,45 * * * ?")
    public void hello(){
        System.out.println(new Date());
    }

}
