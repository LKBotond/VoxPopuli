package com.VoxPopuli.SessionService.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VoxPopuli.SessionService.services.SessionService;
import com.VoxPopuli.headercontracts.NamingConventions;
import com.VoxPopuli.sessioncontracts.InternalUserData;
import com.VoxPopuli.sessioncontracts.SessionToken;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
@Slf4j
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionToken> createSession(@RequestBody InternalUserData request) {
        SessionToken response = sessionService.createSession(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> endSession(@PathVariable("id") String sessionId) {
        sessionService.endSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<InternalUserData> validateSession(
            @RequestHeader(NamingConventions.sessionId) String tokenString) {
        log.debug("Token String: " + tokenString);
        System.out.flush();
        InternalUserData dto = sessionService.validateSession(tokenString);
        return ResponseEntity.ok(dto);
    }

}
