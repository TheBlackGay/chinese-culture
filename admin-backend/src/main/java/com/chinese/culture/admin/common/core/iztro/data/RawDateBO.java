package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.tyme.lunar.LunarDay;
import com.chinese.culture.admin.common.core.tyme.solar.SolarDay;
import lombok.Data;

/**
 * 原始日期数据
 */
@Data
public class RawDateBO {

    /**
     * 农历日期数据
     */
    private LunarDate lunarDate;

    /**
     * 干支纪年日期数据
     */
    private ChineseDate chineseDate;

    @Data
    public static class LunarDate {

        private int year;

        private int month;

        private int day;

        private boolean leapMonth;

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

        private SolarDay solarDay;

        private LunarDay lunarDay;

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

    }

}
