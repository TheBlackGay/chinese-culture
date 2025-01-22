package com.chinese.culture.admin.common.core.iztro.analyzer;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 婚姻分析器
 */
@Slf4j
public class MarriageAnalyzer {

    /**
     * 分析婚姻状况
     */
    public static MarriageAnalysisResult analyze(PalaceBO palace) {
        MarriageAnalysisResult result = new MarriageAnalysisResult();
        result.setScore(calculateScore(palace));
        result.setMarriageFactors(analyzeMarriageFactors(palace));
        result.setRiskFactors(analyzeRiskFactors(palace));
        result.setSuggestions(generateSuggestions(palace));
        return result;
    }

    /**
     * 计算婚姻分数
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
                case TAIYANG, TAIYIN -> score += 10;  // 太阳、太阴加分
                case TIANXIANG, TIANLIANG -> score += 8;  // 天相、天梁加分
                case POJUN, TIANXING -> score -= 8;   // 破军、天刑减分
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription()) ||
                mutagen.equals(Mutagen.QUAN.getDescription())) {
                score += 5;  // 化禄、化权加分
            } else if (mutagen.equals(Mutagen.JI.getDescription())) {
                score -= 5;  // 化忌减分
            }
        }

        // 确保分数在0-100范围内
        return Math.min(100, Math.max(0, score));
    }

    /**
     * 分析婚姻有利因素
     */
    private static List<String> analyzeMarriageFactors(PalaceBO palace) {
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
                case TAIYANG -> factors.add("太阳星入主，主婚姻美满");
                case TAIYIN -> factors.add("太阴星入主，主家庭和睦");
                case TIANXIANG -> factors.add("天相星入主，主感情专一");
                case TIANLIANG -> factors.add("天梁星入主，主关系稳定");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                factors.add("化禄在此，主婚姻幸福");
            } else if (mutagen.equals(Mutagen.QUAN.getDescription())) {
                factors.add("化权在此，主家庭地位高");
            }
        }

        return factors;
    }

    /**
     * 分析婚姻风险因素
     */
    private static List<String> analyzeRiskFactors(PalaceBO palace) {
        List<String> factors = new ArrayList<>();

        // 分析主星
        for (StarBO star : palace.getStars()) {
            // 检查星耀亮度
            Brightness brightness = star.getBrightnessInfo().getBrightness();
            if (brightness == Brightness.DE) {
                factors.add(star.getName().getDescription() + "失势，婚姻易有波折");
            } else if (brightness == Brightness.XIAN) {
                factors.add(star.getName().getDescription() + "陷地，感情易受伤");
            }

            // 检查煞星
            switch (star.getName()) {
                case POJUN -> factors.add("破军星入主，主感情不稳定");
                case TIANXING -> factors.add("天刑星入主，主婚姻易有变故");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.JI.getDescription())) {
                factors.add("化忌在此，婚姻易有阻碍");
            }
        }

        return factors;
    }

    /**
     * 生成婚姻建议
     */
    private static List<String> generateSuggestions(PalaceBO palace) {
        List<String> suggestions = new ArrayList<>();

        // 根据宫位性质生成建议
        switch (palace.getName()) {
            case "命宫" -> suggestions.add("注意培养感情");
            case "夫妻" -> suggestions.add("维护婚姻关系");
            case "子女" -> suggestions.add("关注家庭和谐");
            case "财帛" -> suggestions.add("注意家庭经济");
        }

        // 根据四化生成建议
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                suggestions.add("珍惜婚姻幸福");
            } else if (mutagen.equals(Mutagen.JI.getDescription())) {
                suggestions.add("化解婚姻矛盾");
            }
        }

        return suggestions;
    }

    /**
     * 婚姻分析结果
     */
    public static class MarriageAnalysisResult {
        private int score;
        private List<String> marriageFactors;
        private List<String> riskFactors;
        private List<String> suggestions;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public List<String> getMarriageFactors() {
            return marriageFactors;
        }

        public void setMarriageFactors(List<String> marriageFactors) {
            this.marriageFactors = marriageFactors;
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
