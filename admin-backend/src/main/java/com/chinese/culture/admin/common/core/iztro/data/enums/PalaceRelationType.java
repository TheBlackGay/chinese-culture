package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 宫位关系类型
 */
public enum PalaceRelationType {

    /**
     * 对宫关系
     */
    OPPOSITE("对宫"),

    /**
     * 三合关系
     */
    TRINE("三合"),

    /**
     * 六合关系 HARMONY SEXTILE
     */
    SEXTILE("六合"),

    /**
     * 四冲关系 CONFLICT SQUARE
     */
    SQUARE("四冲"),

    /**
     * 无特殊关系
     */
    NONE("无关系");

    private final String description;

    PalaceRelationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PalaceRelationType fromDescription(String description) {
        for (PalaceRelationType type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown palace relation type: " + description);
    }
}
