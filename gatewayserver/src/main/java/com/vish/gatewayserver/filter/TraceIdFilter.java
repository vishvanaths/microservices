package com.vish.gatewayserver.filter;

import com.vish.gatewayserver.utils.FilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class TraceIdFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        
        if(filterUtils.isTraceIdPresent(requestHeaders)){
            logger.info("Request trace id found:" + filterUtils.getCorrelationId(requestHeaders));
        }else{
            String traceId = filterUtils.getTraceId();
            exchange = filterUtils.setCorrelationId(exchange, traceId);
            logger.info("New trace id generated:" + traceId);
        }
        
        return chain.filter(exchange);
    }

}
