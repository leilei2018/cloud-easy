package com.fd.cloud.serviceapi.ums.support.exception;

import com.fd.cloud.serviceapi.common.support.enums.ExceptionEnum;
import com.fd.cloud.serviceapi.common.support.exception.impl.BizException;
import lombok.ToString;

@ToString
public class UmsException extends BizException{

    public UmsException(ExceptionEnum ums) {
        super(ums.code(), ums.message());
    }
}
