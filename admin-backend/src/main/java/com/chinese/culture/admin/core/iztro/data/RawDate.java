package com.chinese.culture.admin.core.iztro.data;

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
        private String yearGan;
        private String yearZhi;
        private String monthGan;
        private String monthZhi;
        private String dayGan;
        private String dayZhi;
        private String hourEarthlyBranch;

        public String getYearGan() {
            return yearGan;
        }

        public void setYearGan(String yearGan) {
            this.yearGan = yearGan;
        }

        public String getYearZhi() {
            return yearZhi;
        }

        public void setYearZhi(String yearZhi) {
            this.yearZhi = yearZhi;
        }

        public String getMonthGan() {
            return monthGan;
        }

        public void setMonthGan(String monthGan) {
            this.monthGan = monthGan;
        }

        public String getMonthZhi() {
            return monthZhi;
        }

        public void setMonthZhi(String monthZhi) {
            this.monthZhi = monthZhi;
        }

        public String getDayGan() {
            return dayGan;
        }

        public void setDayGan(String dayGan) {
            this.dayGan = dayGan;
        }

        public String getDayZhi() {
            return dayZhi;
        }

        public void setDayZhi(String dayZhi) {
            this.dayZhi = dayZhi;
        }

        public String getHourEarthlyBranch() {
            return hourEarthlyBranch;
        }

        public void setHourEarthlyBranch(String hourEarthlyBranch) {
            this.hourEarthlyBranch = hourEarthlyBranch;
        }

        public String getYearHeavenlyStem() {
            return yearGan;
        }
        
        public String getYearEarthlyBranch() {
            return yearZhi;
        }
        
        public String getMonthHeavenlyStem() {
            return monthGan;
        }
        
        public String getMonthEarthlyBranch() {
            return monthZhi;
        }
        
        public String getDayHeavenlyStem() {
            return dayGan;
        }
        
        public String getDayEarthlyBranch() {
            return dayZhi;
        }
    }
} 