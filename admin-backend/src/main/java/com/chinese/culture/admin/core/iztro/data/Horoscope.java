package com.chinese.culture.admin.core.iztro.data;

import lombok.Data;
import java.util.List;

/**
 * 运限
 */
@Data
public class Horoscope {
    /**
     * 大限
     */
    private DecadalHoroscope decadal;
    
    /**
     * 流年
     */
    private YearlyHoroscope yearly;
    
    /**
     * 流月
     */
    private MonthlyHoroscope monthly;
    
    /**
     * 流日
     */
    private DailyHoroscope daily;
    
    /**
     * 流时
     */
    private HourlyHoroscope hourly;
    
    @Data
    public static class DecadalHoroscope {
        private int startYear;
        private int endYear;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<String> stars;
        private List<String> mutagens;
    }
    
    @Data
    public static class YearlyHoroscope {
        private int year;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<String> stars;
        private List<String> mutagens;
        private List<String> yearlyDecStars;
    }
    
    @Data
    public static class MonthlyHoroscope {
        private int year;
        private int month;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<String> stars;
        private List<String> mutagens;
    }
    
    @Data
    public static class DailyHoroscope {
        private int year;
        private int month;
        private int day;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<String> stars;
        private List<String> mutagens;
    }
    
    @Data
    public static class HourlyHoroscope {
        private int year;
        private int month;
        private int day;
        private int hour;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<String> stars;
        private List<String> mutagens;
    }
} 