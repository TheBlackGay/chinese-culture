package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.utils.CalendarConverter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 紫微斗数运限计算器
 */
@Slf4j
public class HoroscopeCalculator {
    
    // 大限起始年龄（阳男阴女顺行，阴男阳女逆行）
    private static final int[] DECADAL_START_AGE = {1, 11, 21, 31, 41, 51, 61, 71, 81, 91};
    
    /**
     * 计算大限
     * 
     * @param mingGongPosition 命宫位置
     * @param gender 性别（male/female）
     * @param lunarYear 农历年
     * @return 大限信息列表
     */
    public static List<Map<String, Object>> calculateDecadalHoroscope(
            int mingGongPosition, String gender, int lunarYear) {
        try {
            List<Map<String, Object>> decadalInfo = new ArrayList<>();
            
            // 判断顺逆
            boolean isForward = isForwardDirection(gender, lunarYear);
            
            // 计算每个大限
            for (int i = 0; i < 10; i++) {
                Map<String, Object> limit = new HashMap<>();
                int position = calculateDecadalPosition(mingGongPosition, i, isForward);
                
                limit.put("startAge", DECADAL_START_AGE[i]);
                limit.put("endAge", DECADAL_START_AGE[i] + 9);
                limit.put("position", position);
                limit.put("palaceName", getPalaceName(position));
                
                decadalInfo.add(limit);
            }
            
            return decadalInfo;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("大限计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 计算流年
     * 
     * @param mingGongPosition 命宫位置
     * @param currentYear 当前年份
     * @param birthYear 出生年份
     * @return 流年信息
     */
    public static Map<String, Object> calculateYearlyHoroscope(
            int mingGongPosition, int currentYear, int birthYear) {
        try {
            Map<String, Object> yearlyInfo = new HashMap<>();
            
            // 计算年龄
            int age = currentYear - birthYear + 1;
            
            // 计算当前大限
            int decadalIndex = (age - 1) / 10;
            if (decadalIndex >= 0 && decadalIndex < 10) {
                yearlyInfo.put("decadalIndex", decadalIndex);
                yearlyInfo.put("age", age);
                
                // TODO: 实现流年星耀计算
            }
            
            return yearlyInfo;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("流年计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 计算流月
     * 
     * @param yearlyPosition 流年位置
     * @param currentMonth 当前月份
     * @return 流月信息
     */
    public static Map<String, Object> calculateMonthlyHoroscope(int yearlyPosition, int currentMonth) {
        try {
            Map<String, Object> monthlyInfo = new HashMap<>();
            
            // TODO: 实现流月计算
            
            return monthlyInfo;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("流月计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 计算流日
     * 
     * @param monthlyPosition 流月位置
     * @param currentDate 当前日期
     * @return 流日信息
     */
    public static Map<String, Object> calculateDailyHoroscope(int monthlyPosition, LocalDate currentDate) {
        try {
            Map<String, Object> dailyInfo = new HashMap<>();
            
            // TODO: 实现流日计算
            
            return dailyInfo;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("流日计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 计算流时
     * 
     * @param dailyPosition 流日位置
     * @param currentHour 当前时辰
     * @return 流时信息
     */
    public static Map<String, Object> calculateHourlyHoroscope(int dailyPosition, int currentHour) {
        try {
            Map<String, Object> hourlyInfo = new HashMap<>();
            
            // TODO: 实现流时计算
            
            return hourlyInfo;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("流时计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 判断大限顺逆
     */
    private static boolean isForwardDirection(String gender, int lunarYear) {
        String yearGanZhi = CalendarConverter.getYearGanZhi(lunarYear);
        boolean isYangYear = isYangGan(yearGanZhi.substring(0, 1));
        return ("male".equalsIgnoreCase(gender) && isYangYear) || 
               ("female".equalsIgnoreCase(gender) && !isYangYear);
    }
    
    /**
     * 判断天干阴阳
     */
    private static boolean isYangGan(String gan) {
        return "甲丙戊庚壬".contains(gan);
    }
    
    /**
     * 计算大限宫位
     */
    private static int calculateDecadalPosition(int mingGongPosition, int index, boolean isForward) {
        if (isForward) {
            return (mingGongPosition + index) % 12;
        } else {
            return (mingGongPosition - index + 12) % 12;
        }
    }
    
    /**
     * 获取宫位名称
     */
    private static String getPalaceName(int position) {
        String[] palaceNames = {
            "命宫", "兄弟", "夫妻", "子女", 
            "财帛", "疾厄", "迁移", "交友",
            "官禄", "田宅", "福德", "父母"
        };
        return palaceNames[position];
    }

    /**
     * 计算运限
     */
    public static Map<String, Object> calculate(Astrolabe astrolabe) {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // 计算大运
            result.put("majorHoroscope", calculateMajorHoroscope(astrolabe));
            
            // 计算流年
            result.put("yearlyHoroscope", calculateYearlyHoroscope(astrolabe));
            
            // 计算流月
            result.put("monthlyHoroscope", calculateMonthlyHoroscope(astrolabe));
            
            // 计算流日
            result.put("dailyHoroscope", calculateDailyHoroscope(astrolabe));
            
            // 计算流时
            result.put("hourlyHoroscope", calculateHourlyHoroscope(astrolabe));
            
            return result;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("运限计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 计算大运
     */
    private static Map<String, Object> calculateMajorHoroscope(Astrolabe astrolabe) {
        Map<String, Object> result = new HashMap<>();
        // TODO: 实现大运计算逻辑
        return result;
    }
    
    /**
     * 计算流年
     */
    private static Map<String, Object> calculateYearlyHoroscope(Astrolabe astrolabe) {
        Map<String, Object> result = new HashMap<>();
        // TODO: 实现流年计算逻辑
        return result;
    }
    
    /**
     * 计算流月
     */
    private static Map<String, Object> calculateMonthlyHoroscope(Astrolabe astrolabe) {
        Map<String, Object> result = new HashMap<>();
        // TODO: 实现流月计算逻辑
        return result;
    }
    
    /**
     * 计算流日
     */
    private static Map<String, Object> calculateDailyHoroscope(Astrolabe astrolabe) {
        Map<String, Object> result = new HashMap<>();
        // TODO: 实现流日计算逻辑
        return result;
    }
    
    /**
     * 计算流时
     */
    private static Map<String, Object> calculateHourlyHoroscope(Astrolabe astrolabe) {
        Map<String, Object> result = new HashMap<>();
        // TODO: 实现流时计算逻辑
        return result;
    }
} 