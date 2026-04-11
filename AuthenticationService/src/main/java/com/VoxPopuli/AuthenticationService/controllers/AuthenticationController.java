package com.VoxPopuli.AuthenticationService.controllers;

import com.VoxPopuli.AuthenticationService.services.AuthenticationService;
import com.VoxPopuli.authcontracts.LoginRequest;
import com.VoxPopuli.authcontracts.PassUpdateRequest;
import com.VoxPopuli.authcontracts.RegistrationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;

import jakarta.annotation.security.DenyAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        log.info("========================================");
        log.info("RegistrationRequest Alias: " + request.getAlias());
        log.info("RegistrationRequest Email: " + request.getEmail());
        log.info("RegistrationRequest passAray: " + Arrays.toString(request.getPassArray()));
        log.info("========================================");
        return ResponseEntity.ok(authService.registerUser(request));
    }

    // unimplemented
    @DenyAll
    @PutMapping("/updatepass")
    public ResponseEntity<HashedRegistrationRequest> updatePass(@RequestBody PassUpdateRequest request) {
        return ResponseEntity.ok().build();
    }

}
