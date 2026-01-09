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
import com.VoxPopuli.Gateway.dtos.userClient.PassRequest;
import com.VoxPopuli.Gateway.dtos.userClient.RegistrationRequest;



@RequestMapping("api/v1/gateway/user")
public class AuthController {
    @PostMapping("/register")
    public ResponseEntity<SessionToken> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<SessionToken> loginUser(@PathVariable("email") String email) {
        return ResponseEntity.ok().build();
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
