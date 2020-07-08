package com.fd.servicegateway;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
@Configuration
public class MyLBInstanceConfig {
    @Bean
   // @ConditionalOnBean(ReactiveDiscoveryClient.class)
    @ConditionalOnMissingBean
    public ServiceInstanceListSupplier fixedServiceInstanceListSupplier(
            ReactiveDiscoveryClient discoveryClient, Environment env,
            ApplicationContext context) {
        //LoadBalancerClientFactory.PROPERTY_NAMEaa
        ServiceInstanceListSupplier.FixedServiceInstanceListSupplier build = ServiceInstanceListSupplier.fixed(env)
                .instance(8000, "user-service")
                .instance(8001, "user-service")
                .build();
        return build;
    }
}
