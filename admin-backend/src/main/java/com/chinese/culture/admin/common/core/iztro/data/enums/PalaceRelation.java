package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 宫位关系枚举:1-原宫,2-对宫,3-财帛位,4-官禄位
 */
@Getter
@AllArgsConstructor
public enum PalaceRelation {

    ORIGINAL(1, "原宫"),

    OPPOSITE(2, "对宫"),

    WEALTH(3, "财帛位"),

    CAREER(4, "官禄位"),

    ;

    private final int code;

    private final String description;

    public static PalaceRelation fromDescription(String description) {

        for (PalaceRelation relation : values()) {
            if (relation.getDescription().equals(description)) {
                return relation;
            }
        }
        throw new IllegalArgumentException("Invalid palace relation description: " + description);
    }
}
