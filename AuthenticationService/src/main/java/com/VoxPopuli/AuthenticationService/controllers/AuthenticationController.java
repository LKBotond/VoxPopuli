package com.VoxPopuli.AuthenticationService.controllers;

import com.VoxPopuli.AuthenticationService.dtos.PassValidationRequest;
import com.VoxPopuli.AuthenticationService.dtos.RegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.sessionClient.SessionToken;
import com.VoxPopuli.AuthenticationService.dtos.userClient.HashedRegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.LoginRequest;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequest;
import com.VoxPopuli.AuthenticationService.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<SessionToken> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<SessionToken> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updatepass")
    public ResponseEntity<HashedRegistrationRequest> updatePass(@RequestBody PassUpdateRequest request) {
        return ResponseEntity.ok().build();
    }

}
