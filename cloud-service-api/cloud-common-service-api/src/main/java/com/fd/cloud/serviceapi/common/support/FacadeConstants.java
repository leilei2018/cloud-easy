package com.fd.cloud.serviceapi.common.support;

public interface FacadeConstants {
    String SERVICE_ENV_PRO = "service.env";

    String SERVICE_ENV_PREFIX = "${";
    String SERVICE_ENV_SUFFIX = ":}";

    /**
     * 服务注册环境，解决在开放环境，服务串改
     * user-service${service.env:}
     */
    String SERVICE_ENV = SERVICE_ENV_PREFIX+SERVICE_ENV_PRO+SERVICE_ENV_SUFFIX;

    String FALLBACK_MESSAGE = "系统繁忙";
}
