package com.example.library.exception;

public class PostNotFound extends RuntimeException {
    public PostNotFound(Long id) {
        super("Post ID not found: " + id);
    }
}
