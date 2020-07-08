package com.fd.userservice.support.enums;

import com.fd.cloud.serviceapi.common.support.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UmsEnum implements ExceptionEnum {
    REQ_MESSAGE_NOT_READ(2001,"请求消息body不正确");
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
