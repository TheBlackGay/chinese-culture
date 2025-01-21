package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;

import java.util.*;

/**
 * 大限小限解释器
 */
public class LimitInterpreter {
    
    /**
     * 解释大限运势
     * 
     * @param palace 大限宫位
     * @param age 年龄
     * @param fiveElementsClass 五行局数
     * @return 大限运势解释
     */
    public static Map<String, Object> interpretMajorLimit(Palace palace, int age, int fiveElementsClass) {
        Map<String, Object> interpretation = new HashMap<>();
        
        // 1. 计算大限年数
        int majorLimitYears = LuckCalculator.calculateMajorLimitYears(fiveElementsClass);
        int currentLimit = LuckCalculator.calculateCurrentMajorLimit(age, majorLimitYears);
        
        // 2. 获取宫位吉凶分析
        Map<String, Object> auspiciousness = PalaceAuspiciousnessCalculator.getDetailedAnalysis(palace);
        interpretation.put("auspiciousness", auspiciousness);
        
        // 3. 分析星耀组合
        List<Map<String, Object>> combinations = analyzePalaceCombinations(palace);
        interpretation.put("combinations", combinations);
        
        // 4. 生成大限解释
        StringBuilder desc = new StringBuilder();
        desc.append(String.format("当前处于第%d个大限（%d-%d岁），落在%s。\n", 
            currentLimit + 1, age - age % majorLimitYears, 
            age - age % majorLimitYears + majorLimitYears - 1, palace.getName()));
        
        // 添加宫位主题解释
        desc.append(String.format("大限宫位主题：%s\n", getPalaceTheme(palace.getName())));
        
        // 添加吉凶分析
        desc.append(auspiciousness.get("interpretation"));
        
        // 添加星耀组合分析
        if (!combinations.isEmpty()) {
            desc.append("\n星耀组合：\n");
            for (Map<String, Object> combination : combinations) {
                desc.append(String.format("%s与%s：%s\n",
                    combination.get("star1"),
                    combination.get("star2"),
                    combination.get("effect")));
            }
        }
        
        interpretation.put("description", desc.toString());
        
        return interpretation;
    }
    
    /**
     * 解释小限运势
     * 
     * @param palace 小限宫位
     * @param age 年龄
     * @param gender 性别
     * @return 小限运势解释
     */
    public static Map<String, Object> interpretMinorLimit(Palace palace, int age, boolean gender) {
        Map<String, Object> interpretation = new HashMap<>();
        
        // 1. 获取宫位吉凶分析
        Map<String, Object> auspiciousness = PalaceAuspiciousnessCalculator.getDetailedAnalysis(palace);
        interpretation.put("auspiciousness", auspiciousness);
        
        // 2. 分析星耀组合
        List<Map<String, Object>> combinations = analyzePalaceCombinations(palace);
        interpretation.put("combinations", combinations);
        
        // 3. 生成小限解释
        StringBuilder desc = new StringBuilder();
        desc.append(String.format("%d岁小限落在%s。\n", age, palace.getName()));
        
        // 添加宫位主题解释
        desc.append(String.format("小限宫位主题：%s\n", getPalaceTheme(palace.getName())));
        
        // 添加吉凶分析
        desc.append(auspiciousness.get("interpretation"));
        
        // 添加星耀组合分析
        if (!combinations.isEmpty()) {
            desc.append("\n星耀组合：\n");
            for (Map<String, Object> combination : combinations) {
                desc.append(String.format("%s与%s：%s\n",
                    combination.get("star1"),
                    combination.get("star2"),
                    combination.get("effect")));
            }
        }
        
        interpretation.put("description", desc.toString());
        
        return interpretation;
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
     * 获取大限流年组合解释
     * 
     * @param majorLimitPalace 大限宫位
     * @param yearlyPalace 流年宫位
     * @return 组合解释
     */
    public static String getMajorLimitYearlyCombo(Palace majorLimitPalace, Palace yearlyPalace) {
        StringBuilder interpretation = new StringBuilder();
        
        // 1. 分析宫位关系
        String relation = PalaceRelationCalculator.getRelationType(majorLimitPalace, yearlyPalace)
            .getDescription();
        
        interpretation.append(String.format("大限%s与流年%s%s，",
            majorLimitPalace.getName(), yearlyPalace.getName(), relation));
        
        // 2. 分析吉凶组合
        int majorScore = PalaceAuspiciousnessCalculator.calculateAuspiciousness(majorLimitPalace);
        int yearlyScore = PalaceAuspiciousnessCalculator.calculateAuspiciousness(yearlyPalace);
        
        if (majorScore >= 60 && yearlyScore >= 60) {
            interpretation.append("大运与流年相生，运势畅通。");
        } else if (majorScore >= 60 && yearlyScore < 60) {
            interpretation.append("大运有利但流年受阻，需谨慎行事。");
        } else if (majorScore < 60 && yearlyScore >= 60) {
            interpretation.append("大运受阻但流年有利，可把握机会。");
        } else {
            interpretation.append("大运与流年均受阻，需格外谨慎。");
        }
        
        return interpretation.toString();
    }
    
    /**
     * 获取小限流年组合解释
     * 
     * @param minorLimitPalace 小限宫位
     * @param yearlyPalace 流年宫位
     * @return 组合解释
     */
    public static String getMinorLimitYearlyCombo(Palace minorLimitPalace, Palace yearlyPalace) {
        StringBuilder interpretation = new StringBuilder();
        
        // 1. 分析宫位关系
        String relation = PalaceRelationCalculator.getRelationType(minorLimitPalace, yearlyPalace)
            .getDescription();
        
        interpretation.append(String.format("小限%s与流年%s%s，",
            minorLimitPalace.getName(), yearlyPalace.getName(), relation));
        
        // 2. 分析吉凶组合
        int minorScore = PalaceAuspiciousnessCalculator.calculateAuspiciousness(minorLimitPalace);
        int yearlyScore = PalaceAuspiciousnessCalculator.calculateAuspiciousness(yearlyPalace);
        
        if (minorScore >= 60 && yearlyScore >= 60) {
            interpretation.append("小限与流年相生，有利发展。");
        } else if (minorScore >= 60 && yearlyScore < 60) {
            interpretation.append("小限有利但流年受阻，进退需谨慎。");
        } else if (minorScore < 60 && yearlyScore >= 60) {
            interpretation.append("小限受阻但流年有利，可寻求突破。");
        } else {
            interpretation.append("小限与流年均受阻，宜守不宜进。");
        }
        
        return interpretation.toString();
    }
    
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
} 