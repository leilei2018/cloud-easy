package com.fd.eurekaserver.handler;


import com.fd.eurekaserver.model.base.JsonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pan
 * @since 2018-08-31
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonVo handleHaolefuException(Exception e) {
        log.error("系统异常：", e);
        return JsonVo.fail(e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public JsonVo missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("请求缺少参数：", e);
        return JsonVo.fail(e.getMessage());
    }

}
