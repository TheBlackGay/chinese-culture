package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀属性枚举
 */
public enum StarAttribute {
    YIN("阴"),
    YANG("阳"),
    NEUTRAL("中性");

    private final String description;

    StarAttribute(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StarAttribute fromDescription(String description) {
        for (StarAttribute attribute : values()) {
            if (attribute.getDescription().equals(description)) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Invalid star attribute description: " + description);
    }
}
