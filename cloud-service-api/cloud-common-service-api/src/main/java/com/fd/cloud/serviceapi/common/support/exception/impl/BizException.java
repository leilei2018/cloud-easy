package com.fd.cloud.serviceapi.common.support.exception.impl;

import com.fd.cloud.serviceapi.common.support.exception.CloudException;

public class BizException extends CloudException {

    public BizException(int code, String message) {
        super(code, message);
    }
}
