package com.fd.cloud.serviceapi.common.support.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

@AllArgsConstructor
@Getter
public enum RequestExceptionEnum implements ExceptionEnum{
    REQ_MESSAGE_NOT_READ(1001,"请求消息body不正确");
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
