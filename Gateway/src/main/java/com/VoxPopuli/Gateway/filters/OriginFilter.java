package com.VoxPopuli.Gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.VoxPopuli.headercontracts.NamingConventions;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Component
public class OriginFilter extends AbstractGatewayFilterFactory<OriginFilter.Config> {

    public OriginFilter() {
    super(Config.class);
}
    @Override
    public GatewayFilter apply(Config config){
        
        return(exchange,chain)->{
        String originId= exchange.getRequest().getHeaders().getFirst(NamingConventions.extensionId);

        if(originId==null|| originId.isBlank()||! originId.matches(config.getAcceptedId())){
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }
        return chain.filter(exchange);
    };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    @Getter
    @Setter
    public static class Config {
        private String acceptedId;
    }

}
