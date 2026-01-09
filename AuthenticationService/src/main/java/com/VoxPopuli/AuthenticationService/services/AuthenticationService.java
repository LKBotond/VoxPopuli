package com.VoxPopuli.AuthenticationService.services;

import org.springframework.stereotype.Service;

import com.VoxPopuli.AuthenticationService.dtos.HashedPass;
import com.VoxPopuli.AuthenticationService.dtos.PassHashRequest;
import com.VoxPopuli.AuthenticationService.dtos.ValidationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final Argon2PassHashingService passHashingService;

    public boolean authenticatePassword(ValidationRequest request) {
        return passHashingService.verifyPass(request.getSusPass(), request.getHashedPass());
    }

    public HashedPass hashPass(PassHashRequest request) {
        return new HashedPass(passHashingService.hashWithArgon2(request.getPass()));
    }

}
