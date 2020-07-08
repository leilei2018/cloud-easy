package com.fd.cloud.serviceapi.common.support.exception;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CloudException extends RuntimeException implements Exceptionable {
    private int code;
    private String message;

    @Override
    public int code() {
        return code;
    }
    @Override
    public String message() {
            return message;
        }
}