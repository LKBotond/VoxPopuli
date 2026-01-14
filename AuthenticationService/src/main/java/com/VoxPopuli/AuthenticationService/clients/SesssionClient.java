package com.VoxPopuli.AuthenticationService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.VoxPopuli.sessioncontracts.SessionCreationRequest;
import com.VoxPopuli.sessioncontracts.SessionToken;

@FeignClient(name = "session-service", url = "http://session-service:8080")
public interface SesssionClient {
    @PostMapping("/create")
    public SessionToken createSession(@RequestBody SessionCreationRequest request);

    @DeleteMapping("/delete/{id}")
    public void endSession(@PathVariable("id") String sessionId);

}
