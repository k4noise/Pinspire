package com.k4noise.pinspire.adapter.web.errors;

import org.springframework.http.HttpStatus;

public class FileStorageException extends RuntimeException {
    private final HttpStatus code;
    public FileStorageException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

    public HttpStatus getStatusCode() {
        return this.code;
    }
}