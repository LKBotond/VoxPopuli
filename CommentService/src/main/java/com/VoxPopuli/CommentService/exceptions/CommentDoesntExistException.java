package com.VoxPopuli.CommentService.exceptions;

public class CommentDoesntExistException extends RuntimeException {
    public CommentDoesntExistException(String message) {
        super(message);
    }
}
