package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.constant.ChineseCalendarConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 天干地支转换工具类
 */
@Slf4j
public class StemBranchConverter {

    private static final Map<String, Integer> HEAVENLY_STEM_MAP = new HashMap<>();
    private static final Map<String, Integer> EARTHLY_BRANCH_MAP = new HashMap<>();

    static {
        // 初始化天干映射
        for (int i = 0; i < ChineseCalendarConstants.HEAVENLY_STEMS.length; i++) {
            HEAVENLY_STEM_MAP.put(ChineseCalendarConstants.HEAVENLY_STEMS[i], i);
        }

        // 初始化地支映射
        for (int i = 0; i < ChineseCalendarConstants.EARTHLY_BRANCHES.length; i++) {
            EARTHLY_BRANCH_MAP.put(ChineseCalendarConstants.EARTHLY_BRANCHES[i], i);
        }
    }

    /**
     * 获取天干索引
     */
    public static int getHeavenlyStemIndex(String stem) {
        return HEAVENLY_STEM_MAP.getOrDefault(stem, -1);
    }

    /**
     * 获取地支索引
     */
    public static int getEarthlyBranchIndex(String branch) {
        return EARTHLY_BRANCH_MAP.getOrDefault(branch, -1);
    }

    /**
     * 根据索引获取天干
     */
    public static String getHeavenlyStem(int index) {
        index = index % 10;
        if (index < 0) {
            index += 10;
        }
        return ChineseCalendarConstants.HEAVENLY_STEMS[index];
    }

    /**
     * 根据索引获取地支
     */
    public static String getEarthlyBranch(int index) {
        index = index % 12;
        if (index < 0) {
            index += 12;
        }
        return ChineseCalendarConstants.EARTHLY_BRANCHES[index];
    }

    /**
     * 获取干支组合
     */
    public static String getStemBranch(int stemIndex, int branchIndex) {
        return getHeavenlyStem(stemIndex) + getEarthlyBranch(branchIndex);
    }

    /**
     * 获取年干
     */
    public static String getYearStem(int year) {
        return getHeavenlyStem((year - 4) % 10);
    }

    /**
     * 获取年支
     */
    public static String getYearBranch(int year) {
        return getEarthlyBranch((year - 4) % 12);
    }

    /**
     * 获取月干
     */
    public static String getMonthStem(String yearStem, int month) {
        int yearStemIndex = getHeavenlyStemIndex(yearStem);
        int baseIndex = (yearStemIndex * 2 + 2) % 10;
        return getHeavenlyStem((baseIndex + month - 1) % 10);
    }

    /**
     * 获取月支
     */
    public static String getMonthBranch(int month) {
        return getEarthlyBranch((month + 1) % 12);
    }

    /**
     * 获取日干
     */
    public static String getDayStem(int year, int month, int day) {
        // 使用计算公式：4 * C + [C / 4] + 5 * y + [y / 4] + [3 * (m + 1) / 5] + d - 3
        int c = year / 100;
        int y = year % 100;
        if (month <= 2) {
            month += 12;
            y--;
        }
        int index = 4 * c + c / 4 + 5 * y + y / 4 + (3 * (month + 1)) / 5 + day - 3;
        return getHeavenlyStem(index % 10);
    }

    /**
     * 获取日支
     */
    public static String getDayBranch(int year, int month, int day) {
        // 使用计算公式：8 * C + [C / 4] + 5 * y + [y / 4] + [3 * (m + 1) / 5] + d + 7 + i
        int c = year / 100;
        int y = year % 100;
        if (month <= 2) {
            month += 12;
            y--;
        }
        int index = 8 * c + c / 4 + 5 * y + y / 4 + (3 * (month + 1)) / 5 + day + 7;
        return getEarthlyBranch(index % 12);
    }

    /**
     * 获取时干
     */
    public static String getHourStem(String dayStem, int hour) {
        int dayStemIndex = getHeavenlyStemIndex(dayStem);
        int baseIndex = (dayStemIndex * 2) % 10;
        return getHeavenlyStem((baseIndex + (hour - 1)) % 10);
    }

    /**
     * 获取时支
     */
    public static String getHourBranch(int hour) {
        return getEarthlyBranch((hour - 1) % 12);
    }
}
