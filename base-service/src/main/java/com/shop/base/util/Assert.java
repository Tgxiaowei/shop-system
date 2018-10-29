package com.shop.base.util;

import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.shop.base.exception.BizException;

/**
 * Assert工具类
 * @description copy from “org.springframework.util.Assert”
 * @author xiaowei 2018年1月23日 下午1:35:42
 */
public class Assert {

    private Assert() {

    }

    /**
     * obj1与obj2相等，否则抛出BizException
     */
    public static void equals(Object obj1, Object obj2, String message) {

        isTrue(obj1 == null ? obj2 == null : obj1.equals(obj2), message);
    }

    /**
     * expression为true，否则抛出BizException
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BizException(message);
        }
    }

    /**
     * expression为true，否则抛出BizException
     */
    public static void isTrue(boolean expression, String code, String message) {
        if (!expression) {
            throw new BizException(code, message);
        }
    }

    /**
     * object为null，否则抛出BizException
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BizException(message);
        }
    }

    /**
     * object为null，否则抛出BizException
     */
    public static void isNull(Object object, String code, String message) {
        if (object != null) {
            throw new BizException(code, message);
        }
    }

    /**
     * object不为null，否则抛出BizException
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BizException(message);
        }
    }

    /**
     * object不为null，否则抛出BizException
     */
    public static void notNull(Object object, String code, String message) {
        if (object == null) {
            throw new BizException(code, message);
        }
    }

    /**
     * text不为null且长度>0，否则抛出BizException
     */
    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new BizException(message);
        }
    }

    /**
     * str不为null且不为""，否则抛出BizException
     */
    public static void notBlank(String str, String message) {
        if (str == null || str.length() == 0) {
            throw new BizException(message);
        }
    }

    /**
     * text不为null且长度>0，否则抛出BizException
     */
    public static void hasLength(String text, String code, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new BizException(code, message);
        }
    }

    /**
     * array至少包含一个元素，否则抛出BizException
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new BizException(message);
        }
    }

    /**
     * array至少包含一个元素，否则抛出BizException
     */
    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BizException(message);
        }
    }

    /**
     * array至少包含一个元素，否则抛出BizException
     */
    public static void notEmpty(Object[] array, String code, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BizException(code, message);
        }
    }

    /**
     * collection至少包含一个元素，否则抛出BizException
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(message);
        }
    }

    /**
     * collection至少包含一个元素，否则抛出BizException
     */
    public static void notEmpty(Collection<?> collection, String code, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BizException(code, message);
        }
    }

    /**
     * map至少包含一个元素，否则抛出BizException
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BizException(message);
        }
    }

    /**
     * map至少包含一个元素，否则抛出BizException
     */
    public static void notEmpty(Map<?, ?> map, String code, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new BizException(code, message);
        }
    }

    /**
     * array不包含null元素，否则抛出BizException
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BizException(message);
                }
            }
        }
    }

}
