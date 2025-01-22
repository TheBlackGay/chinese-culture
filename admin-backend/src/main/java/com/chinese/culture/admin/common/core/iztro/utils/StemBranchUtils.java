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
        return SixtyCycle.getStemBranch(HeavenStem.fromIndex(stemIndex), EarthBranch.fromIndex(branchIndex)).getIndex();
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
        return LunarUtils.getHourGanZhi(date, time);
    }
}
