package com.VoxPopuli.AuthenticationService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.usercontracts.HashedRegistrationRequest;
import com.VoxPopuli.usercontracts.UserData;

@FeignClient(name = "user-service", url = "http://user-service:8080")
public interface UserClient {
    @PostMapping("/users")
    public UserData register(@RequestBody HashedRegistrationRequest request);

    @GetMapping("/users/{email}")
    public UserData getUserByEmail(@PathVariable("email") String email);

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable("userId") String userId);
}
