package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.common.core.iztro.analyzer.PalaceRelationAnalyzer;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PalaceRelationAnalyzerTest {

    @Test
    void testAnalyzePalaceRelations() {
        // 准备测试数据
        List<PalaceBO> palaces = new ArrayList<>();
        palaces.add(createPalace("命宫", "寅"));
        palaces.add(createPalace("兄弟", "巳"));
        palaces.add(createPalace("夫妻", "申"));
        palaces.add(createPalace("子女", "亥"));

        // 执行测试
        Map<String, Map<String, List<String>>> relations = PalaceRelationAnalyzer.analyzePalaceRelations(palaces);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.containsKey("相刑"));
        assertTrue(relations.containsKey("相合"));
        assertTrue(relations.containsKey("三合"));
    }

    @Test
    void testAnalyzePunishmentRelations() {
        // 准备测试数据
        List<PalaceBO> palaces = new ArrayList<>();
        palaces.add(createPalace("命宫", "寅"));
        palaces.add(createPalace("兄弟", "巳"));
        palaces.add(createPalace("夫妻", "申"));

        // 执行测试
        Map<String, Map<String, List<String>>> relations = PalaceRelationAnalyzer.analyzePalaceRelations(palaces);
        Map<String, List<String>> punishments = relations.get("相刑");

        // 验证结果
        assertNotNull(punishments);
        assertTrue(punishments.containsKey("命宫"));
        assertTrue(punishments.get("命宫").contains("兄弟")); // 寅刑巳
        assertTrue(punishments.containsKey("兄弟"));
        assertTrue(punishments.get("兄弟").contains("夫妻")); // 巳刑申
        assertTrue(punishments.containsKey("夫妻"));
        assertTrue(punishments.get("夫妻").contains("命宫")); // 申刑寅
    }

    @Test
    void testAnalyzeHarmonyRelations() {
        // 准备测试数据
        List<PalaceBO> palaces = new ArrayList<>();
        palaces.add(createPalace("命宫", "子"));
        palaces.add(createPalace("兄弟", "丑"));
        palaces.add(createPalace("夫妻", "寅"));
        palaces.add(createPalace("子女", "亥"));

        // 执行测试
        Map<String, Map<String, List<String>>> relations = PalaceRelationAnalyzer.analyzePalaceRelations(palaces);
        Map<String, List<String>> harmonies = relations.get("相合");

        // 验证结果
        assertNotNull(harmonies);
        assertTrue(harmonies.containsKey("命宫"));
        assertTrue(harmonies.get("命宫").contains("兄弟")); // 子合丑
        assertTrue(harmonies.containsKey("夫妻"));
        assertTrue(harmonies.get("夫妻").contains("子女")); // 寅合亥
    }

    @Test
    void testAnalyzeTrineRelations() {
        // 准备测试数据
        List<PalaceBO> palaces = new ArrayList<>();
        palaces.add(createPalace("命宫", "寅"));
        palaces.add(createPalace("疾厄", "午"));
        palaces.add(createPalace("财帛", "戌"));

        // 执行测试
        Map<String, Map<String, List<String>>> relations = PalaceRelationAnalyzer.analyzePalaceRelations(palaces);
        Map<String, List<String>> trines = relations.get("三合");

        // 验证结果
        assertNotNull(trines);
        assertTrue(trines.containsKey("命宫"));
        List<String> trinePalaces = trines.get("命宫");
        assertEquals(2, trinePalaces.size());
        assertTrue(trinePalaces.contains("疾厄")); // 寅午戌三合
        assertTrue(trinePalaces.contains("财帛"));
    }

    private PalaceBO createPalace(String name, String branch) {
        PalaceBO palace = new PalaceBO();
        palace.setName(name);
        palace.setBranch(branch);
        return palace;
    }
}
