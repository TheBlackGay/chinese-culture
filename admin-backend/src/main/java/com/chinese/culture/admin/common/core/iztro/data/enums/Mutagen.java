package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 四化枚举
 */
public enum Mutagen {
    LU("化禄"),
    QUAN("化权"),
    KE("化科"),
    JI("化忌");

    private final String chinese;

    Mutagen(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 