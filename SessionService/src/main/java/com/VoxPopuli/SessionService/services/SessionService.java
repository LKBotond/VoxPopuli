package com.VoxPopuli.SessionService.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.VoxPopuli.SessionService.domain.SessionDomain;
import com.VoxPopuli.SessionService.dtos.SessionCreationRequest;
import com.VoxPopuli.SessionService.dtos.SessionToken;
import com.VoxPopuli.SessionService.repositories.RedisRepo;
import com.VoxPopuli.SessionService.exceptions.InvalidSessionException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final RedisRepo redisRepository;

    @Value("${app.session.timeout:600}")
    private long sessionLifetime;

    public SessionToken createSession(SessionCreationRequest sessionCreationRequest) {
        SessionDomain token = buildToken(sessionCreationRequest);
        saveToken(token);
        return new SessionToken(token.getSessionId(), sessionCreationRequest.getAlias());
    }

    public SessionCreationRequest validateSession(SessionToken validationRequest) {
        SessionDomain token = authenticateToken(validationRequest.getSessionId());
        refreshToken(token);
        return new SessionCreationRequest(token.getUserId(), validationRequest.getAlias());
    }

    public void endSession(String sessionId) {
        deleteSession(sessionId);
    }

    public void saveToken(SessionDomain token) {
        redisRepository.save(token);
    }

    private SessionDomain authenticateToken(String sessionId) {
        return redisRepository.findById(sessionId)
                .orElseThrow(() -> new InvalidSessionException("Token does not exist"));
    }

    private SessionDomain refreshToken(SessionDomain token) {
        token.setExpiryInSeconds(sessionLifetime);
        redisRepository.save(token);
        return token;
    }

    private SessionDomain buildToken(SessionCreationRequest request) {
        return SessionDomain.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .expiryInSeconds(sessionLifetime).build();
    }

    private void deleteSession(String sessionId) {
        redisRepository.delete(authenticateToken(sessionId));
    }
}
