package com.fd.cloud.serviceapi.cms.support.exception;

import com.fd.cloud.serviceapi.common.support.enums.ExceptionEnum;
import com.fd.cloud.serviceapi.common.support.exception.impl.BizException;

public class CmsException extends BizException {
    public CmsException(int code, String message) {
        super(code, message);
    }

    public CmsException(ExceptionEnum ums) {
        super(ums.code(), ums.message());
    }
}
