package com.VoxPopuli.AuthenticationService.integrationTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.bouncycastle.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.VoxPopuli.AuthenticationService.clients.SesssionClient;
import com.VoxPopuli.AuthenticationService.clients.UserClient;
import com.VoxPopuli.AuthenticationService.services.Argon2PassHashingService;
import com.VoxPopuli.AuthenticationService.services.AuthenticationService;
import com.VoxPopuli.AuthenticationService.utils.TestDataUtils;
import com.VoxPopuli.authcontracts.LoginRequest;
import com.VoxPopuli.authcontracts.RegistrationRequest;
import com.VoxPopuli.sessioncontracts.SessionCreationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;

@SpringBootTest
public class AccessServiceTest {

    @MockitoBean
    UserClient userClient;

    @MockitoBean
    SesssionClient sessionClient;

    @Autowired
    AuthenticationService authService;

    @Autowired
    Argon2PassHashingService passHashingService;

    @Test
    void testRegistration() {

        // Setup
        RegistrationRequest request = TestDataUtils.createRegistrationRequest();
        String hash = passHashingService
                .hashWithArgon2(Arrays.copyOf(request.getPassArray(), request.getPassArray().length));

        when(userClient.register(any(HashedRegistrationRequest.class)))
                .thenReturn(TestDataUtils.createUserData(hash));
        when(sessionClient.createSession(any(SessionCreationRequest.class)))
                .thenReturn(TestDataUtils.createSessionToken());

        SessionToken token = authService.registerUser(request);

        assertNotNull(token);
        assertThat(token).hasNoNullFieldsOrProperties();
    }

    @Test
    void testLogin() {

        // Setup
        LoginRequest request = TestDataUtils.createLoginRequest();
        String hash = passHashingService
                .hashWithArgon2(Arrays.copyOf(request.getPass(), request.getPass().length));

        when(userClient.getUserByEmail(any(String.class)))
                .thenReturn(TestDataUtils.createUserData(hash));
        when(sessionClient.createSession(any(SessionCreationRequest.class)))
                .thenReturn(TestDataUtils.createSessionToken());

        SessionToken token = authService.loginUser(request);

        assertNotNull(token);
        assertThat(token).hasNoNullFieldsOrProperties();
    }
}
