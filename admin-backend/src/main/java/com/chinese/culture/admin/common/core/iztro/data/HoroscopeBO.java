package com.chinese.culture.admin.common.core.iztro.data;

import lombok.Data;
import java.util.List;

/**
 * 运限数据
 */
@Data
public class HoroscopeBO {
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
        private int startAge;
        private int endAge;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<StarBO> stars;
        private List<String> mutagens;
    }

    @Data
    public static class YearlyHoroscope {
        private int age;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<StarBO> stars;
        private List<String> mutagens;

        /**
         * 流年将前十二神
         */
        private List<String> yearlyDecStars;
    }

    @Data
    public static class MonthlyHoroscope {
        private int month;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<StarBO> stars;
        private List<String> mutagens;
    }

    @Data
    public static class DailyHoroscope {
        private String date;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<StarBO> stars;
        private List<String> mutagens;
    }

    @Data
    public static class HourlyHoroscope {
        private int hour;
        private String heavenlyStem;
        private String earthlyBranch;
        private List<StarBO> stars;
        private List<String> mutagens;
    }
}
