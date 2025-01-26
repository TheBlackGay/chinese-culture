package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.PalaceName;
import com.chinese.culture.admin.common.core.iztro.data.SoulAndBodyBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.SurroundedPalacesBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 宫位计算工具类测试
 */
@DisplayName("宫位计算工具类测试")
class PalaceUtilsTest {

    @Test
    @DisplayName("测试命宫和身宫计算")
    void testGetSoulAndBody() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(1995, 2, 15, 23, 0);
        
        // 当：正月亥时
        SoulAndBodyBO result = PalaceUtils.getSoulAndBody(birthTime, false);

        // 则
        assertNotNull(result);
        assertEquals(0, result.getSoulIndex());
        assertEquals(0, result.getBodyIndex());
    }

    @Test
    @DisplayName("测试命宫和身宫计算（闰月）")
    void testGetSoulAndBodyWithLeapMonth() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(2023, 4, 20, 23, 0);
        
        // 当：闰二月亥时
        SoulAndBodyBO result = PalaceUtils.getSoulAndBody(birthTime, true);

        // 则
        assertNotNull(result);
        assertNotEquals(8, result.getSoulIndex());
    }

    @Test
    @DisplayName("测试十二宫位安排")
    void testArrangePalaces() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(1995, 2, 15, 23, 0);
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(birthTime, false);

        // 当
        List<PalaceBO> palaces = PalaceUtils.arrangePalaces(soulAndBody);

        // 则
        assertNotNull(palaces);
        assertEquals(12, palaces.size());
        assertEquals(PalaceName.XIONG_DI, palaces.get(0).getName());
        assertEquals(PalaceName.FU_MU, palaces.get(11).getName());
    }

    @Test
    @DisplayName("测试获取三方四正宫位")
    void testGetSurroundedPalaces() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(1995, 2, 15, 23, 0);
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(birthTime, false);
        List<PalaceBO> palaces = PalaceUtils.arrangePalaces(soulAndBody);

        // 当
        SurroundedPalacesBO surroundedPalaces = PalaceUtils.getSurroundedPalaces(palaces.get(0), palaces);

        // 则
        assertNotNull(surroundedPalaces);
        assertEquals(palaces.get(0), surroundedPalaces.getTarget());
        assertEquals(palaces.get(6), surroundedPalaces.getOpposite());
        assertEquals(palaces.get(4), surroundedPalaces.getWealth());
        assertEquals(palaces.get(8), surroundedPalaces.getCareer());
    }

    @Test
    @DisplayName("测试三合关系")
    void testTriangleRelation() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(1995, 2, 15, 23, 0);
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(birthTime, false);
        List<PalaceBO> palaces = PalaceUtils.arrangePalaces(soulAndBody);

        // 当
        boolean isTriangle = PalaceUtils.isTriangle(palaces.get(0), palaces.get(4), palaces.get(8));

        // 则
        assertTrue(isTriangle);
    }

    @Test
    @DisplayName("测试六合关系")
    void testHarmonyRelation() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(1995, 2, 15, 23, 0);
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(birthTime, false);
        List<PalaceBO> palaces = PalaceUtils.arrangePalaces(soulAndBody);

        // 找到地支为子和丑的两个宫位
        PalaceBO ziPalace = palaces.stream()
                .filter(p -> p.getEarthlyBranch() == EarthlyBranch.ZI)
                .findFirst()
                .orElse(null);
        PalaceBO chouPalace = palaces.stream()
                .filter(p -> p.getEarthlyBranch() == EarthlyBranch.CHOU)
                .findFirst()
                .orElse(null);

        // 当：检查这两个宫位是否构成六合关系
        boolean isHarmony = PalaceUtils.isHarmony(ziPalace, chouPalace);

        // 则：应该返回true，因为子丑相合
        assertTrue(isHarmony, "子丑应该构成六合关系");
    }

    @Test
    @DisplayName("测试宫位安排的边界条件")
    void testArrangePalacesBoundary() {
        // 测试子时出生的情况
        LocalDateTime birthTimeZi = LocalDateTime.of(1995, 2, 15, 0, 0);
        SoulAndBodyBO soulAndBodyZi = PalaceUtils.getSoulAndBody(birthTimeZi, false);
        List<PalaceBO> palacesZi = PalaceUtils.arrangePalaces(soulAndBodyZi);
        
        // 验证子时出生时宫位数量和顺序
        assertEquals(12, palacesZi.size());
        assertNotNull(palacesZi.get(11));
        
        // 测试午时出生的情况
        LocalDateTime birthTimeWu = LocalDateTime.of(1995, 2, 15, 12, 0);
        SoulAndBodyBO soulAndBodyWu = PalaceUtils.getSoulAndBody(birthTimeWu, false);
        List<PalaceBO> palacesWu = PalaceUtils.arrangePalaces(soulAndBodyWu);
        
        // 验证午时出生时宫位数量和顺序
        assertEquals(12, palacesWu.size());
        assertNotNull(palacesWu.get(11));
    }

    @Test
    @DisplayName("测试三方四正宫位边界情况")
    void testSurroundedPalacesBoundary() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(1995, 2, 15, 23, 0);
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(birthTime, false);
        List<PalaceBO> palaces = PalaceUtils.arrangePalaces(soulAndBody);

        // 当：传入空参数
        SurroundedPalacesBO surroundedPalaces1 = PalaceUtils.getSurroundedPalaces(null, palaces);
        SurroundedPalacesBO surroundedPalaces2 = PalaceUtils.getSurroundedPalaces(palaces.get(0), null);

        // 则
        assertNull(surroundedPalaces1);
        assertNull(surroundedPalaces2);
    }

    @Test
    @DisplayName("测试三合关系的边界条件")
    void testTriangleRelationBoundary() {
        // 给定
        LocalDateTime birthTime = LocalDateTime.of(1995, 2, 15, 23, 0);
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(birthTime, false);
        List<PalaceBO> palaces = PalaceUtils.arrangePalaces(soulAndBody);

        // 当
        boolean isTriangle = PalaceUtils.isTriangle(palaces.get(0), palaces.get(0), palaces.get(8));

        // 则
        assertFalse(isTriangle);
    }

    @Test
    @DisplayName("测试六合关系的边界条件")
    void testHarmonyRelationBoundary() {
        // 测试相同地支的情况
        PalaceBO palace1 = PalaceBO.builder()
            .earthlyBranch(EarthlyBranch.ZI)
            .build();
        PalaceBO palace2 = PalaceBO.builder()
            .earthlyBranch(EarthlyBranch.ZI)
            .build();
        
        // 验证相同地支不构成六合
        assertFalse(PalaceUtils.isHarmony(palace1, palace2));
        
        // 测试六合的对称性
        PalaceBO palace3 = PalaceBO.builder()
            .earthlyBranch(EarthlyBranch.ZI)
            .build();
        PalaceBO palace4 = PalaceBO.builder()
            .earthlyBranch(EarthlyBranch.CHOU)
            .build();
        
        // 验证六合关系的对称性
        assertTrue(PalaceUtils.isHarmony(palace3, palace4));
        assertTrue(PalaceUtils.isHarmony(palace4, palace3));
        
        // 测试所有六合组合
        EarthlyBranch[] branches = {
            EarthlyBranch.ZI, EarthlyBranch.CHOU,    // 子丑合
            EarthlyBranch.YIN, EarthlyBranch.HAI,    // 寅亥合
            EarthlyBranch.MAO, EarthlyBranch.XU,     // 卯戌合
            EarthlyBranch.CHEN, EarthlyBranch.YOU,   // 辰酉合
            EarthlyBranch.SI, EarthlyBranch.SHEN,    // 巳申合
            EarthlyBranch.WU, EarthlyBranch.WEI      // 午未合
        };
        
        for (int i = 0; i < branches.length; i += 2) {
            PalaceBO p1 = PalaceBO.builder()
                .earthlyBranch(branches[i])
                .build();
            PalaceBO p2 = PalaceBO.builder()
                .earthlyBranch(branches[i + 1])
                .build();
            assertTrue(PalaceUtils.isHarmony(p1, p2), 
                String.format("%s与%s应该六合", branches[i], branches[i + 1]));
        }
    }

    @Test
    @DisplayName("测试特定时间的命宫身宫计算 - 1994年12月8日")
    void testSpecificSoulAndBody() {
        // 给定：1994年12月8日 早上9点05分
        LocalDateTime birthTime = LocalDateTime.of(1994, 12, 8, 9, 5);
        
        // 当：计算命宫和身宫
        SoulAndBodyBO result = PalaceUtils.getSoulAndBody(birthTime, false);

        // 则：命宫应该在戌宫（索引为5），身宫在辰宫（索引为3）
        assertNotNull(result);
        assertEquals(5, result.getSoulIndex(), "命宫应该在戌宫");
        assertEquals(3, result.getBodyIndex(), "身宫应该在辰宫");
    }

    @Test
    @DisplayName("测试特定时间的命宫身宫计算 - 1999年4月18日（谷雨）")
    void testSpecificSoulAndBody2() {
        // 给定：1999年4月18日 12:30（午时，谷雨节气）
        LocalDateTime birthTime = LocalDateTime.of(1999, 4, 18, 12, 30);
        
        // 当：计算命宫和身宫（因为是谷雨节气，应该按四月计算）
        SoulAndBodyBO result = PalaceUtils.getSoulAndBody(birthTime, false);

        // 则：命宫和身宫都应该在戌宫（索引为10）
        assertNotNull(result);
        assertEquals(10, result.getSoulIndex(), "命宫应该在戌宫");
        assertEquals(10, result.getBodyIndex(), "身宫应该在戌宫");

        // 验证地支
        assertEquals(EarthlyBranch.XU, result.getEarthlyBranchOfSoul(), "命宫地支应该为戌");
    }

    @Test
    @DisplayName("测试特定时间的命宫身宫计算 - 2024年11月15日")
    void testSpecificSoulAndBody3() {
        // 给定：2024年11月15日 03:46（寅时）
        LocalDateTime birthTime = LocalDateTime.of(2024, 11, 15, 3, 46);
        
        // 当：计算命宫和身宫
        SoulAndBodyBO result = PalaceUtils.getSoulAndBody(birthTime, false);

        // 则：命宫应该在酉宫（索引为9），身宫在丑宫（索引为1）
        assertNotNull(result);
        assertEquals(9, result.getSoulIndex(), "命宫应该在酉宫");
        assertEquals(1, result.getBodyIndex(), "身宫应该在丑宫");

        // 验证地支
        assertEquals(EarthlyBranch.YOU, result.getEarthlyBranchOfSoul(), "命宫地支应该为酉");
    }
} 