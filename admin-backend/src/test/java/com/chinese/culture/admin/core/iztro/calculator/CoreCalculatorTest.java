package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.StarName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoreCalculatorTest {

    @Test
    void testCalculate() {
        // 测试用例：1990年农历7月15日寅时男性
        Astrolabe astrolabe = CoreCalculator.calculate(1990, 7, 15, 3, 1);
        
        // 验证基本信息
        assertNotNull(astrolabe);
        assertEquals("1990年7月15日", astrolabe.getLunarDate());
        assertEquals("男", astrolabe.getGender());
        assertEquals(3, astrolabe.getBirthHour());

        // 验证十二宫
        List<Palace> palaces = astrolabe.getPalaces();
        assertNotNull(palaces);
        assertEquals(12, palaces.size());

        // 验证命宫
        Palace mingGong = palaces.get(0);  // 命宫总是第一个宫位
        assertNotNull(mingGong);
        assertEquals("命宫", mingGong.getName());

        // 验证星耀
        List<Star> stars = astrolabe.getStars();
        assertNotNull(stars);
        assertFalse(stars.isEmpty());

        // 验证主星
        assertTrue(stars.stream().anyMatch(star -> StarName.ZIWEI.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.TIANJI.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.TAIYANG.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.WUQU.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.TIANTONG.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.LIANZHEN.equals(star.getName())));

        // 验证辅星
        assertTrue(stars.stream().anyMatch(star -> StarName.WENCHANG.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.WENQU.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.ZUOFU.equals(star.getName())));
        assertTrue(stars.stream().anyMatch(star -> StarName.YOUBI.equals(star.getName())));

        // 验证四化
        boolean hasTransformation = false;
        for (Palace palace : palaces) {
            if (!palace.getMutagens().isEmpty()) {
                hasTransformation = true;
                break;
            }
        }
        assertTrue(hasTransformation, "至少应该有一个宫位包含四化星");
    }

    @ParameterizedTest
    @CsvSource({
        "1899, 7, 15, 3, 1", // 年份太早
        "2101, 7, 15, 3, 1", // 年份太晚
        "1990, 0, 15, 3, 1", // 无效月份
        "1990, 13, 15, 3, 1", // 无效月份
        "1990, 7, 0, 3, 1",  // 无效日期
        "1990, 7, 31, 3, 1", // 无效日期
        "1990, 7, 15, 0, 1", // 无效时辰
        "1990, 7, 15, 13, 1", // 无效时辰
        "1990, 7, 15, 3, 0",  // 无效性别
        "1990, 7, 15, 3, 3"   // 无效性别
    })
    void testCalculate_InvalidInput(int year, int month, int day, int hour, int gender) {
        assertThrows(BusinessException.class, () -> {
            CoreCalculator.calculate(year, month, day, hour, gender);
        });
    }

    @Test
    void testCalculate_VerifyPalaceRelations() {
        // 测试宫位关系 - 1990年农历7月15日寅时男性
        Astrolabe astrolabe = CoreCalculator.calculate(1990, 7, 15, 3, 1);
        List<Palace> palaces = astrolabe.getPalaces();
        
        // 验证命宫
        boolean hasMingGong = palaces.stream().anyMatch(Palace::isMing);
        assertTrue(hasMingGong, "命盘中应该有命宫");
        
        // 验证地支顺序
        for (int i = 0; i < palaces.size() - 1; i++) {
            EarthlyBranch current = palaces.get(i).getBranch();
            EarthlyBranch next = palaces.get(i + 1).getBranch();
            assertEquals(1, (next.ordinal() - current.ordinal() + 12) % 12, 
                "地支顺序应该是连续的");
        }
    }

    @Test
    void testCalculate_VerifyStarDistribution() {
        // 测试星耀分布 - 1990年农历7月15日寅时男性
        Astrolabe astrolabe = CoreCalculator.calculate(1990, 7, 15, 3, 1);
        
        // 验证每个星耀都被分配到宫位中
        List<Star> stars = astrolabe.getStars();
        List<Palace> palaces = astrolabe.getPalaces();
        
        for (Star star : stars) {
            boolean isAssigned = false;
            for (Palace palace : palaces) {
                if (palace.getStars().contains(star)) {
                    isAssigned = true;
                    break;
                }
            }
            assertTrue(isAssigned, "星耀 " + star.getName() + " 应该被分配到某个宫位");
        }
    }
} 