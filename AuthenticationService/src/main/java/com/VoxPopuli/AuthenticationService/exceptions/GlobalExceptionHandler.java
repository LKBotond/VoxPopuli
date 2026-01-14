package com.VoxPopuli.AuthenticationService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Void> handleFeignException(FeignException ex) {

        log.error("Feign Exception invoked:", ex);

        HttpStatus status;
        switch (ex.status()) {
            case 400:
                status = HttpStatus.BAD_REQUEST;
                break;
            case 401:
                status = HttpStatus.UNAUTHORIZED;
                break;
            case 403:
                status = HttpStatus.FORBIDDEN;
                break;
            case 404:
                status = HttpStatus.NOT_FOUND;
                break;
            case 409:
                status = HttpStatus.CONFLICT;
                break;
            case 500:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            default:
                status = HttpStatus.BAD_GATEWAY;
        }

        return ResponseEntity.status(status).build();
    }

    @ExceptionHandler(InvalidPassException.class)
    public ResponseEntity<Void> handleInvalidPassException(InvalidPassException ex) {
        log.error("InvalidPassException invoked :", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGenericException(Exception ex) {
        log.error("Something went Rather Wrong:", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
