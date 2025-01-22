package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 五行属性枚举
 */
public enum FiveElements {
    METAL("金"),
    WOOD("木"),
    WATER("水"),
    FIRE("火"),
    EARTH("土");

    private final String chinese;

    FiveElements(String chinese) {

        this.chinese = chinese;
    }

    public String getChinese() {

        return chinese;
    }
}
