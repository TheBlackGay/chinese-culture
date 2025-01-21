package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;

import java.util.*;

/**
 * 宫位吉凶判断器
 */
public class PalaceAuspiciousnessCalculator {
    
    // 吉星权重
    private static final Map<String, Integer> AUSPICIOUS_WEIGHTS = new HashMap<>();
    static {
        // 主星
        AUSPICIOUS_WEIGHTS.put("紫微", 10);
        AUSPICIOUS_WEIGHTS.put("天机", 9);
        AUSPICIOUS_WEIGHTS.put("太阳", 8);
        AUSPICIOUS_WEIGHTS.put("武曲", 8);
        AUSPICIOUS_WEIGHTS.put("天同", 7);
        AUSPICIOUS_WEIGHTS.put("天梁", 7);
        
        // 辅星
        AUSPICIOUS_WEIGHTS.put("文昌", 5);
        AUSPICIOUS_WEIGHTS.put("文曲", 5);
        AUSPICIOUS_WEIGHTS.put("左辅", 4);
        AUSPICIOUS_WEIGHTS.put("右弼", 4);
        
        // 杂耀
        AUSPICIOUS_WEIGHTS.put("天魁", 3);
        AUSPICIOUS_WEIGHTS.put("天钺", 3);
        AUSPICIOUS_WEIGHTS.put("禄存", 6);
        AUSPICIOUS_WEIGHTS.put("天马", 3);
        AUSPICIOUS_WEIGHTS.put("三台", 2);
        AUSPICIOUS_WEIGHTS.put("八座", 2);
        AUSPICIOUS_WEIGHTS.put("恩光", 2);
        AUSPICIOUS_WEIGHTS.put("天贵", 2);
        AUSPICIOUS_WEIGHTS.put("天官", 3);
        AUSPICIOUS_WEIGHTS.put("天福", 3);
        AUSPICIOUS_WEIGHTS.put("月德", 2);
        AUSPICIOUS_WEIGHTS.put("天才", 2);
        AUSPICIOUS_WEIGHTS.put("天厨", 1);
        AUSPICIOUS_WEIGHTS.put("解神", 2);
        AUSPICIOUS_WEIGHTS.put("天寿", 2);
        AUSPICIOUS_WEIGHTS.put("龙池", 2);
        AUSPICIOUS_WEIGHTS.put("凤阁", 2);
    }
    
    // 凶星权重
    private static final Map<String, Integer> INAUSPICIOUS_WEIGHTS = new HashMap<>();
    static {
        // 主星
        INAUSPICIOUS_WEIGHTS.put("七杀", -8);
        INAUSPICIOUS_WEIGHTS.put("破军", -8);
        
        // 杂耀
        INAUSPICIOUS_WEIGHTS.put("擎羊", -5);
        INAUSPICIOUS_WEIGHTS.put("陀罗", -5);
        INAUSPICIOUS_WEIGHTS.put("火星", -5);
        INAUSPICIOUS_WEIGHTS.put("铃星", -5);
        INAUSPICIOUS_WEIGHTS.put("地空", -4);
        INAUSPICIOUS_WEIGHTS.put("地劫", -4);
        INAUSPICIOUS_WEIGHTS.put("天使", -3);
        INAUSPICIOUS_WEIGHTS.put("天空", -3);
        INAUSPICIOUS_WEIGHTS.put("天虚", -3);
        INAUSPICIOUS_WEIGHTS.put("天哭", -3);
        INAUSPICIOUS_WEIGHTS.put("孤辰", -2);
        INAUSPICIOUS_WEIGHTS.put("寡宿", -2);
        INAUSPICIOUS_WEIGHTS.put("天刑", -4);
        INAUSPICIOUS_WEIGHTS.put("天伤", -3);
        INAUSPICIOUS_WEIGHTS.put("咸池", -2);
        INAUSPICIOUS_WEIGHTS.put("蜚廉", -2);
        INAUSPICIOUS_WEIGHTS.put("旬空", -3);
        INAUSPICIOUS_WEIGHTS.put("截空", -3);
        INAUSPICIOUS_WEIGHTS.put("空亡", -4);
        INAUSPICIOUS_WEIGHTS.put("阴煞", -3);
    }
    
    /**
     * 计算宫位吉凶评分
     * 
     * @param palace 宫位
     * @return 吉凶评分（0-100）
     */
    public static int calculateAuspiciousness(Palace palace) {
        int score = 50; // 基础分
        
        // 1. 计算星耀权重
        score += calculateStarWeights(palace);
        
        // 2. 考虑星耀亮度
        score += calculateBrightnessEffect(palace);
        
        // 3. 考虑四化影响
        score += calculateMutagenEffect(palace);
        
        // 4. 考虑宫位本身属性
        score += calculatePalaceEffect(palace);
        
        // 确保分数在0-100范围内
        return Math.max(0, Math.min(100, score));
    }
    
    /**
     * 计算星耀权重
     */
    private static int calculateStarWeights(Palace palace) {
        int weight = 0;
        
        // 计算吉星权重
        for (Star star : palace.getAllStars()) {
            Integer auspiciousWeight = AUSPICIOUS_WEIGHTS.get(star.getName());
            if (auspiciousWeight != null) {
                weight += auspiciousWeight;
            }
            
            Integer inauspiciousWeight = INAUSPICIOUS_WEIGHTS.get(star.getName());
            if (inauspiciousWeight != null) {
                weight += inauspiciousWeight;
            }
        }
        
        return weight;
    }
    
