package com.VoxPopuli.SessionService.utils;

import com.VoxPopuli.SessionService.domain.SessionDomain;
import com.VoxPopuli.sessioncontracts.InternalUserData;
import com.VoxPopuli.sessioncontracts.SessionToken;

public final class TestDataUtils {
    private TestDataUtils() {
    }

    public static final InternalUserData createSessionCreationRequest() {
        return new InternalUserData("TestUserId", "alias");
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
