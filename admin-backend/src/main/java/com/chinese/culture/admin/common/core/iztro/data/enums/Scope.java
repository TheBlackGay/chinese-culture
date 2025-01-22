package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.Getter;

/**
 * 作用范围枚举
 */
@Getter
public enum Scope {
    ORIGIN("本命盘"),    // 本命盘
    DECADAL("大限盘"),   // 大限盘
    YEARLY("流年盘"),    // 流年盘
    MONTHLY("流月盘"),   // 流月盘
    DAILY("流日盘"),     // 流日盘
    HOURLY("流时盘");    // 流时盘

    private final String chinese;

    Scope(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 