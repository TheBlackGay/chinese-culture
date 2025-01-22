package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.core.iztro.calculator.BrightnessCalculator;
import com.chinese.culture.admin.core.iztro.constants.AstroConstants;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.StarName;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 宫位吉凶分析器
 */
@Component
public class PalaceAuspiciousnessAnalyzer {

    /**
     * 判定宫位吉凶
     *
     * @param palace 宫位
     * @return 吉凶判定结果
     * @throws IllegalArgumentException 如果输入参数为空
     */
    public static Map<String, Object> judgeAuspiciousness(Palace palace) {
        // 参数检查
        if (palace == null) {
            throw new IllegalArgumentException("宫位不能为空");
        }

        // 获取宫位所有星耀
        List<Star> stars = palace.getAllStars();
        if (stars == null) {
            stars = Collections.emptyList();
        }

        // 一次遍历收集所有需要的信息
        StarAnalysisResult analysisResult = analyzeStars(stars, palace);

        // 计算各项分数
        int luckyScore = calculateLuckyScore(analysisResult.getLuckyStars());
        int unluckyScore = calculateUnluckyScore(analysisResult.getUnluckyStars());
        int brightnessBonus = calculateBrightnessBonus(analysisResult);
        int mutagenBonus = calculateMutagenBonus(analysisResult.getMutagenInfo());

        // 计算最终分数
        int finalScore = calculateFinalScore(luckyScore, unluckyScore, brightnessBonus, mutagenBonus);

        // 生成结果
        return generateResult(palace, analysisResult, luckyScore, unluckyScore,
                brightnessBonus, mutagenBonus, finalScore);
    }

    /**
     * 分析星耀信息
     */
    private static StarAnalysisResult analyzeStars(List<Star> stars, Palace palace) {

        StarAnalysisResult result = new StarAnalysisResult();

        for (Star star : stars) {
            // 检查是否是吉星/凶星
            if (AstroConstants.LUCKY_STARS.contains(star.getName())) {
                result.getLuckyStars().add(star);
            }
            if (AstroConstants.UNLUCKY_STARS.contains(star.getName())) {
                result.getUnluckyStars().add(star);
            }

            // 检查亮度
            Brightness brightness = BrightnessCalculator.calculateBrightness(star, palace.getBranch());
            switch (brightness) {
                case TEMPLE:
                    result.getBrightStars().add(star);
                    break;
                case TRAPPED:
                    result.getDeadStars().add(star);
                    break;
            }

            // 收集四化信息
            for (Mutagen mutagen : star.getMutagens()) {
                result.getMutagenInfo()
                        .computeIfAbsent(mutagen, k -> new ArrayList<>())
                        .add(star);
            }
        }

        return result;
    }

    /**
     * 计算吉星分数
     */
    private static int calculateLuckyScore(List<Star> luckyStars) {

        return luckyStars.stream()
                .mapToInt(star -> {
                    switch (star.getType()) {
                        case MAJOR:
                            return AstroConstants.MAJOR_STAR_WEIGHT;
                        case MINOR:
                            return AstroConstants.MINOR_STAR_WEIGHT;
                        case ADJECTIVE:
                            return AstroConstants.ADJECTIVE_STAR_WEIGHT;
                        default:
                            return 0;
                    }
                })
                .sum() * AstroConstants.SCORE_MULTIPLIER;
    }

    /**
     * 计算凶星分数
     */
    private static int calculateUnluckyScore(List<Star> unluckyStars) {

        return unluckyStars.stream()
                .mapToInt(star -> {
                    switch (star.getType()) {
                        case MAJOR:
                            return AstroConstants.MAJOR_STAR_WEIGHT;
                        case MINOR:
                            return AstroConstants.MINOR_STAR_WEIGHT;
                        case ADJECTIVE:
                            return AstroConstants.ADJECTIVE_STAR_WEIGHT;
                        default:
                            return 0;
                    }
                })
                .sum() * AstroConstants.SCORE_MULTIPLIER;
    }

    /**
     * 计算亮度加成
     */
    private static int calculateBrightnessBonus(StarAnalysisResult result) {

        int bonus = 0;
        bonus += result.getBrightStars().size() * AstroConstants.BRIGHT_BONUS;
        bonus += result.getDeadStars().size() * AstroConstants.DEAD_PENALTY;
        return bonus;
    }

    /**
     * 计算四化加成
     */
    private static int calculateMutagenBonus(Map<Mutagen, List<Star>> mutagenInfo) {

        int bonus = 0;
        for (Map.Entry<Mutagen, List<Star>> entry : mutagenInfo.entrySet()) {
            switch (entry.getKey()) {
                case LUCKY:
                    bonus += entry.getValue().size() * AstroConstants.LUCKY_MUTAGEN_BONUS;
                    break;
                case POWER:
                case SKILL:
                    bonus += entry.getValue().size() * AstroConstants.POWER_SKILL_MUTAGEN_BONUS;
                    break;
                case WEAK:
                    bonus += entry.getValue().size() * AstroConstants.WEAK_MUTAGEN_PENALTY;
                    break;
            }
        }
        return bonus;
    }

    /**
     * 计算最终分数
     */
    private static int calculateFinalScore(int luckyScore, int unluckyScore,
                                           int brightnessBonus, int mutagenBonus) {

        int finalScore = AstroConstants.BASE_SCORE +
                (luckyScore - unluckyScore) + brightnessBonus + mutagenBonus;
        return Math.min(AstroConstants.MAX_SCORE,
                Math.max(AstroConstants.MIN_SCORE, finalScore));
    }

