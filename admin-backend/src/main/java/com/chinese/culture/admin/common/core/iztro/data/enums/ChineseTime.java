package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 时辰枚举
 * 子时分早晚：00:00-01:00 为早子时，23:00-00:00 为晚子时
 */
public enum ChineseTime {
    EARLY_RAT("早子时", "00:00~01:00"),
    OX("丑时", "01:00~03:00"),
    TIGER("寅时", "03:00~05:00"),
    RABBIT("卯时", "05:00~07:00"),
    DRAGON("辰时", "07:00~09:00"),
    SNAKE("巳时", "09:00~11:00"),
    HORSE("午时", "11:00~13:00"),
    GOAT("未时", "13:00~15:00"),
    MONKEY("申时", "15:00~17:00"),
    ROOSTER("酉时", "17:00~19:00"),
    DOG("戌时", "19:00~21:00"),
    PIG("亥时", "21:00~23:00"),
    LATE_RAT("晚子时", "23:00~00:00");

    private final String chinese;
    private final String timeRange;

    ChineseTime(String chinese, String timeRange) {
        this.chinese = chinese;
        this.timeRange = timeRange;
    }

    public String getChinese() {
        return chinese;
    }

    public String getTimeRange() {
        return timeRange;
    }
} 