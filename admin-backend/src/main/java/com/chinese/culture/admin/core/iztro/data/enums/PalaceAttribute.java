package com.chinese.culture.admin.core.iztro.data.enums;

/**
 * 宫位属性枚举
 */
public enum PalaceAttribute {
    YIN("阴"),
    YANG("阳");

    private final String description;

    PalaceAttribute(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PalaceAttribute fromDescription(String description) {
        for (PalaceAttribute attribute : values()) {
            if (attribute.getDescription().equals(description)) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Invalid palace attribute description: " + description);
    }
} 