package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀类型枚举
 */
public enum StarType {
    MAJOR("主星"),
    MINOR("辅星"),
    ADJECTIVE("杂耀");

    private final String description;

    StarType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StarType fromDescription(String description) {
        for (StarType type : values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid star type description: " + description);
    }
}
