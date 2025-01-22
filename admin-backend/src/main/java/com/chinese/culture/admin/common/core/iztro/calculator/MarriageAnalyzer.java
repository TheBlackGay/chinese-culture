package com.chinese.culture.admin.common.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.common.core.iztro.data.enums.Brightness;

import java.util.*;

/**
 * 婚姻感情分析器
 */
public class MarriageAnalyzer {

    // 吉星列表
    private static final List<String> LUCKY_STARS = Arrays.asList(
        "天同", "文昌", "文曲", "左辅", "右弼", "天魁", "天钺"
    );

    // 桃花星列表
    private static final List<String> ROMANCE_STARS = Arrays.asList(
        "红鸾", "天喜", "咸池"
    );

    // 煞星列表
    private static final List<String> EVIL_STARS = Arrays.asList(
        "七杀", "破军", "天刑", "天姚", "火星", "铃星"
    );

    /**
     * 分析婚姻感情
     *
     * @param palaces 所有宫位列表
     * @param age 年龄
     * @param gender 性别
     * @return 婚姻感情分析结果
     */
    public static Map<String, Object> analyzeMarriage(List<PalaceBO> palaces,
                                                    int age,
                                                    boolean gender) {
        Map<String, Object> result = new HashMap<>();

        // 1. 找到夫妻宫
        PalaceBO marriagePalace = findMarriagePalace(palaces);
        result.put("marriagePalace", marriagePalace);

        // 2. 分析夫妻宫强度
        int strength = analyzeMarriagePalaceStrength(marriagePalace);
        result.put("strength", strength);

        // 3. 分析桃花运
        Map<String, Object> romanceAnalysis = analyzeRomance(palaces);
        result.put("romance", romanceAnalysis);

        // 4. 分析婚姻时间
        Map<String, Object> timingAnalysis = analyzeMarriageTiming(marriagePalace, age, gender);
        result.put("timing", timingAnalysis);

        // 5. 生成综合建议
        String suggestion = generateMarriageSuggestion(strength,
                                                     (int)romanceAnalysis.get("score"),
                                                     age);
        result.put("suggestion", suggestion);

        return result;
    }

    /**
     * 找到夫妻宫
     */
    private static PalaceBO findMarriagePalace(List<PalaceBO> palaces) {
        for (PalaceBO palace : palaces) {
            if ("夫妻".equals(palace.getName())) {
                return palace;
            }
        }
        return null;
    }

    /**
     * 分析夫妻宫强度
     */
    private static int analyzeMarriagePalaceStrength(PalaceBO palace) {
        if (palace == null) {
            return 0;
        }

        int strength = 60; // 基础分

        // 1. 分析吉星
        for (StarBO star : palace.getAllStars()) {
            if (LUCKY_STARS.contains(star.getName())) {
                strength += 5;
            }
            if (EVIL_STARS.contains(star.getName())) {
                strength -= 5;
            }
        }

        // 2. 分析四化
        if (!palace.getMutagens().isEmpty()) {
            strength += 5;
        }

        // 3. 分析星耀亮度
        for (StarBO star : palace.getMajorStars()) {
            strength += calculateBrightnessEffect(star);
        }

        // 确保分数在0-100之间
        return Math.max(0, Math.min(100, strength));
    }

    private static int calculateBrightnessEffect(StarBO star) {
        Brightness Brightness = (star.getBrightnessInfo().getBrightness());
        switch (Brightness) {
            case MIAO:
                return 2;
            case WANG:
                return 1;
            case DE:
                return -1;
            case XIAN:
                return -2;
            default:
                return 0;
        }
    }

    /**
     * 分析桃花运
     */
    private static Map<String, Object> analyzeRomance(List<PalaceBO> palaces) {
        Map<String, Object> result = new HashMap<>();
        int score = 60; // 基础分
        List<String> details = new ArrayList<>();

        // 遍历所有宫位寻找桃花星
        for (PalaceBO palace : palaces) {
            for (StarBO star : palace.getAllStars()) {
                if (ROMANCE_STARS.contains(star.getName())) {
                    score += 10;
                    details.add(String.format("%s星位于%s宫", star.getName(), palace.getName()));
                }
            }
        }

        // 确保分数在0-100之间
        score = Math.max(0, Math.min(100, score));
        result.put("score", score);
        result.put("details", details);

        // 生成桃花运描述
        String description;
        if (score >= 80) {
            description = "桃花运旺盛，容易获得异性青睐。";
        } else if (score >= 60) {
            description = "桃花运平稳，感情发展自然。";
        } else {
            description = "桃花运较弱，需要主动经营感情。";
        }
        result.put("description", description);

        return result;
    }