    /**
     * 计算亮度影响
     */
    private static int calculateBrightnessEffect(Palace palace) {
        int effect = 0;
        
        for (Star star : palace.getMajorStars()) {
            Brightness brightness = star.getBrightness() != null ? 
                (star.getBrightness()) : Brightness.STRONG;
                
            switch (brightness) {
                case TEMPLE:
                    effect += 5;
                    break;
                case GAIN:
                    effect -= 3;
                    break;
                case TRAPPED:
                    effect -= 5;
                    break;
                default:
                    break;
            }
        }
        
        return effect;
    }
    
    /**
     * 计算四化影响
     */
    private static int calculateMutagenEffect(Palace palace) {
        int effect = 0;
        
        for (Star star : palace.getAllStars()) {
            for (Mutagen mutagen : star.getMutagens()) {
                switch (mutagen) {
                    case LUCKY:
                        effect += 5;
                        break;
                    case POWER:
                        effect += 4;
                        break;
                    case SKILL:
                        effect += 3;
                        break;
                    case WEAK:
                        effect -= 4;
                        break;
                }
            }
        }
        
        return effect;
    }
    
    /**
     * 计算宫位本身属性影响
     */
    private static int calculatePalaceEffect(Palace palace) {
        int effect = 0;
        
        // 重要宫位加分
        if (isImportantPalace(palace.getName())) {
            effect += 5;
        }
        
        // 空亡宫位减分
        if (palace.getAllStars().isEmpty()) {
            effect -= 10;
        }
        
        return effect;
    }
    
    /**
     * 判断是否为重要宫位
     */
    private static boolean isImportantPalace(String palaceName) {
        return "命宫".equals(palaceName) || 
               "身宫".equals(palaceName) || 
               "财帛".equals(palaceName) || 
               "官禄".equals(palaceName);
    }
    
    /**
     * 判断是否为吉星
     */
    private static boolean isAuspiciousStar(Star star) {
        return AUSPICIOUS_WEIGHTS.containsKey(star.getName());
    }
    
    /**
     * 判断是否为煞星
     */
    private static boolean isMaleficStar(Star star) {
        return INAUSPICIOUS_WEIGHTS.containsKey(star.getName());
    }
    
    /**
     * 获取吉凶等级描述
     */
    public static String getAuspiciousnessLevel(int score) {
        if (score >= 90) {
            return "上上";
        } else if (score >= 80) {
            return "上";
        } else if (score >= 70) {
            return "中上";
        } else if (score >= 60) {
            return "中";
        } else if (score >= 50) {
            return "中下";
        } else if (score >= 40) {
            return "下";
        } else {
            return "下下";
        }
    }
    
    /**
     * 获取宫位吉凶详细分析
     */
    public static Map<String, Object> getDetailedAnalysis(Palace palace) {
        Map<String, Object> analysis = new HashMap<>();
        
        // 计算总分
        int score = calculateAuspiciousness(palace);
        analysis.put("score", score);
        analysis.put("level", getAuspiciousnessLevel(score));
        
        // 分析吉星
        List<String> auspiciousStars = new ArrayList<>();
        for (Star star : palace.getAllStars()) {
            if (AUSPICIOUS_WEIGHTS.containsKey(star.getName())) {
                auspiciousStars.add(star.getName());
            }
        }
        analysis.put("auspiciousStars", auspiciousStars);
        
        // 分析凶星
        List<String> inauspiciousStars = new ArrayList<>();
        for (Star star : palace.getAllStars()) {
            if (INAUSPICIOUS_WEIGHTS.containsKey(star.getName())) {
                inauspiciousStars.add(star.getName());
            }
        }
        analysis.put("inauspiciousStars", inauspiciousStars);
        
        // 分析四化
        Map<String, List<String>> mutagens = new HashMap<>();
        for (Star star : palace.getAllStars()) {
            for (Mutagen mutagen : star.getMutagens()) {
                mutagens.computeIfAbsent(mutagen.getDescription(), k -> new ArrayList<>())
                    .add(star.getName());
            }
        }
        analysis.put("mutagens", mutagens);
        
        // 生成解释
        StringBuilder interpretation = new StringBuilder();
        interpretation.append(String.format("%s宫位吉凶分析：\n", palace.getName()));
        interpretation.append(String.format("总体评分：%d分，等级：%s\n", score, getAuspiciousnessLevel(score)));
        
        if (!auspiciousStars.isEmpty()) {
            interpretation.append("吉星：").append(String.join("、", auspiciousStars)).append("\n");
        }
        if (!inauspiciousStars.isEmpty()) {
            interpretation.append("凶星：").append(String.join("、", inauspiciousStars)).append("\n");
        }
        
        for (Map.Entry<String, List<String>> entry : mutagens.entrySet()) {
            interpretation.append(entry.getKey()).append("化：")
                .append(String.join("、", entry.getValue())).append("\n");
        }
        
        analysis.put("interpretation", interpretation.toString());
        
        return analysis;
    }
} 
