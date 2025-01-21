package com.chinese.culture.admin.core.iztro.utils;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 农历阳历转换工具类
 */
@Slf4j
public class CalendarConverter {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // 农历数据基准日期：1900年1月31日
    private static final LocalDate LUNAR_EPOCH = LocalDate.of(1900, 1, 31);
    
    // 农历月份天数数据（从1900年到2100年）
    // 每个long值的低16位表示月份天数（1表示30天，0表示29天）
    // 高4位表示闰月月份（0表示没有闰月）
    private static final long[] LUNAR_INFO = {
        0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
        0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
        0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
        0x14b63, 0x09370, 0x049f8, 0x04970, 0x064b0, 0x168a6, 0x0ea50, 0x06b20, 0x1a6c4, 0x0aae0,
        0x0a2e0, 0x0d2e3, 0x0c960, 0x0d557, 0x0d4a0, 0x0da50, 0x05d55, 0x056a0, 0x0a6d0, 0x055d4,
        0x052d0, 0x0a9b8, 0x0a950, 0x0b4a0, 0x0b6a6, 0x0ad50, 0x055a0, 0x0aba4, 0x0a5b0, 0x052b0,
        0x0b273, 0x06930, 0x07337, 0x06aa0, 0x0ad50, 0x14b55, 0x04b60, 0x0a570, 0x054e4, 0x0d160,
        0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0, 0x0d150, 0x0f252
    };
    
    // 天干
    private static final String[] HEAVENLY_STEMS = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    
    // 地支
    private static final String[] EARTHLY_BRANCHES = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    
    // 生肖
    private static final String[] ZODIAC_ANIMALS = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    
    // 农历数字
    private static final String[] LUNAR_NUMBERS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    
    // 二十四节气
    private static final String[] SOLAR_TERMS = {
        "小寒", "大寒", "立春", "雨水", "惊蛰", "春分",
        "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
        "小暑", "大暑", "立秋", "处暑", "白露", "秋分",
        "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"
    };
    
    // 1900-2100年的节气数据，每个元素存储一年中所有节气的日期信息
    private static final int[][] SOLAR_TERM_INFO = {
        {5, 20, 3, 18, 5, 20, 4, 19, 5, 20, 5, 21},  // 1900
        {5, 20, 4, 19, 5, 20, 4, 19, 5, 20, 5, 21},  // 1901
        {6, 21, 4, 19, 5, 20, 4, 19, 5, 20, 5, 21},  // 1902
        {6, 21, 4, 19, 5, 20, 4, 19, 5, 20, 5, 21},  // 1903
        // ... 更多年份数据 ...
    };
    
    // 农历节日
    private static final Map<String, String> LUNAR_FESTIVALS = new HashMap<>();
    static {
        LUNAR_FESTIVALS.put("1-1", "春节");
        LUNAR_FESTIVALS.put("1-15", "元宵节");
        LUNAR_FESTIVALS.put("2-2", "龙抬头");
        LUNAR_FESTIVALS.put("5-5", "端午节");
        LUNAR_FESTIVALS.put("7-7", "七夕节");
        LUNAR_FESTIVALS.put("7-15", "中元节");
        LUNAR_FESTIVALS.put("8-15", "中秋节");
        LUNAR_FESTIVALS.put("9-9", "重阳节");
        LUNAR_FESTIVALS.put("12-8", "腊八节");
        LUNAR_FESTIVALS.put("12-23", "小年");
        LUNAR_FESTIVALS.put("12-30", "除夕");
    }
    
