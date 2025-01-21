package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.Horoscope;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 命盘分析器
 */
@Slf4j
public class AstrolabeAnalyzer {

    // 命主格局类型
    private static final Map<String, String> PATTERN_TYPES = new HashMap<>();
    static {
        // 紫微命主格局
        PATTERN_TYPES.put("紫微天府同宫", "紫微天府同宫格");
        PATTERN_TYPES.put("紫微天机同宫", "紫微天机同宫格");
        PATTERN_TYPES.put("紫微武曲同宫", "紫微武曲同宫格");
        PATTERN_TYPES.put("紫微破军同宫", "紫微破军同宫格");

        // 天府命主格局
        PATTERN_TYPES.put("天府天相同宫", "天府天相同宫格");
        PATTERN_TYPES.put("天府太阴同宫", "天府太阴同宫格");
        PATTERN_TYPES.put("天府贪狼同宫", "天府贪狼同宫格");

        // 其他特殊格局
        PATTERN_TYPES.put("命宫三方四正会众吉星", "三方四正格");
        PATTERN_TYPES.put("命宫有禄存科权", "禄权科格");
    }

    /**
     * 分析命主格局
     *
     * @param astrolabe 命盘数据
     * @return 格局描述
     */
    public static String analyzeMingZhuPattern(Astrolabe astrolabe) {
        try {
            // 验证命盘数据完整性
            validateAstrolabeData(astrolabe);

            List<String> patterns = new ArrayList<>();

            // 获取命宫
            Palace mingGong = findMingGong(astrolabe.getPalaces());
            if (mingGong == null) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "未找到命宫");
            }

            // 分析命宫星耀组合
            patterns.addAll(analyzeMingGongStars(mingGong));

            // 分析三方四正
            patterns.addAll(analyzeTrineAndOpposition(astrolabe.getPalaces(), mingGong));

            // 分析四化
            patterns.addAll(analyzeMutagens(mingGong));

            // 如果没有找到特殊格局，返回基础格局
            if (patterns.isEmpty()) {
                return "普通格局";
            }

