package com.fd.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

public class ObjectFastjsonConverter {

    /**
     * @Description 如将ReqDto转换成vo 或将Do转换成ResDto
     * @param source 原对象
     * @param targetClazz 目标class
     * @param <T>
     * @return
     */
    public static <T> T doObjToObj(Object source,Class<T> targetClazz){
        if (null != source) {
            String s = JSON.toJSONString(source);
            return JSON.parseObject(s, targetClazz);
        }
        return null;
    }

    public static <T> T doObjToObjComplex(Object source, TypeReference<T> typeReference){
        if (null != source) {
            String s = JSON.toJSONString(source);
            return JSON.parseObject(s,typeReference);
        }
        return null;
    }

    /**
     *  @Description 如将List<ReqDto>转换成List<Do> 或将List<Do>转换成List<ResDto>
     * @param sources 原对象
     * @param targetClazz 目标class
     * @param <T>
     * @return
     */
    public static <T> List<T> doListToList(List sources,Class<T> targetClazz){
        if(null != sources && !sources.isEmpty()){
            String s = JSON.toJSONString(sources);
            List<T> targets = JSON.parseArray(s, targetClazz);
            return targets;
        }
        return null;
    }

}