    /**
     * 分析婚姻时间
     */
    private static Map<String, Object> analyzeMarriageTiming(PalaceBO palace,
                                                             int age,
                                                             boolean gender) {
        Map<String, Object> result = new HashMap<>();

        // 基于年龄和性别的基础判断
        int bestAge;
        if (gender) { // 男性
            bestAge = 28;
        } else { // 女性
            bestAge = 26;
        }

        // 根据宫位星耀调整最佳年龄
        for (StarBO star : palace.getAllStars()) {
            if (LUCKY_STARS.contains(star.getName())) {
                bestAge -= 1;
            }
            if (EVIL_STARS.contains(star.getName())) {
                bestAge += 1;
            }
        }

        result.put("bestAge", bestAge);

        // 生成时间建议
        String suggestion;
        if (age < bestAge - 2) {
            suggestion = String.format("建议%d岁左右考虑婚姻大事。", bestAge);
        } else if (age <= bestAge + 2) {
            suggestion = "目前正值适婚年龄，可以认真考虑婚姻大事。";
        } else {
            suggestion = "已过最佳婚龄，建议适时把握机会。";
        }
        result.put("suggestion", suggestion);

        return result;
    }

    /**
     * 生成婚姻建议
     */
    private static String generateMarriageSuggestion(int strength,
                                                   int romanceScore,
                                                   int age) {
        StringBuilder suggestion = new StringBuilder();

        // 1. 根据夫妻宫强度给出建议
        if (strength >= 80) {
            suggestion.append("婚姻基础良好，具有稳定持久的潜质。");
        } else if (strength >= 60) {
            suggestion.append("婚姻基础尚可，需要用心经营。");
        } else {
            suggestion.append("婚姻基础较弱，需要特别注意维护。");
        }

        // 2. 根据桃花运给出建议
        if (romanceScore >= 80) {
            suggestion.append("感情机会众多，建议谨慎选择，避免感情纠纷。");
        } else if (romanceScore >= 60) {
            suggestion.append("感情发展顺利，建议把握机会，认真对待。");
        } else {
            suggestion.append("感情机会较少，建议主动创造机会，增加社交。");
        }

        // 3. 根据年龄给出建议
        if (age < 25) {
            suggestion.append("年龄尚轻，建议先专注个人发展，稳步提升自我。");
        } else if (age < 35) {
            suggestion.append("正值适婚年龄，建议认真考虑终身大事。");
        } else {
            suggestion.append("建议适时把握机会，不要过分挑剔。");
        }

        return suggestion.toString();
    }

    /**
     * 分析婚姻状况
     */
    private static Map<String, Object> analyzeMarriage(PalaceBO marriagePalace, int age, boolean gender) {
        Map<String, Object> result = new HashMap<>();
        int score = 60; // 基础分

        // 分析主星
        for (StarBO star : marriagePalace.getMajorStars()) {
            switch (star.getBrightnessInfo().getBrightness()) {
                case MIAO:
                    score += 10;
                    break;
                case BU:
                    score -= 5;
                    break;
                case XIAN:
                    score -= 10;
                    break;
            }
        }

        // 分析四化
        for (String mutagenStr : marriagePalace.getMutagens()) {
            try {
                Mutagen mutagen = parseMutagen(mutagenStr);
                switch (mutagen) {
                    case LU:
                        score += 10;
                        break;
                    case QUAN:
                        score += 5;
                        break;
                    case JI:
                        score -= 5;
                        break;
                }
            } catch (IllegalArgumentException e) {
                // 忽略无效的四化值
            }
        }

        // 确保分数在0-100之间
        score = Math.max(0, Math.min(100, score));
        result.put("score", score);

        return result;
    }

    private static Mutagen parseMutagen(String text) {
        return Mutagen.fromString(text);
    }
}
