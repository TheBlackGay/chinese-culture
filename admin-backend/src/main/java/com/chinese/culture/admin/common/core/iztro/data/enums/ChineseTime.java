package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 时辰枚举
 * 子时分早晚：00:00-01:00 为早子时，23:00-00:00 为晚子时
 */
public enum ChineseTime {
    EARLY_RAT("早子时", "00:00~01:00", 0),
    OX("丑时", "01:00~03:00", 1),
    TIGER("寅时", "03:00~05:00", 2),
    RABBIT("卯时", "05:00~07:00", 3),
    DRAGON("辰时", "07:00~09:00", 4),
    SNAKE("巳时", "09:00~11:00", 5),
    HORSE("午时", "11:00~13:00", 6),
    GOAT("未时", "13:00~15:00", 7),
    MONKEY("申时", "15:00~17:00", 8),
    ROOSTER("酉时", "17:00~19:00", 9),
    DOG("戌时", "19:00~21:00", 10),
    PIG("亥时", "21:00~23:00", 11),
    LATE_RAT("晚子时", "23:00~00:00", 11);

    private final String chinese;
    private final String timeRange;
    private final int timeIndex;

    ChineseTime(String chinese, String timeRange, int timeIndex) {
        this.chinese = chinese;
        this.timeRange = timeRange;
        this.timeIndex = timeIndex;
    }

    public String getChinese() {
        return chinese;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public int getTimeIndex() {
        return timeIndex;
    }

    /**
     * 根据时辰索引获取对应的时辰枚举
     * @param timeIndex 时辰索引
     * @return 时辰枚举
     */
    public static ChineseTime fromTimeIndex(int timeIndex) {
        for (ChineseTime time : values()) {
            if (time.timeIndex == timeIndex) {
                return time;
            }
        }
        throw new IllegalArgumentException("Invalid timeIndex: " + timeIndex);
    }
} 