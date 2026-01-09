package com.VoxPopuli.Gateway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.Gateway.dtos.sessionClient.SessionToken;
import com.VoxPopuli.Gateway.dtos.sessionClient.SessionUserData;


@FeignClient(name = "session-service", url = "http://user-service:8080")
public interface SessionClient {

    @PostMapping("/interior/sesions/create")
    public SessionToken createSession(@RequestBody SessionUserData request);

    @DeleteMapping("/interior/sesions/delete/{id}")
    public Void endSession(@PathVariable String sessionId);

    @PutMapping("/interior/sesions/validate")
    public SessionUserData validateSession(@RequestBody SessionToken request);
}
