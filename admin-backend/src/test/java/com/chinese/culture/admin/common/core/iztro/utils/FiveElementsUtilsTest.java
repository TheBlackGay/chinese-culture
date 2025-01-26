package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElements;
import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElementsClass;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 五行计算工具类测试
 */
public class FiveElementsUtilsTest {

    @Test
    public void testGetFiveElementsClass() {
        // 测试天干对应的五行局
        assertEquals(FiveElementsClass.METAL, FiveElementsUtils.getFiveElementsClass("庚", "子"));
        assertEquals(FiveElementsClass.FIRE, FiveElementsUtils.getFiveElementsClass("丙", "子"));
        assertEquals(FiveElementsClass.FIRE, FiveElementsUtils.getFiveElementsClass("丁", "子"));
        assertEquals(FiveElementsClass.EARTH, FiveElementsUtils.getFiveElementsClass("戊", "子"));
        assertEquals(FiveElementsClass.WOOD, FiveElementsUtils.getFiveElementsClass("甲", "子"));
        assertEquals(FiveElementsClass.WATER, FiveElementsUtils.getFiveElementsClass("壬", "子"));
    }

    @Test
    public void testGetEarthlyBranchFiveElements() {
        // 测试地支五行
        assertEquals(FiveElements.WATER, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.ZI));  // 子水
        assertEquals(FiveElements.EARTH, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.CHOU)); // 丑土
        assertEquals(FiveElements.WOOD, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.YIN));  // 寅木
        assertEquals(FiveElements.WOOD, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.MAO));  // 卯木
        assertEquals(FiveElements.EARTH, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.CHEN)); // 辰土
        assertEquals(FiveElements.FIRE, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.SI));   // 巳火
        assertEquals(FiveElements.FIRE, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.WU));   // 午火
        assertEquals(FiveElements.EARTH, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.WEI));  // 未土
        assertEquals(FiveElements.METAL, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.SHEN)); // 申金
        assertEquals(FiveElements.METAL, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.YOU));  // 酉金
        assertEquals(FiveElements.EARTH, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.XU));   // 戌土
        assertEquals(FiveElements.WATER, FiveElementsUtils.getEarthlyBranchFiveElements(EarthlyBranch.HAI));  // 亥水
    }

    @Test
    public void testGetHeavenlyStemFiveElements() {
        // 测试天干五行
        assertEquals(FiveElements.WOOD, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.JIA));  // 甲木
        assertEquals(FiveElements.WOOD, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.YI));   // 乙木
        assertEquals(FiveElements.FIRE, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.BING)); // 丙火
        assertEquals(FiveElements.FIRE, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.DING)); // 丁火
        assertEquals(FiveElements.EARTH, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.WU));  // 戊土
        assertEquals(FiveElements.EARTH, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.JI));  // 己土
        assertEquals(FiveElements.METAL, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.GENG)); // 庚金
        assertEquals(FiveElements.METAL, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.XIN)); // 辛金
        assertEquals(FiveElements.WATER, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.REN)); // 壬水
        assertEquals(FiveElements.WATER, FiveElementsUtils.getHeavenlyStemFiveElements(HeavenlyStem.GUI)); // 癸水
    }

    @Test
    public void testIsReinforce() {
        // 测试五行相生关系
        assertTrue(FiveElementsUtils.isReinforce(FiveElements.WOOD, FiveElements.FIRE));   // 木生火
        assertTrue(FiveElementsUtils.isReinforce(FiveElements.FIRE, FiveElements.EARTH));  // 火生土
        assertTrue(FiveElementsUtils.isReinforce(FiveElements.EARTH, FiveElements.METAL)); // 土生金
        assertTrue(FiveElementsUtils.isReinforce(FiveElements.METAL, FiveElements.WATER)); // 金生水
        assertTrue(FiveElementsUtils.isReinforce(FiveElements.WATER, FiveElements.WOOD));  // 水生木

        // 测试非相生关系
        assertFalse(FiveElementsUtils.isReinforce(FiveElements.WOOD, FiveElements.EARTH)); // 木克土
        assertFalse(FiveElementsUtils.isReinforce(FiveElements.WOOD, FiveElements.METAL)); // 金克木
    }

    @Test
    public void testIsRestrain() {
        // 测试五行相克关系
        assertTrue(FiveElementsUtils.isRestrain(FiveElements.WOOD, FiveElements.EARTH));  // 木克土
        assertTrue(FiveElementsUtils.isRestrain(FiveElements.EARTH, FiveElements.WATER)); // 土克水
        assertTrue(FiveElementsUtils.isRestrain(FiveElements.WATER, FiveElements.FIRE));  // 水克火
        assertTrue(FiveElementsUtils.isRestrain(FiveElements.FIRE, FiveElements.METAL)); // 火克金
        assertTrue(FiveElementsUtils.isRestrain(FiveElements.METAL, FiveElements.WOOD)); // 金克木

        // 测试非相克关系
        assertFalse(FiveElementsUtils.isRestrain(FiveElements.WOOD, FiveElements.FIRE));  // 木生火
        assertFalse(FiveElementsUtils.isRestrain(FiveElements.WOOD, FiveElements.WATER)); // 水生木
    }
} 