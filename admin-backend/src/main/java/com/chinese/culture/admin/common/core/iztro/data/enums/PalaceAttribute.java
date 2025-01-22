package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 宫位阴阳枚举：0-阴、1-阳
 */
@Getter
@AllArgsConstructor
public enum PalaceAttribute {

    YIN(0, "阴"),

    YANG(1, "阳");

    private final int code;

    private final String description;

    public static PalaceAttribute fromDescription(String description) {

        for (PalaceAttribute attribute : values()) {
            if (attribute.getDescription().equals(description)) {
                return attribute;
            }
        }
        throw new IllegalArgumentException("Invalid palace attribute description: " + description);
    }

}
