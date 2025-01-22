package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.tyme.lunar.LunarDay;
import com.chinese.culture.admin.common.core.tyme.lunar.LunarHour;
import com.chinese.culture.admin.common.core.tyme.sixtycycle.EarthBranch;
import com.chinese.culture.admin.common.core.tyme.sixtycycle.HeavenStem;
import com.chinese.culture.admin.common.core.tyme.sixtycycle.SixtyCycle;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 天干地支计算工具类
 */
public class StemBranchUtils {

    private static final String[][] SIXTY_CYCLE_PAIRS = {
        {"甲", "子"}, {"乙", "丑"}, {"丙", "寅"}, {"丁", "卯"}, {"戊", "辰"}, {"己", "巳"},
        {"庚", "午"}, {"辛", "未"}, {"壬", "申"}, {"癸", "酉"}, {"甲", "戌"}, {"乙", "亥"},
        {"丙", "子"}, {"丁", "丑"}, {"戊", "寅"}, {"己", "卯"}, {"庚", "辰"}, {"辛", "巳"},
        {"壬", "午"}, {"癸", "未"}, {"甲", "申"}, {"乙", "酉"}, {"丙", "戌"}, {"丁", "亥"},
        {"戊", "子"}, {"己", "丑"}, {"庚", "寅"}, {"辛", "卯"}, {"壬", "辰"}, {"癸", "巳"},
        {"甲", "午"}, {"乙", "未"}, {"丙", "申"}, {"丁", "酉"}, {"戊", "戌"}, {"己", "亥"},
        {"庚", "子"}, {"辛", "丑"}, {"壬", "寅"}, {"癸", "卯"}, {"甲", "辰"}, {"乙", "巳"},
        {"丙", "午"}, {"丁", "未"}, {"戊", "申"}, {"己", "酉"}, {"庚", "戌"}, {"辛", "亥"},
        {"壬", "子"}, {"癸", "丑"}, {"甲", "寅"}, {"乙", "卯"}, {"丙", "辰"}, {"丁", "巳"},
        {"戊", "午"}, {"己", "未"}, {"庚", "申"}, {"辛", "酉"}, {"壬", "戌"}, {"癸", "亥"}
    };

