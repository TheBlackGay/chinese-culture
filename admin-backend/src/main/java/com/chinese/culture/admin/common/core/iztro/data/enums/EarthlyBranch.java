package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.Getter;

/**
 * 地支枚举
 * 在紫微斗数中，十二宫位按照地支顺序排列，从寅宫开始：
 * 寅宫(0)、卯宫(1)、辰宫(2)、巳宫(3)、午宫(4)、未宫(5)、
 * 申宫(6)、酉宫(7)、戌宫(8)、亥宫(9)、子宫(10)、丑宫(11)
 */
@Getter
public enum EarthlyBranch {
    YIN("寅", 0),     // 寅
    MAO("卯", 1),     // 卯
    CHEN("辰", 2),    // 辰
    SI("巳", 3),      // 巳
    WU("午", 4),      // 午
    WEI("未", 5),     // 未
    SHEN("申", 6),    // 申
    YOU("酉", 7),     // 酉
    XU("戌", 8),      // 戌
    HAI("亥", 9),     // 亥
    ZI("子", 10),     // 子
    CHOU("丑", 11);   // 丑

    private final String chinese;
    private final int palaceIndex; // 宫位索引

    EarthlyBranch(String chinese, int palaceIndex) {
        this.chinese = chinese;
        this.palaceIndex = palaceIndex;
    }

    public String getChinese() {
        return chinese;
    }

    public int getPalaceIndex() {
        return palaceIndex;
    }

    /**
     * 根据宫位索引获取地支
     * @param palaceIndex 宫位索引
     * @return 地支
     */
    public static EarthlyBranch fromPalaceIndex(int palaceIndex) {
        for (EarthlyBranch branch : values()) {
            if (branch.getPalaceIndex() == palaceIndex) {
                return branch;
            }
        }
        throw new IllegalArgumentException("Invalid palace index: " + palaceIndex);
    }
} 