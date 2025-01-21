package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.Star;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 四化星计算器
 */
@Slf4j
public class MutagenCalculator {

    // 四化规则表
    private static final Map<HeavenlyStem, Map<Mutagen, String>> MUTAGEN_RULES = new HashMap<>();
    
    static {
        // 甲年四化
        Map<Mutagen, String> jiaRules = new HashMap<>();
        jiaRules.put(Mutagen.LUCKY, "廉贞");
        jiaRules.put(Mutagen.POWER, "破军");
        jiaRules.put(Mutagen.SKILL, "武曲");
        jiaRules.put(Mutagen.WEAK, "太阳");
        MUTAGEN_RULES.put(HeavenlyStem.JIA, jiaRules);
        
        // 乙年四化
        Map<Mutagen, String> yiRules = new HashMap<>();
        yiRules.put(Mutagen.LUCKY, "天机");
        yiRules.put(Mutagen.POWER, "天梁");
        yiRules.put(Mutagen.SKILL, "紫微");
        yiRules.put(Mutagen.WEAK, "太阴");
        MUTAGEN_RULES.put(HeavenlyStem.YI, yiRules);
        
        // 丙年四化
        Map<Mutagen, String> bingRules = new HashMap<>();
        bingRules.put(Mutagen.LUCKY, "天同");
        bingRules.put(Mutagen.POWER, "天机");
        bingRules.put(Mutagen.SKILL, "文昌");
        bingRules.put(Mutagen.WEAK, "廉贞");
        MUTAGEN_RULES.put(HeavenlyStem.BING, bingRules);
        
        // 丁年四化
        Map<Mutagen, String> dingRules = new HashMap<>();
        dingRules.put(Mutagen.LUCKY, "太阳");
        dingRules.put(Mutagen.POWER, "武曲");
        dingRules.put(Mutagen.SKILL, "天同");
        dingRules.put(Mutagen.WEAK, "天机");
        MUTAGEN_RULES.put(HeavenlyStem.DING, dingRules);
        
        // 戊年四化
        Map<Mutagen, String> wuRules = new HashMap<>();
        wuRules.put(Mutagen.LUCKY, "武曲");
        wuRules.put(Mutagen.POWER, "贪狼");
        wuRules.put(Mutagen.SKILL, "太阴");
        wuRules.put(Mutagen.WEAK, "天同");
        MUTAGEN_RULES.put(HeavenlyStem.WU, wuRules);
        
        // 己年四化
        Map<Mutagen, String> jiRules = new HashMap<>();
        jiRules.put(Mutagen.LUCKY, "太阴");
        jiRules.put(Mutagen.POWER, "紫微");
        jiRules.put(Mutagen.SKILL, "天梁");
        jiRules.put(Mutagen.WEAK, "文曲");
        MUTAGEN_RULES.put(HeavenlyStem.JI, jiRules);
        
        // 庚年四化
        Map<Mutagen, String> gengRules = new HashMap<>();
        gengRules.put(Mutagen.LUCKY, "贪狼");
        gengRules.put(Mutagen.POWER, "太阴");
        gengRules.put(Mutagen.SKILL, "右弼");
        gengRules.put(Mutagen.WEAK, "武曲");
        MUTAGEN_RULES.put(HeavenlyStem.GENG, gengRules);
        
        // 辛年四化
        Map<Mutagen, String> xinRules = new HashMap<>();
        xinRules.put(Mutagen.LUCKY, "巨门");
        xinRules.put(Mutagen.POWER, "天同");
        xinRules.put(Mutagen.SKILL, "文曲");
        xinRules.put(Mutagen.WEAK, "贪狼");
        MUTAGEN_RULES.put(HeavenlyStem.XIN, xinRules);
        
        // 壬年四化
        Map<Mutagen, String> renRules = new HashMap<>();
        renRules.put(Mutagen.LUCKY, "天梁");
        renRules.put(Mutagen.POWER, "文昌");
        renRules.put(Mutagen.SKILL, "左辅");
        renRules.put(Mutagen.WEAK, "巨门");
        MUTAGEN_RULES.put(HeavenlyStem.REN, renRules);
        
        // 癸年四化
        Map<Mutagen, String> guiRules = new HashMap<>();
        guiRules.put(Mutagen.LUCKY, "破军");
        guiRules.put(Mutagen.POWER, "巨门");
        guiRules.put(Mutagen.SKILL, "廉贞");
        guiRules.put(Mutagen.WEAK, "文昌");
        MUTAGEN_RULES.put(HeavenlyStem.GUI, guiRules);
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
     * 获取星耀的四化
     * 
     * @param yearStem 年干
     * @param star 星耀
     * @return 四化类型，如果没有四化则返回null
     */
    public static Mutagen getMutagen(HeavenlyStem yearStem, Star star) {
        Map<Mutagen, String> rules = MUTAGEN_RULES.get(yearStem);
        if (rules != null) {
            for (Map.Entry<Mutagen, String> entry : rules.entrySet()) {
                if (entry.getValue().equals(star.getName())) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
    
    /**
     * 获取指定四化对应的星耀
     * 
     * @param yearStem 年干
     * @param mutagen 四化类型
     * @return 星耀名称
     */
    public static String getMutagenStar(HeavenlyStem yearStem, Mutagen mutagen) {
        Map<Mutagen, String> rules = MUTAGEN_RULES.get(yearStem);
        if (rules != null) {
            return rules.get(mutagen);
        }
        return null;
    }

    /**
     * 计算四化关系
     * 
     * @param yearStem 年干
     * @param stars 星耀列表
     * @return 四化关系
     */
    public static Map<String, List<String>> calculateMutagenRelations(HeavenlyStem yearStem, List<String> stars) {
        try {
            Map<String, List<String>> relations = new HashMap<>();
            Map<Mutagen, String> yearRules = MUTAGEN_RULES.get(yearStem);
            
            if (yearRules != null) {
                for (String star : stars) {
                    for (Map.Entry<Mutagen, String> entry : yearRules.entrySet()) {
                        if (entry.getValue().equals(star)) {
                            relations.computeIfAbsent(star, k -> new ArrayList<>())
                                    .add(entry.getKey().getDescription());
                        }
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
    public static Map<String, List<String>> calculateTransformations(HeavenlyStem yearStem) {
        try {
            Map<String, List<String>> transformations = new HashMap<>();
            Map<Mutagen, String> yearRules = MUTAGEN_RULES.get(yearStem);
            
            if (yearRules != null) {
                for (Map.Entry<Mutagen, String> entry : yearRules.entrySet()) {
                    String star = entry.getValue();
                    String mutagen = entry.getKey().getDescription();
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