    private static final String[][] DAY_HOUR_STEMS = {
        // 甲己日
        {"丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁"},
        // 乙庚日
        {"戊", "己", "庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己"},
        // 丙辛日
        {"庚", "辛", "壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛"},
        // 丁壬日
        {"壬", "癸", "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"},
        // 戊癸日
        {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸", "甲", "乙"}
    };

    private static final String[] BRANCHES = {
        "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"
    };

    /**
     * 获取天干
     * @param index 序号(0-9)
     * @return 天干名称
     */
    public static String getHeavenStem(int index) {
        return HeavenStem.fromIndex(index).getName();
    }

    /**
     * 获取地支
     * @param index 序号(0-11)
     * @return 地支名称
     */
    public static String getEarthBranch(int index) {
        return EarthBranch.fromIndex(index).getName();
    }

    /**
     * 获取干支组合
     * @param stemIndex 天干序号(0-9)
     * @param branchIndex 地支序号(0-11)
     * @return 干支组合名称
     */
    public static String getStemBranch(int stemIndex, int branchIndex) {
        return HeavenStem.fromIndex(stemIndex).getName() + EarthBranch.fromIndex(branchIndex).getName();
    }

    /**
     * 获取六十甲子序号
     * @param stemIndex 天干序号(0-9)
     * @param branchIndex 地支序号(0-11)
     * @return 六十甲子序号(0-59)
     */
    public static int getSixtyCycleIndex(int stemIndex, int branchIndex) {
        String stem = getHeavenStem(stemIndex);
        String branch = getEarthBranch(branchIndex);
        
        // 遍历六十甲子数组，找到匹配的组合
        for (int i = 0; i < 60; i++) {
            if (SIXTY_CYCLE_PAIRS[i][0].equals(stem) && SIXTY_CYCLE_PAIRS[i][1].equals(branch)) {
                return i;
            }
        }
        
        throw new IllegalArgumentException("Invalid stem-branch combination: " + stem + branch);
    }

    /**
     * 根据六十甲子序号获取干支组合
     * @param index 序号(0-59)
     * @return 干支组合名称
     */
    public static String getSixtyCycleName(int index) {
        return SixtyCycle.fromIndex(index).getName();
    }

    /**
     * 计算五虎遁年起月
     * 五虎遁口诀：甲己之年丙作首，乙庚之年戊为头，丙辛之年庚寅起，丁壬壬寅顺行流，戊癸外寅上朝天
     * @param yearStem 年干
     * @return 正月的天干序号(0-9)
     */
    public static int getMonthStemByYear(String yearStem) {
        switch (yearStem) {
            case "甲":
            case "己":
                return 2; // 丙
            case "乙":
            case "庚":
                return 4; // 戊
            case "丙":
            case "辛":
                return 6; // 庚
            case "丁":
            case "壬":
                return 8; // 壬
            case "戊":
            case "癸":
                return 0; // 甲
            default:
                throw new IllegalArgumentException("Invalid year stem: " + yearStem);
        }
    }

    /**
     * 计算日干支
     * 1. 1900-01-01是甲戌日
     * 2. 每天干支递增一位
     * @param date 公历日期
     * @return 日干支名称
     */
    public static String getDayGanZhi(LocalDate date) {
        LunarDay lunarDay = LunarUtils.fromSolar(date);
        return lunarDay.getSixtyCycle().getName();
    }

    /**
     * 计算时辰干支
     * 日上起时法：
     * 甲己还生甲，乙庚丙作初，
     * 丙辛从戊起，丁壬庚子居，
     * 戊癸何方发，壬子是真途。
     * @param date 公历日期
     * @param time 时间
     * @return 时辰干支名称
     */
    public static String getHourGanZhi(LocalDate date, LocalTime time) {
        int hour = time.getHour();
        LocalDate effectiveDate = date;
        
        // 处理子时跨日的情况
        if (hour >= 23) {
            // 23:00-24:00 属于当天的子时
            hour = 23;
        } else if (hour < 1) {
            // 00:00-01:00 属于前一天的子时
            effectiveDate = date.minusDays(1);
            hour = 23;
        }
        
        // 获取日干
        String dayGanZhi = getDayGanZhi(effectiveDate);
        String dayStem = dayGanZhi.substring(0, 1);
        
        // 确定日干所在的组
        int dayGroup;
        switch (dayStem) {
            case "甲":
            case "己":
                dayGroup = 0;
                break;
            case "乙":
            case "庚":
                dayGroup = 1;
                break;
            case "丙":
            case "辛":
                dayGroup = 2;
                break;
            case "丁":
            case "壬":
                dayGroup = 3;
                break;
            case "戊":
            case "癸":
                dayGroup = 4;
                break;
            default:
                throw new IllegalArgumentException("Invalid day stem: " + dayStem);
        }
        
        // 计算时辰地支序号：子时为0，丑时为1，寅时为2，...
        int hourBranchIndex;
        if (hour == 23) {
            hourBranchIndex = 0; // 子时
        } else {
            hourBranchIndex = ((hour + 1) / 2) % 12;
        }
        
        // 获取时辰天干和地支
        String hourStem = DAY_HOUR_STEMS[dayGroup][hourBranchIndex];
        String hourBranch = BRANCHES[hourBranchIndex];
        
        return hourStem + hourBranch;
    }

    /**
     * 根据天干名称获取其索引
     *
     * @param stem 天干名称
     * @return 天干索引 (0-9)
     */
    public static int getHeavenStemIndex(String stem) {
        switch (stem) {
            case "甲": return 0;
            case "乙": return 1;
            case "丙": return 2;
            case "丁": return 3;
            case "戊": return 4;
            case "己": return 5;
            case "庚": return 6;
            case "辛": return 7;
            case "壬": return 8;
            case "癸": return 9;
            default:
                throw new IllegalArgumentException("Invalid heavenly stem: " + stem);
        }
    }
}
