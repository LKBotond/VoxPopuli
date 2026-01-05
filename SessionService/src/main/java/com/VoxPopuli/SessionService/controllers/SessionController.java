package com.VoxPopuli.SessionService.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.VoxPopuli.SessionService.dtos.SessionCreationRequest;
import com.VoxPopuli.SessionService.dtos.SessionEndRequest;
import com.VoxPopuli.SessionService.dtos.SessionResponse;
import com.VoxPopuli.SessionService.dtos.UserDto;
import com.VoxPopuli.SessionService.dtos.ValidationRequest;
import com.VoxPopuli.SessionService.services.SessionService;
import org.springframework.web.bind.annotation.PutMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sesions")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/create")
    public ResponseEntity<SessionResponse> createSession(@RequestBody SessionCreationRequest request) {
        SessionResponse response = sessionService.createSession(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> endSession(@RequestBody SessionEndRequest request) {
        sessionService.endSession(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/validate")
    public ResponseEntity<UserDto> validateSession(@RequestBody ValidationRequest request) {
        UserDto dto = sessionService.validateSession(request);
        return ResponseEntity.ok(dto);
    }

}
