package com.fd.cloud.serviceapi.ums.support.exception;

import com.fd.cloud.serviceapi.common.support.enums.ExceptionEnum;
import com.fd.cloud.serviceapi.common.support.exception.CloudException;
import com.fd.cloud.serviceapi.common.support.exception.impl.BizException;
import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
public class UmsException extends BizException{
    public UmsException(int code, String message) {
        super(code, message);
    }
    public UmsException(ExceptionEnum ums) {
        super(ums.code(), ums.message());
    }
}
