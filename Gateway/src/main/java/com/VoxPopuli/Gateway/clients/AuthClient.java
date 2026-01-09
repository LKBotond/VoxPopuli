package com.VoxPopuli.Gateway.clients;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;

import com.VoxPopuli.Gateway.dtos.authClient.HashedPass;
import com.VoxPopuli.Gateway.dtos.authClient.PassHashRequest;
import com.VoxPopuli.Gateway.dtos.authClient.PassValidationRequest;

@FeignClient(name = "authentication-service", url = "http://authentication-service:8080")
public interface AuthClient {

    @PostMapping("/internal/auth/validate")
    public Void validatePass(@RequestBody PassValidationRequest request);

    @PostMapping("/internal/auth/hash")
    public HashedPass hashPass(@RequestBody PassHashRequest request);
}
