package com.chinese.culture.admin.core.iztro.data.enums;

/**
 * 星耀亮度枚举
 */
public enum Brightness {
    TEMPLE("庙"),
    STRONG("旺"),
    GAIN("得"),
    BENEFIT("利"),
    NORMAL("平"),
    WEAK("不"),
    TRAPPED("陷");

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