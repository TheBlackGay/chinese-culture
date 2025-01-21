package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;

import java.util.*;

/**
 * 大限小限分析器
 */
public class LimitAnalyzer {

    /**
     * 分析大限
     *
     * @param palaces 所有宫位列表
     * @param age 年龄
     * @param fiveElementsClass 五行局数
     * @param gender 性别
     * @return 大限分析结果
     */
    public static Map<String, Object> analyzeMajorLimit(List<Palace> palaces,
                                                      int age,
                                                      int fiveElementsClass,
                                                      boolean gender) {
        Map<String, Object> result = new HashMap<>();

        // 1. 计算大限年数
        int majorLimitYears = calculateMajorLimitYears(fiveElementsClass);
        result.put("majorLimitYears", majorLimitYears);

        // 2. 计算当前大限
        int currentMajorLimit = calculateCurrentMajorLimit(age, majorLimitYears);
        result.put("currentMajorLimit", currentMajorLimit);

        // 3. 找到大限宫位
        Palace majorLimitPalace = findMajorLimitPalace(palaces, currentMajorLimit, gender);
        result.put("majorLimitPalace", majorLimitPalace);

        // 4. 分析大限宫位
        Map<String, Object> palaceAnalysis = analyzePalace(majorLimitPalace);
        result.put("palaceAnalysis", palaceAnalysis);

        // 5. 生成大限运势描述
        String description = generateMajorLimitDescription(majorLimitPalace, palaceAnalysis);
        result.put("description", description);

        // 6. 生成大限建议
        String suggestion = generateMajorLimitSuggestion(majorLimitPalace, palaceAnalysis, age);
        result.put("suggestion", suggestion);

        return result;
    }

    /**
     * 分析小限
     *
     * @param palaces 所有宫位列表
     * @param age 年龄
     * @param gender 性别
     * @return 小限分析结果
     */
    public static Map<String, Object> analyzeMinorLimit(List<Palace> palaces,
                                                      int age,
                                                      boolean gender) {
        Map<String, Object> result = new HashMap<>();

        // 1. 计算当前小限
        int currentMinorLimit = calculateCurrentMinorLimit(age);
        result.put("currentMinorLimit", currentMinorLimit);

        // 2. 找到小限宫位
        Palace minorLimitPalace = findMinorLimitPalace(palaces, currentMinorLimit, gender);
        result.put("minorLimitPalace", minorLimitPalace);

        // 3. 分析小限宫位
        Map<String, Object> palaceAnalysis = analyzePalace(minorLimitPalace);
        result.put("palaceAnalysis", palaceAnalysis);

        // 4. 生成小限运势描述
        String description = generateMinorLimitDescription(minorLimitPalace, palaceAnalysis);
        result.put("description", description);

        // 5. 生成小限建议
        String suggestion = generateMinorLimitSuggestion(minorLimitPalace, palaceAnalysis, age);
        result.put("suggestion", suggestion);

        return result;
    }

    /**
     * 计算大限年数
     */
    private static int calculateMajorLimitYears(int fiveElementsClass) {
        switch (fiveElementsClass) {
            case 1: return 10; // 水二局
            case 2: return 9;  // 木三局
            case 3: return 8;  // 金四局
            case 4: return 7;  // 土五局
            case 5: return 6;  // 火六局
            default: return 8;
        }
    }

    /**
     * 计算当前大限
     */
    private static int calculateCurrentMajorLimit(int age, int majorLimitYears) {
        return (age - 1) / majorLimitYears + 1;
    }

    /**
     * 计算当前小限
     */
    private static int calculateCurrentMinorLimit(int age) {
        return (age - 1) % 12 + 1;
    }

    /**
     * 找到大限宫位
     */
    private static Palace findMajorLimitPalace(List<Palace> palaces,
                                             int currentMajorLimit,
                                             boolean gender) {
        int startIndex = gender ? 0 : 6; // 男顺女逆
        int targetIndex = (startIndex + (gender ? currentMajorLimit - 1 : -(currentMajorLimit - 1)) + 12) % 12;

        for (Palace palace : palaces) {
            if (palace.getIndex() == targetIndex) {
                return palace;
            }
        }
        return null;
    }

    /**
     * 找到小限宫位
     */
    private static Palace findMinorLimitPalace(List<Palace> palaces,
                                             int currentMinorLimit,
                                             boolean gender) {
        int startIndex = gender ? 0 : 6; // 男顺女逆
        int targetIndex = (startIndex + (gender ? currentMinorLimit - 1 : -(currentMinorLimit - 1)) + 12) % 12;

        for (Palace palace : palaces) {
            if (palace.getIndex() == targetIndex) {
                return palace;
            }
        }
        return null;
    }

    /**
     * 分析宫位
     */
    private static Map<String, Object> analyzePalace(Palace palace) {
        Map<String, Object> analysis = new HashMap<>();

        if (palace == null) {
            return analysis;
        }

        // 1. 计算宫位强度
        int strength = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        analysis.put("strength", strength);

        // 2. 分析主星
        List<Star> majorStars = palace.getMajorStars();
        analysis.put("majorStars", majorStars);

        // 3. 分析辅星
        List<Star> minorStars = palace.getMinorStars();
        analysis.put("minorStars", minorStars);

        // 4. 分析四化
        List<Mutagen> mutagens = palace.getMutagens();
        analysis.put("mutagens", mutagens);

        return analysis;
    }