    // 阳历节日
    private static final Map<String, String> SOLAR_FESTIVALS = new HashMap<>();
    static {
        SOLAR_FESTIVALS.put("1-1", "元旦");
        SOLAR_FESTIVALS.put("2-14", "情人节");
        SOLAR_FESTIVALS.put("3-8", "妇女节");
        SOLAR_FESTIVALS.put("3-12", "植树节");
        SOLAR_FESTIVALS.put("4-1", "愚人节");
        SOLAR_FESTIVALS.put("5-1", "劳动节");
        SOLAR_FESTIVALS.put("5-4", "青年节");
        SOLAR_FESTIVALS.put("6-1", "儿童节");
        SOLAR_FESTIVALS.put("7-1", "建党节");
        SOLAR_FESTIVALS.put("8-1", "建军节");
        SOLAR_FESTIVALS.put("9-10", "教师节");
        SOLAR_FESTIVALS.put("10-1", "国庆节");
        SOLAR_FESTIVALS.put("12-24", "平安夜");
        SOLAR_FESTIVALS.put("12-25", "圣诞节");
    }
    
    // 每日宜忌
    private static final Map<String, List<String>> DAILY_SUITABLE = new HashMap<>();
    private static final Map<String, List<String>> DAILY_UNSUITABLE = new HashMap<>();
    
    static {
        // 初始化宜忌数据（简化版本，实际应该根据天干地支和节气等综合判断）
        DAILY_SUITABLE.put("甲子", Arrays.asList("祭祀", "开光", "求财", "嫁娶"));
        DAILY_SUITABLE.put("乙丑", Arrays.asList("修造", "动土", "开市", "交易"));
        DAILY_SUITABLE.put("丙寅", Arrays.asList("祈福", "求嗣", "开光", "出行"));
        // ... 更多配置
        
        DAILY_UNSUITABLE.put("甲子", Arrays.asList("诉讼", "动土", "开仓", "掘井"));
        DAILY_UNSUITABLE.put("乙丑", Arrays.asList("祭祀", "祈福", "安葬", "行丧"));
        DAILY_UNSUITABLE.put("丙寅", Arrays.asList("开市", "安床", "入宅", "开光"));
        // ... 更多配置
    }
    
    /**
     * 获取农历年的总天数
     * 
     * @param year 农历年份
     * @return 该农历年的总天数
     */
    private static int getLunarYearDays(int year) {
        int total = 0;
        int leapMonth = getLeapMonth(year);
        
        // 计算12个月的天数
        for (int i = 1; i <= 12; i++) {
            total += getLunarMonthDays(year, i);
        }
        
        // 如果有闰月，加上闰月的天数
        if (leapMonth > 0) {
            total += getLeapMonthDays(year);
        }
        
        return total;
    }
    
    /**
     * 获取农历年闰月的天数
     */
    private static int getLeapMonthDays(int year) {
        if (getLeapMonth(year) > 0) {
            long lunarInfo = LUNAR_INFO[year - 1900];
            return ((lunarInfo & 0x10000) == 0) ? 29 : 30;
        }
        return 0;
    }
    
    /**
     * 获取农历年闰月月份
     */
    private static int getLeapMonth(int year) {
        return (int) (LUNAR_INFO[year - 1900] & 0xf);
    }
    
    /**
     * 获取农历月份的天数
     * 
     * @param year 农历年份
     * @param month 农历月份
     * @return 该月天数，如果参数无效返回0
     */
    private static int getLunarMonthDays(int year, int month) {
        long lunarInfo = LUNAR_INFO[year - 1900];
        return ((lunarInfo & (0x10000 >> month)) == 0) ? 29 : 30;
    }
    
