package com.VoxPopuli.Users.exceptions;

public class AliasTakenException extends RuntimeException {
    public AliasTakenException(String message) {
        super(message);
    }
}