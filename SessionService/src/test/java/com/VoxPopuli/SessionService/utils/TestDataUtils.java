package com.VoxPopuli.SessionService.utils;

import com.VoxPopuli.SessionService.domain.SessionToken;
import com.VoxPopuli.SessionService.dtos.SessionCreationRequest;
import com.VoxPopuli.SessionService.dtos.SessionEndRequest;
import com.VoxPopuli.SessionService.dtos.SessionResponse;
import com.VoxPopuli.SessionService.dtos.UserDto;
import com.VoxPopuli.SessionService.dtos.ValidationRequest;

public final class TestDataUtils {
    private TestDataUtils() {
    }

    public static final SessionCreationRequest createSessionCreationRequest() {
        return new SessionCreationRequest("TestUserId");
    }

    public static final UserDto createUserDto() {
        return new UserDto("TestUserId");
    }

    public static final SessionEndRequest createSessionEndRequest() {
        return new SessionEndRequest("TestSessionId");
    }

    public static final SessionResponse createSessionResponse() {
        return new SessionResponse("TestSessionId");
    }

    public static final ValidationRequest createValidationRequest() {
        return new ValidationRequest("TestSessionId");
    }

    public static final SessionToken createSessionToken(long lifespan) {
        return SessionToken.builder()
                .expiryInSeconds(lifespan)
                .sessionId("sessionId")
                .userId("userId").build();
    }
}
