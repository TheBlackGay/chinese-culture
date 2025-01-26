package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 五行局数枚举
 * 水二局、木三局、金四局、土五局、火六局
 */
public enum FiveElementsClass {
    WATER(2),  // 水二局
    WOOD(3),   // 木三局
    METAL(4),  // 金四局
    EARTH(5),  // 土五局
    FIRE(6);   // 火六局

    private final int value;

    FiveElementsClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
