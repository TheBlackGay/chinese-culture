package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.core.iztro.data.Star;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StarCombinationAnalyzerTest {

    @Test
    void testAnalyzeTrineAndOpposition() {
        // 准备测试数据
        List<Star> stars = new ArrayList<>();
        stars.add(createStar("紫微", 0));
        stars.add(createStar("天机", 4)); // 三方位置
        stars.add(createStar("太阳", 8)); // 三方位置
        stars.add(createStar("武曲", 6)); // 对宫位置

        // 执行测试
        Map<String, List<String>> result = StarCombinationAnalyzer.analyzeTrineAndOpposition(stars, 0);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("三方四正"));
        List<String> combinations = result.get("三方四正");
        assertTrue(combinations.contains("智慧超群")); // 紫微天机组合
        assertTrue(combinations.contains("贵气逼人")); // 紫微太阳组合
        assertTrue(combinations.contains("权威显赫")); // 紫微武曲组合
    }

    @Test
    void testAnalyzeStarConvergence() {
        // 准备测试数据
        List<Star> stars = new ArrayList<>();
        stars.add(createStar("文昌", 0));
        stars.add(createStar("文曲", 0));
        stars.add(createStar("左辅", 1));
        stars.add(createStar("右弼", 1));

        // 执行测试
        Map<String, List<String>> result = StarCombinationAnalyzer.analyzeStarConvergence(stars);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("宫位0"));
        assertTrue(result.containsKey("宫位1"));
        
        List<String> combinations0 = result.get("宫位0");
        assertTrue(combinations0.contains("才华横溢")); // 文昌文曲组合
        
        List<String> combinations1 = result.get("宫位1");
        assertTrue(combinations1.contains("贵人相助")); // 左辅右弼组合
    }

    @Test
    void testAnalyzeMajorCombinations() {
        // 准备测试数据
        List<Star> stars = new ArrayList<>();
        stars.add(createStar("天府", 0));
        stars.add(createStar("太阴", 0));
        stars.add(createStar("贪狼", 4));

        // 执行测试
        Map<String, List<String>> result = StarCombinationAnalyzer.analyzeTrineAndOpposition(stars, 0);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("三方四正"));
        List<String> combinations = result.get("三方四正");
        assertTrue(combinations.contains("富贵安逸")); // 天府太阴组合
    }

    @Test
    void testAnalyzeLuckyCombinations() {
        // 准备测试数据
        List<Star> stars = new ArrayList<>();
        stars.add(createStar("天魁", 0));
        stars.add(createStar("天钺", 0));

        // 执行测试
        Map<String, List<String>> result = StarCombinationAnalyzer.analyzeStarConvergence(stars);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.containsKey("宫位0"));
        List<String> combinations = result.get("宫位0");
        assertTrue(combinations.contains("贵人提携")); // 天魁天钺组合
    }

    @Test
    void testAnalyzeStarCombination() {
        // 创建测试数据
        Palace palace = new Palace(EarthlyBranch.CHEN);
        palace.setName("命宫");
        
        // 添加星耀
        Star star = new Star(StarName.ZIWEI, StarType.MAJOR);
        palace.addMajorStar(star);
        
        // 添加四化
        star.addMutagen(Mutagen.LUCKY);
        
        // 测试分析结果
        String combination = StarCombinationAnalyzer.analyzeStarCombination(palace);
        assertNotNull(combination);
        assertTrue(combination.contains("紫微"));
    }

    private Star createStar(String name, int position) {
        Star star = new Star(StarName.valueOf(name), StarType.MAJOR);
        star.setPosition(position);
        return star;
    }
} 