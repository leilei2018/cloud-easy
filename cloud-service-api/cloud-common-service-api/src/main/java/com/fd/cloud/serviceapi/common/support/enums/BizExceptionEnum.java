package com.fd.cloud.serviceapi.common.support.enums;

public class BizExceptionEnum extends ExceptionEnumAdapter {

    public static final BizExceptionEnum result_null = new BizExceptionEnum(0001,"BaseResult is null exception");
    public static final BizExceptionEnum result_code_error = new BizExceptionEnum(0002,"Request Fail");

    public BizExceptionEnum(int code, String message) {
        super(code, message);
    }
}
