package com.fd.cloud.serviceapi.common.support.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class ExceptionEnumAdapter implements ExceptionEnum {
    int code;
    String message;

    @Override
    public int code() {
        return code;
    }
    @Override
    public String message() {
        return message;
    }
}
