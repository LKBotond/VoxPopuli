package com.VoxPopuli.AuthenticationService.controllers;



import com.VoxPopuli.AuthenticationService.dtos.ValidationRequest;
import com.VoxPopuli.AuthenticationService.dtos.HashedPass;
import com.VoxPopuli.AuthenticationService.dtos.PassHashRequest;
import com.VoxPopuli.AuthenticationService.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;

    @PostMapping("/validate")
    public ResponseEntity<Void> loginUser(@RequestBody ValidationRequest loginRequest) {
        if (authService.authenticatePassword(loginRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @PostMapping("/hash")
    public ResponseEntity<HashedPass> registerUser(@RequestBody PassHashRequest request) {
        return ResponseEntity.ok(authService.hashPass(request));
    }

}
