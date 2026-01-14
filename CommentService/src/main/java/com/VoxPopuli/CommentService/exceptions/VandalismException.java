package com.VoxPopuli.CommentService.exceptions;

import java.util.List;

public class VandalismException extends RuntimeException {
    public VandalismException(String message, List<String> caught) {
        super(message + String.join(", ", caught));
    }
}
