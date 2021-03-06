package com.android.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;

/**
 * 反射工具类
 */
public class ReflexUtil {

    public static <T> T getTypeInstance(@NonNull Object object, @NonNull Class<?> clazz) {
        Class<?> targetClass = getTargetClass(object, clazz);
        if (targetClass != null) {
            try {
                @SuppressWarnings("unchecked")
                T instance = (T) targetClass.newInstance();
                return instance;
            } catch (IllegalAccessException | InstantiationException | ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getTargetClass(@NonNull Object object, @NonNull Class<T> clazz) {
        try {
            Class<T> target = null;
            Type genericSuperclass = object.getClass().getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                for (Type type : actualTypeArguments) {
                    if (type instanceof Class && clazz.isAssignableFrom((Class<T>) type)) {
                        target = (Class<T>) type;
                        break;
                    }
                }
            }
            return target;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

}
