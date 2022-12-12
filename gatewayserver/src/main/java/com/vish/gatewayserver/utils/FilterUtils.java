package com.vish.gatewayserver.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.UUID;

@Service
public class FilterUtils {
    public static String CORRELATION_ID = "bank_co_relation_id";

    public boolean isTraceIdPresent(HttpHeaders requestHeaders) {
        if(requestHeaders.containsKey(CORRELATION_ID) && requestHeaders.get(CORRELATION_ID) != null){
            return true;
        }

        return false;
    }

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if(requestHeaders.containsKey(CORRELATION_ID) && requestHeaders.get(CORRELATION_ID) != null){
            return requestHeaders.get(CORRELATION_ID).stream().findFirst().get();
        }
        return null;
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

    public String getTraceId() {
        return UUID.randomUUID().toString();
    }
}
