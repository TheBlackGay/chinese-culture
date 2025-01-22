package com.chinese.culture.admin.common.core.iztro.analyzer;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康分析器
 */
public class HealthAnalyzer {

    /**
     * 分析健康状况
     */
    public static HealthAnalysisResult analyze(PalaceBO palace) {
        HealthAnalysisResult result = new HealthAnalysisResult();
        result.setScore(calculateScore(palace));
        result.setHealthFactors(analyzeHealthFactors(palace));
        result.setRiskFactors(analyzeRiskFactors(palace));
        result.setSuggestions(generateSuggestions(palace));
        return result;
    }

    /**
     * 计算健康分数
     */
    private static int calculateScore(PalaceBO palace) {
        int score = 60;  // 基础分数

        // 分析主星
        for (StarBO star : palace.getStars()) {
            // 根据星耀亮度评分
            Brightness brightness = star.getBrightnessInfo().getBrightness();
            switch (brightness) {
                case MIAO -> score += 10;  // 庙地加分
                case WANG -> score += 5;   // 旺地加分
                case DE -> score -= 5;     // 陷地减分
                case XIAN -> score -= 10; // 落陷减分
            }

            // 根据星耀性质评分
            switch (star.getName()) {
                case ZIWEI, TIANFU, TAIYANG, WUQU -> score += 10;  // 主星加分
                case TIANXIANG, TIANLIANG -> score += 8;           // 吉星加分
                case POJUN, TIANXING -> score -= 8;                // 煞星减分
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription()) ||
                mutagen.equals(Mutagen.QUAN.getDescription()) ||
                mutagen.equals(Mutagen.KE.getDescription())) {
                score += 5;  // 化禄、化权、化科加分
            } else if (mutagen.equals(Mutagen.JI.getDescription())) {
                score -= 5;  // 化忌减分
            }
        }

        // 确保分数在0-100范围内
        return Math.min(100, Math.max(0, score));
    }

    /**
     * 分析健康因素
     */
    private static List<String> analyzeHealthFactors(PalaceBO palace) {
        List<String> factors = new ArrayList<>();

        // 分析主星
        for (StarBO star : palace.getStars()) {
            // 检查星耀亮度
            Brightness brightness = star.getBrightnessInfo().getBrightness();
            if (brightness == Brightness.MIAO) {
                factors.add(star.getName().getDescription() + "庙旺");
            } else if (brightness == Brightness.WANG) {
                factors.add(star.getName().getDescription() + "有力");
            }

            // 检查吉星
            switch (star.getName()) {
                case ZIWEI -> factors.add("紫微星入主，主身体强健");
                case TIANFU -> factors.add("天府星入主，主体质良好");
                case TAIYANG -> factors.add("太阳星入主，主精力充沛");
                case WUQU -> factors.add("武曲星入主，主体魄健壮");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                factors.add("化禄在此，主身体康泰");
            } else if (mutagen.equals(Mutagen.QUAN.getDescription())) {
                factors.add("化权在此，主体魄强健");
            } else if (mutagen.equals(Mutagen.KE.getDescription())) {
                factors.add("化科在此，主身心平衡");
            }
        }

        return factors;
    }

    /**
     * 分析健康风险因素
     */
    private static List<String> analyzeRiskFactors(PalaceBO palace) {
        List<String> factors = new ArrayList<>();

        // 分析主星
        for (StarBO star : palace.getStars()) {
            // 检查星耀亮度
            Brightness brightness = star.getBrightnessInfo().getBrightness();
            if (brightness == Brightness.DE) {
                factors.add(star.getName().getDescription() + "失势，需注意保养");
            } else if (brightness == Brightness.XIAN) {
                factors.add(star.getName().getDescription() + "陷地，易有不适");
            }

            // 检查煞星
            switch (star.getName()) {
                case POJUN -> factors.add("破军星入主，易有突发状况");
                case TIANXING -> factors.add("天刑星入主，易有外伤");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.JI.getDescription())) {
                factors.add("化忌在此，需注意保健");
            }
        }

        return factors;
    }

    /**
     * 生成健康建议
     */
    private static List<String> generateSuggestions(PalaceBO palace) {
        List<String> suggestions = new ArrayList<>();

        // 根据宫位性质生成建议
        switch (palace.getName()) {
            case "命宫" -> suggestions.add("注意调养身心");
            case "身宫" -> suggestions.add("保持良好作息");
            case "疾厄" -> suggestions.add("定期体检");
            case "迁移" -> suggestions.add("注意运动健身");
        }

        // 根据四化生成建议
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                suggestions.add("保持规律作息");
            } else if (mutagen.equals(Mutagen.JI.getDescription())) {
                suggestions.add("避免过度劳累");
            }
        }

        return suggestions;
    }

    /**
     * 健康分析结果
     */
    public static class HealthAnalysisResult {
        private int score;
        private List<String> healthFactors;
        private List<String> riskFactors;
        private List<String> suggestions;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public List<String> getHealthFactors() {
            return healthFactors;
        }

        public void setHealthFactors(List<String> healthFactors) {
            this.healthFactors = healthFactors;
        }

        public List<String> getRiskFactors() {
            return riskFactors;
        }

        public void setRiskFactors(List<String> riskFactors) {
            this.riskFactors = riskFactors;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }
    }
}
