package com.fd.eurekaserver.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class JsonVo<T> {
    private Integer code;
    /** 返回信息 */
    private String msg;
    /** 返回结果 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public JsonVo() {
    }

    public JsonVo(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public static <T> JsonVo<T> ok() {
        return new JsonVo<>(0, "SUCCESS", null);
    }

    public static <T> JsonVo<T> ok(T data) {
        return new JsonVo<T>(0, "SUCCESS", data);
    }

    public static <T> JsonVo<T> ok(T data,String message) {
        return new JsonVo<T>(0, message, data);
    }


    public static <T> JsonVo<T> fail() {
        return new JsonVo<>(1, "FAIL", null);
    }

    public static <T> JsonVo<T> fail(String msg) {
        return new JsonVo<>(1, msg, null);
    }

    public static <T> JsonVo<T> fail(Integer code, String msg) {
        return new JsonVo<>(code, msg, null);
    }
}
