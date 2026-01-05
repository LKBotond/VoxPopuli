package com.VoxPopuli.SessionService.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.VoxPopuli.SessionService.domain.SessionToken;
import com.VoxPopuli.SessionService.dtos.SessionCreationRequest;
import com.VoxPopuli.SessionService.dtos.SessionEndRequest;
import com.VoxPopuli.SessionService.dtos.SessionResponse;
import com.VoxPopuli.SessionService.dtos.UserDto;
import com.VoxPopuli.SessionService.dtos.ValidationRequest;
import com.VoxPopuli.SessionService.repositories.RedisRepo;
import com.VoxPopuli.SessionService.exceptions.InvalidSessionException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final RedisRepo redisRepository;

    @Value("${app.session.timeout:600}")
    private long sessionLifetime;

    public SessionResponse createSession(SessionCreationRequest request) {
        SessionToken token = buildToken(request);
        saveToken(token);
        return new SessionResponse(token.getSessionId());
    }

    public UserDto validateSession(ValidationRequest request) {
        SessionToken token = authenticateToken(request.getSessionId());
        refreshToken(token);
        return new UserDto(token.getUserId());
    }

    public void endSession(SessionEndRequest request) {
        deleteSession(request.getSessionId());
    }

    public void saveToken(SessionToken token) {
        redisRepository.save(token);
    }

    private SessionToken authenticateToken(String sessionId) {
        return redisRepository.findById(sessionId)
                .orElseThrow(() -> new InvalidSessionException("Token does not exist"));
    }

    private SessionToken refreshToken(SessionToken token) {
        token.setExpiryInSeconds(sessionLifetime);
        redisRepository.save(token);
        return token;
    }

    private SessionToken buildToken(SessionCreationRequest request) {
        return SessionToken.builder()
                .sessionId(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .expiryInSeconds(sessionLifetime).build();
    }

    private void deleteSession(String sessionId) {
        redisRepository.delete(authenticateToken(sessionId));
    }
}
