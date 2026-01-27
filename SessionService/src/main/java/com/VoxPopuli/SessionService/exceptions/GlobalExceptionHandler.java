package com.VoxPopuli.SessionService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<Void> handleInvalidPassException(InvalidSessionException ex) {
        log.error("InvalidSessionException invoked :", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
