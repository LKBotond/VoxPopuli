package com.VoxPopuli.AuthenticationService.utils;

import com.VoxPopuli.authcontracts.LoginRequest;
import com.VoxPopuli.authcontracts.RegistrationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;
import com.VoxPopuli.usercontracts.UserData;

public final class TestDataUtils {
    private TestDataUtils() {
    }

    public RegistrationRequest createRegistrationRequest() {
        return RegistrationRequest.builder()
                .email("test@test.test")
                .alias("TestAlias").passArray("pass".toCharArray())
                .build();
    }

    public LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .email("test@test.test")
                .pass("pass".toCharArray())
                .build();
    }

    public SessionToken createSessionToken() {
        return SessionToken.builder()
                .sessionId("session123")
                .alias("TestAlias")
                .build();
    }

    public UserData createUserData(String passHash) {
        return UserData.builder()
                .userId("user123")
                .alias("TestAlias")
                .passHash(passHash)
                .build();
    }
}
