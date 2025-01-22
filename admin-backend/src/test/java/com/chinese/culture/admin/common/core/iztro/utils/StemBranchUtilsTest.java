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
        LocalDate date = LocalDate.of(2024, 2, 10);
        
        // 子时 (23:00-01:00)
        assertEquals("丙子", StemBranchUtils.getHourGanZhi(date, LocalTime.of(23, 30)));
        // 丑时 (01:00-03:00)
        assertEquals("丁丑", StemBranchUtils.getHourGanZhi(date, LocalTime.of(1, 30)));
        // 寅时 (03:00-05:00)
        assertEquals("戊寅", StemBranchUtils.getHourGanZhi(date, LocalTime.of(3, 30)));
    }
} 