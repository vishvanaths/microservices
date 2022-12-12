package com.vish.gatewayserver.filter;

import com.vish.gatewayserver.utils.FilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class PostFilterAddHeader {

    private static final Logger logger = LoggerFactory.getLogger(PostFilterAddHeader.class);

    @Autowired
    FilterUtils filterUtils;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtils.getCorrelationId(requestHeaders);
                logger.debug("Updated the correlation id to the outbound headers. {}", correlationId);
                exchange.getResponse().getHeaders().add(filterUtils.CORRELATION_ID, correlationId);
            }));
        };
    }
}
