package com.chinese.culture.admin.common.core.iztro.analyzer;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 紫微斗数格局分析器
 */
@Slf4j
public class PatternAnalyzer {

    // 命格类型
    private static final String[] DESTINY_PATTERNS = {
        "财官双美", "印绶格", "官印相生", "财印双美",
        "伤官佩印", "食神生财", "偏财格", "正财格",
        "七杀格", "正官格", "偏官格", "枭神格"
    };

    // 吉星组合
    private static final String[][] LUCKY_COMBINATIONS = {
        {"紫微", "天机", "太阳"}, // 三奇
        {"文昌", "文曲", "左辅", "右弼"}, // 四辅
        {"天魁", "天钺"}, // 魁钺
        // ... 更多组合
    };

    // 凶星组合
    private static final String[][] UNLUCKY_COMBINATIONS = {
        {"擎羊", "陀罗"}, // 羊陀
        {"火星", "铃星"}, // 火铃
        {"地空", "地劫"}, // 空劫
        // ... 更多组合
    };

    // 命主格局星耀
    private static final List<String> MASTER_KEY_STARS = Arrays.asList("紫微", "天府", "贪狼");
    private static final List<String> WISDOM_STARS = Arrays.asList("紫微", "破军");
    private static final List<String> MIND_STARS = Arrays.asList("天府", "太阴");
    private static final List<String> AUTHORITY_STARS = Arrays.asList("紫微", "七杀");

    // 财帛格局星耀
    private static final List<String> WEALTH_KEY_STARS = Arrays.asList("武曲", "天同", "太阳");
    private static final List<String> SIDE_WEALTH_STARS = Arrays.asList("贪狼", "破军", "武曲", "太阳");
    private static final List<String> BUSINESS_STARS = Arrays.asList("武曲", "天相");

    // 官禄格局星耀
    private static final List<String> CAREER_KEY_STARS = Arrays.asList("天机", "天梁", "七杀");
    private static final List<String> ACADEMIC_STARS = Arrays.asList("文昌", "文曲");
    private static final List<String> LEADERSHIP_STARS = Arrays.asList("天机", "天相");

    // 婚姻格局星耀
    private static final List<String> MARRIAGE_KEY_STARS = Arrays.asList("天同", "太阴", "文昌", "文曲");
    private static final List<String> EMOTION_STARS = Arrays.asList("天姚", "红鸾");
    private static final List<String> SPOUSE_STARS = Arrays.asList("天同", "文昌");

    // 四化组合
    private static final List<String> NOBLE_MUTAGENS = Arrays.asList("禄", "权");
    private static final List<String> WEALTH_MUTAGENS = Arrays.asList("禄", "科");
    private static final List<String> CAREER_MUTAGENS = Arrays.asList("权", "科");
    private static final List<String> MARRIAGE_MUTAGENS = Arrays.asList("禄", "科");

