package com.fd.cloud.serviceapi.common.support;


import com.fd.cloud.serviceapi.common.support.enums.BizExceptionEnum;
import com.fd.cloud.serviceapi.common.support.exception.impl.BizException;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     * 请求成功返回码
     */
    public static int SUCCESS_CODE = 0000;

    /**
     * 请求失败返回码
     */
    public static int FAIL_CODE = 500;
    public static int FALLBACK_CODE = 1000;


    /**
     * 通讯状态码
     */
    private int rspCode;
    /**
     * 通讯描述
     */
    private String rspMsg;
    /**
     *  响应结果集
     */
    private T data;

    public BaseResult(){
    }

    public BaseResult(int rspCode, String rspMsg, T data){
        this.rspCode = rspCode;
        this.rspMsg = rspMsg;
        this.data = data;
    }

    public static <T> BaseResult<T> success(T data) {
        BaseResult<T> result = new BaseResult<>(SUCCESS_CODE, "成功", data);
        return result;
    }

    public static <T> BaseResult<T> success(T data, String message) {
        BaseResult<T> result = new BaseResult<>(SUCCESS_CODE, message, data);
        return result;
    }

    public static <T> BaseResult<T> fail(String message) {
        return fail(FAIL_CODE, message);
    }

    public static <T> BaseResult<T> fail(int code, String message) {
        return fail(code, message, null);
    }

    public static <T> BaseResult<T> fail(int code, String message, T data) {
        BaseResult<T> result = new BaseResult<>(code, message, data);
        return result;
    }

    public static <T> boolean isOk(BaseResult<T> result){
        return SUCCESS_CODE==result.getRspCode();
    }


    public static <T> T getResult(BaseResult<T> result){
        if (result==null){
            throw new BizException(BizExceptionEnum.result_null);
        }
        if (!isOk(result)){
            int rspCode = result.rspCode;

            boolean cmsExp = rspCode>=1000&&rspCode<2000;
            boolean umsExp = rspCode>=2000&&rspCode<3000;
            boolean bizExp = cmsExp||umsExp;
            if (bizExp){
                throw new BizException(BizExceptionEnum.result_code_error,result.rspMsg);
            }

            throw new RuntimeException(result.rspMsg);
        }
        return result.data;
    }

    public static <T> BaseResult<T> fallback(String message, T data) {
        BaseResult<T> result = new BaseResult<>(FALLBACK_CODE, message, data);
        return result;
    }
}
