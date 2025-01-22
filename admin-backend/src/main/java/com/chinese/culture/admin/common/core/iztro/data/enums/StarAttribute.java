package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 阴阳属性枚举
 */
public enum StarAttribute {
    YIN("阴"),
    YANG("阳");

    private final String chinese;

    StarAttribute(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 