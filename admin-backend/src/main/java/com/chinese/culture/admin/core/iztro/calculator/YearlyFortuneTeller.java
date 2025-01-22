package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.core.iztro.analyzer.PalaceAuspiciousnessAnalyzer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流年运势预测器
 */
public class YearlyFortuneTeller {
    
    /**
     * 预测流年运势
     * 
     * @param palaces 所有宫位列表
     * @param currentYear 当前年份
     * @param birthYear 出生年份
     * @param gender 性别
     * @return 运势预测结果
     */
    public static Map<String, Object> predictYearlyFortune(List<Palace> palaces, int currentYear, 
                                                         int birthYear, boolean gender) {
        Map<String, Object> prediction = new HashMap<>();
        
        // 1. 获取流年宫位
        Palace yearlyPalace = findYearlyFlowPalace(palaces);
        if (yearlyPalace == null) {
            return prediction;
        }
        
        // 2. 计算流年地支
        EarthlyBranch yearlyBranch = LuckCalculator.calculateYearlyFlow(currentYear);
        
        // 3. 计算流年天干
        HeavenlyStem yearlyStem = calculateYearlyStem(currentYear);
        
        // 4. 分析流年宫位
        Map<String, Object> palaceAnalysis = analyzePalaceForYear(yearlyPalace);
        prediction.put("palaceAnalysis", palaceAnalysis);
        
        // 5. 分析流年四化
        Map<String, Object> mutagenAnalysis = analyzeMutagenForYear(palaces, yearlyStem);
        prediction.put("mutagenAnalysis", mutagenAnalysis);
        
        // 6. 分析大小限
        Map<String, Object> limitAnalysis = analyzeLimitsForYear(palaces, birthYear, gender, currentYear);
        prediction.put("limitAnalysis", limitAnalysis);
        
        // 7. 生成运势解释
        String interpretation = generateYearlyInterpretation(yearlyPalace, palaceAnalysis, 
            mutagenAnalysis, limitAnalysis);
        prediction.put("interpretation", interpretation);
        
        return prediction;
    }
    
