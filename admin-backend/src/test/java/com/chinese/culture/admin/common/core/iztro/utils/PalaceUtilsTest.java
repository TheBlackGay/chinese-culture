package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.SoulAndBodyBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * 宫位计算工具类测试
 */
@DisplayName("宫位计算工具类测试")
class PalaceUtilsTest {

    @Test
    @DisplayName("测试命宫和身宫计算")
    void testGetSoulAndBody() {
        // 测试用例1: 1995年2月15日 23时
        // 乙亥年正月十六日 亥时
        LocalDateTime birthTime1 = LocalDateTime.of(1995, 2, 15, 23, 0);
        SoulAndBodyBO result1 = PalaceUtils.getSoulAndBody(birthTime1, false);
        
        // 验证命宫索引和身宫索引
        assertEquals(0, result1.getSoulIndex());
        assertEquals(10, result1.getBodyIndex());
        
        // 验证命宫天干和地支
        assertEquals(HeavenlyStem.WU, result1.getHeavenlyStemOfSoul());
        assertEquals(EarthlyBranch.YIN, result1.getEarthlyBranchOfSoul());

        // 测试用例2: 1988年8月8日 12时
        // 戊辰年六月廿五日 午时
        LocalDateTime birthTime2 = LocalDateTime.of(1988, 8, 8, 12, 0);
        SoulAndBodyBO result2 = PalaceUtils.getSoulAndBody(birthTime2, false);
        
        // 验证命宫索引和身宫索引
        assertEquals(0, result2.getSoulIndex());
        assertEquals(0, result2.getBodyIndex());
        
        // 验证命宫天干和地支
        assertEquals(HeavenlyStem.JIA, result2.getHeavenlyStemOfSoul());
        assertEquals(EarthlyBranch.YIN, result2.getEarthlyBranchOfSoul());

        // 测试用例3: 2000年1月1日 0时
        // 己卯年十一月廿四日 子时
        LocalDateTime birthTime3 = LocalDateTime.of(2000, 1, 1, 0, 0);
        SoulAndBodyBO result3 = PalaceUtils.getSoulAndBody(birthTime3, false);
        
        // 验证命宫索引和身宫索引
        assertEquals(11, result3.getSoulIndex());
        assertEquals(11, result3.getBodyIndex());
        
        // 验证命宫天干和地支
        assertEquals(HeavenlyStem.DING, result3.getHeavenlyStemOfSoul());
        assertEquals(EarthlyBranch.CHOU, result3.getEarthlyBranchOfSoul());
    }

    @Test
    @DisplayName("测试闰月情况")
    void testGetSoulAndBodyWithLeapMonth() {
        // 测试闰月情况: 2023年闰二月
        LocalDateTime birthTime = LocalDateTime.of(2023, 3, 15, 12, 0);
        
        // 不修正闰月
        SoulAndBodyBO resultWithoutFix = PalaceUtils.getSoulAndBody(birthTime, false);
        
        // 修正闰月
        SoulAndBodyBO resultWithFix = PalaceUtils.getSoulAndBody(birthTime, true);
        
        // 验证结果不同
        assertNotEquals(resultWithoutFix.getSoulIndex(), resultWithFix.getSoulIndex());
    }
} 