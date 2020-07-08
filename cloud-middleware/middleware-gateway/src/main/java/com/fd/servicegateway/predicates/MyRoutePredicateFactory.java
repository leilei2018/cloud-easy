package com.fd.servicegateway.predicates;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public MyRoutePredicateFactory(){
        super(MyRoutePredicateFactory.Config.class);

    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.DEFAULT;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("ads");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate(){
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                System.out.println("my predicate..."+config.isAds());
                return true;
            }
        };
    }

    static class Config{
        private boolean ads;

        public boolean isAds() {
            return ads;
        }

        public void setAds(boolean ads) {
            this.ads = ads;
        }
    }
}
