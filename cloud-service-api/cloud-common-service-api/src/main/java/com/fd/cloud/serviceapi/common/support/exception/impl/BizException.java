package com.fd.cloud.serviceapi.common.support.exception.impl;

import com.fd.cloud.serviceapi.common.support.enums.BizExceptionEnum;
import com.fd.cloud.serviceapi.common.support.exception.CloudException;

public class BizException extends CloudException {
    public BizException(int code,String message) {
        super(code, message);
    }
    public BizException(BizExceptionEnum exceptionEnum) {
        super(exceptionEnum.code(), exceptionEnum.message());
    }
    public BizException(BizExceptionEnum exceptionEnum,String args) {
        super(exceptionEnum.code(), exceptionEnum.message()+":"+args);
    }
}
