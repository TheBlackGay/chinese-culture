package com.chinese.culture.admin.common.core.iztro.analyzer;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 宫位吉凶分析器
 */
@Component
public class PalaceAuspiciousnessAnalyzer {

    /**
     * 分析宫位吉凶
     */
    public static PalaceAuspiciousnessResult analyze(PalaceBO palace) {
        PalaceAuspiciousnessResult result = new PalaceAuspiciousnessResult();
        result.setScore(getScore(palace));
        result.setAuspiciousFactors(analyzeAuspiciousFactors(palace));
        result.setInauspiciousFactors(analyzeInauspiciousFactors(palace));
        result.setSuggestions(generateSuggestions(palace));
        return result;
    }

    /**
     * 计算宫位吉凶分数
     */
    private static int getScore(PalaceBO palace) {
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
     * 分析吉利因素
     */
    private static List<String> analyzeAuspiciousFactors(PalaceBO palace) {
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
                case ZIWEI -> factors.add("紫微星入主，主贵气");
                case TIANFU -> factors.add("天府星入主，主富贵");
                case TAIYANG -> factors.add("太阳星入主，主光明");
                case WUQU -> factors.add("武曲星入主，主权力");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                factors.add("化禄在此，主财运");
            } else if (mutagen.equals(Mutagen.QUAN.getDescription())) {
                factors.add("化权在此，主权力");
            } else if (mutagen.equals(Mutagen.KE.getDescription())) {
                factors.add("化科在此，主学业");
            }
        }

        return factors;
    }

    /**
     * 分析凶险因素
     */
    private static List<String> analyzeInauspiciousFactors(PalaceBO palace) {
        List<String> factors = new ArrayList<>();

        // 分析主星
        for (StarBO star : palace.getStars()) {
            // 检查星耀亮度
            Brightness brightness = star.getBrightnessInfo().getBrightness();
            if (brightness == Brightness.DE) {
                factors.add(star.getName().getDescription() + "失势");
            } else if (brightness == Brightness.XIAN) {
                factors.add(star.getName().getDescription() + "陷地");
            }

            // 检查煞星
            switch (star.getName()) {
                case POJUN -> factors.add("破军星入主，主波折");
                case TIANXING -> factors.add("天刑星入主，主灾厄");
            }
        }

        // 分析四化
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.JI.getDescription())) {
                factors.add("化忌在此，主阻碍");
            }
        }

        return factors;
    }

    /**
     * 生成建议
     */
    private static List<String> generateSuggestions(PalaceBO palace) {
        List<String> suggestions = new ArrayList<>();

        // 根据宫位性质生成建议
        switch (palace.getName()) {
            case "命宫" -> suggestions.add("注意个人发展方向");
            case "财帛" -> suggestions.add("注意理财投资");
            case "官禄" -> suggestions.add("注意事业发展");
            case "田宅" -> suggestions.add("注意居住环境");
        }

        // 根据四化生成建议
        for (String mutagen : palace.getMutagens()) {
            if (mutagen.equals(Mutagen.LU.getDescription())) {
                suggestions.add("可以主动把握机会");
            } else if (mutagen.equals(Mutagen.JI.getDescription())) {
                suggestions.add("需要谨慎行事");
            }
        }

        return suggestions;
    }

    /**
     * 宫位吉凶分析结果
     */
    public static class PalaceAuspiciousnessResult {
        private int score;                    // 分数
        private String level;                 // 等级
        private String interpretation;        // 解释
        private List<String> auspiciousFactors;    // 吉利因素
        private List<String> inauspiciousFactors;  // 不利因素
        private List<String> suggestions;          // 建议

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
            // 根据分数设置等级
            if (score >= 80) {
                this.level = "上吉";
            } else if (score >= 60) {
                this.level = "吉";
            } else if (score >= 40) {
                this.level = "平";
            } else if (score >= 20) {
                this.level = "凶";
            } else {
                this.level = "大凶";
            }
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getInterpretation() {
            return interpretation;
        }

        public void setInterpretation(String interpretation) {
            this.interpretation = interpretation;
        }

        public List<String> getAuspiciousFactors() {
            return auspiciousFactors;
        }

        public void setAuspiciousFactors(List<String> auspiciousFactors) {
            this.auspiciousFactors = auspiciousFactors;
        }

        public List<String> getInauspiciousFactors() {
            return inauspiciousFactors;
        }

        public void setInauspiciousFactors(List<String> inauspiciousFactors) {
            this.inauspiciousFactors = inauspiciousFactors;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }

        public boolean containsKey(String key) {
            switch (key) {
                case "score":
                    return true;
                case "level":
                    return level != null;
                case "interpretation":
                    return interpretation != null;
                case "auspiciousFactors":
                    return auspiciousFactors != null;
                case "inauspiciousFactors":
                    return inauspiciousFactors != null;
                case "suggestions":
                    return suggestions != null;
                default:
                    return false;
            }
        }

        public Object get(String key) {
            switch (key) {
                case "score":
                    return score;
                case "level":
                    return level;
                case "interpretation":
                    return interpretation;
                case "auspiciousFactors":
                    return auspiciousFactors;
                case "inauspiciousFactors":
                    return inauspiciousFactors;
                case "suggestions":
                    return suggestions;
                default:
                    return null;
            }
        }
    }

}
