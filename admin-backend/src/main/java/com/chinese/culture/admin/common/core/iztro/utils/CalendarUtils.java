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
        
        // 子时：23:00-00:59
        if (hour == 23 || hour == 0) {
            return EarthlyBranch.ZI;
        }
        // 丑时：01:00-02:59
        else if (hour >= 1 && hour < 3) {
            return EarthlyBranch.CHOU;
        }
        // 寅时：03:00-04:59
        else if (hour >= 3 && hour < 5) {
            return EarthlyBranch.YIN;
        }
        // 卯时：05:00-06:59
        else if (hour >= 5 && hour < 7) {
            return EarthlyBranch.MAO;
        }
        // 辰时：07:00-08:59
        else if (hour >= 7 && hour < 9) {
            return EarthlyBranch.CHEN;
        }
        // 巳时：09:00-10:59
        else if (hour >= 9 && hour < 11) {
            return EarthlyBranch.SI;
        }
        // 午时：11:00-12:59
        else if (hour >= 11 && hour < 13) {
            return EarthlyBranch.WU;
        }
        // 未时：13:00-14:59
        else if (hour >= 13 && hour < 15) {
            return EarthlyBranch.WEI;
        }
        // 申时：15:00-16:59
        else if (hour >= 15 && hour < 17) {
            return EarthlyBranch.SHEN;
        }
        // 酉时：17:00-18:59
        else if (hour >= 17 && hour < 19) {
            return EarthlyBranch.YOU;
        }
        // 戌时：19:00-20:59
        else if (hour >= 19 && hour < 21) {
            return EarthlyBranch.XU;
        }
        // 亥时：21:00-22:59
        else {
            return EarthlyBranch.HAI;
        }
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
