package com.fd.servicegateway.gatewayfiter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

//已GatewayFilterFactory结尾
@Component
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.MyConfig> {

    static final String keys = "enabled";

    public MyGatewayFilterFactory(){
        super(MyGatewayFilterFactory.MyConfig.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(keys);
    }
    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.DEFAULT;
    }



    @Override
    public GatewayFilter apply(MyConfig config) {
        int a = 2;
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("dodoing"+config.getEnabled());
                return chain.filter(exchange)
                        .then(Mono.fromRunnable(()->{

                            System.out.println("ending"+exchange.getRequest().getURI().getRawPath());

                        }));

            }
        };
    }

    static class MyConfig{

        private String enabled;

        public String getEnabled() {
            return enabled;
        }

        public void setEnabled(String enabled) {
            this.enabled = enabled;
        }
    }


}
