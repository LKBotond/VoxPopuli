package com.VoxPopuli.Users.controllers;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.helpers.UserMapper;
import com.VoxPopuli.Users.services.UserService;
import com.VoxPopuli.usercontracts.HashedPass;
import com.VoxPopuli.usercontracts.HashedRegistrationRequest;
import com.VoxPopuli.usercontracts.UserData;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserData> registerUser(@RequestBody HashedRegistrationRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(userMapper.toUserData(user));
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<UserData> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.loginByEmail(email);
        return ResponseEntity.ok(userMapper.toUserData(user));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePass(@PathVariable("id") UUID userId, @RequestBody HashedPass request) {
        userService.changePass(userId, request.getPassHash());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
