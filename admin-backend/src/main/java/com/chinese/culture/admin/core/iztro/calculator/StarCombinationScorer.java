package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import java.util.*;

/**
 * 星耀组合评分器
 */
public class StarCombinationScorer {

    // 基础分值配置
    private static final int BASE_SCORE = 60;  // 基础分
    private static final int MAJOR_STAR_WEIGHT = 20;  // 主星权重
    private static final int MINOR_STAR_WEIGHT = 15;  // 辅星权重
    private static final int ADJECTIVE_STAR_WEIGHT = 10;  // 杂耀权重

    /**
     * 计算星耀组合的综合评分
     * 
     * @param stars 星耀列表
     * @param palace 所在宫位
     * @return 评分结果
     */
    public static Map<String, Object> calculateScore(List<Star> stars, Palace palace) {
        Map<String, Object> result = new HashMap<>();
        int totalScore = BASE_SCORE;
        List<String> details = new ArrayList<>();
        
        // 1. 计算星耀本身的分值
        int starScore = calculateStarScore(stars);
        totalScore += starScore;
        details.add(String.format("星耀基础分：%d", starScore));
        
        // 2. 计算亮度加成
        int brightnessScore = calculateBrightnessScore(stars, palace);
        totalScore += brightnessScore;
        details.add(String.format("亮度加成：%d", brightnessScore));
        
        // 3. 计算四化加成
        int mutagenScore = calculateMutagenScore(stars);
        totalScore += mutagenScore;
        details.add(String.format("四化加成：%d", mutagenScore));
        
        // 4. 检查四化冲突扣分
        int conflictPenalty = calculateConflictPenalty(stars, palace);
        totalScore -= conflictPenalty;
        if (conflictPenalty > 0) {
            details.add(String.format("四化冲突扣分：-%d", conflictPenalty));
        }
        
        // 5. 计算宫位加成
        int palaceBonus = calculatePalaceBonus(stars, palace);
        totalScore += palaceBonus;
        details.add(String.format("宫位加成：%d", palaceBonus));
        
        // 确保最终分值在0-100之间
        totalScore = Math.min(100, Math.max(0, totalScore));
        
        // 生成评分等级和建议
        String grade = getScoreGrade(totalScore);
        String suggestion = generateSuggestion(totalScore, stars, palace);
        
        result.put("score", totalScore);
        result.put("grade", grade);
        result.put("details", details);
        result.put("suggestion", suggestion);
        
        return result;
    }

    /**
     * 计算星耀本身的分值
     */
    private static int calculateStarScore(List<Star> stars) {
        int score = 0;
        for (Star star : stars) {
            switch (star.getType()) {
                case MAJOR:
                    score += MAJOR_STAR_WEIGHT;
                    break;
                case MINOR:
                    score += MINOR_STAR_WEIGHT;
                    break;
                case ADJECTIVE:
                    score += ADJECTIVE_STAR_WEIGHT;
                    break;
            }
        }
        return score;
    }

    /**
     * 计算亮度加成
     */
    private static int calculateBrightnessScore(List<Star> stars, Palace palace) {
        int score = 0;
        for (Star star : stars) {
            Brightness brightness = BrightnessCalculator.calculateBrightness(star, palace.getBranch());
            switch (brightness) {
                case TEMPLE:
                    score += 20;
                    break;
                case NORMAL:
                    score += 10;
                    break;
                case WEAK:
                    score += 5;
                    break;
                case TRAPPED:
                    break;
            }
        }
        return score;
    }

    /**
     * 计算四化加成
     */
    private static int calculateMutagenScore(List<Star> stars) {
        int score = 0;
        for (Star star : stars) {
            for (Mutagen mutagen : star.getMutagens()) {
                switch (mutagen) {
                    case LUCKY:
                        score += 15;
                        break;
                    case POWER:
                    case SKILL:
                        score += 10;
                        break;
                    case WEAK:
                        score -= 10;
                        break;
                }
            }
        }
        return score;
    }

    /**
     * 计算四化冲突扣分
     */
    private static int calculateConflictPenalty(List<Star> stars, Palace palace) {
        int penalty = 0;
        Map<String, Object> conflicts = MutagenConflictDetector.detectPalaceConflicts(palace);
        if ((boolean) conflicts.get("hasConflict")) {
            List<Map<String, Object>> conflictList = (List<Map<String, Object>>) conflicts.get("conflicts");
            for (Map<String, Object> conflict : conflictList) {
                Mutagen mutagen1 = (Mutagen) conflict.get("mutagen1");
                Mutagen mutagen2 = (Mutagen) conflict.get("mutagen2");
                penalty += MutagenConflictDetector.getConflictSeverity(mutagen1, mutagen2) / 2;
            }
        }
        return penalty;
    }

    /**
     * 计算宫位加成
     */
    private static int calculatePalaceBonus(List<Star> stars, Palace palace) {
        // TODO: 根据宫位属性计算加成
        return 0;
    }

    /**
     * 获取评分等级
     */
    private static String getScoreGrade(int score) {
        if (score >= 90) {
            return "极好";
        } else if (score >= 80) {
            return "上好";
        } else if (score >= 70) {
            return "良好";
        } else if (score >= 60) {
            return "中等";
        } else if (score >= 50) {
            return "平平";
        } else {
            return "欠佳";
        }
    }

    /**
     * 生成建议
     */
    private static String generateSuggestion(int score, List<Star> stars, Palace palace) {
        StringBuilder suggestion = new StringBuilder();
        
        if (score >= 90) {
            suggestion.append("星耀组合极为有利，可大展宏图。");
        } else if (score >= 80) {
            suggestion.append("星耀组合良好，宜积极进取。");
        } else if (score >= 70) {
            suggestion.append("星耀组合尚可，稳中求进为宜。");
        } else if (score >= 60) {
            suggestion.append("星耀组合平平，需谨慎行事。");
        } else if (score >= 50) {
            suggestion.append("星耀组合欠佳，宜守不宜进。");
        } else {
            suggestion.append("星耀组合不利，需特别谨慎。");
        }
        
        // 添加具体建议
        if (hasStrongMajorStars(stars)) {
            suggestion.append("主星有力，可重点发挥其优势。");
        }
        
        if (hasBrightStars(stars, palace)) {
            suggestion.append("星耀明亮，利于发展。");
        }
        
        if (hasGoodMutagens(stars)) {
            suggestion.append("四化有利，可把握机会。");
        }
        
        return suggestion.toString();
    }

    /**
     * 判断是否有强力主星
     */
    private static boolean hasStrongMajorStars(List<Star> stars) {
        return stars.stream()
            .anyMatch(s -> s.getType() == StarType.MAJOR);
    }

    /**
     * 判断是否有明亮星耀
     */
    private static boolean hasBrightStars(List<Star> stars, Palace palace) {
        return stars.stream()
            .anyMatch(s -> BrightnessCalculator.isBright(s, palace.getBranch()));
    }

    /**
     * 判断是否有有利四化
     */
    private static boolean hasGoodMutagens(List<Star> stars) {
        return stars.stream()
            .anyMatch(s -> s.getMutagens().stream()
                .anyMatch(m -> m == Mutagen.LUCKY || m == Mutagen.POWER || m == Mutagen.SKILL));
    }
} 
