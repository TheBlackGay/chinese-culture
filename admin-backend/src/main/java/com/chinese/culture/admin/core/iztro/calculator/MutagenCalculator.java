package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 四化计算器
 */
@Slf4j
public class MutagenCalculator {

    // 四化规则
    private static final Map<String, Map<String, String>> MUTAGEN_RULES = new HashMap<>();
    static {
        // 甲年四化
        Map<String, String> jiaRules = new HashMap<>();
        jiaRules.put("廉贞", "化禄");
        jiaRules.put("破军", "化权");
        jiaRules.put("武曲", "化科");
        jiaRules.put("太阳", "化忌");
        MUTAGEN_RULES.put("甲", jiaRules);

        // 乙年四化
        Map<String, String> yiRules = new HashMap<>();
        yiRules.put("天机", "化禄");
        yiRules.put("天梁", "化权");
        yiRules.put("紫微", "化科");
        yiRules.put("太阴", "化忌");
        MUTAGEN_RULES.put("乙", yiRules);

        // 丙年四化
        Map<String, String> bingRules = new HashMap<>();
        bingRules.put("天同", "化禄");
        bingRules.put("天机", "化权");
        bingRules.put("文昌", "化科");
        bingRules.put("廉贞", "化忌");
        MUTAGEN_RULES.put("丙", bingRules);

        // 丁年四化
        Map<String, String> dingRules = new HashMap<>();
        dingRules.put("太阳", "化禄");
        dingRules.put("天同", "化权");
        dingRules.put("文曲", "化科");
        dingRules.put("天机", "化忌");
        MUTAGEN_RULES.put("丁", dingRules);

        // 戊年四化
        Map<String, String> wuRules = new HashMap<>();
        wuRules.put("武曲", "化禄");
        wuRules.put("太阳", "化权");
        wuRules.put("天魁", "化科");
        wuRules.put("天同", "化忌");
        MUTAGEN_RULES.put("戊", wuRules);

        // 己年四化
        Map<String, String> jiRules = new HashMap<>();
        jiRules.put("太阴", "化禄");
        jiRules.put("武曲", "化权");
        jiRules.put("天钺", "化科");
        jiRules.put("太阳", "化忌");
        MUTAGEN_RULES.put("己", jiRules);

        // 庚年四化
        Map<String, String> gengRules = new HashMap<>();
        gengRules.put("贪狼", "化禄");
        gengRules.put("太阴", "化权");
        gengRules.put("右弼", "化科");
        gengRules.put("武曲", "化忌");
        MUTAGEN_RULES.put("庚", gengRules);

        // 辛年四化
        Map<String, String> xinRules = new HashMap<>();
        xinRules.put("巨门", "化禄");
        xinRules.put("贪狼", "化权");
        xinRules.put("左辅", "化科");
        xinRules.put("太阴", "化忌");
        MUTAGEN_RULES.put("辛", xinRules);

        // 壬年四化
        Map<String, String> renRules = new HashMap<>();
        renRules.put("天梁", "化禄");
        renRules.put("巨门", "化权");
        renRules.put("文昌", "化科");
        renRules.put("贪狼", "化忌");
        MUTAGEN_RULES.put("壬", renRules);

        // 癸年四化
        Map<String, String> guiRules = new HashMap<>();
        guiRules.put("紫微", "化禄");
        guiRules.put("天梁", "化权");
        guiRules.put("文曲", "化科");
        guiRules.put("巨门", "化忌");
        MUTAGEN_RULES.put("癸", guiRules);
    }

    // 四化冲突规则
    private static final Map<String, List<String>> MUTAGEN_CONFLICTS = new HashMap<>();
    static {
        MUTAGEN_CONFLICTS.put("化禄", Arrays.asList("化忌"));
        MUTAGEN_CONFLICTS.put("化权", Arrays.asList("化忌"));
        MUTAGEN_CONFLICTS.put("化科", Arrays.asList("化忌"));
        MUTAGEN_CONFLICTS.put("化忌", Arrays.asList("化禄", "化权", "化科"));
    }

    /**
     * 计算四化关系
     * 
     * @param yearStem 年干
     * @param stars 星耀列表
     * @return 四化关系
     */
    public static Map<String, List<String>> calculateMutagenRelations(String yearStem, List<String> stars) {
        try {
            Map<String, List<String>> relations = new HashMap<>();
            Map<String, String> yearRules = MUTAGEN_RULES.get(yearStem);
            
            if (yearRules != null) {
                for (String star : stars) {
                    String mutagen = yearRules.get(star);
                    if (mutagen != null) {
                        relations.computeIfAbsent(star, k -> new ArrayList<>())
                                .add(mutagen);
                    }
                }
            }
            
            return relations;

        } catch (Exception e) {
            log.error("四化关系计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 计算星耀冲突
     * 
     * @param starPositions 星耀位置
     * @return 冲突关系
     */
    public static Map<String, List<String>> calculateStarConflicts(Map<String, Integer> starPositions) {
        try {
            Map<String, List<String>> conflicts = new HashMap<>();
            
            // 检查同宫四化冲突
            Map<Integer, List<String>> positionMutagens = new HashMap<>();
            
            // 按宫位分组四化
            for (Map.Entry<String, Integer> entry : starPositions.entrySet()) {
                String star = entry.getKey();
                Integer position = entry.getValue();
                
                if (star.startsWith("化")) {
                    positionMutagens.computeIfAbsent(position, k -> new ArrayList<>())
                            .add(star);
                }
            }
            
            // 检查每个宫位的四化冲突
            for (List<String> mutagens : positionMutagens.values()) {
                if (mutagens.size() >= 2) {
                    for (int i = 0; i < mutagens.size(); i++) {
                        String mutagen1 = mutagens.get(i);
                        for (int j = i + 1; j < mutagens.size(); j++) {
                            String mutagen2 = mutagens.get(j);
                            if (isMutagenConflict(mutagen1, mutagen2)) {
                                conflicts.computeIfAbsent(mutagen1, k -> new ArrayList<>())
                                        .add(mutagen2);
                                conflicts.computeIfAbsent(mutagen2, k -> new ArrayList<>())
                                        .add(mutagen1);
                            }
                        }
                    }
                }
            }
            
            return conflicts;

        } catch (Exception e) {
            log.error("星耀冲突计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 判断四化是否冲突
     */
    private static boolean isMutagenConflict(String mutagen1, String mutagen2) {
        List<String> conflicts = MUTAGEN_CONFLICTS.get(mutagen1);
        return conflicts != null && conflicts.contains(mutagen2);
    }

    /**
     * 计算四化
     * 
     * @param yearStem 年干
     * @return 四化映射
     */
    public static Map<String, List<String>> calculateTransformations(String yearStem) {
        try {
            Map<String, List<String>> transformations = new HashMap<>();
            Map<String, String> yearRules = MUTAGEN_RULES.get(yearStem);
            
            if (yearRules != null) {
                for (Map.Entry<String, String> entry : yearRules.entrySet()) {
                    String star = entry.getKey();
                    String mutagen = entry.getValue();
                    transformations.computeIfAbsent(star, k -> new ArrayList<>())
                            .add(mutagen);
                }
            }
            
            return transformations;
            
        } catch (Exception e) {
            log.error("四化计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
} 