package com.fd.servicegateway.globalfilter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyAuthFilter implements GlobalFilter, Ordered {

    public static final String token = "token";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       // exchange.get
        List<String> values = exchange.getRequest().getQueryParams()
                .get(token);
        String reqToken = values!=null?values.get(0):null;
        if (reqToken==null){
            return Mono.defer(() -> {
                Exception ex = new ResponseStatusException(HttpStatus.FORBIDDEN, "auth fail");

                Map map = new HashMap<>();
                map.put("HttpStatus.FORBIDDEN","auth fail");
                //return Mono.just(map);
                return Mono.error(ex);
            });
        }

   /*     Map map = new HashMap<>();
        map.put("HttpStatus.FORBIDDEN","auth fail");
        return Mono.just(map);*/

        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
