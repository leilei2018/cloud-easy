package com.fd.eurekaserver.handler;


import com.fd.eurekaserver.annotation.ResponseBodyMessage;
import com.fd.eurekaserver.annotation.SimpleResponseBody;
import com.fd.eurekaserver.model.base.JsonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class SimpleResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getMethod().getReturnType().isAssignableFrom(JsonVo.class)
                && (returnType.hasMethodAnnotation(SimpleResponseBody.class) || AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), SimpleResponseBody.class));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (returnType.hasMethodAnnotation(ResponseBodyMessage.class)){
            String message = returnType.getMethodAnnotation(ResponseBodyMessage.class).value();
            JsonVo<Object> ok = JsonVo.ok(body, message);
            return ok;
        }

        return JsonVo.ok(body);
    }
}

