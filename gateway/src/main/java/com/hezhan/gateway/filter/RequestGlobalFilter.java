package com.hezhan.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * <p>全局过滤器</p>
 * <p>所有的请求都会过滤</p>
 * <p>可拦截get、post等等类型的请求，然后做逻辑处理</p>
 * @Date 2021/3/4 14:39
 * @Author hezhan
 */
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().toString();
        System.out.println("uri=" + url);
        String method = request.getMethodValue();

        return chain.filter(exchange);
    }

    /**
     * 执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
