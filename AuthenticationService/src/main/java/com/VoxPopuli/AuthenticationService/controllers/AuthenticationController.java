package com.VoxPopuli.AuthenticationService.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VoxPopuli.AuthenticationService.dtos.LoginRequest;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequestForAuthService;
import com.VoxPopuli.AuthenticationService.dtos.RegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.SessionToken;
import com.VoxPopuli.AuthenticationService.services.AccessService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AccessService accessService;

    @PostMapping("/login")
    public ResponseEntity<SessionToken> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accessService.loginUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SessionToken> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(accessService.registerUser(registrationRequest));
    }

    @PutMapping("/users/{id}/passwords")
    public ResponseEntity<Void> changePassForUser(@PathVariable("id") UUID userId,
            @RequestBody PassUpdateRequestForAuthService request) {
        accessService.changePass(request, userId);
        return ResponseEntity.noContent().build();
    }
}
