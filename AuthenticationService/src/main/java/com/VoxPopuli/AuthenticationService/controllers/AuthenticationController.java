package com.VoxPopuli.AuthenticationService.controllers;


import com.VoxPopuli.AuthenticationService.services.AuthenticationService;
import com.VoxPopuli.authcontracts.LoginRequest;
import com.VoxPopuli.authcontracts.PassUpdateRequest;
import com.VoxPopuli.authcontracts.RegistrationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;

import jakarta.annotation.security.DenyAll;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<SessionToken> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.loginUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SessionToken> register(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }
    
    //unimplemented
    @DenyAll
    @PutMapping("/updatepass")
    public ResponseEntity<HashedRegistrationRequest> updatePass(@RequestBody PassUpdateRequest request) {
        return ResponseEntity.ok().build();
    }

}
