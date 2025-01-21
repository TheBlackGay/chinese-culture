package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;

import java.util.*;

/**
 * 流月流日运势预测器
 */
public class MonthlyDailyFortuneTeller {
    
    /**
     * 预测流月运势
     * 
     * @param palaces 所有宫位列表
     * @param yearlyFlow 流年地支
     * @param month 月份(1-12)
     * @return 流月运势预测结果
     */
    public static Map<String, Object> predictMonthlyFortune(List<Palace> palaces, 
                                                          EarthlyBranch yearlyFlow,
                                                          int month) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 计算流月地支
        EarthlyBranch monthlyFlow = calculateMonthlyFlow(yearlyFlow, month);
        result.put("monthlyFlow", monthlyFlow);
        
        // 2. 找到流月所在宫位
        Palace monthlyPalace = findPalaceByBranch(palaces, monthlyFlow);
        result.put("monthlyPalace", monthlyPalace);
        
        // 3. 分析流月宫位吉凶
        int score = PalaceAuspiciousnessCalculator.calculateAuspiciousness(monthlyPalace);
        result.put("score", score);
        
        // 4. 生成运势描述
        String description = generateMonthlyDescription(monthlyPalace, score);
        result.put("description", description);
        
        // 5. 生成建议
        String suggestion = generateMonthlySuggestion(monthlyPalace, score);
        result.put("suggestion", suggestion);
        
