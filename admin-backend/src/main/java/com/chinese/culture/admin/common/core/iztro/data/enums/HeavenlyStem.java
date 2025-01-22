package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.Getter;

/**
 * 天干枚举
 */
@Getter
public enum HeavenlyStem {
    JIA("甲"),    // 甲
    YI("乙"),     // 乙
    BING("丙"),   // 丙
    DING("丁"),   // 丁
    WU("戊"),     // 戊
    JI("己"),     // 己
    GENG("庚"),   // 庚
    XIN("辛"),    // 辛
    REN("壬"),    // 壬
    GUI("癸");    // 癸

    private final String chinese;

    HeavenlyStem(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 