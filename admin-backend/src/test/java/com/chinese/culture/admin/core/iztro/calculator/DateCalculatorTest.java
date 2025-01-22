package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.calculator.DateCalculator;
import com.chinese.culture.admin.common.core.iztro.data.RawDateBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("日期计算工具测试")
class DateCalculatorTest {

    private final DateCalculator calculator = new DateCalculator();

    @Test
    @DisplayName("测试阳历转农历")
    void testSolar2Lunar() {
        // 测试普通日期转换
        RawDateBO.SolarDate solarDate1 = new RawDateBO.SolarDate();
        solarDate1.setYear(2024);
        solarDate1.setMonth(1);
        solarDate1.setDay(21);

        RawDateBO.LunarDate result1 = calculator.solar2lunar(solarDate1);
        assertEquals(2023, result1.getYear());
        assertEquals(12, result1.getMonth());
        assertEquals(11, result1.getDay());
        assertFalse(result1.isLeapMonth());

        // 测试闰月日期
        RawDateBO.SolarDate solarDate2 = new RawDateBO.SolarDate();
        solarDate2.setYear(2023);
        solarDate2.setMonth(6);
        solarDate2.setDay(18);

        RawDateBO.LunarDate result2 = calculator.solar2lunar(solarDate2);
        assertEquals(2023, result2.getYear());
        assertEquals(5, result2.getMonth());
        assertEquals(1, result2.getDay());
        assertTrue(result2.isLeapMonth());
    }

    @Test
    @DisplayName("测试农历转阳历")
    void testLunar2Solar() {
        // 测试普通日期转换
        RawDateBO.LunarDate lunarDate1 = new RawDateBO.LunarDate();
        lunarDate1.setYear(2023);
        lunarDate1.setMonth(12);
        lunarDate1.setDay(11);
        lunarDate1.setLeapMonth(false);

        RawDateBO.SolarDate result1 = calculator.lunar2solar(lunarDate1);
        assertEquals(2024, result1.getYear());
        assertEquals(1, result1.getMonth());
        assertEquals(21, result1.getDay());

        // 测试闰月日期
        RawDateBO.LunarDate lunarDate2 = new RawDateBO.LunarDate();
        lunarDate2.setYear(2023);
        lunarDate2.setMonth(5);
        lunarDate2.setDay(1);
        lunarDate2.setLeapMonth(true);

        RawDateBO.SolarDate result2 = calculator.lunar2solar(lunarDate2);
        assertEquals(2023, result2.getYear());
        assertEquals(6, result2.getMonth());
        assertEquals(18, result2.getDay());
    }

    @Test
    @DisplayName("测试获取干支纪年日期")
    void testGetChineseDate() {
        RawDateBO.SolarDate solarDate = new RawDateBO.SolarDate();
        solarDate.setYear(2024);
        solarDate.setMonth(1);
        solarDate.setDay(21);

        RawDateBO.ChineseDate result = calculator.getChineseDate(solarDate, 3);
        assertEquals(HeavenlyStem.fromIndex(result.getYearGan()).getDescription(), "癸");
        assertEquals(EarthlyBranch.fromIndex(result.getYearZhi()).getDescription(), "卯");
        assertEquals(HeavenlyStem.fromIndex(result.getMonthGan()).getDescription(), "戊");
        assertEquals(EarthlyBranch.fromIndex(result.getMonthZhi()).getDescription(), "子");
        assertEquals(HeavenlyStem.fromIndex(result.getDayGan()).getDescription(), "丁");
        assertEquals(EarthlyBranch.fromIndex(result.getDayZhi()).getDescription(), "巳");
        assertEquals(HeavenlyStem.fromIndex(result.getTimeGan()).getDescription(), "己");
        assertEquals(EarthlyBranch.fromIndex(result.getTimeZhi()).getDescription(), "卯");
    }

