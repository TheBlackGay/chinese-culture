package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀亮度枚举
 */
public enum Brightness {
    MIAO("庙"),      // 最旺 [+3]
    WANG("旺"),      // 次旺 [+2]
    DE("得"),        // 次次旺 [+1]
    LI("利"),        // 平 [0]
    PING("平"),      // 稍弱 [-1]
    BU("不"),        // 较弱 [-2]
    XIAN("陷");      // 最弱 [-3]

    private final String chinese;

    Brightness(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 