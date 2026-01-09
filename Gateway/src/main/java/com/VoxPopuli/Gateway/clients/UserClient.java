package com.VoxPopuli.Gateway.clients;

import java.util.UUID;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.Gateway.dtos.userClient.PassRequest;
import com.VoxPopuli.Gateway.dtos.userClient.RegistrationRequest;
import com.VoxPopuli.Gateway.dtos.userClient.UserData;



@FeignClient(name = "user-service", url = "http://authentication-service:8080")
public interface UserClient {

    @PostMapping("/internal/users/register")
    UserData registerUser(@RequestBody RegistrationRequest request);

    @GetMapping("/internal/users/login/{email}")
    UserData loginUser(@PathVariable("email") String email);

    @GetMapping("/internal/users/auth/{id}")
    UserData getCredentialsById(@PathVariable("id") UUID userId);

    @PutMapping("/internal/users/{id}/password")
    void updatePass(@PathVariable("id") UUID userId, @RequestBody PassRequest request);

    @DeleteMapping("/internal/users/delete/{userId}")
    void deleteUser(@PathVariable("userId") UUID userId);
}
