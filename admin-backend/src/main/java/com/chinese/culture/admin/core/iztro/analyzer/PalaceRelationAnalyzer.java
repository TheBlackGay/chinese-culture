package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Palace;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 宫位关系分析器
 */
@Slf4j
public class PalaceRelationAnalyzer {

    // 相刑关系
    private static final Map<String, String> PUNISHMENT_RELATIONS = new HashMap<>();
    static {
        PUNISHMENT_RELATIONS.put("寅", "巳");
        PUNISHMENT_RELATIONS.put("巳", "申");
        PUNISHMENT_RELATIONS.put("申", "寅");
        PUNISHMENT_RELATIONS.put("丑", "未");
        PUNISHMENT_RELATIONS.put("未", "丑");
        PUNISHMENT_RELATIONS.put("子", "卯");
        PUNISHMENT_RELATIONS.put("卯", "子");
        PUNISHMENT_RELATIONS.put("辰", "辰");
        PUNISHMENT_RELATIONS.put("戌", "戌");
        PUNISHMENT_RELATIONS.put("午", "午");
        PUNISHMENT_RELATIONS.put("酉", "酉");
        PUNISHMENT_RELATIONS.put("亥", "亥");
    }

    // 相合关系
    private static final Map<String, String> HARMONY_RELATIONS = new HashMap<>();
    static {
        HARMONY_RELATIONS.put("子", "丑");
        HARMONY_RELATIONS.put("寅", "亥");
        HARMONY_RELATIONS.put("卯", "戌");
        HARMONY_RELATIONS.put("辰", "酉");
        HARMONY_RELATIONS.put("巳", "申");
        HARMONY_RELATIONS.put("午", "未");
    }

    // 三合关系
    private static final Map<String, List<String>> TRINE_RELATIONS = new HashMap<>();
    static {
        TRINE_RELATIONS.put("寅", Arrays.asList("午", "戌")); // 火局
        TRINE_RELATIONS.put("巳", Arrays.asList("酉", "丑")); // 金局
        TRINE_RELATIONS.put("申", Arrays.asList("子", "辰")); // 水局
        TRINE_RELATIONS.put("亥", Arrays.asList("卯", "未")); // 木局
    }

    /**
     * 分析宫位关系
     * 
     * @param palaces 宫位列表
     * @return 宫位关系
     */
    public static Map<String, Map<String, List<String>>> analyzePalaceRelations(List<Palace> palaces) {
        try {
            Map<String, Map<String, List<String>>> relations = new HashMap<>();
            
            // 分析相刑关系
            relations.put("相刑", analyzePunishmentRelations(palaces));
            
            // 分析相合关系
            relations.put("相合", analyzeHarmonyRelations(palaces));
            
            // 分析三合关系
            relations.put("三合", analyzeTrineRelations(palaces));
            
            return relations;

        } catch (Exception e) {
            log.error("宫位关系分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析相刑关系
     */
    private static Map<String, List<String>> analyzePunishmentRelations(List<Palace> palaces) {
        Map<String, List<String>> punishments = new HashMap<>();
        
        for (Palace palace : palaces) {
            String branch = palace.getBranch();
            String punishingBranch = PUNISHMENT_RELATIONS.get(branch);
            
            if (punishingBranch != null) {
                // 查找相刑宫位
                Palace punishingPalace = findPalaceByBranch(palaces, punishingBranch);
                if (punishingPalace != null) {
                    punishments.computeIfAbsent(palace.getName(), k -> new ArrayList<>())
                            .add(punishingPalace.getName());
                }
            }
        }
        
        return punishments;
    }

    /**
     * 分析相合关系
     */
    private static Map<String, List<String>> analyzeHarmonyRelations(List<Palace> palaces) {
        Map<String, List<String>> harmonies = new HashMap<>();
        
        for (Palace palace : palaces) {
            String branch = palace.getBranch();
            String harmonyBranch = HARMONY_RELATIONS.get(branch);
            
            if (harmonyBranch != null) {
                // 查找相合宫位
                Palace harmonyPalace = findPalaceByBranch(palaces, harmonyBranch);
                if (harmonyPalace != null) {
                    harmonies.computeIfAbsent(palace.getName(), k -> new ArrayList<>())
                            .add(harmonyPalace.getName());
                }
            }
        }
        
        return harmonies;
    }

    /**
     * 分析三合关系
     */
    private static Map<String, List<String>> analyzeTrineRelations(List<Palace> palaces) {
        Map<String, List<String>> trines = new HashMap<>();
        
        for (Palace palace : palaces) {
            String branch = palace.getBranch();
            List<String> trineBranches = TRINE_RELATIONS.get(branch);
            
            if (trineBranches != null) {
                List<String> trinePalaces = new ArrayList<>();
                
                // 查找三合宫位
                for (String trineBranch : trineBranches) {
                    Palace trinePalace = findPalaceByBranch(palaces, trineBranch);
                    if (trinePalace != null) {
                        trinePalaces.add(trinePalace.getName());
                    }
                }
                
                if (!trinePalaces.isEmpty()) {
                    trines.put(palace.getName(), trinePalaces);
                }
            }
        }
        
        return trines;
    }

    /**
     * 根据地支查找宫位
     */
    private static Palace findPalaceByBranch(List<Palace> palaces, String branch) {
        return palaces.stream()
                .filter(p -> p.getBranch().equals(branch))
                .findFirst()
                .orElse(null);
    }
} 