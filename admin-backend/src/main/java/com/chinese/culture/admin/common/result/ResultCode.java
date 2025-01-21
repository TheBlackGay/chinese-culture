package com.chinese.culture.admin.common.result;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    
    // 业务异常
    BIRTH_TIME_ERROR(1001, "出生时间错误"),
    HOROSCOPE_CALC_ERROR(1002, "命盘计算错误"),
    PARAM_ERROR(1003, "参数错误"),
    INVALID_PARAM(1001, "参数无效");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
} 