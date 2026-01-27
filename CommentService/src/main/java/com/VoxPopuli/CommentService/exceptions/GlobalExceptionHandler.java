package com.VoxPopuli.CommentService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CommentDoesntExistException.class)
    public ResponseEntity<String> handleResourceNotFound(CommentDoesntExistException e) {
        log.error("Comment is not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleCensorServiceDown(FeignException e) {
        log.error("CensorService is down read only", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(VandalismException.class)
    public ResponseEntity<String> handleVandalismServiceDown(VandalismException e) {
        log.error("Naughty naughty words found: ", e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIdTempering(IllegalArgumentException e) {
        log.error("Id Tampering detected", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
