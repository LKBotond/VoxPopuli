package com.VoxPopuli.SessionService.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.VoxPopuli.SessionService.domain.SessionDomain;
import com.VoxPopuli.SessionService.repositories.RedisRepo;
import com.VoxPopuli.sessioncontracts.InternalUserData;
import com.VoxPopuli.sessioncontracts.SessionToken;
import com.VoxPopuli.SessionService.exceptions.InvalidSessionException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {
    private final RedisRepo redisRepository;

    @Value("${app.session.timeout:600}")
    private long sessionLifetime;

    public SessionToken createSession(InternalUserData sessionCreationRequest) {
        SessionDomain token = buildToken(sessionCreationRequest);
        log.debug("Created Token:" + token.getSessionId());
        saveToken(token);
        SessionDomain loaded = authenticateToken(token.getSessionId());
        log.debug("Loaded Token:" + loaded.getSessionId());
        return new SessionToken(token.getSessionId(), sessionCreationRequest.getAlias());
    }

    public InternalUserData validateSession(String tokenString) {
        SessionDomain token = authenticateToken(tokenString);
        refreshToken(token);
        log.debug("Loaded Token sessionId:" + token.getSessionId());
        log.debug("Loaded Token userId:" + token.getUserId());
        return new InternalUserData(token.getUserId(), token.getAlias());
    }

    public void endSession(String sessionId) {
        deleteSession(sessionId);
    }

    public void saveToken(SessionDomain token) {
        redisRepository.save(token);
    }

    private SessionDomain authenticateToken(String sessionId) {
        return redisRepository.findById(sessionId)
                .orElseThrow(() -> new InvalidSessionException("Token does not exist id:" + sessionId));
    }

    private SessionDomain refreshToken(SessionDomain token) {
        token.setExpiryInSeconds(sessionLifetime);
        redisRepository.save(token);
        return token;
    }

    private SessionDomain buildToken(InternalUserData request) {
        return SessionDomain.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .alias(request.getAlias())
                .expiryInSeconds(sessionLifetime).build();
    }

    private void deleteSession(String sessionId) {
        redisRepository.delete(authenticateToken(sessionId));
    }
}
