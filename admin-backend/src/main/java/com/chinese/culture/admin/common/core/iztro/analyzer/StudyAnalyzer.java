package com.chinese.culture.admin.common.core.iztro.analyzer;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;

import java.util.ArrayList;
import java.util.List;

/**
 * 学业分析器
 */
public class StudyAnalyzer {

    /**
     * 分析学业状况
     */
    public static StudyAnalysisResult analyze(PalaceBO palace) {
        StudyAnalysisResult result = new StudyAnalysisResult();
        result.setScore(calculateScore(palace));
        result.setStudyFactors(analyzeStudyFactors(palace));
        result.setRiskFactors(analyzeRiskFactors(palace));
        result.setSuggestions(generateSuggestions(palace));
        return result;
    }

    /**
     * 计算学业分数
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
                case WENCHANG, WENQU -> score += 10;  // 文昌、文曲加分
                case TIANJI, TAIYANG -> score += 8;   // 天机、太阳加分
                case POJUN, TIANXING -> score -= 8;   // 破军、天刑减分
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription()) ||
                mutagen.equals(Mutagen.KE.getDescription())) {
                score += 5;  // 化禄、化科加分
            } else if (mutagen.equals(Mutagen.JI.getDescription())) {
                score -= 5;  // 化忌减分
            }
        }

        // 确保分数在0-100范围内
        return Math.min(100, Math.max(0, score));
    }

    /**
     * 分析学业有利因素
     */
    private static List<String> analyzeStudyFactors(PalaceBO palace) {
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
                case WENCHANG -> factors.add("文昌星入主，主学习能力强");
                case WENQU -> factors.add("文曲星入主，主思维敏捷");
                case TIANJI -> factors.add("天机星入主，主悟性好");
                case TAIYANG -> factors.add("太阳星入主，主记忆力强");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                factors.add("化禄在此，主学业有成");
            } else if (mutagen.equals(Mutagen.KE.getDescription())) {
                factors.add("化科在此，主学习优秀");
            }
        }

        return factors;
    }

    /**
     * 分析学业风险因素
     */
    private static List<String> analyzeRiskFactors(PalaceBO palace) {
        List<String> factors = new ArrayList<>();

        // 分析主星
        for (StarBO star : palace.getStars()) {
            // 检查星耀亮度
            Brightness brightness = star.getBrightnessInfo().getBrightness();
            if (brightness == Brightness.DE) {
                factors.add(star.getName().getDescription() + "失势，学习效率低");
            } else if (brightness == Brightness.XIAN) {
                factors.add(star.getName().getDescription() + "陷地，学习困难");
            }

            // 检查煞星
            switch (star.getName()) {
                case POJUN -> factors.add("破军星入主，易半途而废");
                case TIANXING -> factors.add("天刑星入主，易分心走神");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.JI.getDescription())) {
                factors.add("化忌在此，学习易受阻");
            }
        }

        return factors;
    }

    /**
     * 生成学业建议
     */
    private static List<String> generateSuggestions(PalaceBO palace) {
        List<String> suggestions = new ArrayList<>();

        // 根据宫位性质生成建议
        switch (palace.getName()) {
            case "命宫" -> suggestions.add("注意培养学习兴趣");
            case "迁移" -> suggestions.add("可考虑出国深造");
            case "官禄" -> suggestions.add("注重实践应用");
            case "财帛" -> suggestions.add("关注专业前景");
        }

        // 根据四化生成建议
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                suggestions.add("坚持学习计划");
            } else if (mutagen.equals(Mutagen.JI.getDescription())) {
                suggestions.add("克服学习障碍");
            }
        }

        return suggestions;
    }

    /**
     * 学业分析结果
     */
    public static class StudyAnalysisResult {
        private int score;
        private List<String> studyFactors;
        private List<String> riskFactors;
        private List<String> suggestions;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public List<String> getStudyFactors() {
            return studyFactors;
        }

        public void setStudyFactors(List<String> studyFactors) {
            this.studyFactors = studyFactors;
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