        return result;
    }
    
    /**
     * 预测流日运势
     * 
     * @param palaces 所有宫位列表
     * @param monthlyFlow 流月地支
     * @param day 日期(1-31)
     * @return 流日运势预测结果
     */
    public static Map<String, Object> predictDailyFortune(List<Palace> palaces,
                                                        EarthlyBranch monthlyFlow,
                                                        int day) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 计算流日地支
        EarthlyBranch dailyFlow = calculateDailyFlow(monthlyFlow, day);
        result.put("dailyFlow", dailyFlow);
        
        // 2. 找到流日所在宫位
        Palace dailyPalace = findPalaceByBranch(palaces, dailyFlow);
        result.put("dailyPalace", dailyPalace);
        
        // 3. 分析流日宫位吉凶
        int score = PalaceAuspiciousnessCalculator.calculateAuspiciousness(dailyPalace);
        result.put("score", score);
        
        // 4. 生成运势描述
        String description = generateDailyDescription(dailyPalace, score);
        result.put("description", description);
        
        // 5. 生成建议
        String suggestion = generateDailySuggestion(dailyPalace, score);
        result.put("suggestion", suggestion);
        
        return result;
    }
    
    /**
     * 计算流月地支
     */
    private static EarthlyBranch calculateMonthlyFlow(EarthlyBranch yearlyFlow, int month) {
        int yearlyIndex = yearlyFlow.ordinal();
        int monthlyIndex = (yearlyIndex + month - 1) % 12;
        return EarthlyBranch.values()[monthlyIndex];
    }
    
    /**
     * 计算流日地支
     */
    private static EarthlyBranch calculateDailyFlow(EarthlyBranch monthlyFlow, int day) {
        int monthlyIndex = monthlyFlow.ordinal();
        int dailyIndex = (monthlyIndex + day - 1) % 12;
        return EarthlyBranch.values()[dailyIndex];
    }
    
    /**
     * 根据地支找到对应宫位
     */
    private static Palace findPalaceByBranch(List<Palace> palaces, EarthlyBranch branch) {
        for (Palace palace : palaces) {
            if (palace.getBranch().equals(branch)) {
                return palace;
            }
        }
        return null;
    }
    
    /**
     * 生成流月运势描述
     */
    private static String generateMonthlyDescription(Palace palace, int score) {
        StringBuilder description = new StringBuilder();
        description.append("本月流落").append(palace.getName()).append("宫，");
        
        // 根据分数评价运势
        if (score >= 80) {
            description.append("运势极佳。");
        } else if (score >= 60) {
            description.append("运势平稳。");
        } else {
            description.append("运势欠佳。");
        }
        
        // 分析宫位特点
        description.append("宫中");
        List<Star> stars = palace.getAllStars();
        if (!stars.isEmpty()) {
            description.append("有");
            for (int i = 0; i < stars.size(); i++) {
                if (i > 0) {
                    description.append("、");
                }
                description.append(stars.get(i).getName());
            }
            description.append("星耀");
        }
        
        return description.toString();
    }
    
    /**
     * 生成流月建议
     */
    private static String generateMonthlySuggestion(Palace palace, int score) {
        StringBuilder suggestion = new StringBuilder();
        
        // 根据宫位和分数给出建议
        if (score >= 80) {
            switch (palace.getName()) {
                case "命宫":
                    suggestion.append("本月个人发展机会良多，宜积极进取。");
                    break;
                case "兄弟":
                    suggestion.append("本月人际关系和谐，宜加强社交。");
                    break;
                case "夫妻":
                    suggestion.append("本月感情运佳，宜增进感情。");
                    break;
                case "子女":
                    suggestion.append("本月亲子关系融洽，宜关注家庭。");
                    break;
                case "财帛":
                    suggestion.append("本月财运亨通，宜把握投资机会。");
                    break;
                case "疾厄":
                    suggestion.append("本月身体状况良好，宜保持作息规律。");
                    break;
                case "迁移":
                    suggestion.append("本月出行顺利，宜拓展视野。");
                    break;
                case "交友":
                    suggestion.append("本月贵人运旺，宜扩展人脉。");
                    break;
                case "官禄":
                    suggestion.append("本月事业运佳，宜争取晋升。");
                    break;
                case "田宅":
                    suggestion.append("本月居住环境和谐，宜改善居住条件。");
                    break;
                case "福德":
                    suggestion.append("本月心情愉悦，宜培养兴趣爱好。");
                    break;
                case "父母":
                    suggestion.append("本月长辈关系融洽，宜尽孝道。");
                    break;
            }
        } else if (score >= 60) {
            suggestion.append("本月运势平稳，宜稳中求进，循序渐进。");
        } else {
            suggestion.append("本月运势欠佳，宜谨慎行事，避免冒进。");
        }
        
        return suggestion.toString();
    }
    
    /**
     * 生成流日运势描述
     */
    private static String generateDailyDescription(Palace palace, int score) {
        StringBuilder description = new StringBuilder();
        description.append("今日流落").append(palace.getName()).append("宫，");
        
        // 根据分数评价运势
        if (score >= 80) {
            description.append("日运极佳。");
        } else if (score >= 60) {
            description.append("日运平稳。");
        } else {
            description.append("日运欠佳。");
        }
        
        // 分析宫位特点
        description.append("宫中");
        List<Star> stars = palace.getAllStars();
        if (!stars.isEmpty()) {
            description.append("有");
            for (int i = 0; i < stars.size(); i++) {
                if (i > 0) {
                    description.append("、");
                }
                description.append(stars.get(i).getName());
            }
            description.append("星耀");
        }
        
        return description.toString();
    }
    
    /**
     * 生成流日建议
     */
    private static String generateDailySuggestion(Palace palace, int score) {
        StringBuilder suggestion = new StringBuilder();
        
        // 根据宫位和分数给出建议
        if (score >= 80) {
            switch (palace.getName()) {
                case "命宫":
                    suggestion.append("今日适合展现自我，把握机会。");
                    break;
                case "兄弟":
                    suggestion.append("今日适合社交活动，增进友谊。");
                    break;
                case "夫妻":
                    suggestion.append("今日适合约会，增进感情。");
                    break;
                case "子女":
                    suggestion.append("今日适合亲子活动，关注家庭。");
                    break;
                case "财帛":
                    suggestion.append("今日适合理财投资，把握机会。");
                    break;
                case "疾厄":
                    suggestion.append("今日身体状况良好，适合运动。");
                    break;
                case "迁移":
                    suggestion.append("今日适合出行，拓展视野。");
                    break;
                case "交友":
                    suggestion.append("今日适合社交，结识贵人。");
                    break;
                case "官禄":
                    suggestion.append("今日适合工作，争取表现。");
                    break;
                case "田宅":
                    suggestion.append("今日适合处理居住相关事务。");
                    break;
                case "福德":
                    suggestion.append("今日适合休闲娱乐，培养兴趣。");
                    break;
                case "父母":
                    suggestion.append("今日适合与长辈互动，尽孝道。");
                    break;
            }
        } else if (score >= 60) {
            suggestion.append("今日运势平稳，宜按部就班，保持平常心。");
        } else {
            suggestion.append("今日运势欠佳，宜谨慎行事，避免冲动。");
        }
        
        return suggestion.toString();
    }
} 