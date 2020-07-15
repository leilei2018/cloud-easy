package com.fd.servicegateway.globalfilter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.RemoveCachedBodyFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
/**
 * 建议WebFilter，还是GlobalFilter，思考选择
 * WebFilter ==》 javax中的filter
 * GlobalFilter  => 代表springmvc的拦截器，即找到对应得到handler之后调用的
 */
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
                Exception ex = new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");

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
        return HIGHEST_PRECEDENCE;
    }

    public static void main(String[] args) {
        int a = Integer.MIN_VALUE;

        List<GlobalFilter> list = new ArrayList<>();
        list.add(new RemoveCachedBodyFilter());
        list.add(new MyAuthFilter());
        AnnotationAwareOrderComparator.sort(list);

        list.forEach(System.out::println);

    }
}
