package com.fd.eurekaserver;

import com.fd.eurekaserver.annotation.SimpleRestController;
import com.fd.eurekaserver.service.ValidateService;
import com.fd.eurekaserver.vo.LogVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootApplication
//@EnableEurekaServer
@SimpleRestController
@EnableScheduling
@Slf4j
@EnableCaching
public class EurekaServerBootstrapApplication  {

    RedissonClient redissonClient;

    RBloomFilter<Integer> goods_query_bf;

    Random r = new Random();

    public void init(){
        goods_query_bf = redissonClient.getBloomFilter("goods_query_bf");
        goods_query_bf.tryInit(10000,0.01);
        for (int i=0;i<10000;i++){
            goods_query_bf.add(r.nextInt(10000));
        }
    }

    @GetMapping("/add")
    public void add(int q){
        goods_query_bf.add(q);
    }

    @GetMapping("/query")
    public boolean query(Integer key){
        int i = r.nextInt(10000);
        if (key!=null){
            i = key;
        }
        boolean contains = goods_query_bf.contains(i);
        log.info("生成数字为："+i+",是否存在结果："+contains);

        if (!contains){
            log.info("不存在的ID访问，直接返回NULL");
        }else{
            log.info("访问一个存在的ID，先查询缓存，如果不存在，再查询db");
        }

        return contains;
    }

    @Autowired
    private ValidateService validateService;


    @PostConstruct
    public void cache(){

    }

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder()
                .sources(EurekaServerBootstrapApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        ValidateService validateService = ctx.getBean(ValidateService.class);
        LogVo log = new LogVo();
        log.setName("we");
        validateService.vad1(log);
        LogVo logVo1 = validateService.vad1(log);
        System.out.println(logVo1);

        LogVo log2 = new LogVo();
        log2.setName("qwer");
        validateService.vad1(log2);
        LogVo logVo2 = validateService.vad1(log2);
        System.out.println(logVo2);
        //SpringApplication.run(EurekaServerBootstrapApplication.class,args);
        //log.info("---------------用户编号[{}]的提现流水号[{}]的交易流水结果:[{}]",new Object[]{"aa","bb","cc"});



    }
}
