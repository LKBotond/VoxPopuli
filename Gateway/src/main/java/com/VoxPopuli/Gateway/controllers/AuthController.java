package com.VoxPopuli.Gateway.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.VoxPopuli.Gateway.dtos.sessionClient.SessionToken;
import com.VoxPopuli.Gateway.dtos.userClient.CharPassedRegistrationRequest;
import com.VoxPopuli.Gateway.dtos.userClient.LoginRequest;
import com.VoxPopuli.Gateway.dtos.userClient.PassRequest;
import com.VoxPopuli.Gateway.services.UserOrchestrator;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/gateway/user")
@RequiredArgsConstructor
public class AuthController {
    private final UserOrchestrator orchestrator;

    @PostMapping("/register")
    public ResponseEntity<SessionToken> registerUser(@RequestBody CharPassedRegistrationRequest request) {
        return ResponseEntity.ok(orchestrator.registerUser(request));
    }

    @GetMapping("/login")
    public ResponseEntity<SessionToken> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(orchestrator.loginUser(request));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePass(@PathVariable("id") UUID userId, @RequestBody PassRequest request) {
        
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok().build();
    }

}
