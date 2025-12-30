package com.VoxPopuli.AuthenticationService.services;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import org.springframework.stereotype.Service;
import com.VoxPopuli.AuthenticationService.dtos.LoginResponse;
import com.VoxPopuli.AuthenticationService.dtos.SessionToken;

//move this into a separate microservice, which should be a stateful sessionToken based service.
@Service
public class SessionService {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getEncoder().withoutPadding();
    private static final int BYTES = 32;
    private static final int defaultDuration = 15;

    public SessionToken createSession(LoginResponse loginResponse) {
        return new SessionToken();
    }

    private String generateAccessToken() {
        byte[] bytes = new byte[BYTES];
        secureRandom.nextBytes(bytes);
        return base64Encoder.encodeToString(bytes);
    }

    private long setExpirationFromNow(int minutes) {
        return Instant.now().plus(Duration.ofMinutes(minutes)).getEpochSecond();
    }
}
