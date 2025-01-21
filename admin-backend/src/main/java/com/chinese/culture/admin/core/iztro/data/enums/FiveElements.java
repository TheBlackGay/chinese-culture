package com.chinese.culture.admin.core.iztro.data.enums;

/**
 * 五行枚举
 */
public enum FiveElements {
    METAL("金"),
    WOOD("木"),
    WATER("水"),
    FIRE("火"),
    EARTH("土");

    private final String description;

    FiveElements(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static FiveElements fromDescription(String description) {
        for (FiveElements element : values()) {
            if (element.getDescription().equals(description)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Invalid five elements description: " + description);
    }

    /**
     * 获取生我者
     */
    public FiveElements getGeneratingElement() {
        switch (this) {
            case METAL:
                return EARTH;
            case WOOD:
                return WATER;
            case WATER:
                return METAL;
            case FIRE:
                return WOOD;
            case EARTH:
                return FIRE;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    /**
     * 获取克我者
     */
    public FiveElements getRestrictingElement() {
        switch (this) {
            case METAL:
                return FIRE;
            case WOOD:
                return METAL;
            case WATER:
                return EARTH;
            case FIRE:
                return WATER;
            case EARTH:
                return WOOD;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    /**
     * 获取我生者
     */
    public FiveElements getGeneratedElement() {
        switch (this) {
            case METAL:
                return WATER;
            case WOOD:
                return FIRE;
            case WATER:
                return WOOD;
            case FIRE:
                return EARTH;
            case EARTH:
                return METAL;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    /**
     * 获取我克者
     */
    public FiveElements getRestrictedElement() {
        switch (this) {
            case METAL:
                return WOOD;
            case WOOD:
                return EARTH;
            case WATER:
                return FIRE;
            case FIRE:
                return METAL;
            case EARTH:
                return WATER;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
} 