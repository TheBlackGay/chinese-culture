//package com.chinese.culture.admin.util;
//
//import com.chinese.culture.admin.common.exception.BusinessException;
//import com.chinese.culture.admin.common.result.ResultCode;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 农历工具类
// */
//@Slf4j
//public class LunarUtils {
//
//    private LunarUtils() {}
//
//    /**
//     * 阳历转农历
//     *
//     * @param year 年
//     * @param month 月
//     * @param day 日
//     * @return 农历日期
//     */
//    public static Lunar solarToLunar(int year, int month, int day) {
//        try {
//            Solar solar = Solar.fromYmd(year, month, day);
//            return solar.getLunar();
//        } catch (Exception e) {
//            log.error("阳历转农历失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.BIRTH_TIME_ERROR);
//        }
//    }
//
//    /**
//     * 农历转阳历
//     *
//     * @param year 年
//     * @param month 月
//     * @param day 日
//     * @param isLeapMonth 是否闰月
//     * @return 阳历日期
//     */
//    public static Solar lunarToSolar(int year, int month, int day, boolean isLeapMonth) {
//        try {
//            Lunar lunar = Lunar.fromYmd(year, month, day);
//            if (isLeapMonth) {
//                lunar = lunar.next(month - 1);
//            }
//            return lunar.getSolar();
//        } catch (Exception e) {
//            log.error("农历转阳历失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.BIRTH_TIME_ERROR);
//        }
//    }
//
//    /**
//     * 获取农历年的生肖
//     *
//     * @param year 农历年
//     * @return 生肖
//     */
//    public static String getAnimalYear(int year) {
//        try {
//            Lunar lunar = Lunar.fromYmd(year, 1, 1);
//            return lunar.getYearShengXiao();
//        } catch (Exception e) {
//            log.error("获取生肖失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.PARAM_ERROR);
//        }
//    }
//
//    /**
//     * 获取农历日期的天干地支
//     *
//     * @param year 年
//     * @param month 月
//     * @param day 日
//     * @return 天干地支
//     */
//    public static String getGanZhi(int year, int month, int day) {
//        try {
//            Lunar lunar = Lunar.fromYmd(year, month, day);
//            return lunar.getYearInGanZhi();
//        } catch (Exception e) {
//            log.error("获取天干地支失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.PARAM_ERROR);
//        }
//    }
//
//    /**
//     * 获取农历节气
//     *
//     * @param year 年
//     * @param month 月
//     * @param day 日
//     * @return 节气名称，如果不是节气日期则返回null
//     */
//    public static String getSolarTerm(int year, int month, int day) {
//        try {
//            Solar solar = Solar.fromYmd(year, month, day);
//            return solar.getJieQi();
//        } catch (Exception e) {
//            log.error("获取节气失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.PARAM_ERROR);
//        }
//    }
//
//    /**
//     * 获取农历节日
//     *
//     * @param year 年
//     * @param month 月
//     * @param day 日
//     * @return 农历节日名称，如果不是节日则返回null
//     */
//    public static String getLunarFestival(int year, int month, int day) {
//        try {
//            Lunar lunar = Lunar.fromYmd(year, month, day);
//            return lunar.getFestivals().isEmpty() ? null : lunar.getFestivals().get(0);
//        } catch (Exception e) {
//            log.error("获取农历节日失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.PARAM_ERROR);
//        }
//    }
//
//    /**
//     * 获取阳历节日
//     *
//     * @param year 年
//     * @param month 月
//     * @param day 日
//     * @return 阳历节日名称，如果不是节日则返回null
//     */
//    public static String getSolarFestival(int year, int month, int day) {
//        try {
//            Solar solar = Solar.fromYmd(year, month, day);
//            return solar.getFestivals().isEmpty() ? null : solar.getFestivals().get(0);
//        } catch (Exception e) {
//            log.error("获取阳历节日失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.PARAM_ERROR);
//        }
//    }
//
//    /**
//     * 获取日期的吉凶
//     *
//     * @param year 年
//     * @param month 月
//     * @param day 日
//     * @return 吉凶
//     */
//    public static String getDayLuck(int year, int month, int day) {
//        try {
//            Lunar lunar = Lunar.fromYmd(year, month, day);
//            return lunar.getDayYi() + "," + lunar.getDayJi();
//        } catch (Exception e) {
//            log.error("获取吉凶失败：{}", e.getMessage(), e);
//            throw new BusinessException(ResultCode.PARAM_ERROR);
//        }
//    }
//}
