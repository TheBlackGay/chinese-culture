package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 五行枚举:1-金、2-木、3-水、4-火、5-土
 */
@Getter
@AllArgsConstructor
public enum FiveElements {

    METAL(1,"金"),

    WOOD(2,"木"),

    WATER(3,"水"),

    FIRE(4,"火"),

    EARTH(5,"土");

    private final int code;

    private final String description;

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
