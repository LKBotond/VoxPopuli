package com.VoxPopuli.AuthenticationService.services;

import org.springframework.stereotype.Service;

import com.VoxPopuli.AuthenticationService.dtos.HashedRegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.RegistrationRequest;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Argon2PassHashingService passHashingService;

    public boolean authenticatePassword(String passHash, char[] susPass) {
        return passHashingService.verifyPass(susPass, passHash);
    }

    public HashedRegistrationRequest hashPassForRegistration(RegistrationRequest request) {
        return mapRegistrationRequest(request, passHashingService.hashWithArgon2(request.getPassword()));
    }

    public String hashPass(char[] pass) {
        return passHashingService.hashWithArgon2(pass);
    }

    private HashedRegistrationRequest mapRegistrationRequest(RegistrationRequest request, String hashedPass) {
        return HashedRegistrationRequest.builder()
                .alias(request.getAlias())
                .email(request.getEmail())
                .passHash(hashedPass).build();
    }

}