    /**
     * 获取吉凶等级
     */
    private static String getAuspiciousnessLevel(int score) {

        if (score >= AstroConstants.GREAT_AUSPICIOUS_THRESHOLD) {
            return "大吉";
        } else if (score >= AstroConstants.AUSPICIOUS_THRESHOLD) {
            return "吉";
        } else if (score >= AstroConstants.MEDIUM_AUSPICIOUS_THRESHOLD) {
            return "中吉";
        } else if (score >= AstroConstants.MEDIUM_THRESHOLD) {
            return "中平";
        } else if (score >= AstroConstants.MEDIUM_INAUSPICIOUS_THRESHOLD) {
            return "中凶";
        } else if (score >= AstroConstants.INAUSPICIOUS_THRESHOLD) {
            return "凶";
        } else {
            return "大凶";
        }
    }

    /**
     * 生成结果
     */
    private static Map<String, Object> generateResult(Palace palace, StarAnalysisResult analysisResult,
                                                      int luckyScore, int unluckyScore, int brightnessBonus, int mutagenBonus, int finalScore) {

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> analysis = new HashMap<>();

        // 转换星耀名称列表
        analysis.put("luckyStars", analysisResult.getLuckyStars().stream()
                .map(Star::getName).collect(Collectors.toList()));
        analysis.put("unluckyStars", analysisResult.getUnluckyStars().stream()
                .map(Star::getName).collect(Collectors.toList()));
        analysis.put("brightStars", analysisResult.getBrightStars().stream()
                .map(Star::getName).collect(Collectors.toList()));
        analysis.put("deadStars", analysisResult.getDeadStars().stream()
                .map(Star::getName).collect(Collectors.toList()));

        // 转换四化信息
        Map<String, List<String>> mutagenInfo = new HashMap<>();
        analysisResult.getMutagenInfo().forEach((mutagen, stars) ->
                mutagenInfo.put(mutagen.name(), stars.stream()
                        .map(Star::getName)
                        .map(StarName::getDescription)
                        .collect(Collectors.toList())));
        analysis.put("mutagenInfo", mutagenInfo);

        // 添加分数信息
        analysis.put("scores", new HashMap<String, Integer>() {{
            put("luckyScore", luckyScore);
            put("unluckyScore", unluckyScore);
            put("brightnessBonus", brightnessBonus);
            put("mutagenBonus", mutagenBonus);
            put("finalScore", finalScore);
        }});

        // 生成描述
        String description = generateDescription(palace, analysisResult, finalScore);
        analysis.put("description", description);

        // 组装最终结果
        result.put("score", finalScore);
        result.put("level", getAuspiciousnessLevel(finalScore));
        result.put("analysis", analysis);

        return result;
    }

    /**
     * 生成描述文本
     */
    private static String generateDescription(Palace palace, StarAnalysisResult result, int finalScore) {

        StringBuilder description = new StringBuilder();

        // 1. 宫位基本信息
        description.append(String.format("%s：", palace.getName()));

        // 2. 吉凶星耀分析
        if (!result.getLuckyStars().isEmpty()) {
            description.append(String.format("吉星有%s；",
                    result.getLuckyStars().stream()
                            .map(Star::getName)
                            .map(it -> it.getDescription())
                            .collect(Collectors.joining("、"))));
        }
        if (!result.getUnluckyStars().isEmpty()) {
            description.append(String.format("凶星有%s；",
                    result.getUnluckyStars().stream()
                            .map(Star::getName)
                            .map(it -> it.getDescription())
                            .collect(Collectors.joining("、"))));
        }

        // 3. 星耀亮度分析
        if (!result.getBrightStars().isEmpty()) {
            description.append(String.format("明亮星耀有%s；",
                    result.getBrightStars().stream()
                            .map(Star::getName)
                            .map(it -> it.getDescription())
                            .collect(Collectors.joining("、"))));
        }
        if (!result.getDeadStars().isEmpty()) {
            description.append(String.format("失辉星耀有%s；",
                    result.getDeadStars().stream()
                            .map(Star::getName)
                            .map(it -> it.getDescription())
                            .collect(Collectors.joining("、"))));
        }

        // 4. 四化分析
        if (!result.getMutagenInfo().isEmpty()) {
            description.append("四化情况：");
            result.getMutagenInfo().forEach((mutagen, stars) ->
                    description.append(String.format("%s化：%s；",
                            mutagen.name(),
                            stars.stream()
                                    .map(Star::getName)
                                    .map(it -> it.getDescription())
                                    .collect(Collectors.joining("、")))));
        }

        // 5. 总体评价
        description.append(String.format("总体评价为%s。", getAuspiciousnessLevel(finalScore)));

        return description.toString();
    }

    /**
     * 星耀分析结果类
     */
    private static class StarAnalysisResult {

        private final List<Star> luckyStars = new ArrayList<>();

        private final List<Star> unluckyStars = new ArrayList<>();

        private final List<Star> brightStars = new ArrayList<>();

        private final List<Star> deadStars = new ArrayList<>();

        private final Map<Mutagen, List<Star>> mutagenInfo = new HashMap<>();

        public List<Star> getLuckyStars() {

            return luckyStars;
        }

        public List<Star> getUnluckyStars() {

            return unluckyStars;
        }

        public List<Star> getBrightStars() {

            return brightStars;
        }

        public List<Star> getDeadStars() {

            return deadStars;
        }

        public Map<Mutagen, List<Star>> getMutagenInfo() {

            return mutagenInfo;
        }

    }

}
