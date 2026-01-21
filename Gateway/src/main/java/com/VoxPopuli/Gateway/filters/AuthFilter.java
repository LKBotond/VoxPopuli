package com.VoxPopuli.Gateway.filters;

import com.VoxPopuli.Gateway.clients.SessionClient;
import com.VoxPopuli.headercontracts.NamingConventions;
import com.VoxPopuli.sessioncontracts.InternalUserData;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final SessionClient sessionClient;

    public AuthFilter(SessionClient sessionClient) {
        super(Config.class);
        this.sessionClient = sessionClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(NamingConventions.sessionId);

            if (token == null || token.isBlank()) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            return sessionClient.validateSession(token)
                    .flatMap((InternalUserData session) -> {
                        if (session.getUserId().isBlank()) {
                            return onError(exchange, HttpStatus.FORBIDDEN);
                        }
                        var request = exchange.getRequest().mutate()
                                .headers(h -> h.remove(NamingConventions.userId))
                                .headers(h -> h.remove(NamingConventions.aliasId))
                                .header(NamingConventions.userId, session.getUserId())
                                .header(NamingConventions.aliasId, session.getAlias())
                                .build();

                        return chain.filter(exchange.mutate().request(request).build());
                    })
                    .onErrorResume(e -> onError(exchange, HttpStatus.UNAUTHORIZED));
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
    }
}