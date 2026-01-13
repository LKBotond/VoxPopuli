package com.VoxPopuli.SessionService.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VoxPopuli.SessionService.dtos.SessionCreationRequest;
import com.VoxPopuli.SessionService.dtos.SessionToken;
import com.VoxPopuli.SessionService.services.SessionService;
import org.springframework.web.bind.annotation.PutMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interior/sesions")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/create")
    public ResponseEntity<SessionToken> createSession(@RequestBody SessionCreationRequest request) {
        SessionToken response = sessionService.createSession(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> endSession(@PathVariable("id") String sessionId) {
        sessionService.endSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/validate")
    public ResponseEntity<SessionCreationRequest> validateSession(@RequestBody SessionToken validateThis) {
        SessionCreationRequest dto = sessionService.validateSession(validateThis);
        return ResponseEntity.ok(dto);
    }

}
