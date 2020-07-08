package com.fd.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Slf4j
public class ReflectUtil {
    public static Field getPkField(Class<?> targetClass, Class<? extends Annotation> pkAnnoClass) {
        while(!"java.lang.Object".equals(targetClass.getName())){
            Field[] declaredFields = targetClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Annotation annotation = field.getAnnotation(pkAnnoClass);
                if (annotation!=null) {
                    return field;
                }
            }
            targetClass = targetClass.getSuperclass();
        }
        return null;
    }

    /**
     * @param <T>
     * @param target 目标对象
     * @param fname 属性名称
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object target, String fname, Class<T> type) {
        T result = null;
        if (target == null
                || fname == null
                || "".equals(fname)) {
            return result;
        }
        Class<?> clazz = target.getClass();
        try {
            String methodName = fname;
            if(!(type.getName().equals("java.lang.Boolean")) &&!(type.getName().equals("boolean"))){
                methodName = "get"
                        + Character.toUpperCase(fname.charAt(0))
                        + fname.substring(1);
            }else {
                if(!"is".equals(fname.substring(0,2))){
                    methodName = "get"
                            + Character.toUpperCase(fname.charAt(0))
                            + fname.substring(1);
                }
            }

            Method method = null;//ReflectionUtils.findMethod(clazz, methodName);

            if (method==null) {
                return null;
            }

            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            result = (T)method.invoke(target);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

}
