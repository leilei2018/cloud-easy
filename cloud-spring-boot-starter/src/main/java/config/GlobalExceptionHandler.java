package config;

import com.fd.cloud.serviceapi.common.support.BaseResult;
import com.fd.cloud.serviceapi.common.support.exception.CloudException;
import com.fd.cloud.serviceapi.common.support.exception.impl.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 参数校验异常捕获
     * @param req 请求
     * @param e 异常
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public BaseResult<?> constraintViolationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) {
        log.info("web统一异常处理(ConstraintViolationException 参数校验异常...)：url={},exception={}", req.getRequestURL(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder errInfo =  new StringBuilder();
        constraintViolations.forEach(constraintViolation -> {
            errInfo.append(constraintViolation.getMessageTemplate());
            errInfo.append(";");
        });
        String errMsg = errInfo.length() != 0 ? errInfo.substring(0, errInfo.length() - 1) : "";
        return BaseResult.fail(errMsg);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public BaseResult missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("请求缺少参数：", e);
        return BaseResult.fail(e.getMessage());
    }

    /**
     * 必填参数校验异常处理
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResult<?> methodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.info("web统一异常处理(MethodArgumentNotValidException 输入参数异常...)：url={},exception={}", req.getRequestURL(), e);
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        for (ObjectError allError : allErrors) {
            sb.append(allError.getDefaultMessage()).append(";");
        }
        String msg = sb.length() != 0 ? sb.substring(0, sb.length()-1) : "";
        return BaseResult.fail(msg);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public BaseResult<?> bindExceptionHandler(HttpServletRequest req, Exception e) {
        log.info("web统一异常处理(Exception系统异常...)：url={},exception={}", req.getRequestURL(), e);
        log.error("数据绑定异常，输入内容不符，请您重新输入.", e);
        if(e instanceof BindException){
            BindingResult bindingResult = ((BindException) e).getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError allError : allErrors) {
                sb.append(allError.getDefaultMessage()).append(";");
            }
            String msg = sb.length() != 0 ? sb.substring(0, sb.length()-1) : "";
            return BaseResult.fail(msg);
        }
        return BaseResult.fail("数据绑定异常.");
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public BaseResult<?> defaultErrorHandler(HttpServletRequest req, BizException e) {
        log.info("web统一异常处理(Exception系统异常...)：url={},exception={}", req.getRequestURL(), e);
        log.error("未知系统异常,请联系管理员.", e);
        return BaseResult.fail(e.code(),e.message());
    }

    /**
     * 针对exceptin异常做统一处理
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResult<?> defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.info("web统一异常处理(Exception系统异常...)：url={},exception={}", req.getRequestURL(), e);
        log.error("未知系统异常,请联系管理员.", e);
        return BaseResult.fail(500,"系统开小差了,请联系管理员.");
    }
}
