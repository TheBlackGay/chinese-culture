package com.chinese.culture.admin.core.iztro.data.enums;

/**
 * 宫位关系枚举
 */
public enum PalaceRelation {
    OPPOSITE("对宫"),
    WEALTH("财帛位"),
    CAREER("官禄位"),
    ORIGINAL("原宫");

    private final String description;

    PalaceRelation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PalaceRelation fromDescription(String description) {
        for (PalaceRelation relation : values()) {
            if (relation.getDescription().equals(description)) {
                return relation;
            }
        }
        throw new IllegalArgumentException("Invalid palace relation description: " + description);
    }
} 