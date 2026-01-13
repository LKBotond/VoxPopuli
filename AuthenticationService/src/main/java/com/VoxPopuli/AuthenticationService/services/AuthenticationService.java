package com.VoxPopuli.AuthenticationService.services;

import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Argon2PassHashingService passHashingService;
    private final UserClient userClient;
    private final SessionClient sessionClient;

    public SessionToken registerUser(RegistrationRequest request) {
        UserData data = userClient.registerUser(buildHashedRegistrationRequest(request));
        return sessionClient.createSession(data);
    }

    public SessionToken loginUser(LoginRequest request) {
        UserData data = userClient.getUserByEmail(request.getEmail());
        passHashingService.verifyPass(request.getPass(), data.getPassHash());
        return sessionClient.createSession(data);
    }

    public void updatePass(PassUpdateRequest request){
        pr
    }

    private boolean authenticatePassword(PassValidationRequest request) {
        return passHashingService.verifyPass(request.getSusPass(), request.getHashedPass());
    }

    private String hashPass(char[] pass) {
        return passHashingService.hashWithArgon2(pass);
    }

    private HashedRegistrationRequest buildHashedRegistrationRequest(RegistrationRequest request) {
        String hashedPass = hashPass(request.getPassArray());
        return HashedRegistrationRequest.builder()
                .alias(request.getAlias())
                .email(request.getEmail())
                .passHash(hashedPass).build();
    }
}
