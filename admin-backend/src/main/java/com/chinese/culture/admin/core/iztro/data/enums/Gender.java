package com.chinese.culture.admin.core.iztro.data.enums;

/**
 * 性别枚举
 */
public enum Gender {
    MALE("男"),
    FEMALE("女");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Gender fromDescription(String description) {
        for (Gender gender : values()) {
            if (gender.getDescription().equals(description)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender description: " + description);
    }
} 