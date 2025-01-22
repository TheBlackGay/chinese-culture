package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀会合类型
 */
public enum StarConvergenceType {

    /**
     * 同宫会合：两星在同一宫位
     */
    SAME_PALACE("同宫"),

    /**
     * 对宫会合：两星在对宫
     */
    OPPOSITE_PALACE("对宫"),

    /**
     * 三合会合：两星在三合位置
     */
    TRINE_PALACE("三合"),

    /**
     * 六合会合：两星在六合位置
     */
    HARMONY_PALACE("六合"),

    /**
     * 相刑会合：两星在相刑位置
     */
    CONFLICT_PALACE("相刑"),

    /**
     * 无会合关系
     */
    NONE("无会合");

    private final String description;

    StarConvergenceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StarConvergenceType fromDescription(String description) {
        for (StarConvergenceType type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown star convergence type: " + description);
    }
}