    /**
     * 阳历转农历
     */
    public static int[] solarToLunar(int year, int month, int day) {
        // 验证输入
        validateSolarDate(year, month, day);
        
        try {
            // 计算与1900年1月31日相差的天数
            int offset = getDaysSince1900(year, month, day);
            
            // 用offset减去每农历年的天数，计算当前农历年份
            int lunarYear = 1900;
            int daysInLunarYear;
            while (offset >= getLunarYearDays(lunarYear)) {
                daysInLunarYear = getLunarYearDays(lunarYear);
                offset -= daysInLunarYear;
                lunarYear++;
            }
            
            // 计算农历月份
            int lunarMonth = 1;
            int leapMonth = getLeapMonth(lunarYear);
            boolean isLeap = false;
            
            // 处理闰月
            int daysInMonth;
            while (offset >= 0) {
                // 获取当前月份的天数
                if (leapMonth > 0 && lunarMonth == leapMonth + 1 && !isLeap) {
                    daysInMonth = getLeapMonthDays(lunarYear);
                    isLeap = true;
                } else {
                    daysInMonth = getLunarMonthDays(lunarYear, lunarMonth);
                    if (isLeap && lunarMonth == leapMonth + 1) {
                        isLeap = false;
                    }
                }
                
                if (offset < daysInMonth) {
                    break;
                }
                
                offset -= daysInMonth;
                if (!isLeap) {
                    lunarMonth++;
                }
            }
            
            // 计算农历日期
            int lunarDay = offset + 1;
            
            return new int[]{lunarYear, lunarMonth, lunarDay};
            
        } catch (Exception e) {
            log.error("阳历转农历失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.BIRTH_TIME_ERROR);
        }
    }
    
    /**
     * 验证阳历日期
     */
    private static void validateSolarDate(int year, int month, int day) {
        if (year < 1900 || year > 2100) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "年份必须在1900-2100之间");
        }
        if (month < 1 || month > 12) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "月份必须在1-12之间");
        }
        if (day < 1 || day > 31) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "日期必须在1-31之间");
        }
    }
    
    /**
     * 计算从1900年1月31日开始的天数
     */
    private static int getDaysSince1900(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        return (int) ChronoUnit.DAYS.between(LUNAR_EPOCH, date);
    }
    
    /**
     * 农历转阳历
     */
    public static int[] lunarToSolar(int year, int month, int day) {
        // 验证输入
        validateLunarDate(year, month, day);
        
        try {
            // 计算从1900年正月初一到目标日期的总天数
            int offset = 0;
            
            // 计算年的天数
            for (int y = 1900; y < year; y++) {
                offset += getLunarYearDays(y);
            }
            
            // 计算月的天数
            int leapMonth = getLeapMonth(year);
            for (int m = 1; m < month; m++) {
                // 处理闰月
                if (leapMonth > 0 && m == leapMonth) {
                    offset += getLeapMonthDays(year);
                }
                offset += getLunarMonthDays(year, m);
            }
            
            // 加上当月天数
            offset += day - 1;
            
            // 加上基准日期的偏移
            LocalDate baseDate = LUNAR_EPOCH;
            LocalDate resultDate = baseDate.plusDays(offset);
            
            return new int[]{
                resultDate.getYear(),
                resultDate.getMonthValue(),
                resultDate.getDayOfMonth()
            };
            
        } catch (Exception e) {
            log.error("农历转阳历失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.BIRTH_TIME_ERROR);
        }
    }
    
    /**
     * 验证农历日期
     */
    private static void validateLunarDate(int year, int month, int day) {
        if (year < 1900 || year > 2100) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "年份必须在1900-2100之间");
        }
        if (month < 1 || month > 12) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "月份必须在1-12之间");
        }
        int maxDays = getLunarMonthDays(year, month);
        if (day < 1 || day > maxDays) {
            throw new BusinessException(ResultCode.PARAM_ERROR, 
                String.format("日期必须在1-%d之间", maxDays));
        }
    }
    
    /**
     * 获取农历年的天干地支
     * 
     * @param lunarYear 农历年
     * @return 天干地支纪年
     */
    public static String getYearGanZhi(int lunarYear) {
        int offset = lunarYear - 1900 + 36; // 1900年是庚子年
        return HEAVENLY_STEMS[offset % 10] + EARTHLY_BRANCHES[offset % 12] + "年";
    }
    
    /**
     * 获取农历月的天干地支
     * 
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @return 天干地支纪月
     */
    public static String getMonthGanZhi(int lunarYear, int lunarMonth) {
        int yearGan = (lunarYear - 1900 + 36) % 10;
        int monthGan = (yearGan * 2 + lunarMonth) % 10;
        int monthZhi = (lunarMonth + 2) % 12;
        if (monthZhi == 0) monthZhi = 12;
        monthZhi = (monthZhi - 1) % 12;
        return HEAVENLY_STEMS[monthGan] + EARTHLY_BRANCHES[monthZhi] + "月";
    }
    
    /**
     * 获取农历日的天干地支
     * 
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param lunarDay 农历日
     * @return 天干地支纪日
     */
    public static String getDayGanZhi(int lunarYear, int lunarMonth, int lunarDay) {
        // 计算距离1900年1月31日的天数
        LocalDate date = LUNAR_EPOCH.plusDays(getTotalDays(lunarYear, lunarMonth, lunarDay, false));
        long offset = ChronoUnit.DAYS.between(LocalDate.of(1900, 1, 31), date);
        int dayGanIndex = (int) ((offset + 40) % 10); // 1900年1月31日是庚辰日
        int dayZhiIndex = (int) ((offset + 40) % 12);
        return HEAVENLY_STEMS[dayGanIndex] + EARTHLY_BRANCHES[dayZhiIndex] + "日";
    }
    
    /**
     * 获取生肖
     * 
     * @param lunarYear 农历年
     * @return 生肖
     */
    public static String getZodiacAnimal(int lunarYear) {
        return ZODIAC_ANIMALS[(lunarYear - 1900) % 12];
    }
    
    /**
     * 将数字转换为中文数字
     * 
     * @param num 数字
     * @return 中文数字
     */
    private static String numberToChinese(int num) {
        if (num <= 10) {
            return LUNAR_NUMBERS[num];
        } else if (num < 20) {
            return "十" + (num == 10 ? "" : LUNAR_NUMBERS[num % 10]);
        } else {
            return LUNAR_NUMBERS[num / 10] + "十" + (num % 10 == 0 ? "" : LUNAR_NUMBERS[num % 10]);
        }
    }
    
    /**
     * 获取从1900年到指定农历日期的总天数
     */
    private static int getTotalDays(int year, int month, int day, boolean isLeapMonth) {
        int offset = 0;
        
        // 计算年的天数
        for (int y = 1900; y < year; y++) {
            offset += getLunarYearDays(y);
        }
        
        // 计算月的天数
        for (int m = 1; m < month; m++) {
            offset += getLunarMonthDays(year, m);
            if (m == getLeapMonth(year)) {
                offset += getLeapMonthDays(year);
            }
        }
        
        // 如果是闰月
        if (isLeapMonth && getLeapMonth(year) == month) {
            offset += getLunarMonthDays(year, month);
        }
        
        // 加上当月天数
        offset += day - 1;
        
        return offset;
    }
    
    /**
     * 获取指定日期的节气信息
     * 
     * @param solarDate 阳历日期
     * @return 节气名称，如果不是节气日期则返回null
     */
    public static String getSolarTerm(LocalDate solarDate) {
        int year = solarDate.getYear();
        if (year < 1900 || year > 2100) {
            return null;
        }
        
        int month = solarDate.getMonthValue();
        int day = solarDate.getDayOfMonth();
        
        // 每个月有两个节气
        int term1 = 0, term2 = 0;
        
        // 计算节气日期
        if (year >= 1900 && year <= 2100) {
            term1 = SOLAR_TERM_INFO[year - 1900][month - 1];
            term2 = SOLAR_TERM_INFO[year - 1900][month - 1 + 6];
        }
        
        // 返回节气名称
        if (day == term1) {
            return SOLAR_TERMS[(month - 1) * 2];
        } else if (day == term2) {
            return SOLAR_TERMS[(month - 1) * 2 + 1];
        }
        
        return null;
    }
    
    /**
     * 获取下一个节气的日期
     * 
     * @param solarDate 当前阳历日期
     * @return 下一个节气的日期和名称
     */
    public static Map<String, Object> getNextSolarTerm(LocalDate solarDate) {
        LocalDate date = solarDate;
        String termName = null;
        
        // 最多查找60天
        for (int i = 0; i < 60; i++) {
            date = date.plusDays(1);
            termName = getSolarTerm(date);
            if (termName != null) {
                break;
            }
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("date", date.format(DATE_FORMATTER));
        result.put("name", termName);
        return result;
    }
    
    /**
     * 获取指定年月的两个节气日期
     * 
     * @param year 年份
     * @param month 月份
     * @return 该月的两个节气日期
     */
    public static Map<String, String> getMonthSolarTerms(int year, int month) {
        if (year < 1900 || year > 2100 || month < 1 || month > 12) {
            throw new BusinessException(ResultCode.BIRTH_TIME_ERROR.getCode(), "日期超出范围");
        }
        
        Map<String, String> result = new HashMap<>();
        int term1 = SOLAR_TERM_INFO[year - 1900][month - 1];
        int term2 = SOLAR_TERM_INFO[year - 1900][month - 1 + 6];
        
        result.put(SOLAR_TERMS[(month - 1) * 2], 
            String.format("%d-%02d-%02d", year, month, term1));
        result.put(SOLAR_TERMS[(month - 1) * 2 + 1], 
            String.format("%d-%02d-%02d", year, month, term2));
        
        return result;
    }
    
    /**
     * 获取农历节日
     * 
     * @param lunarMonth 农历月
     * @param lunarDay 农历日
     * @return 节日名称，如果不是节日则返回null
     */
    public static String getLunarFestival(int lunarMonth, int lunarDay) {
        return LUNAR_FESTIVALS.get(String.format("%d-%d", lunarMonth, lunarDay));
    }
    
    /**
     * 获取阳历节日
     * 
     * @param solarMonth 阳历月
     * @param solarDay 阳历日
     * @return 节日名称，如果不是节日则返回null
     */
    public static String getSolarFestival(int solarMonth, int solarDay) {
        return SOLAR_FESTIVALS.get(String.format("%d-%d", solarMonth, solarDay));
    }
    
    /**
     * 获取指定日期的所有节日信息（包括农历节日、阳历节日和节气）
     * 
     * @param solarDate 阳历日期
     * @return 节日信息列表
     */
    public static List<String> getAllFestivals(LocalDate solarDate) {
        List<String> festivals = new ArrayList<>();
        
        // 获取阳历节日
        String solarFestival = getSolarFestival(
            solarDate.getMonthValue(), 
            solarDate.getDayOfMonth()
        );
        if (solarFestival != null) {
            festivals.add(solarFestival);
        }
        
        // 获取农历节日
        int[] lunarDate = solarToLunar(solarDate.getYear(), solarDate.getMonthValue(), solarDate.getDayOfMonth());
        String lunarFestival = getLunarFestival(
            lunarDate[1], 
            lunarDate[2]
        );
        if (lunarFestival != null) {
            festivals.add(lunarFestival);
        }
        
        // 获取节气
        String solarTerm = getSolarTerm(solarDate);
        if (solarTerm != null) {
            festivals.add(solarTerm);
        }
        
        return festivals;
    }
    
    // 五行
    private static final String[] FIVE_ELEMENTS = {"金", "木", "水", "火", "土"};
    
    // 天干五行
    private static final String[] HEAVENLY_STEM_ELEMENTS = {"木", "木", "火", "火", "土", "土", "金", "金", "水", "水"};
    
    // 地支五行
    private static final String[] EARTHLY_BRANCH_ELEMENTS = {"水", "土", "木", "木", "土", "火", "火", "土", "金", "金", "土", "水"};
    
    /**
     * 获取天干的五行属性
     * 
     * @param stem 天干
     * @return 五行属性
     */
    public static String getStemElement(String stem) {
        for (int i = 0; i < HEAVENLY_STEMS.length; i++) {
            if (HEAVENLY_STEMS[i].equals(stem)) {
                return HEAVENLY_STEM_ELEMENTS[i];
            }
        }
        return null;
    }
    
    /**
     * 获取地支的五行属性
     * 
     * @param branch 地支
     * @return 五行属性
     */
    public static String getBranchElement(String branch) {
        for (int i = 0; i < EARTHLY_BRANCHES.length; i++) {
            if (EARTHLY_BRANCHES[i].equals(branch)) {
                return EARTHLY_BRANCH_ELEMENTS[i];
            }
        }
        return null;
    }
    
    /**
     * 判断两个五行属性的关系
     * 
     * @param element1 五行属性1
     * @param element2 五行属性2
     * @return 关系描述：相生、相克、相同或无关
     */
    public static String getElementRelation(String element1, String element2) {
        if (element1.equals(element2)) {
            return "相同";
        }
        
        // 五行相生顺序：金生水，水生木，木生火，火生土，土生金
        Map<String, String> generates = new HashMap<>();
        generates.put("金", "水");
        generates.put("水", "木");
        generates.put("木", "火");
        generates.put("火", "土");
        generates.put("土", "金");
        
        if (generates.get(element1).equals(element2)) {
            return "相生";
        } else if (generates.get(element2).equals(element1)) {
            return "被生";
        } else if (generates.get(generates.get(element1)).equals(element2)) {
            return "相克";
        } else if (generates.get(generates.get(element2)).equals(element1)) {
            return "被克";
        }
        
        return "无关";
    }
    
    /**
     * 获取日期的吉凶宜忌
     * 
     * @param solarDate 阳历日期
     * @return 包含宜忌信息的Map
     */
    public static Map<String, List<String>> getDailyAdvice(LocalDate solarDate) {
        Map<String, List<String>> result = new HashMap<>();
        
        // 获取日期的天干地支
        int[] lunarDate = solarToLunar(solarDate.getYear(), solarDate.getMonthValue(), solarDate.getDayOfMonth());
        String dayGanZhi = getDayGanZhi(lunarDate[0], lunarDate[1], lunarDate[2])
            .replace("日", ""); // 移除"日"字
        
        // 获取宜事项
        List<String> suitable = DAILY_SUITABLE.getOrDefault(dayGanZhi, new ArrayList<>());
        result.put("suitable", suitable);
        
        // 获取忌事项
        List<String> unsuitable = DAILY_UNSUITABLE.getOrDefault(dayGanZhi, new ArrayList<>());
        result.put("unsuitable", unsuitable);
        
        return result;
    }
    
    /**
     * 判断指定日期是否适合特定事项
     * 
     * @param solarDate 阳历日期
     * @param activity 活动事项
     * @return 是否适合（true: 适合，false: 不适合，null: 未知）
     */
    public static Boolean isActivitySuitable(LocalDate solarDate, String activity) {
        Map<String, List<String>> advice = getDailyAdvice(solarDate);
        
        if (advice.get("suitable").contains(activity)) {
            return true;
        } else if (advice.get("unsuitable").contains(activity)) {
            return false;
        }
        
        return null;
    }
    
    /**
     * 获取月相
     * 
     * @param lunarDay 农历日期
     * @return 月相描述
     */
    public static String getMoonPhase(int lunarDay) {
        if (lunarDay == 1) {
            return "朔月";
        } else if (lunarDay == 15) {
            return "望月";
        } else if (lunarDay < 15) {
            return "上弦月";
        } else {
            return "下弦月";
        }
    }
    
    /**
     * 获取年份的天干
     * 
     * @param year 公历年份
     * @return 天干
     */
    public static String getYearStem(int year) {
        if (year < 1900 || year > 2100) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "年份超出计算范围");
        }
        return HEAVENLY_STEMS[(year - 4) % 10];
    }
} 