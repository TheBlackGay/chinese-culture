package com.chinese.culture.admin.core.iztro.calculator;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import com.chinese.culture.admin.core.iztro.data.RawDate;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.tyme.lunar.LunarDay;
import com.tyme.lunar.LunarHour;
import com.tyme.solar.SolarDay;
import com.tyme.solar.SolarTime;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 日期计算工具类
 */
@Slf4j
public class DateCalculator {

    /**
     * 农历到时分秒
     */
    private LunarHour lunarHour;

    /**
     * 阳历到时分秒
     */
    private SolarTime solarTime;


    /**
     * 将阳历日期转换为农历日期
     */
    public static RawDate.LunarDate solar2lunar(String solarDate) {

        Date date = DateUtil.parse(solarDate);
        ChineseDate chineseDate = new ChineseDate(date);

        RawDate.LunarDate lunarDate = new RawDate.LunarDate();
        lunarDate.setYear(chineseDate.getChineseYear());
        lunarDate.setMonth(Integer.parseInt(chineseDate.getChineseMonth()));
        lunarDate.setDay(Integer.parseInt(chineseDate.getChineseDay()));
        lunarDate.setLeapMonth(chineseDate.isLeapMonth());

        return lunarDate;
    }

    /**
     * 获取干支纪年日期
     */
    public static RawDate.ChineseDate getChineseDate(String solarDate, int timeIndex) {

        Date date = DateUtil.parse(solarDate);
        ChineseDate chineseDate = new ChineseDate(date);

        LunarDay lunarDay = LunarDay.fromYmd(chineseDate.getChineseYear(), Integer.parseInt(chineseDate.getChineseMonth()), Integer.parseInt(chineseDate.getChineseDay()));

        RawDate.ChineseDate result = new RawDate.ChineseDate();

        // 设置年干支
        result.setYearGan(lunarDay.getYearSixtyCycle().getHeavenStem().getName());
        result.setYearZhi(lunarDay.getYearSixtyCycle().getEarthBranch().getName());

        // 设置月干支
        result.setMonthGan(lunarDay.getMonthSixtyCycle().getHeavenStem().getName());
        result.setMonthZhi(lunarDay.getMonthSixtyCycle().getEarthBranch().getName());

        // 设置日干支
        result.setDayGan(lunarDay.getSixtyCycle().getHeavenStem().getName());
        result.setDayZhi(lunarDay.getSixtyCycle().getEarthBranch().getName());

        // 设置时干支
        result.setHourEarthlyBranch(calculateHourEarthlyBranch(timeIndex));

        return result;
    }

    /**
     * 计算时干
     */
    private static String calculateHourHeavenlyStem(String dayHeavenlyStem, int timeIndex) {

        HeavenlyStem dayStem = HeavenlyStem.fromDescription(dayHeavenlyStem);
        int baseIndex = (dayStem.ordinal() % 5) * 2;
        int hourStemIndex = (baseIndex + timeIndex) % 10;
        return HeavenlyStem.values()[hourStemIndex].getDescription();
    }

    /**
     * 计算时支
     */
    private static String calculateHourEarthlyBranch(int timeIndex) {

        return EarthlyBranch.values()[timeIndex % 12].getDescription();

    }

    /**
     * 获取时辰范围
     */
    public static String getTimeRange(int timeIndex) {

        String[] timeRanges = {
                "23:00-01:00", "01:00-03:00", "03:00-05:00", "05:00-07:00",
                "07:00-09:00", "09:00-11:00", "11:00-13:00", "13:00-15:00",
                "15:00-17:00", "17:00-19:00", "19:00-21:00", "21:00-23:00"
        };
        return timeRanges[timeIndex % 12];
    }

    /**
     * 计算年龄
     */
    public static int calculateAge(String birthDate) {

        Date birth = DateUtil.parse(birthDate);

        return DateUtil.age(birth, new Date());
    }

    public void calculate(LunarHour lunarHour) {
        this.lunarHour = lunarHour;
        this.solarTime = lunarHour.getSolarTime();
    }

    public LunarHour calculateChineseDate(String birthYear, String birthMonth, String birthDay, String birthHour) {

        try {
            int year = Integer.parseInt(birthYear);
            int month = Integer.parseInt(birthMonth);
            int day = Integer.parseInt(birthDay);
            int hour = Integer.parseInt(birthHour);
            this.solarTime = new SolarTime(year, month, day, hour,0,0);
            return solarTime.getLunarHour();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("日期格式不正确");
        }
    }

}
