package com.VoxPopuli.AuthenticationService.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.AuthenticationService.dtos.HashedRegistrationRequest;
import com.VoxPopuli.AuthenticationService.dtos.LoginResponse;
import com.VoxPopuli.AuthenticationService.dtos.PassUpdateRequestForUserService;
import com.VoxPopuli.AuthenticationService.dtos.UserDto;

@FeignClient(name = "user-service", url = "http://user-service:8080")
public interface UserClient {

    @PostMapping("/users/register")
    LoginResponse registerUser(@RequestBody HashedRegistrationRequest request);

    @GetMapping("/users/login/{email}")
    UserDto getUserCredentials(@PathVariable("email") String email);

    @PutMapping("/users/{id}/passwords")
    Void updatePass(@PathVariable("id") UUID id, PassUpdateRequestForUserService updated);

    @DeleteMapping("/users/delete/{userId}")
    void deleteUser(@PathVariable("userId") UUID userId);

}