            return String.join("，", patterns);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("命主格局分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 验证命盘数据完整性
     */
    private static void validateAstrolabeData(Astrolabe astrolabe) {
        if (astrolabe == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命盘数据不能为空");
        }
        if (astrolabe.getPalaces() == null || astrolabe.getPalaces().isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命盘宫位数据不能为空");
        }
        if (astrolabe.getStars() == null || astrolabe.getStars().isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命盘星耀数据不能为空");
        }
        if (astrolabe.getPalaces().size() != 12) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命盘宫位数量必须为12");
        }

        // 验证命宫数据
        Palace mingGong = findMingGong(astrolabe.getPalaces());
        if (mingGong == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命盘数据不完整：未找到命宫");
        }
        if (mingGong.getStars() == null || mingGong.getStars().isEmpty()) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命盘数据不完整：命宫星耀为空");
        }
    }

    /**
     * 查找命宫
     */
    private static Palace findMingGong(List<Palace> palaces) {
        return palaces.stream()
                .filter(Palace::isMing)
                .findFirst()
                .orElse(null);
    }

    /**
     * 分析命宫星耀组合
     */
    private static List<String> analyzeMingGongStars(Palace mingGong) {
        List<String> patterns = new ArrayList<>();
        List<Star> stars = mingGong.getStars();

        if (stars == null || stars.isEmpty()) {
            return patterns;
        }

        // 获取所有星耀名称
        List<String> starNames = stars.stream()
                .map(Star::getName)
                .collect(Collectors.toList());

        // 检查特殊星耀组合
        for (Map.Entry<String, String> entry : PATTERN_TYPES.entrySet()) {
            String key = entry.getKey();
            if (key.contains("同宫")) {
                String[] starPair = key.split("同宫")[0].split("(?<=..)(?=..)");
                if (starPair.length == 2 &&
                    starNames.contains(starPair[0]) &&
                    starNames.contains(starPair[1])) {
                    patterns.add(entry.getValue());
                }
            } else if (key.equals("命宫有禄存科权")) {
                // 检查四化组合
                List<String> mutagens = mingGong.getMutagens();
                if (mutagens != null && mutagens.contains("化禄") &&
                    mutagens.contains("化权") && mutagens.contains("化科")) {
                    patterns.add(entry.getValue());
                }
            } else if (key.equals("命宫三方四正会众吉星")) {
                // 检查三方四正是否有吉星会集
                int goodStarCount = countGoodStars(stars);
                if (goodStarCount >= 3) {
                    patterns.add(entry.getValue());
                }
            }
        }

        return patterns;
    }

    /**
     * 分析三方四正
     */
    private static List<String> analyzeTrineAndOpposition(List<Palace> palaces, Palace mingGong) {
        List<String> patterns = new ArrayList<>();

        // 获取命宫位置
        int mingGongIndex = mingGong.getIndex();

        // 计算三方位置
        int[] trinePositions = {
            mingGongIndex,
            (mingGongIndex + 4) % 12,
            (mingGongIndex + 8) % 12
        };

        // 计算四正位置
        int[] oppositionPositions = {
            mingGongIndex,
            (mingGongIndex + 3) % 12,
            (mingGongIndex + 6) % 12,
            (mingGongIndex + 9) % 12
        };

        // 检查三方四正是否有吉星会集
        int goodStarCount = 0;
        for (Palace palace : palaces) {
            if (isPositionInArray(palace.getIndex(), trinePositions) ||
                isPositionInArray(palace.getIndex(), oppositionPositions)) {
                goodStarCount += countGoodStars(palace.getStars());
            }
        }

        if (goodStarCount >= 3) {
            patterns.add(PATTERN_TYPES.get("命宫三方四正会众吉星"));
        }

        return patterns;
    }

    /**
     * 分析四化
     */
    private static List<String> analyzeMutagens(Palace mingGong) {
        List<String> patterns = new ArrayList<>();
        List<String> mutagens = mingGong.getMutagens();

        if (mutagens == null || mutagens.isEmpty()) {
            return patterns;
        }

        // 检查是否同时具有禄、权、科
        boolean hasLu = mutagens.contains("化禄");
        boolean hasQuan = mutagens.contains("化权");
        boolean hasKe = mutagens.contains("化科");

        if (hasLu && hasQuan && hasKe) {
            patterns.add(PATTERN_TYPES.get("命宫有禄存科权"));
        }

        return patterns;
    }

    /**
     * 检查位置是否在数组中
     */
    private static boolean isPositionInArray(int position, int[] positions) {
        for (int pos : positions) {
            if (position == pos) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统计吉星数量
     */
    private static int countGoodStars(List<Star> stars) {
        if (stars == null || stars.isEmpty()) {
            return 0;
        }

        Set<String> goodStars = new HashSet<>(Arrays.asList(
            "紫微", "天机", "太阳", "武曲", "天同",
            "天府", "太阴", "天相", "天梁", "文昌",
            "文曲", "左辅", "右弼", "天魁", "天钺"
        ));

        return (int) stars.stream()
                .filter(star -> goodStars.contains(star.getName()))
                .count();
    }

    /**
     * 分析大限运势
     *
     * @param horoscope 运限数据
     * @return 运势描述
     */
    public static String analyzeDecadalFortune(Horoscope horoscope) {
        try {
            if (horoscope == null || horoscope.getDecadal() == null) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "运限数据不完整");
            }

            StringBuilder fortune = new StringBuilder();
            Horoscope.DecadalHoroscope decadal = horoscope.getDecadal();

            // 添加大限基本信息
            int startAge = decadal.getStartAge();
            int endAge = decadal.getEndAge();
            List<Star> decadalStars = decadal.getStars();
            fortune.append(String.format("大限：%d-%d岁，", startAge, endAge));
            fortune.append(String.format("天干地支：%s%s，", decadal.getHeavenlyStem(), decadal.getEarthlyBranch()));

            // 分析星耀
            if (decadalStars != null && !decadalStars.isEmpty()) {
                fortune.append("主要星耀：").append(String.join("、", decadalStars.stream().map(Star::getName).collect(Collectors.toList())));
            }

            // 分析四化
            List<String> mutagens = getMutagens(decadal);
            if (mutagens != null && !mutagens.isEmpty()) {
                fortune.append("，四化：").append(String.join("、", mutagens));
            }

            // 分析吉凶
            String fortune_level = analyzeFortuneLevel(decadalStars, mutagens);
            fortune.append("，").append(fortune_level);

            return fortune.toString();

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("大限运势分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析流年运势
     *
     * @param horoscope 运限数据
     * @return 运势描述
     */
    public static String analyzeYearlyFortune(Horoscope horoscope) {
        try {
            if (horoscope == null || horoscope.getYearly() == null) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "运限数据不完整");
            }

            StringBuilder fortune = new StringBuilder();
            Horoscope.YearlyHoroscope yearly = horoscope.getYearly();

            // 添加流年基本信息
            int yearlyAge = yearly.getAge();
            List<Star> yearlyStars = yearly.getStars();
            fortune.append(String.format("流年：%d年，", yearlyAge));
            fortune.append(String.format("天干地支：%s%s，", yearly.getHeavenlyStem(), yearly.getEarthlyBranch()));

            // 分析星耀
            if (yearlyStars != null && !yearlyStars.isEmpty()) {
                fortune.append("主要星耀：").append(String.join("、", yearlyStars.stream().map(Star::getName).collect(Collectors.toList())));
            }

            // 分析四化
            List<String> mutagens = getMutagens(yearly);
            if (mutagens != null && !mutagens.isEmpty()) {
                fortune.append("，四化：").append(String.join("、", mutagens));
            }

            // 分析流年将前
            List<String> yearlyDecStars = getYearlyDecStars(yearly);
            if (yearlyDecStars != null && !yearlyDecStars.isEmpty()) {
                fortune.append("，流年将前：").append(String.join("、", yearlyDecStars));
            }

            // 分析吉凶
            String fortune_level = analyzeFortuneLevel(yearlyStars, mutagens);
            fortune.append("，").append(fortune_level);

            return fortune.toString();

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("流年运势分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析运势等级
     */
    private static String analyzeFortuneLevel(List<Star> stars, List<String> mutagens) {
        int score = 0;

        // 计算吉星分数
        Set<String> goodStars = new HashSet<>(Arrays.asList(
            "紫微", "天机", "太阳", "武曲", "天同",
            "天府", "太阴", "天相", "天梁", "文昌",
            "文曲", "左辅", "右弼", "天魁", "天钺"
        ));

        // 计算凶星分数
        Set<String> badStars = new HashSet<>(Arrays.asList(
            "七杀", "破军", "擎羊", "陀罗", "火星",
            "铃星", "地空", "地劫"
        ));

        if (stars != null) {
            for (Star star : stars) {
                String starName = star.getName();
                if (goodStars.contains(starName)) {
                    score += 2;
                } else if (badStars.contains(starName)) {
                    score -= 2;
                }
            }
        }

        // 计算四化分数
        if (mutagens != null) {
            for (String mutagen : mutagens) {
                switch (mutagen) {
                    case "化禄":
                    case "化科":
                        score += 3;
                        break;
                    case "化权":
                        score += 2;
                        break;
                    case "化忌":
                        score -= 2;
                        break;
                }
            }
        }

        // 根据分数判断运势等级
        if (score >= 10) {
            return "大吉";
        } else if (score >= 5) {
            return "吉";
        } else if (score >= 0) {
            return "平";
        } else if (score >= -5) {
            return "凶";
        } else {
            return "大凶";
        }
    }

    private static List<String> getMutagens(Horoscope.DecadalHoroscope decadal) {
        if (decadal == null) {
            return Collections.emptyList();
        }
        List<String> mutagens = decadal.getMutagens();
        return mutagens != null ? mutagens : Collections.emptyList();
    }

    private static List<String> getYearlyDecStars(Horoscope.YearlyHoroscope yearly) {
        if (yearly == null) {
            return Collections.emptyList();
        }
        List<String> yearlyDecStars = yearly.getYearlyDecStars();
        return yearlyDecStars != null ? yearlyDecStars : Collections.emptyList();
    }

    private static List<String> getMutagens(Horoscope.YearlyHoroscope yearly) {
        if (yearly == null) {
            return Collections.emptyList();
        }
        List<String> mutagens = yearly.getMutagens();
        return mutagens != null ? mutagens : Collections.emptyList();
    }
}
