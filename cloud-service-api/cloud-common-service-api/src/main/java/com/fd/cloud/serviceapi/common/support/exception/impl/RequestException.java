package com.fd.cloud.serviceapi.common.support.exception.impl;
import com.fd.cloud.serviceapi.common.support.enums.RequestExceptionEnum;
import com.fd.cloud.serviceapi.common.support.exception.CloudException;

public class RequestException extends CloudException {
    public RequestException(RequestExceptionEnum ree){
        super(ree.getCode(), ree.getMessage());
    }
}
