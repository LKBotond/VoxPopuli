package com.VoxPopuli.AuthenticationService.controllers;

import com.VoxPopuli.AuthenticationService.services.AuthenticationService;
import com.VoxPopuli.authcontracts.LoginRequest;
import com.VoxPopuli.authcontracts.PassUpdateRequest;
import com.VoxPopuli.authcontracts.RegistrationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;
import com.VoxPopuli.headercontracts.NamingConventions;

import jakarta.annotation.security.DenyAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
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

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(NamingConventions.sessionId) String sessionId) {
        authService.logoutUser(sessionId);
        return ResponseEntity.ok().build();
    }

    // unimplemented
    @DenyAll
    @PutMapping("/updatepass")
    public ResponseEntity<HashedRegistrationRequest> updatePass(@RequestBody PassUpdateRequest request) {
        return ResponseEntity.ok().build();
    }

}
