package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.tyme.lunar.LunarDay;
import com.chinese.culture.admin.common.core.tyme.lunar.LunarHour;
import com.chinese.culture.admin.common.core.tyme.lunar.LunarMonth;
import com.chinese.culture.admin.common.core.tyme.lunar.LunarYear;
import com.chinese.culture.admin.common.core.tyme.solar.SolarDay;
import com.chinese.culture.admin.common.core.tyme.solar.SolarTerm;
import com.chinese.culture.admin.common.core.tyme.sixtycycle.SixtyCycle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * 农历日期工具类
 * 基于tyme包的实现
 */
public class LunarUtils {

    /**
     * 将阳历日期转换为农历日期
     * @param solarDate 阳历日期
     * @return 农历日期对象
     */
    public static LunarDay fromSolar(LocalDate solarDate) {
        SolarDay solarDay = SolarDay.fromYmd(solarDate.getYear(), solarDate.getMonthValue(), solarDate.getDayOfMonth());
        return solarDay.getLunarDay();
    }

    /**
     * 创建农历日期对象
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param lunarDay 农历日
     * @param isLeapMonth 是否闰月
     * @return 农历日期对象
     */
    public static LunarDay fromLunar(int lunarYear, int lunarMonth, int lunarDay, boolean isLeapMonth) {
        int month = isLeapMonth ? -lunarMonth : lunarMonth;
        return LunarDay.fromYmd(lunarYear, month, lunarDay);
    }

    /**
     * 将农历日期转换为阳历日期
     * @param lunarDay 农历日期对象
     * @return 阳历日期
     */
    public static LocalDate toSolar(LunarDay lunarDay) {
        SolarDay solar = lunarDay.getSolarDay();
        return LocalDate.of(solar.getYear(), solar.getMonth(), solar.getDay());
    }

    /**
     * 将农历日期转换为阳历日期
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param lunarDay 农历日
     * @param isLeapMonth 是否闰月
     * @return 阳历日期
     */
    public static LocalDate toSolar(int lunarYear, int lunarMonth, int lunarDay, boolean isLeapMonth) {
        return toSolar(fromLunar(lunarYear, lunarMonth, lunarDay, isLeapMonth));
    }

    /**
     * 获取时辰
     * @param time 时间
     * @return 时辰名称，例如："子时"、"丑时"等
     */
    public static String getTimeName(LocalTime time) {
        // 获取当前日期，仅用于构造LunarHour对象
        LocalDate now = LocalDate.now();
        LunarDay today = SolarDay.fromYmd(now.getYear(), now.getMonthValue(), now.getDayOfMonth()).getLunarDay();
        LunarHour hour = LunarHour.fromYmdHms(
            today.getYear(),
            today.getMonth(),
            today.getDay(),
            time.getHour(),
            time.getMinute(),
            time.getSecond()
        );
        return hour.getName();
    }

    /**
     * 获取农历年的天干地支
     * @param lunarYear 农历年
     * @return 天干地支，例如："癸卯"
     */
    public static String getYearGanZhi(int lunarYear) {
        LunarYear year = LunarYear.fromYear(lunarYear);
        return year.getSixtyCycle().getName();
    }

    /**
     * 获取农历月的天干地支
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param isLeapMonth 是否闰月
     * @return 天干地支，例如："甲寅"
     */
    public static String getMonthGanZhi(int lunarYear, int lunarMonth, boolean isLeapMonth) {
        int month = isLeapMonth ? -lunarMonth : lunarMonth;
        LunarMonth lunarM = LunarMonth.fromYm(lunarYear, month);
        return lunarM.getSixtyCycle().getName();
    }

    /**
     * 获取农历日的天干地支
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param lunarDay 农历日
     * @param isLeapMonth 是否闰月
     * @return 天干地支，例如："丙子"
     */
    public static String getDayGanZhi(int lunarYear, int lunarMonth, int lunarDay, boolean isLeapMonth) {
        int month = isLeapMonth ? -lunarMonth : lunarMonth;
        LunarDay day = LunarDay.fromYmd(lunarYear, month, lunarDay);
        return day.getSixtyCycle().getName();
    }

    /**
     * 获取时辰的天干地支
     * @param date 日期
     * @param time 时间
     * @return 天干地支，例如："甲子"
     */
    public static String getHourGanZhi(LocalDate date, LocalTime time) {
        SolarDay solarDay = SolarDay.fromYmd(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        LunarDay lunarDay = solarDay.getLunarDay();
        LunarHour hour = LunarHour.fromYmdHms(
            lunarDay.getYear(),
            lunarDay.getMonth(),
            lunarDay.getDay(),
            time.getHour(),
            time.getMinute(),
            time.getSecond()
        );
        return hour.getSixtyCycle().getName();
    }

    /**
     * 获取指定年份的闰月月份，如果没有闰月返回0
     * @param lunarYear 农历年
     * @return 闰月月份，如果没有闰月返回0
     */
    public static int getLeapMonth(int lunarYear) {
        LunarYear year = LunarYear.fromYear(lunarYear);
        return year.getLeapMonth();
    }

    /**
     * 获取指定农历月份的天数
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param isLeapMonth 是否闰月
     * @return 该月天数
     */
    public static int getMonthDays(int lunarYear, int lunarMonth, boolean isLeapMonth) {
        int month = isLeapMonth ? -lunarMonth : lunarMonth;
        LunarMonth lunarM = LunarMonth.fromYm(lunarYear, month);
        return lunarM.getDayCount();
    }

    /**
     * 获取节气名称
     * @param date 阳历日期
     * @return 节气名称，如果不是节气日期则返回null
     */
    public static String getSolarTerm(LocalDate date) {
        SolarDay solarDay = SolarDay.fromYmd(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        SolarTerm term = solarDay.getTerm();
        return term != null ? term.getName() : null;
    }

    /**
     * 获取下一个节气的日期
     * @param date 当前日期
     * @return 下一个节气的日期和名称
     */
    public static String getNextSolarTerm(LocalDate date) {
        LocalDate current = date;
        SolarTerm term = null;
        
        // 向后查找最多60天，直到找到下一个节气
        for (int i = 1; i <= 60; i++) {
            current = current.plusDays(1);
            SolarDay solarDay = SolarDay.fromYmd(current.getYear(), current.getMonthValue(), current.getDayOfMonth());
            term = solarDay.getTerm();
            if (term != null) {
                break;
            }
        }
        
        if (term == null) {
            return null;
        }
        
        return String.format("%d年%d月%d日 %s", 
            current.getYear(), current.getMonthValue(), current.getDayOfMonth(), term.getName());
    }
}
