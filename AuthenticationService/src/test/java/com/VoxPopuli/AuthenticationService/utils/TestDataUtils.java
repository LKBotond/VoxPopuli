package com.VoxPopuli.AuthenticationService.utils;

import java.util.UUID;

import com.VoxPopuli.AuthenticationService.dtos.LoginRequest;
import com.VoxPopuli.AuthenticationService.dtos.LoginResponse;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequestForAuthService;
import com.VoxPopuli.AuthenticationService.dtos.RegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.UserDto;

public class TestDataUtils {
    private TestDataUtils() {
    }

    public static LoginRequest createLoginRequest() {
        String pass = "password";
        return LoginRequest.builder()
                .email("Test@test.test")
                .password(pass.toCharArray())
                .build();
    }

    public static LoginResponse createLoginResponse() {
        return LoginResponse.builder()
                .alias("TestAlias")
                .userId(UUID.randomUUID())
                .build();
    }

    public static UserDto createUserDtoWithUUID() {

        return UserDto.builder().alias("Test")
                .passHash("password")
                .userId(UUID.randomUUID())
                .build();
    }

    public static RegistrationRequest createRegistrationRequest() {
        String pass = "password";
        return RegistrationRequest.builder()
                .alias("Test")
                .email("Test@test.test")
                .password(pass.toCharArray())
                .build();
    }

    public static PassUpdateRequestForAuthService createPassUpdateRequest() {
        String oldPass = "password";
        String newPass = "newPass";
        return PassUpdateRequestForAuthService.builder()
                .oldPassArray(oldPass.toCharArray())
                .newPassArray(newPass.toCharArray())
                .build();
    }
}
