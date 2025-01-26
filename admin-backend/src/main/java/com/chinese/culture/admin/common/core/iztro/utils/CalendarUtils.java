package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * 日历工具类
 */
@Slf4j
public class CalendarUtils {

    /**
     * 获取年份天干
     */
    public static HeavenlyStem getYearHeavenlyStem(LocalDateTime dateTime) {
        // 1900年为庚子年
        int baseYear = 1900;
        int targetYear = dateTime.getYear();
        
        // 计算年干索引：1900年为庚(6)
        int stemIndex = (targetYear - baseYear + 6) % 10;
        if (stemIndex < 0) {
            stemIndex += 10;
        }
        
        return HeavenlyStem.values()[stemIndex];
    }

    /**
     * 获取时辰地支
     * 时辰对应关系：
     * 23:00-00:59 子时(0)
     * 01:00-02:59 丑时(1)
     * 03:00-04:59 寅时(2)
     * 05:00-06:59 卯时(3)
     * 07:00-08:59 辰时(4)
     * 09:00-10:59 巳时(5)
     * 11:00-12:59 午时(6)
     * 13:00-14:59 未时(7)
     * 15:00-16:59 申时(8)
     * 17:00-18:59 酉时(9)
     * 19:00-20:59 戌时(10)
     * 21:00-22:59 亥时(11)
     */
    public static EarthlyBranch getHourEarthlyBranch(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        
        // 子时特殊处理：23点和0点都是子时
        if (hour == 23 || hour == 0) {
            return EarthlyBranch.ZI;  // 子时
        }
        
        // 其他时辰：每两个小时为一个时辰
        int branchIndex = (hour + 1) / 2;
        
        // 时辰顺序：子(0)、丑(1)、寅(2)、卯(3)、辰(4)、巳(5)、午(6)、未(7)、申(8)、酉(9)、戌(10)、亥(11)
        return EarthlyBranch.values()[branchIndex];
    }

    /**
     * 获取农历月份(1-12)
     */
    public static int getLunarMonth(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        return LunarUtils.fromSolar(date).getMonth();
    }

    /**
     * 修正农历闰月
     * 闰月前15天按上月算，后15天按下月算
     */
    public static int fixLunarMonth(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        int lunarYear = LunarUtils.fromSolar(date).getYear();
        int lunarMonth = LunarUtils.fromSolar(date).getMonth();
        int lunarDay = LunarUtils.fromSolar(date).getDay();
        
        // 判断是否闰月
        int leapMonth = LunarUtils.getLeapMonth(lunarYear);
        if (leapMonth == Math.abs(lunarMonth)) {
            if (lunarDay <= 15) {
                // 闰月前15天按上月算
                return lunarMonth - 1;
            } else {
                // 闰月后15天按下月算
                return lunarMonth + 1;
            }
        }
        
        return lunarMonth;
    }
} 