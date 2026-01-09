package com.VoxPopuli.Gateway.services;

import org.springframework.stereotype.Service;

import com.VoxPopuli.Gateway.clients.AuthClient;
import com.VoxPopuli.Gateway.clients.SessionClient;
import com.VoxPopuli.Gateway.clients.UserClient;
import com.VoxPopuli.Gateway.dtos.authClient.HashedPass;
import com.VoxPopuli.Gateway.dtos.authClient.PassHashRequest;
import com.VoxPopuli.Gateway.dtos.authClient.PassValidationRequest;
import com.VoxPopuli.Gateway.dtos.sessionClient.SessionToken;
import com.VoxPopuli.Gateway.dtos.sessionClient.SessionUserData;
import com.VoxPopuli.Gateway.dtos.userClient.CharPassedRegistrationRequest;
import com.VoxPopuli.Gateway.dtos.userClient.LoginRequest;
import com.VoxPopuli.Gateway.dtos.userClient.RegistrationRequest;
import com.VoxPopuli.Gateway.dtos.userClient.UserData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrchestrator {
    private final SessionClient sessionClient;
    private final UserClient userClient;
    private final AuthClient authClient;

    public SessionToken registerUser(CharPassedRegistrationRequest request) {
        HashedPass pass = authClient.hashPass(new PassHashRequest(request.getPass()));
        UserData userData = userClient.registerUser(buildRegistrationRequest(request, pass));
        return sessionClient.createSession(buildSessionUserData(userData));
    }

    public SessionToken loginUser(LoginRequest request) {
        UserData records = userClient.loginUser(request.getEmail());
        authClient.validatePass(new PassValidationRequest(records.getHashedPass(), request.getPass()));
        return sessionClient.createSession(buildSessionUserData(records));
    }

    public void logout(SessionToken token) {
        sessionClient.endSession(token.getSessionId());
    }

    public void deleteUser(SessionToken token) {
        SessionUserData data = sessionClient.validateSession(token);
        userClient.deleteUser(data.getUserId());
        sessionClient.endSession(token.getSessionId());
    }

    private RegistrationRequest buildRegistrationRequest(CharPassedRegistrationRequest request, HashedPass hashedPass) {
        return RegistrationRequest.builder()
                .alias(request.getAlias())
                .email(request.getEmail())
                .passHash(hashedPass.getHashedPass()).build();
    }

    private SessionUserData buildSessionUserData(UserData data) {
        return SessionUserData.builder().alias(data.getAlias()).userId(data.getUserId()).build();
    }
}
