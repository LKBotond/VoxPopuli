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
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.dto.UserDto;
import com.VoxPopuli.Users.services.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login/{email}")
    public ResponseEntity<UserDto> loginUser(@PathVariable("email") String email) {
        User user = userService.loginByEmail(email);
        UserDto userDto = new UserDto();
        userDto.mapUserToDTO(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationRequest request) {
        User user = userService.createUser(request);
        UserDto userDto = new UserDto();
        userDto.mapUserToDTO(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

}
