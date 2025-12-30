package com.VoxPopuli.AuthenticationService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.AuthenticationService.dtos.LoginResponse;
import com.VoxPopuli.AuthenticationService.dtos.SessionToken;

@FeignClient(name = "session-service", url = "http://session-service:8080")
public interface SessionClient {
    @PostMapping("/session/create")
    SessionToken createSessionToken(@RequestBody LoginResponse loginResponse);
}