    @ParameterizedTest
    @CsvSource({
        "2024-01-21, exact, false",    // 立春前
        "2024-02-05, exact, false",    // 立春当天
        "2024-02-10, exact, true",     // 立春后
        "2024-01-21, normal, false",   // 春节前
        "2024-02-10, normal, false",   // 春节当天
        "2024-02-11, normal, true"     // 春节后
    })
    @DisplayName("测试年份分界点判断")
    void testIsBeforeYearDivide(String date, String rule, boolean expected) {
        assertEquals(expected, DateCalculator.isBeforeYearDivide(date, rule));
    }

    @Test
    @DisplayName("测试获取节气")
    void testGetSolarTerm() {
        assertEquals("立春", DateCalculator.getSolarTerm("2024-02-04"));
        assertEquals("雨水", DateCalculator.getSolarTerm("2024-02-19"));
        assertNull(DateCalculator.getSolarTerm("2024-02-10")); // 非节气日
    }

    @ParameterizedTest
    @CsvSource({
        "2024-01-21, exact, 癸卯",     // 立春前
        "2024-02-05, exact, 甲辰",     // 立春后
        "2024-01-21, normal, 癸卯",    // 春节前
        "2024-02-10, normal, 甲辰"     // 春节后
    })
    @DisplayName("测试获取年柱")
    void testGetYearPillar(String date, String rule, String expected) {
        assertEquals(expected, DateCalculator.getYearPillar(date, rule));
    }

    @ParameterizedTest
    @CsvSource({
        "2024-01-21, 戊子",    // 大寒
        "2024-02-04, 己丑",    // 立春
        "2024-02-19, 庚寅"     // 雨水
    })
    @DisplayName("测试获取月柱")
    void testGetMonthPillar(String date, String expected) {
        assertEquals(expected, DateCalculator.getMonthPillar(date));
    }

    @ParameterizedTest
    @CsvSource({
        "2024-01-21, 丁巳",
        "2024-02-04, 壬午",
        "2024-02-19, 丁未"
    })
    @DisplayName("测试获取日柱")
    void testDayPillar(String date, String expected) {
        assertEquals(expected, DateCalculator.getDayPillar(date));
    }

    @ParameterizedTest
    @CsvSource({
        "2024-01-21, 0, 己子",     // 子时
        "2024-01-21, 3, 壬卯",     // 卯时
        "2024-01-21, 6, 乙午",     // 午时
        "2024-01-21, 9, 戊酉"      // 酉时
    })
    @DisplayName("测试获取时柱")
    void testGetHourPillar(String date, int timeIndex, String expected) {
        assertEquals(expected, DateCalculator.getHourPillar(date, timeIndex));
    }

    @Test
    @DisplayName("测试获取时辰范围")
    void testGetTimeRange() {
        List<String> timeRange = calculator.getTimeRange(3);
        assertNotNull(timeRange);
        assertEquals(2, timeRange.size());
        assertTrue(timeRange.get(0).matches("\\d{2}:\\d{2}"));
        assertTrue(timeRange.get(1).matches("\\d{2}:\\d{2}"));
    }

    @Test
    @DisplayName("测试计算年龄")
    void testCalculateAge() {
        LocalDateTime birthDate = LocalDateTime.of(2000, 1, 21, 0, 0);
        LocalDateTime targetDate = LocalDateTime.now();
        int age = calculator.calculateAge(birthDate, targetDate);
        assertTrue(age > 0);
        assertTrue(age <= 24);  // 2024年测试
    }

    @Test
    @DisplayName("测试异常情况")
    void testExceptions() {
        // 测试无效日期
        RawDateBO.SolarDate invalidDate = new RawDateBO.SolarDate();
        invalidDate.setYear(2024);
        invalidDate.setMonth(13);
        invalidDate.setDay(1);

        assertThrows(IllegalArgumentException.class, () ->
            calculator.solar2lunar(invalidDate)
        );

        // 测试无效时辰索引
        assertThrows(IllegalArgumentException.class, () ->
            calculator.getTimeRange(12)
        );
    }
}
