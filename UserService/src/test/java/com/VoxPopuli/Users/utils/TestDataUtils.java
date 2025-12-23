package com.VoxPopuli.Users.utils;

import com.VoxPopuli.Users.domain.User;
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

    public static String createLoginRequest() {
        return "Test@test.test";
    }

}
