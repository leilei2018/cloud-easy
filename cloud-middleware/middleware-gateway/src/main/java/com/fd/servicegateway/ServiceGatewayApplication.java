package com.fd.servicegateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceSupplier;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class ServiceGatewayApplication {
    @GetMapping("/www")
    public String haha(){
        return "gg";
    }
    public static void main(String[] args) {

      //  ServiceInstanceSupplier

       SpringApplication.run(ServiceGatewayApplication.class,args);

    /*    DefaultServiceInstance instance = new DefaultServiceInstance("aasd","qq","localhost",8080,false);

        List<ServiceInstance> block;
        Environment env = new StandardEnvironment();
        block = ServiceInstanceListSupplier.fixed(env)
                .instance(8000, "user-service")
                .instance(aa8001, "user-service")
                .build().get().blockLast();


        URI uri = LoadBalancerUriTools.reconstructURI(block.get(0), URI.create("http://user-service/haha"));
        System.out.println(uri);*/
    }
}