    /**
     * 生成大限运势描述
     */
    private static String generateMajorLimitDescription(Palace palace,
                                                      Map<String, Object> analysis) {
        if (palace == null) {
            return "大限宫位未找到";
        }

        StringBuilder description = new StringBuilder();
        description.append("大限落").append(palace.getName()).append("宫，");

        // 根据宫位强度评价
        int strength = (int) analysis.get("strength");
        if (strength >= 80) {
            description.append("运势极佳。");
        } else if (strength >= 60) {
            description.append("运势平稳。");
        } else {
            description.append("运势欠佳。");
        }

        // 分析星耀组合
        @SuppressWarnings("unchecked")
        List<Star> majorStars = (List<Star>) analysis.get("majorStars");
        if (majorStars != null && !majorStars.isEmpty()) {
            description.append("主星有");
            for (int i = 0; i < majorStars.size(); i++) {
                if (i > 0) {
                    description.append("、");
                }
                description.append(majorStars.get(i).getName());
            }
            description.append("。");
        }

        return description.toString();
    }

    /**
     * 生成大限建议
     */
    private static String generateMajorLimitSuggestion(Palace palace,
                                                     Map<String, Object> analysis,
                                                     int age) {
        if (palace == null) {
            return "";
        }

        StringBuilder suggestion = new StringBuilder();
        int strength = (int) analysis.get("strength");

        // 根据宫位和强度给出建议
        if (strength >= 80) {
            switch (palace.getName()) {
                case "命宫":
                    suggestion.append("大运极佳，宜积极进取，开创事业。");
                    break;
                case "财帛":
                    suggestion.append("财运亨通，宜投资理财，开源节流。");
                    break;
                case "官禄":
                    suggestion.append("仕途顺遂，宜把握机会，谋求发展。");
                    break;
                default:
                    suggestion.append("运势良好，宜积极进取。");
            }
        } else if (strength >= 60) {
            suggestion.append("运势平稳，宜稳中求进，循序渐进。");
        } else {
            suggestion.append("运势欠佳，宜谨慎行事，避免冒进。");
        }

        // 根据年龄补充建议
        if (age < 30) {
            suggestion.append("年轻有为，建议专注个人发展。");
        } else if (age < 50) {
            suggestion.append("正值壮年，建议平衡事业与家庭。");
        } else {
            suggestion.append("年富经验，建议注重养生保健。");
        }

        return suggestion.toString();
    }

    /**
     * 生成小限运势描述
     */
    private static String generateMinorLimitDescription(Palace palace,
                                                      Map<String, Object> analysis) {
        if (palace == null) {
            return "小限宫位未找到";
        }

        StringBuilder description = new StringBuilder();
        description.append("小限落").append(palace.getName()).append("宫，");

        // 根据宫位强度评价
        int strength = (int) analysis.get("strength");
        if (strength >= 80) {
            description.append("年运极佳。");
        } else if (strength >= 60) {
            description.append("年运平稳。");
        } else {
            description.append("年运欠佳。");
        }

        // 分析星耀组合
        @SuppressWarnings("unchecked")
        List<Star> majorStars = (List<Star>) analysis.get("majorStars");
        if (majorStars != null && !majorStars.isEmpty()) {
            description.append("主星有");
            for (int i = 0; i < majorStars.size(); i++) {
                if (i > 0) {
                    description.append("、");
                }
                description.append(majorStars.get(i).getName());
            }
            description.append("。");
        }

        return description.toString();
    }

    /**
     * 生成小限建议
     */
    private static String generateMinorLimitSuggestion(Palace palace,
                                                     Map<String, Object> analysis,
                                                     int age) {
        if (palace == null) {
            return "";
        }

        StringBuilder suggestion = new StringBuilder();
        int strength = (int) analysis.get("strength");

        // 根据宫位和强度给出建议
        if (strength >= 80) {
            switch (palace.getName()) {
                case "命宫":
                    suggestion.append("今年运势极佳，宜主动进取。");
                    break;
                case "财帛":
                    suggestion.append("今年财运旺盛，宜把握机会。");
                    break;
                case "官禄":
                    suggestion.append("今年仕途顺遂，宜谋求发展。");
                    break;
                default:
                    suggestion.append("今年运势良好，宜积极行动。");
            }
        } else if (strength >= 60) {
            suggestion.append("今年运势平稳，宜稳扎稳打。");
        } else {
            suggestion.append("今年运势欠佳，宜谨慎行事。");
        }

        // 根据年龄补充建议
        if (age < 30) {
            suggestion.append("年轻有为，建议把握机会。");
        } else if (age < 50) {
            suggestion.append("正值壮年，建议稳中求进。");
        } else {
            suggestion.append("年富经验，建议从容应对。");
        }

        return suggestion.toString();
    }
}
