package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.tyme.lunar.LunarDay;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class LunarUtilsTest extends BaseTest {

    @Test
    public void testFromSolar() {
        // 2024年春节是2024-02-10
        LunarDay lunarDay = LunarUtils.fromSolar(LocalDate.of(2024, 2, 10));
        assertEquals(2024, lunarDay.getYear());
        assertEquals(1, lunarDay.getMonth());
        assertEquals(1, lunarDay.getDay());
        assertEquals("农历甲辰年正月初一", lunarDay.toString());

        // 2023年除夕是2024-02-09
        lunarDay = LunarUtils.fromSolar(LocalDate.of(2024, 2, 9));
        assertEquals(2023, lunarDay.getYear());
        assertEquals(12, lunarDay.getMonth());
        assertEquals(30, lunarDay.getDay());
        assertEquals("农历癸卯年十二月三十", lunarDay.toString());
    }

    @Test
    public void testFromLunar() {
        // 2024年正月初一
        LunarDay lunarDay = LunarUtils.fromLunar(2024, 1, 1, false);
        assertEquals(2024, lunarDay.getYear());
        assertEquals(1, lunarDay.getMonth());
        assertEquals(1, lunarDay.getDay());
        assertEquals("农历甲辰年正月初一", lunarDay.toString());
    }

    @Test
    public void testToSolar() {
        // 2024年春节
        LocalDate solarDate = LunarUtils.toSolar(2024, 1, 1, false);
        assertEquals(LocalDate.of(2024, 2, 10), solarDate);

        // 2023年除夕
        solarDate = LunarUtils.toSolar(2023, 12, 30, false);
        assertEquals(LocalDate.of(2024, 2, 9), solarDate);
    }

    @Test
    public void testGetTimeName() {
        assertEquals("子时", LunarUtils.getTimeName(LocalTime.of(23, 30)));
        assertEquals("丑时", LunarUtils.getTimeName(LocalTime.of(1, 30)));
        assertEquals("寅时", LunarUtils.getTimeName(LocalTime.of(3, 30)));
        assertEquals("卯时", LunarUtils.getTimeName(LocalTime.of(5, 30)));
        assertEquals("辰时", LunarUtils.getTimeName(LocalTime.of(7, 30)));
        assertEquals("巳时", LunarUtils.getTimeName(LocalTime.of(9, 30)));
        assertEquals("午时", LunarUtils.getTimeName(LocalTime.of(11, 30)));
        assertEquals("未时", LunarUtils.getTimeName(LocalTime.of(13, 30)));
        assertEquals("申时", LunarUtils.getTimeName(LocalTime.of(15, 30)));
        assertEquals("酉时", LunarUtils.getTimeName(LocalTime.of(17, 30)));
        assertEquals("戌时", LunarUtils.getTimeName(LocalTime.of(19, 30)));
        assertEquals("亥时", LunarUtils.getTimeName(LocalTime.of(21, 30)));
    }

    @Test
    public void testGetLeapMonth() {
        // 2023年闰2月
        assertEquals(2, LunarUtils.getLeapMonth(2023));
        // 2024年无闰月
        assertEquals(0, LunarUtils.getLeapMonth(2024));
    }

    @Test
    public void testGetMonthDays() {
        // 2024年正月（大月，30天）
        assertEquals(30, LunarUtils.getMonthDays(2024, 1, false));
        // 2024年二月（小月，29天）
        assertEquals(29, LunarUtils.getMonthDays(2024, 2, false));
    }

    @Test
    public void testGetSolarTerm() {
        // 2024年立春：2024-02-04
        assertEquals("立春", LunarUtils.getSolarTerm(LocalDate.of(2024, 2, 4)));
        // 非节气日期
        assertNull(LunarUtils.getSolarTerm(LocalDate.of(2024, 2, 5)));
    }
} 