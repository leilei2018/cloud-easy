package com.fd.servicegateway.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.cloud.nacos.client.NacosPropertySourceLocator;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.json.YamlJsonParser;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

@Component
public class NacosConfigRouteDefinitionLocator implements RouteDefinitionLocator {

    Logger logger = LoggerFactory.getLogger(NacosConfigRouteDefinitionLocator.class);

    @Autowired
    NacosConfigProperties nacosConfigProperties;

    @Autowired
    NacosConfigManager nacosConfigManager;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    StandardEnvironment env;

    String root = "NACOS";

    @PostConstruct
    public void init(){
        String group = nacosConfigProperties.getGroup();
        String dataId = nacosConfigProperties.getName();
        //@see BootstrapPropertySource
        String psName =
             PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME+"-"+
                     String.join(NacosConfigProperties.COMMAS, dataId, group);
        try {
            nacosConfigManager.getConfigService().addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }
                @Override
                public void receiveConfigInfo(String configInfo) {
                    Properties updateValue = createPropertiesByConfigInfo(configInfo);
                    PropertySource pv = new PropertiesPropertySource(psName,updateValue);
                    env.getPropertySources().replace(psName,pv);

                    applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));

                }
            });
        } catch (NacosException e) {
           logger.error("nacos-route-init-error",e);
        }
    }


    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        GatewayProperties gatewayProperties = createGatewayProperties(env);
        return Flux.fromIterable(gatewayProperties.getRoutes());
    }

    private GatewayProperties createGatewayProperties(Environment env){
        BindResult<GatewayProperties> bind = Binder.get(env).bind("spring.cloud.gateway", GatewayProperties.class);
        return bind.get();
    }

    private Properties createPropertiesByConfigInfo(String str){
        YamlPropertiesFactoryBean yp = new YamlPropertiesFactoryBean();
        yp.setResources(new ByteArrayResource(str.getBytes()));
        return yp.getObject();
    }
}