    /**
     * 分析命盘格局
     *
     * @param palaces 十二宫位信息
     * @param mainStars 主星位置
     * @param auxiliaryStars 辅星位置
     * @return 格局分析结果
     */
    public static Map<String, Object> analyzePattern(
            Map<Integer, String> palaces,
            Map<String, Integer> mainStars,
            Map<String, Integer> auxiliaryStars) {
        try {
            Map<String, Object> analysis = new HashMap<>();

            // 分析命格类型
            String destinyPattern = analyzeDestinyPattern(palaces, mainStars);
            analysis.put("destinyPattern", destinyPattern);

            // 分析吉星组合
            List<String> luckyPatterns = analyzeLuckyPatterns(mainStars, auxiliaryStars);
            analysis.put("luckyPatterns", luckyPatterns);

            // 分析凶星组合
            List<String> unluckyPatterns = analyzeUnluckyPatterns(mainStars, auxiliaryStars);
            analysis.put("unluckyPatterns", unluckyPatterns);

            // 计算总评分
            int score = calculatePatternScore(destinyPattern, luckyPatterns, unluckyPatterns);
            analysis.put("score", score);

            return analysis;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("格局分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析三方四正关系
     *
     * @param position 宫位
     * @param palaces 十二宫位信息
     * @return 三方四正关系
     */
    public static Map<String, Object> analyzeTrineAndSquare(
            int position, Map<Integer, String> palaces) {
        try {
            Map<String, Object> relations = new HashMap<>();

            // 计算三合位置
            List<Integer> trinePositions = calculateTrinePositions(position);
            relations.put("trine", trinePositions);

            // 计算四冲位置
            List<Integer> squarePositions = calculateSquarePositions(position);
            relations.put("square", squarePositions);

            // 获取宫位信息
            Map<String, List<String>> palaceInfo = new HashMap<>();
            for (int pos : trinePositions) {
                palaceInfo.put("trine_" + pos, getPalaceStars(pos, palaces));
            }
            for (int pos : squarePositions) {
                palaceInfo.put("square_" + pos, getPalaceStars(pos, palaces));
            }
            relations.put("palaceInfo", palaceInfo);

            return relations;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("三方四正分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析星耀组合关系
     *
     * @param position 宫位
     * @param stars 宫位星耀
     * @return 组合关系
     */
    public static List<String> analyzeStarCombinations(int position, List<String> stars) {
        try {
            List<String> combinations = new ArrayList<>();

            // TODO: 实现星耀组合关系分析

            return combinations;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("星耀组合分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析命格类型
     */
    private static String analyzeDestinyPattern(
            Map<Integer, String> palaces, Map<String, Integer> mainStars) {
        // TODO: 实现命格类型分析
        return null;
    }

    /**
     * 分析吉星组合
     */
    private static List<String> analyzeLuckyPatterns(
            Map<String, Integer> mainStars, Map<String, Integer> auxiliaryStars) {
        // TODO: 实现吉星组合分析
        return new ArrayList<>();
    }

    /**
     * 分析凶星组合
     */
    private static List<String> analyzeUnluckyPatterns(
            Map<String, Integer> mainStars, Map<String, Integer> auxiliaryStars) {
        // TODO: 实现凶星组合分析
        return new ArrayList<>();
    }

    /**
     * 计算格局评分
     */
    private static int calculatePatternScore(
            String destinyPattern, List<String> luckyPatterns, List<String> unluckyPatterns) {
        // TODO: 实现格局评分计算
        return 0;
    }

    /**
     * 计算三合位置
     */
    private static List<Integer> calculateTrinePositions(int position) {
        List<Integer> positions = new ArrayList<>();
        positions.add((position + 4) % 12);
        positions.add((position + 8) % 12);
        return positions;
    }

    /**
     * 计算四冲位置
     */
    private static List<Integer> calculateSquarePositions(int position) {
        List<Integer> positions = new ArrayList<>();
        positions.add((position + 3) % 12);
        positions.add((position + 6) % 12);
        positions.add((position + 9) % 12);
        return positions;
    }

    /**
     * 获取宫位星耀
     */
    private static List<String> getPalaceStars(int position, Map<Integer, String> palaces) {
        // TODO: 实现宫位星耀获取
        return new ArrayList<>();
    }

    /**
     * 分析命主格局
     */
    public static boolean isNoblePattern(List<StarBO> stars) {
        return hasKeyStars(stars, MASTER_KEY_STARS) && hasMutagens(stars, NOBLE_MUTAGENS);
    }

    public static boolean isWisdomPattern(List<StarBO> stars) {
        return hasKeyStars(stars, WISDOM_STARS);
    }

    public static boolean isMindPattern(List<StarBO> stars) {
        return hasKeyStars(stars, MIND_STARS);
    }

    public static boolean isAuthorityPattern(List<StarBO> stars) {
        return hasKeyStars(stars, AUTHORITY_STARS);
    }

    /**
     * 分析财帛格局
     */
    public static boolean isWealthPattern(List<StarBO> stars) {
        return hasKeyStars(stars, WEALTH_KEY_STARS) && hasMutagens(stars, WEALTH_MUTAGENS);
    }

    public static boolean isSideWealthPattern(List<StarBO> stars) {
        if (stars == null || stars.isEmpty()) {
            return false;
        }

        // 获取所有星耀名称
        List<String> starNames = stars.stream()
                .map(StarBO::getName)
                .map(StarName::getDescription)
                .collect(java.util.stream.Collectors.toList());

        // 获取所有四化
        List<String> mutagens = stars.stream()
                .filter(star -> star.getMutagenInfo().getMutagens() != null && !star.getMutagenInfo().getMutagens().isEmpty())
                .flatMap(star -> star.getMutagenInfo().getMutagens().stream())
                .map(Mutagen::getDescription)
                .collect(java.util.stream.Collectors.toList());

        // 检查是否包含贪狼或破军
        long sideWealthStarCount = SIDE_WEALTH_STARS.stream()
                .filter(starNames::contains)
                .count();

        // 检查是否包含化禄或化科
        List<String> sideWealthMutagens = Arrays.asList("化禄", "化科");
        long mutagenCount = sideWealthMutagens.stream()
                .filter(mutagens::contains)
                .count();

        return sideWealthStarCount >= 1 && mutagenCount >= 1;
    }

    public static boolean isBusinessPattern(List<StarBO> stars) {
        return hasKeyStars(stars, BUSINESS_STARS);
    }

    /**
     * 分析官禄格局
     */
    public static boolean isCareerPattern(List<StarBO> stars) {
        if (stars == null || stars.isEmpty()) {
            return false;
        }

        // 获取所有星耀名称
        List<String> starNames = stars.stream()
                .map(StarBO::getName)
                .map(StarName::getDescription)
                .collect(java.util.stream.Collectors.toList());

        // 获取所有四化
        List<String> mutagens = stars.stream()
                .filter(star -> star.getMutagenInfo().getMutagens() != null && !star.getMutagenInfo().getMutagens().isEmpty())
                .flatMap(star -> star.getMutagenInfo().getMutagens().stream())
                .map(it->it.getDescription())
                .collect(java.util.stream.Collectors.toList());

        // 检查是否包含天府、破军或天相中的至少两个
        List<String> careerStars = Arrays.asList("天府", "破军", "天相");
        long careerStarCount = careerStars.stream()
                .filter(starNames::contains)
                .count();

        // 检查是否包含化禄、化权、化科中的至少两个
        List<String> careerMutagens = Arrays.asList("化禄", "化权", "化科");
        long mutagenCount = careerMutagens.stream()
                .filter(mutagens::contains)
                .count();

        return careerStarCount >= 2 && mutagenCount >= 2;
    }

    public static boolean isAcademicPattern(List<StarBO> stars) {
        return hasKeyStars(stars, ACADEMIC_STARS);
    }

    public static boolean isLeadershipPattern(List<StarBO> stars) {
        return hasKeyStars(stars, LEADERSHIP_STARS);
    }

    /**
     * 分析婚姻格局
     */
    public static boolean isMarriagePattern(List<StarBO> stars) {
        if (stars == null || stars.isEmpty()) {
            return false;
        }

        // 获取所有星耀名称
        List<String> starNames = stars.stream()
                .map(StarBO::getName)
                .map(StarName::getDescription)
                .collect(java.util.stream.Collectors.toList());

        // 获取所有四化（去掉"化"字）
        List<String> mutagens = stars.stream()
                .filter(star -> star.getMutagenInfo().getMutagens() != null && !star.getMutagenInfo().getMutagens().isEmpty())
                .flatMap(star -> star.getMutagenInfo().getMutagens().stream())
                .map(it->it.getDescription())
                .map(mutagen -> mutagen.replace("化", ""))
                .collect(java.util.stream.Collectors.toList());

        // 检查婚姻主星的数量
        long marriageStarCount = MARRIAGE_KEY_STARS.stream()
                .filter(starNames::contains)
                .count();

        // 检查婚姻四化的数量
        long marriageMutagenCount = MARRIAGE_MUTAGENS.stream()
                .filter(mutagens::contains)
                .count();

        // 满足以下任一条件：
        // 1. 包含两个或以上婚姻主星
        // 2. 包含一个婚姻主星且有婚姻四化
        return marriageStarCount >= 2 || (marriageStarCount >= 1 && marriageMutagenCount >= 1);
    }

    public static boolean isEmotionPattern(List<StarBO> stars) {
        return hasKeyStars(stars, EMOTION_STARS);
    }

    public static boolean isSpousePattern(List<StarBO> stars) {
        return hasKeyStars(stars, SPOUSE_STARS);
    }

    /**
     * 检查是否包含关键星耀
     */
    private static boolean hasKeyStars(List<StarBO> stars, List<String> keyStars) {
        return stars.stream()
                .map(StarBO::getName)
                .anyMatch(keyStars::contains);
    }

    /**
     * 检查是否包含指定变化
     */
    private static boolean hasMutagens(List<StarBO> stars, List<String> targetMutagens) {
        return stars.stream()
                .flatMap(star -> star.getMutagenInfo().getMutagens().stream())
                .map(Mutagen::getDescription)
                .anyMatch(targetMutagens::contains);
    }

    private static <T> List<T> convertToList(String text, Function<String, T> converter) {
        if (text == null || text.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(text.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(converter)
                .collect(Collectors.toList());
    }

    private static String replaceText(String text, String oldValue, String newValue) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.replace(oldValue, newValue);
    }

    private static String getMutagenDescription(Mutagen mutagen) {
        return mutagen.getDescription();
    }

    private static List<Mutagen> parseMutagens(String text) {
        return convertToList(text, Mutagen::fromString);
    }
}
