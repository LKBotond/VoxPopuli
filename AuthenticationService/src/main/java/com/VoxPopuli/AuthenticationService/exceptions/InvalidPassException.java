package com.VoxPopuli.AuthenticationService.exceptions;

public class InvalidPassException extends RuntimeException {
    public InvalidPassException(String message) {
        super(message);
    }
}
