package com.fd.swagger.exception;

public class LockException extends RuntimeException {
    public LockException(String message) {
        super(message);
    }
}
