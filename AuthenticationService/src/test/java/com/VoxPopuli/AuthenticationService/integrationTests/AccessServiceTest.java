package com.VoxPopuli.AuthenticationService.integrationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.VoxPopuli.AuthenticationService.clients.SessionClient;
import com.VoxPopuli.AuthenticationService.clients.UserClient;
import com.VoxPopuli.AuthenticationService.dtos.HashedRegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.LoginRequest;
import com.VoxPopuli.AuthenticationService.dtos.LoginResponse;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequestForAuthService;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequestForUserService;
import com.VoxPopuli.AuthenticationService.dtos.RegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.SessionToken;
import com.VoxPopuli.AuthenticationService.dtos.UserDto;
import com.VoxPopuli.AuthenticationService.exceptions.InvalidPasswordException;
import com.VoxPopuli.AuthenticationService.services.AccessService;
import com.VoxPopuli.AuthenticationService.services.AuthenticationService;
import com.VoxPopuli.AuthenticationService.utils.TestDataUtils;

@SpringBootTest
public class AccessServiceTest {

    @Autowired
    private AccessService accessService;

    @Autowired
    AuthenticationService authService;

    @MockitoBean
    private SessionClient sessionClient;

    @MockitoBean
    private UserClient userClient;

    @Test
    void testSuccesfullLogin() {
        LoginRequest loginRequest = TestDataUtils.createLoginRequest();
        UserDto DtoResponse = hashPassForDto(TestDataUtils.createUserDtoWithUUID());

        // MockitoRules
        when(userClient.getUserCredentialsByEmail(loginRequest.getEmail())).thenReturn(DtoResponse);
        when(sessionClient.createSessionToken(any(LoginResponse.class))).thenReturn(new SessionToken());

        SessionToken sessionToken = accessService.loginUser(loginRequest);

        assertNotNull(sessionToken);
        verify(userClient).getUserCredentialsByEmail(loginRequest.getEmail());
        verify(sessionClient).createSessionToken(DtoResponse.mapToLoginResponse());
    }

    @Test
    void testFailedLogin() {
        LoginRequest loginRequest = TestDataUtils.createLoginRequest();
        loginRequest.setPassword("WrongPass".toCharArray());
        UserDto DtoResponse = hashPassForDto(TestDataUtils.createUserDtoWithUUID());

        // MockitoRules
        when(userClient.getUserCredentialsByEmail(loginRequest.getEmail())).thenReturn(DtoResponse);

        // Tests
        assertThrows(InvalidPasswordException.class, () -> accessService.loginUser(loginRequest));
        verify(sessionClient, never()).createSessionToken(any());
    }

    @Test
    void testRegistration() {
        RegistrationRequest request = TestDataUtils.createRegistrationRequest();
        LoginResponse response = TestDataUtils.createLoginResponse();

        // MockitoRules
        when(userClient.registerUser(any(HashedRegistrationRequest.class))).thenReturn(response);
        when(sessionClient.createSessionToken(any(LoginResponse.class))).thenReturn(new SessionToken());

        SessionToken sessionToken = accessService.registerUser(request);

        // tests
        assertNotNull(sessionToken);
        verify(userClient).registerUser(any(HashedRegistrationRequest.class));
        verify(sessionClient).createSessionToken(any(LoginResponse.class));

    }

    @Test
    void testPassUpdate() {

        // DTO setup
        PassUpdateRequestForAuthService request = TestDataUtils.createPassUpdateRequest();
        PassUpdateRequestForAuthService requestBackup = TestDataUtils.createPassUpdateRequest();
        UserDto editedForTest = TestDataUtils.createUserDtoWithUUID();
        editedForTest.setPassHash(authService.hashPass(request.getOldPassArray()));
        // necesary step because authService wipes senisitve memory
        request.setOldPassArray(requestBackup.getOldPassArray());

        // Mockito rules
        when(userClient.getUserCredentialsById(any(UUID.class))).thenReturn(editedForTest);

        accessService.changePass(request, editedForTest.getUserId());

        verify(userClient).updatePass(eq(editedForTest.getUserId()), any(PassUpdateRequestForUserService.class));

    }

    private UserDto hashPassForDto(UserDto dto) {
        dto.setPassHash(authService.hashPass(dto.getPassHash().toCharArray()));
        return dto;
    }

}
