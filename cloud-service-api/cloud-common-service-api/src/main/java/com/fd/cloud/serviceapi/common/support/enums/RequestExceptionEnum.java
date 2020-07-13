package com.fd.cloud.serviceapi.common.support.enums;

public class RequestExceptionEnum extends ExceptionEnumAdapter{

    public static final RequestExceptionEnum REQ_MESSAGE_NOT_READ = new RequestExceptionEnum(1000,"请求消息body不正确:REQ_MESSAGE_NOT_READ");

    public RequestExceptionEnum(int code, String message) {
        super(code, message);
    }
}
