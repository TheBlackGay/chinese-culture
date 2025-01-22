package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 性别枚举
 * 性别对应阴阳，男为阳，女为阴
 */
public enum Gender {
    MALE("男", "阳"),
    FEMALE("女", "阴");

    private final String chinese;
    private final String yinYang;

    Gender(String chinese, String yinYang) {
        this.chinese = chinese;
        this.yinYang = yinYang;
    }

    public String getChinese() {
        return chinese;
    }

    public String getYinYang() {
        return yinYang;
    }
} 