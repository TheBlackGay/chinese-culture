package com.chinese.culture.admin.util;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

/**
 * 校验工具类
 */
public class ValidationUtils {

    /**
     * 校验对象是否为空
     */
    public static void notNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 校验字符串是否为空
     */
    public static void notBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 校验集合是否为空
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 校验日期是否合法
     */
    public static void validateDate(LocalDate date, String message) {
        notNull(date, message);
        LocalDate now = LocalDate.now();
        if (date.isAfter(now)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 校验时间是否合法
     */
    public static void validateTime(LocalTime time, String message) {
        notNull(time, message);
    }

    /**
     * 校验日期时间是否合法
     */
    public static void validateDateTime(LocalDateTime dateTime, String message) {
        notNull(dateTime, message);
        LocalDateTime now = LocalDateTime.now();
        if (dateTime.isAfter(now)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 校验数字范围
     */
    public static void validateRange(int value, int min, int max, String message) {
        if (value < min || value > max) {
            throw new BusinessException(ResultCode.PARAM_ERROR, message);
        }
    }

    /**
     * 校验时辰范围（1-12）
     */
    public static void validateHour(int hour, String message) {
        validateRange(hour, 1, 12, message);
    }

    /**
     * 校验性别（male/female）
     */
    public static void validateGender(String gender, String message) {
        notBlank(gender, message);
        if (!"male".equals(gender) && !"female".equals(gender)) {
            throw new BusinessException(ResultCode.PARAM_ERROR, message);
        }
    }
} 