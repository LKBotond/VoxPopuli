package com.VoxPopuli.Users.utils;

import java.util.UUID;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.AuthenticationRequest;
import com.VoxPopuli.Users.dto.DeletionRequest;
import com.VoxPopuli.Users.dto.RegistrationRequest;

public final class TestDataUtils {

    private TestDataUtils() {
    }

    public static User createTestUser() {
        return User.builder()
                .alias("Test")
                .email("Test@test.test")
                .passHash("password")
                .build();
    }

    public static RegistrationRequest createTestRegistrationRequest() {
        return RegistrationRequest.builder()
                .alias("Test")
                .email("Test@test.test")
                .passHash("password").build();
    }

    public static AuthenticationRequest createAuthenticationRequest() {
        return AuthenticationRequest.builder().email("Test@test.test").build();
    }

    public static DeletionRequest createDeletionRequest(UUID uuid) {
        return DeletionRequest.builder().userId(uuid).build();
    }
}
