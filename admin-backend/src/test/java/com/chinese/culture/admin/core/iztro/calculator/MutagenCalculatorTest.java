package com.chinese.culture.admin.core.iztro.calculator;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MutagenCalculatorTest {

    @Test
    void testCalculateMutagenRelationsForJia() {
        // 准备测试数据
        String yearStem = "甲";
        List<String> stars = Arrays.asList("廉贞", "破军", "武曲", "太阳");

        // 执行测试
        Map<String, List<String>> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.containsKey("廉贞"));
        assertTrue(relations.get("廉贞").contains("化禄"));
        assertTrue(relations.containsKey("破军"));
        assertTrue(relations.get("破军").contains("化权"));
        assertTrue(relations.containsKey("武曲"));
        assertTrue(relations.get("武曲").contains("化科"));
        assertTrue(relations.containsKey("太阳"));
        assertTrue(relations.get("太阳").contains("化忌"));
    }

    @Test
    void testCalculateMutagenRelationsForYi() {
        // 准备测试数据
        String yearStem = "乙";
        List<String> stars = Arrays.asList("天机", "天梁", "紫微", "太阴");

        // 执行测试
        Map<String, List<String>> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.containsKey("天机"));
        assertTrue(relations.get("天机").contains("化禄"));
        assertTrue(relations.containsKey("天梁"));
        assertTrue(relations.get("天梁").contains("化权"));
        assertTrue(relations.containsKey("紫微"));
        assertTrue(relations.get("紫微").contains("化科"));
        assertTrue(relations.containsKey("太阴"));
        assertTrue(relations.get("太阴").contains("化忌"));
    }

    @Test
    void testCalculateStarConflictsWithConflict() {
        // 准备测试数据
        Map<String, Integer> starPositions = new HashMap<>();
        starPositions.put("化禄", 0);
        starPositions.put("化忌", 0);
        starPositions.put("化权", 0);

        // 执行测试
        Map<String, List<String>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        // 验证结果
        assertNotNull(conflicts);
        assertTrue(conflicts.containsKey("化禄"));
        assertTrue(conflicts.get("化禄").contains("化忌"));
        assertTrue(conflicts.containsKey("化权"));
        assertTrue(conflicts.get("化权").contains("化忌"));
        assertTrue(conflicts.containsKey("化忌"));
        assertTrue(conflicts.get("化忌").containsAll(Arrays.asList("化禄", "化权")));
    }

    @Test
    void testCalculateStarConflictsWithoutConflict() {
        // 准备测试数据
        Map<String, Integer> starPositions = new HashMap<>();
        starPositions.put("化禄", 0);
        starPositions.put("化权", 1);
        starPositions.put("化科", 2);
        starPositions.put("化忌", 3);

        // 执行测试
        Map<String, List<String>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        // 验证结果
        assertNotNull(conflicts);
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void testCalculateMutagenRelationsWithInvalidYearStem() {
        // 准备测试数据
        String yearStem = "无效";
        List<String> stars = Arrays.asList("廉贞", "破军", "武曲", "太阳");

        // 执行测试
        Map<String, List<String>> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.isEmpty());
    }

    @Test
    void testCalculateMutagenRelationsWithEmptyStars() {
        // 准备测试数据
        String yearStem = "甲";
        List<String> stars = new ArrayList<>();

        // 执行测试
        Map<String, List<String>> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.isEmpty());
    }

    @Test
    void testCalculateStarConflictsWithEmptyPositions() {
        // 准备测试数据
        Map<String, Integer> starPositions = new HashMap<>();

        // 执行测试
        Map<String, List<String>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        // 验证结果
        assertNotNull(conflicts);
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void calculateMutagenRelations() {
        // 测试甲年四化
        String yearStem = "甲";
        List<String> stars = Arrays.asList("廉贞", "破军", "武曲", "太阳", "天机");
        
        Map<String, List<String>> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);
        
        assertNotNull(relations);
        assertTrue(relations.containsKey("廉贞"));
        assertTrue(relations.get("廉贞").contains("化禄"));
        assertTrue(relations.containsKey("破军"));
        assertTrue(relations.get("破军").contains("化权"));
        assertTrue(relations.containsKey("武曲"));
        assertTrue(relations.get("武曲").contains("化科"));
        assertTrue(relations.containsKey("太阳"));
        assertTrue(relations.get("太阳").contains("化忌"));
    }

    @Test
    void calculateMutagenRelationsWithInvalidYear() {
        // 测试无效年干
        String yearStem = "无效";
        List<String> stars = Arrays.asList("廉贞", "破军", "武曲", "太阳");
        
        Map<String, List<String>> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);
        
        assertNotNull(relations);
        assertTrue(relations.isEmpty());
    }

    @Test
    void calculateStarConflicts() {
        // 创建测试数据
        Map<String, Integer> starPositions = new HashMap<>();
        
        // 设置同宫四化冲突
        starPositions.put("化禄", 0);
        starPositions.put("化忌", 0);
        starPositions.put("化权", 0);
        
        Map<String, List<String>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);
        
        assertNotNull(conflicts);
        assertTrue(conflicts.containsKey("化禄"));
        assertTrue(conflicts.get("化禄").contains("化忌"));
        assertTrue(conflicts.containsKey("化权"));
        assertTrue(conflicts.get("化权").contains("化忌"));
        assertTrue(conflicts.containsKey("化忌"));
        assertTrue(conflicts.get("化忌").containsAll(Arrays.asList("化禄", "化权")));
    }

    @Test
    void calculateStarConflictsWithNoConflicts() {
        // 创建测试数据
        Map<String, Integer> starPositions = new HashMap<>();
        
        // 设置无冲突的位置
        starPositions.put("化禄", 0);
        starPositions.put("化权", 1);
        starPositions.put("化科", 2);
        starPositions.put("化忌", 3);
        
        Map<String, List<String>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);
        
        assertNotNull(conflicts);
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void calculateMultipleStarConflicts() {
        // 创建测试数据
        Map<String, Integer> starPositions = new HashMap<>();
        
        // 设置多重四化冲突
        starPositions.put("化禄", 0);
        starPositions.put("化忌", 0);
        starPositions.put("化权", 0);
        starPositions.put("化科", 0);
        
        Map<String, List<String>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);
        
        assertNotNull(conflicts);
        assertTrue(conflicts.containsKey("化禄"));
        assertTrue(conflicts.get("化禄").contains("化忌"));
        assertTrue(conflicts.containsKey("化权"));
        assertTrue(conflicts.get("化权").contains("化忌"));
        assertTrue(conflicts.containsKey("化科"));
        assertTrue(conflicts.get("化科").contains("化忌"));
        assertTrue(conflicts.containsKey("化忌"));
        assertTrue(conflicts.get("化忌").containsAll(Arrays.asList("化禄", "化权", "化科")));
    }
} 