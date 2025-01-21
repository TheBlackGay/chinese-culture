package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.RawDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("日期计算工具测试")
class DateCalculatorTest {

    @Test
    @DisplayName("测试阳历转农历")
    void testSolar2Lunar() {
        // 测试普通日期转换
        RawDate.LunarDate result1 = DateCalculator.solar2lunar("2024-01-21");
        assertEquals(2023, result1.getYear());
        assertEquals(12, result1.getMonth());
        assertEquals(11, result1.getDay());
        assertFalse(result1.isLeapMonth());

        // 测试闰月日期
        RawDate.LunarDate result2 = DateCalculator.solar2lunar("2023-06-18");
        assertEquals(2023, result2.getYear());
        assertEquals(5, result2.getMonth());
        assertEquals(1, result2.getDay());
        assertTrue(result2.isLeapMonth());

        // 测试无效日期
        assertThrows(IllegalArgumentException.class, () -> 
            DateCalculator.solar2lunar("2024-13-01")
        );
    }

    @Test
    @DisplayName("测试农历转阳历")
    void testLunar2Solar() {
        // 测试普通日期转换
        RawDate.SolarDate result1 = DateCalculator.lunar2solar(2023, 12, 11, false);
        assertEquals(2024, result1.getYear());
        assertEquals(1, result1.getMonth());
        assertEquals(21, result1.getDay());

        // 测试闰月日期
        RawDate.SolarDate result2 = DateCalculator.lunar2solar(2023, 5, 1, true);
        assertEquals(2023, result2.getYear());
        assertEquals(6, result2.getMonth());
        assertEquals(18, result2.getDay());
    }

    @Test
    @DisplayName("测试获取干支纪年日期")
    void testGetChineseDate() {
        RawDate.ChineseDate result = DateCalculator.getChineseDate("2024-01-21", 3);
        assertEquals("癸", result.getYearGan());
        assertEquals("卯", result.getYearZhi());
        assertEquals("戊", result.getMonthGan());
        assertEquals("子", result.getMonthZhi());
        assertEquals("丁", result.getDayGan());
        assertEquals("巳", result.getDayZhi());
        assertEquals("己", result.getHourGan());
        assertEquals("卯", result.getHourEarthlyBranch());
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

    @ParameterizedTest
    @ValueSource(ints = {0, 3, 6, 9})
    @DisplayName("测试获取时辰范围")
    void testGetTimeRange(int timeIndex) {
        String timeRange = DateCalculator.getTimeRange(timeIndex);
        assertNotNull(timeRange);
        assertTrue(timeRange.matches("\\d{2}:\\d{2}-\\d{2}:\\d{2}"));
    }

    @Test
    @DisplayName("测试计算年龄")
    void testCalculateAge() {
        int age = DateCalculator.calculateAge("2000-01-21");
        assertTrue(age > 0);
        assertTrue(age <= 24);  // 2024年测试
    }

    @Test
    @DisplayName("测试异常情况")
    void testExceptions() {
        // 测试无效日期格式
        assertThrows(IllegalArgumentException.class, () -> 
            DateCalculator.solar2lunar("2024/01/21")
        );

        // 测试无效时辰索引
        assertThrows(IllegalArgumentException.class, () -> 
            DateCalculator.getHourPillar("2024-01-21", 12)
        );

        // 测试无效分界规则
        assertThrows(IllegalArgumentException.class, () -> 
            DateCalculator.isBeforeYearDivide("2024-01-21", "invalid")
        );
    }
} 