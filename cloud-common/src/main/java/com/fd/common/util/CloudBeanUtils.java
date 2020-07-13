package com.fd.common.util;

import com.fd.common.exception.CodeException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CloudBeanUtils {
    public static <T> T copyProperties(Object source,Class<T> dest){
        try {
            Constructor<T> c = dest.getDeclaredConstructor(null);
            T t = c.newInstance(null);
            BeanUtils.copyProperties(source,t);
            return t;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new CodeException("CloudBeanUtils#copyProperties exception!");
    }
}
