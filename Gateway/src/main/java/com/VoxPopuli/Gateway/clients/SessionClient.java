package com.VoxPopuli.Gateway.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.VoxPopuli.headercontracts.NamingConventions;

import reactor.core.publisher.Mono;

@Component
public class SessionClient {

    private final WebClient webClient;

    public SessionClient(WebClient.Builder builder,
            @Value("${gateway.auth.session-service-url}") String sessionServiceUrl) {
        this.webClient = builder.baseUrl(sessionServiceUrl).build();
    }

    public Mono<SessionResponse> validateSession(String sessionToken) {
        return webClient.get()
                .uri("interior/sessions/validate")
                .header(NamingConventions.sessionId, sessionToken)
                .retrieve()
                .bodyToMono(SessionResponse.class);
    }

    public record SessionResponse(String userId, String alias, boolean valid) {
    }
}