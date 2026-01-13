package com.VoxPopuli.SessionService.utils;

import com.VoxPopuli.SessionService.domain.SessionDomain;
import com.VoxPopuli.SessionService.dtos.SessionCreationRequest;
import com.VoxPopuli.SessionService.dtos.SessionToken;

public final class TestDataUtils {
    private TestDataUtils() {
    }

    public static final SessionCreationRequest createSessionCreationRequest() {
        return new SessionCreationRequest("TestUserId", "alias");
    }

    public static final String createSessionEndRequest() {
        return "TestSessionId";
    }

    public static final SessionToken createSessionToken() {
        return new SessionToken("TestSessionId", "alias");
    }


    public static final SessionDomain createSessionToken(long lifespan) {
        return SessionDomain.builder()
                .expiryInSeconds(lifespan)
                .sessionId("sessionId")
                .userId("userId").build();
    }
}
