package com.chinese.culture.admin.common.core.iztro.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class StemBranchUtilsTest extends BaseTest {

    @Test
    public void testGetHeavenStem() {
        String[] stems = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        for (int i = 0; i < 10; i++) {
            assertEquals(stems[i], StemBranchUtils.getHeavenStem(i));
        }
    }

    @Test
    public void testGetEarthBranch() {
        String[] branches = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        for (int i = 0; i < 12; i++) {
            assertEquals(branches[i], StemBranchUtils.getEarthBranch(i));
        }
    }

    @Test
    public void testGetStemBranch() {
        assertEquals("甲子", StemBranchUtils.getStemBranch(0, 0));
        assertEquals("乙丑", StemBranchUtils.getStemBranch(1, 1));
        assertEquals("丙寅", StemBranchUtils.getStemBranch(2, 2));
        assertEquals("癸亥", StemBranchUtils.getStemBranch(9, 11));
    }

    @Test
    public void testGetSixtyCycleIndex() {
        // 甲子
        assertEquals(0, StemBranchUtils.getSixtyCycleIndex(0, 0));
        // 乙丑
        assertEquals(1, StemBranchUtils.getSixtyCycleIndex(1, 1));
        // 癸亥
        assertEquals(59, StemBranchUtils.getSixtyCycleIndex(9, 11));
    }

    @Test
    public void testGetSixtyCycleName() {
        assertEquals("甲子", StemBranchUtils.getSixtyCycleName(0));
        assertEquals("乙丑", StemBranchUtils.getSixtyCycleName(1));
        assertEquals("癸亥", StemBranchUtils.getSixtyCycleName(59));
    }

    @Test
    public void testGetMonthStemByYear() {
        // 五虎遁口诀测试
        assertEquals(2, StemBranchUtils.getMonthStemByYear("甲")); // 丙
        assertEquals(2, StemBranchUtils.getMonthStemByYear("己")); // 丙
        assertEquals(4, StemBranchUtils.getMonthStemByYear("乙")); // 戊
        assertEquals(4, StemBranchUtils.getMonthStemByYear("庚")); // 戊
        assertEquals(6, StemBranchUtils.getMonthStemByYear("丙")); // 庚
        assertEquals(6, StemBranchUtils.getMonthStemByYear("辛")); // 庚
        assertEquals(8, StemBranchUtils.getMonthStemByYear("丁")); // 壬
        assertEquals(8, StemBranchUtils.getMonthStemByYear("壬")); // 壬
        assertEquals(0, StemBranchUtils.getMonthStemByYear("戊")); // 甲
        assertEquals(0, StemBranchUtils.getMonthStemByYear("癸")); // 甲
    }

    @Test
    public void testGetDayGanZhi() {
        // 2024-02-10 甲辰日
        assertEquals("甲辰", StemBranchUtils.getDayGanZhi(LocalDate.of(2024, 2, 10)));
        // 2024-02-11 乙巳日
        assertEquals("乙巳", StemBranchUtils.getDayGanZhi(LocalDate.of(2024, 2, 11)));
    }

    @Test
    public void testGetHourGanZhi() {
        // 2024-02-10 甲辰日
        LocalDate date1 = LocalDate.of(2024, 2, 10);
        // 子时 (23:00-01:00)
        assertEquals("戊子", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(23, 30))); // 23:30属于下一天（乙巳日）的子时
        assertEquals("丙子", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(0, 30))); // 0:30属于当天（甲辰日）的子时
        // 丑时 (01:00-03:00)
        assertEquals("丁丑", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(1, 30)));
        // 寅时 (03:00-05:00)
        assertEquals("戊寅", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(3, 30)));
        // 卯时 (05:00-07:00)
        assertEquals("己卯", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(5, 30)));
        // 辰时 (07:00-09:00)
        assertEquals("庚辰", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(7, 30)));
        // 巳时 (09:00-11:00)
        assertEquals("辛巳", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(9, 30)));
        // 午时 (11:00-13:00)
        assertEquals("壬午", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(11, 30)));
        // 未时 (13:00-15:00)
        assertEquals("癸未", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(13, 30)));
        // 申时 (15:00-17:00)
        assertEquals("甲申", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(15, 30)));
        // 酉时 (17:00-19:00)
        assertEquals("乙酉", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(17, 30)));
        // 戌时 (19:00-21:00)
        assertEquals("丙戌", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(19, 30)));
        // 亥时 (21:00-23:00)
        assertEquals("丁亥", StemBranchUtils.getHourGanZhi(date1, LocalTime.of(21, 30)));

        // 2024-02-11 乙巳日
        LocalDate date2 = LocalDate.of(2024, 2, 11);
        // 子时
        assertEquals("庚子", StemBranchUtils.getHourGanZhi(date2, LocalTime.of(23, 30)));
        // 丑时
        assertEquals("己丑", StemBranchUtils.getHourGanZhi(date2, LocalTime.of(1, 30)));
        // 寅时
        assertEquals("庚寅", StemBranchUtils.getHourGanZhi(date2, LocalTime.of(3, 30)));

        // 2024-02-12 丙午日
        LocalDate date3 = LocalDate.of(2024, 2, 12);
        // 子时
        assertEquals("壬子", StemBranchUtils.getHourGanZhi(date3, LocalTime.of(23, 30)));
        // 丑时
        assertEquals("辛丑", StemBranchUtils.getHourGanZhi(date3, LocalTime.of(1, 30)));
        // 寅时
        assertEquals("壬寅", StemBranchUtils.getHourGanZhi(date3, LocalTime.of(3, 30)));

        // 2024-02-13 丁未日
        LocalDate date4 = LocalDate.of(2024, 2, 13);
        // 子时
        assertEquals("甲子", StemBranchUtils.getHourGanZhi(date4, LocalTime.of(23, 30)));
        // 丑时
        assertEquals("癸丑", StemBranchUtils.getHourGanZhi(date4, LocalTime.of(1, 30)));
        // 寅时
        assertEquals("甲寅", StemBranchUtils.getHourGanZhi(date4, LocalTime.of(3, 30)));

        // 2024-02-14 戊申日
        LocalDate date5 = LocalDate.of(2024, 2, 14);
        // 子时
        assertEquals("丙子", StemBranchUtils.getHourGanZhi(date5, LocalTime.of(23, 30)));
        // 丑时
        assertEquals("乙丑", StemBranchUtils.getHourGanZhi(date5, LocalTime.of(1, 30)));
        // 寅时
        assertEquals("丙寅", StemBranchUtils.getHourGanZhi(date5, LocalTime.of(3, 30)));
    }
} 