    /**
     * 查找流年宫位
     */
    private static Palace findYearlyFlowPalace(List<Palace> palaces) {
        return palaces.stream()
            .filter(Palace::isCurrentYearlyFlow)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * 计算流年天干
     */
    private static HeavenlyStem calculateYearlyStem(int year) {
        int index = (year - 4) % 10;
        if (index <= 0) {
            index += 10;
        }
        return HeavenlyStem.values()[index - 1];
    }
    
    /**
     * 分析流年宫位
     */
    private static Map<String, Object> analyzePalaceForYear(Palace yearlyPalace) {
        Map<String, Object> analysis = new HashMap<>();
        
        // 获取流年宫位吉凶分析
        Map<String, Object> auspiciousness = PalaceAuspiciousnessAnalyzer.judgeAuspiciousness(yearlyPalace);
        analysis.put("auspiciousness", auspiciousness);
        
        // 分析宫位主题
        String theme = getPalaceTheme(yearlyPalace.getName());
        analysis.put("theme", theme);
        
        // 分析星耀组合
        List<Map<String, Object>> combinations = analyzePalaceCombinations(yearlyPalace);
        analysis.put("combinations", combinations);
        
        return analysis;
    }
    
    /**
     * 获取宫位主题
     */
    private static String getPalaceTheme(String palaceName) {
        // 根据宫位名称获取主题
        switch (palaceName) {
            case "命宫":
                return "命宫代表人的先天体质、性格特征和人生格局";
            case "兄弟宫":
                return "兄弟宫主管兄弟姐妹关系、同辈关系和人际交往";
            case "夫妻宫":
                return "夫妻宫主管婚姻、感情和伴侣关系";
            case "子女宫":
                return "子女宫主管子女、后代和创造力";
            case "财帛宫":
                return "财帛宫主管财运、收入和理财能力";
            case "疾厄宫":
                return "疾厄宫主管健康、疾病和困难";
            case "迁移宫":
                return "迁移宫主管旅行、搬迁和变动";
            case "仆役宫":
                return "仆役宫主管下属、助手和服务关系";
            case "官禄宫":
                return "官禄宫主管事业、地位和成就";
            case "田宅宫":
                return "田宅宫主管房产、居所和固定资产";
            case "福德宫":
                return "福德宫主管心灵、修养和内在品质";
            case "父母宫":
                return "父母宫主管长辈、权威和教育";
            default:
                return "未知宫位";
        }
    }
    
    /**
     * 分析宫位星耀组合
     */
    private static List<Map<String, Object>> analyzePalaceCombinations(Palace palace) {
        List<Map<String, Object>> combinations = new ArrayList<>();
        
        // 获取所有星耀组合
        List<Star> allStars = palace.getAllStars();
        for (int i = 0; i < allStars.size(); i++) {
            for (int j = i + 1; j < allStars.size(); j++) {
                Star star1 = allStars.get(i);
                Star star2 = allStars.get(j);
                
                String effect = StarConvergenceCalculator.calculateConvergenceEffect(
                    star1, star2, palace, palace);
                
                if (!effect.contains("无特殊")) {
                    Map<String, Object> combination = new HashMap<>();
                    combination.put("star1", star1.getName());
                    combination.put("star2", star2.getName());
                    combination.put("effect", effect);
                    combinations.add(combination);
                }
            }
        }
        
        return combinations;
    }
    
    /**
     * 分析流年四化
     */
    private static Map<String, Object> analyzeMutagenForYear(List<Palace> palaces, HeavenlyStem yearlyStem) {
        Map<String, Object> analysis = new HashMap<>();
        Map<String, List<String>> mutagenStars = new HashMap<>();
        
        // 遍历所有宫位的星耀
        for (Palace palace : palaces) {
            for (Star star : palace.getAllStars()) {
                Mutagen mutagen = MutagenCalculator.getMutagen(yearlyStem, star);
                if (mutagen != null) {
                    mutagenStars.computeIfAbsent(mutagen.getDescription(), k -> new ArrayList<>())
                        .add(String.format("%s(%s)", star.getName(), palace.getName()));
                }
            }
        }
        
        analysis.put("mutagenStars", mutagenStars);
        
        // 生成四化解释
        StringBuilder interpretation = new StringBuilder("流年四化解释：\n");
        for (Map.Entry<String, List<String>> entry : mutagenStars.entrySet()) {
            interpretation.append(entry.getKey()).append("化：")
                .append(String.join("、", entry.getValue())).append("\n");
        }
        analysis.put("interpretation", interpretation.toString());
        
        return analysis;
    }
    
    /**
     * 分析大小限
     */
    private static Map<String, Object> analyzeLimitsForYear(List<Palace> palaces, int birthYear, 
                                                          boolean gender, int currentYear) {
        Map<String, Object> analysis = new HashMap<>();
        
        // 计算年龄
        int age = currentYear - birthYear;
        
        // 获取当前大限宫位
        Palace majorLimitPalace = palaces.stream()
            .filter(Palace::isCurrentMajorLimit)
            .findFirst()
            .orElse(null);
        
        if (majorLimitPalace != null) {
            Map<String, Object> majorLimitAnalysis = PalaceAuspiciousnessAnalyzer.judgeAuspiciousness(majorLimitPalace);
            analysis.put("majorLimitAnalysis", majorLimitAnalysis);
        }
        
        // 获取当前小限宫位
        Palace minorLimitPalace = palaces.stream()
            .filter(Palace::isCurrentMinorLimit)
            .findFirst()
            .orElse(null);
        
        if (minorLimitPalace != null) {
            Map<String, Object> minorLimitAnalysis = PalaceAuspiciousnessAnalyzer.judgeAuspiciousness(minorLimitPalace);
            analysis.put("minorLimitAnalysis", minorLimitAnalysis);
        }
        
        return analysis;
    }
    
    /**
     * 生成流年运势解释
     */
    private static String generateYearlyInterpretation(Palace yearlyPalace, 
                                                     Map<String, Object> palaceAnalysis,
                                                     Map<String, Object> mutagenAnalysis,
                                                     Map<String, Object> limitAnalysis) {
        StringBuilder interpretation = new StringBuilder();
        
        // 1. 流年宫位基本信息
        interpretation.append(String.format("流年落在%s，主要关注：%s\n", 
            yearlyPalace.getName(), getPalaceTheme(yearlyPalace.getName())));
        
        // 2. 宫位吉凶分析
        @SuppressWarnings("unchecked")
        Map<String, Object> auspiciousness = (Map<String, Object>) palaceAnalysis.get("auspiciousness");
        interpretation.append(auspiciousness.get("interpretation")).append("\n");
        
        // 3. 星耀组合分析
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> combinations = (List<Map<String, Object>>) palaceAnalysis.get("combinations");
        if (!combinations.isEmpty()) {
            interpretation.append("流年星耀组合：\n");
            for (Map<String, Object> combination : combinations) {
                interpretation.append(String.format("%s与%s：%s\n",
                    combination.get("star1"),
                    combination.get("star2"),
                    combination.get("effect")));
            }
        }
        
        // 4. 四化分析
        interpretation.append(mutagenAnalysis.get("interpretation")).append("\n");
        
        // 5. 大小限分析
        if (limitAnalysis.containsKey("majorLimitAnalysis")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> majorLimitAnalysis = (Map<String, Object>) limitAnalysis.get("majorLimitAnalysis");
            interpretation.append("大限运势：").append(majorLimitAnalysis.get("interpretation")).append("\n");
        }
        
        if (limitAnalysis.containsKey("minorLimitAnalysis")) {
            @SuppressWarnings("unchecked")
            Map<String, Object> minorLimitAnalysis = (Map<String, Object>) limitAnalysis.get("minorLimitAnalysis");
            interpretation.append("小限运势：").append(minorLimitAnalysis.get("interpretation")).append("\n");
        }
        
        return interpretation.toString();
    }

    private static int getPalaceIndex(Palace palace) {
        return palace.getIndex();
    }
} 