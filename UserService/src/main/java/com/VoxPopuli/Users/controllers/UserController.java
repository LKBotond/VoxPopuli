package com.VoxPopuli.Users.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.Users.domain.User;
import com.VoxPopuli.Users.dto.AuthenticationRequest;
import com.VoxPopuli.Users.dto.DeletionRequest;
import com.VoxPopuli.Users.dto.RegistrationRequest;
import com.VoxPopuli.Users.dto.UserDto;
import com.VoxPopuli.Users.services.UserService;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody AuthenticationRequest request) {
        User user = userService.loginByEmail(request);
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

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody DeletionRequest request) {
        userService.deleteUser(request);
        return ResponseEntity.ok().build();
    }

}
