package com.chinese.culture.admin.core.iztro.data;

import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import lombok.Data;

/**
 * 原始日期数据
 */
@Data
public class RawDate {
    /**
     * 农历日期数据
     */
    private LunarDate lunarDate;
    
    /**
     * 干支纪年日期数据
     */
    private ChineseDate chineseDate;
    
    public LunarDate getLunarDate() {
        return lunarDate;
    }
    
    public void setLunarDate(LunarDate lunarDate) {
        this.lunarDate = lunarDate;
    }
    
    public ChineseDate getChineseDate() {
        return chineseDate;
    }
    
    public void setChineseDate(ChineseDate chineseDate) {
        this.chineseDate = chineseDate;
    }
    
    @Data
    public static class LunarDate {
        private int year;
        private int month;
        private int day;
        private boolean leapMonth;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public boolean isLeapMonth() {
            return leapMonth;
        }

        public void setLeapMonth(boolean leapMonth) {
            this.leapMonth = leapMonth;
        }
    }
    
    @Data
    public static class ChineseDate {
        private int yearGan;
        private int yearZhi;
        private int monthGan;
        private int monthZhi;
        private int dayGan;
        private int dayZhi;
        private int timeGan;
        private int timeZhi;

        public int getYearGan() {
            return yearGan;
        }

        public void setYearGan(int yearGan) {
            this.yearGan = yearGan;
        }

        public int getYearZhi() {
            return yearZhi;
        }

        public void setYearZhi(int yearZhi) {
            this.yearZhi = yearZhi;
        }

        public int getMonthGan() {
            return monthGan;
        }

        public void setMonthGan(int monthGan) {
            this.monthGan = monthGan;
        }

        public int getMonthZhi() {
            return monthZhi;
        }

        public void setMonthZhi(int monthZhi) {
            this.monthZhi = monthZhi;
        }

        public int getDayGan() {
            return dayGan;
        }

        public void setDayGan(int dayGan) {
            this.dayGan = dayGan;
        }

        public int getDayZhi() {
            return dayZhi;
        }

        public void setDayZhi(int dayZhi) {
            this.dayZhi = dayZhi;
        }

        public int getTimeGan() {
            return timeGan;
        }

        public void setTimeGan(int timeGan) {
            this.timeGan = timeGan;
        }

        public int getTimeZhi() {
            return timeZhi;
        }

        public void setTimeZhi(int timeZhi) {
            this.timeZhi = timeZhi;
        }

        public String getYearHeavenlyStem() {
            return HeavenlyStem.fromIndex(yearGan).getDescription();
        }

        public String getYearEarthlyBranch() {
            return EarthlyBranch.fromIndex(yearZhi).getDescription();
        }

        public String getMonthHeavenlyStem() {
            return HeavenlyStem.fromIndex(monthGan).getDescription();
        }

        public String getMonthEarthlyBranch() {
            return EarthlyBranch.fromIndex(monthZhi).getDescription();
        }

        public String getDayHeavenlyStem() {
            return HeavenlyStem.fromIndex(dayGan).getDescription();
        }

        public String getDayEarthlyBranch() {
            return EarthlyBranch.fromIndex(dayZhi).getDescription();
        }

        public String getTimeHeavenlyStem() {
            return HeavenlyStem.fromIndex(timeGan).getDescription();
        }

        public String getTimeEarthlyBranch() {
            return EarthlyBranch.fromIndex(timeZhi).getDescription();
        }
    }

    @Data
    public static class SolarDate {
        private int year;
        private int month;
        private int day;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
} 