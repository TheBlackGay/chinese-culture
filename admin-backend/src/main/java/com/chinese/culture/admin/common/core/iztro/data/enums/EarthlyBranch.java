package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.Getter;

/**
 * 地支枚举
 */
@Getter
public enum EarthlyBranch {
    ZI("子"),      // 子
    CHOU("丑"),    // 丑
    YIN("寅"),     // 寅
    MAO("卯"),     // 卯
    CHEN("辰"),    // 辰
    SI("巳"),      // 巳
    WU("午"),      // 午
    WEI("未"),     // 未
    SHEN("申"),    // 申
    YOU("酉"),     // 酉
    XU("戌"),      // 戌
    HAI("亥");     // 亥

    private final String chinese;

    EarthlyBranch(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 