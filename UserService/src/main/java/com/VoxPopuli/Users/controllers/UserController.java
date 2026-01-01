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
import com.VoxPopuli.Users.dto.LoginResponse;
import com.VoxPopuli.Users.dto.PassUpdateRequest;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.dto.UserDto;
import com.VoxPopuli.Users.helpers.UserMapper;
import com.VoxPopuli.Users.services.UserService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> registerUser(@RequestBody RegistrationRequest request) {
        User user = userService.createUser(request);
        return ResponseEntity.ok(userMapper.toLoginResponse(user));
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<UserDto> loginUser(@PathVariable("email") String email) {
        User user = userService.loginByEmail(email);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @GetMapping("/internal/users/auth/{id}")
    public ResponseEntity<UserDto> getCredentialsById(@PathVariable("id") UUID userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePass(@PathVariable("id") UUID userId, @RequestBody PassUpdateRequest request) {
        userService.changePass(userId, request.getNewPassHash());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
