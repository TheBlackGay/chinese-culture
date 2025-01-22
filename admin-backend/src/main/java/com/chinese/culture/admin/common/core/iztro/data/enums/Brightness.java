package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀亮度枚举
 */
public enum Brightness {
    MIAO("庙"),
    WANG("旺"),
    DE("得"),
    LI("利"),
    PING("平"),
    BU("不"),
    XIAN("陷");

    private final String description;

    Brightness(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Brightness fromDescription(String description) {
        for (Brightness brightness : values()) {
            if (brightness.getDescription().equals(description)) {
                return brightness;
            }
        }
        throw new IllegalArgumentException("Invalid brightness description: " + description);
    }
}
