package com.VoxPopuli.AuthenticationService.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.VoxPopuli.AuthenticationService.clients.SessionClient;
import com.VoxPopuli.AuthenticationService.clients.UserClient;
import com.VoxPopuli.AuthenticationService.dtos.LoginRequest;
import com.VoxPopuli.AuthenticationService.dtos.LoginResponse;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequestForAuthService;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequestForUserService;
import com.VoxPopuli.AuthenticationService.dtos.RegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.SessionToken;
import com.VoxPopuli.AuthenticationService.dtos.UserDto;
import com.VoxPopuli.AuthenticationService.exceptions.InvalidPasswordException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessService {

    private final AuthenticationService authenticationService;
    private final SessionClient sessionClient;
    private final UserClient userClient;

    public SessionToken loginUser(LoginRequest loginRequest) {
        UserDto userData = userClient.getUserCredentialsByEmail(loginRequest.getEmail());
        if (authenticationService.authenticatePassword(userData.getPassHash(), loginRequest.getPassword())) {
            return sessionClient.createSessionToken(userData.mapToLoginResponse());
        } else {
            throw new InvalidPasswordException("Invalid password for email: " + loginRequest.getEmail());
        }
    }

    public void changePass(PassUpdateRequestForAuthService request, UUID userId) {
        UserDto userData = userClient.getUserCredentialsById(userId);
        if (authenticationService.authenticatePassword(userData.getPassHash(), request.getOldPassArray())) {
            userClient.updatePass(userId, createPassUpdateRequestForUserClient(request.getNewPassArray()));
        } else {
            throw new InvalidPasswordException("Invalid password for id: " + userId);
        }
    }

    public SessionToken registerUser(RegistrationRequest registrationRequest) {
        LoginResponse loginResponse = userClient.registerUser(
                authenticationService.hashPassForRegistration(registrationRequest));
        return sessionClient.createSessionToken(loginResponse);
    }

    private PassUpdateRequestForUserService createPassUpdateRequestForUserClient(char[] newPass) {
        return new PassUpdateRequestForUserService(authenticationService.hashPass(newPass));
    }

}
