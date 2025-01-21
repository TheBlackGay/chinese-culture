package com.chinese.culture.admin.core.iztro.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class StarCalculatorTest {
    
    @Test
    public void testCalculateMainStars() {
        // 测试主星计算
        // 以1990年为例，命宫在寅宫(2)
        Map<String, Integer> mainStars = StarCalculator.calculateMainStars(2, 1990);
        
        // 验证关键主星位置
        assertNotNull(mainStars);
        assertTrue(mainStars.containsKey("紫微"));
        assertTrue(mainStars.containsKey("天府"));
        
        // 验证主星数量
        assertEquals(14, mainStars.size());
        
        // 验证紫微系主星
        assertTrue(mainStars.containsKey("紫微"));
        assertTrue(mainStars.containsKey("天机"));
        assertTrue(mainStars.containsKey("太阳"));
        assertTrue(mainStars.containsKey("武曲"));
        assertTrue(mainStars.containsKey("天同"));
        assertTrue(mainStars.containsKey("廉贞"));
        
        // 验证天府系主星
        assertTrue(mainStars.containsKey("天府"));
        assertTrue(mainStars.containsKey("太阴"));
        assertTrue(mainStars.containsKey("贪狼"));
        assertTrue(mainStars.containsKey("巨门"));
        assertTrue(mainStars.containsKey("天相"));
        assertTrue(mainStars.containsKey("天梁"));
        assertTrue(mainStars.containsKey("七杀"));
        assertTrue(mainStars.containsKey("破军"));
    }
    
    @Test
    public void testCalculateAuxiliaryStars() {
        // 测试辅星计算
        // 以1990年正月初一为例，命宫在寅宫(2)
        Map<String, Integer> auxStars = StarCalculator.calculateAuxiliaryStars(2, 1990, 1, 1);
        
        // 验证关键辅星位置
        assertNotNull(auxStars);
        assertTrue(auxStars.containsKey("文昌"));
        assertTrue(auxStars.containsKey("文曲"));
        
        // 验证辅星数量
        assertEquals(12, auxStars.size());
        
        // 验证所有辅星
        assertTrue(auxStars.containsKey("文昌"));
        assertTrue(auxStars.containsKey("文曲"));
        assertTrue(auxStars.containsKey("左辅"));
        assertTrue(auxStars.containsKey("右弼"));
        assertTrue(auxStars.containsKey("天魁"));
        assertTrue(auxStars.containsKey("天钺"));
        assertTrue(auxStars.containsKey("禄存"));
        assertTrue(auxStars.containsKey("天马"));
        assertTrue(auxStars.containsKey("擎羊"));
        assertTrue(auxStars.containsKey("陀罗"));
        assertTrue(auxStars.containsKey("火星"));
        assertTrue(auxStars.containsKey("铃星"));
    }
    
    @Test
    public void testCalculateMinorStars() {
        // 测试杂耀计算
        // 以1990年正月初一为例，命宫在寅宫(2)
        Map<String, Integer> minorStars = StarCalculator.calculateMinorStars(2, 1990, 1, 1);
        
        // 验证关键杂耀位置
        assertNotNull(minorStars);
        assertTrue(minorStars.containsKey("地空"));
        assertTrue(minorStars.containsKey("地劫"));
        
        // 验证杂耀数量
        assertEquals(11, minorStars.size());
        
        // 验证所有杂耀
        assertTrue(minorStars.containsKey("地空"));
        assertTrue(minorStars.containsKey("地劫"));
        assertTrue(minorStars.containsKey("台辅"));
        assertTrue(minorStars.containsKey("封诰"));
        assertTrue(minorStars.containsKey("龙池"));
        assertTrue(minorStars.containsKey("凤阁"));
        assertTrue(minorStars.containsKey("天喜"));
        assertTrue(minorStars.containsKey("天姚"));
        assertTrue(minorStars.containsKey("红鸾"));
        assertTrue(minorStars.containsKey("天月"));
        assertTrue(minorStars.containsKey("天刑"));
    }
    
    @Test
    public void testCalculateTransformations() {
        // 测试四化计算
        // 以甲子年为例
        Map<String, List<String>> transformations = StarCalculator.calculateTransformations("甲子");
        
        // 验证四化
        assertNotNull(transformations);
        assertTrue(transformations.containsKey("化禄"));
        assertTrue(transformations.containsKey("化权"));
        assertTrue(transformations.containsKey("化科"));
        assertTrue(transformations.containsKey("化忌"));
        
        // 验证甲年四化
        assertEquals("廉贞", transformations.get("化禄").get(0));
        assertEquals("破军", transformations.get("化权").get(0));
        assertEquals("武曲", transformations.get("化科").get(0));
        assertEquals("太阳", transformations.get("化忌").get(0));
    }
    
    @Test
    public void testCalculateBrightness() {
        // 测试星耀亮度计算
        // 以1990年为例，紫微星在午宫
        Map<String, Integer> starPositions = StarCalculator.calculateMainStars(2, 1990);
        int brightness = StarCalculator.calculateBrightness("紫微", 6, "甲子");
        
        // 验证亮度等级
        assertTrue(brightness >= 0 && brightness <= 4);
    }
    
    @Test
    public void testAnalyzeStarCombinations() {
        Map<String, Integer> starPositions = new HashMap<>();
        starPositions.put("紫微", 0);
        starPositions.put("天府", 4);
        starPositions.put("太阳", 8);
        starPositions.put("武曲", 6);
        
        Map<String, List<String>> combinations = StarCalculator.analyzeStarCombinations(starPositions);
        
        assertNotNull(combinations);
        assertFalse(combinations.isEmpty());
        assertTrue(combinations.containsKey("trine"));
        assertTrue(combinations.containsKey("opposition"));
        assertFalse(combinations.get("trine").isEmpty());
        assertFalse(combinations.get("opposition").isEmpty());
    }
    
    @Test
    public void testCalculateRelations() {
        // 测试三方四正关系分析
        Map<String, Integer> starPositions = StarCalculator.calculateMainStars(2, 1990);
        Map<String, List<String>> relations = StarCalculator.calculateRelations(starPositions);
        
        // 验证关系类型
        assertNotNull(relations);
        assertTrue(relations.containsKey("trine"));
        assertTrue(relations.containsKey("opposition"));
        assertTrue(relations.containsKey("convergence"));
        
        // 验证关系内容
        List<String> trineList = relations.get("trine");
        List<String> oppositionList = relations.get("opposition");
        List<String> convergenceList = relations.get("convergence");
        
        assertNotNull(trineList);
        assertNotNull(oppositionList);
        assertNotNull(convergenceList);
        
        assertFalse(trineList.isEmpty());
        assertFalse(oppositionList.isEmpty());
        assertFalse(convergenceList.isEmpty());
    }
} 
