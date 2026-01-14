package com.VoxPopuli.Gateway.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.VoxPopuli.authcontracts.LoginRequest;
import com.VoxPopuli.authcontracts.RegistrationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;

import lombok.RequiredArgsConstructor;

@RequestMapping("api/v1/gateway/user")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<SessionToken> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<SessionToken> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok().build();
    }

}
