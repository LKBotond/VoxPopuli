package com.VoxPopuli.Users.utils;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;

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

    public static HashedRegistrationRequest createTestRegistrationRequest() {
        return HashedRegistrationRequest.builder()
                .alias("Test")
                .email("Test@test.test")
                .passHash("password").build();
    }

    public static String createLoginRequest() {
        return "Test@test.test";
    }

}
