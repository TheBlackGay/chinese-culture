package com.chinese.culture.admin.core.iztro.interpreter;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.HoroscopePattern;
import com.chinese.culture.admin.core.iztro.analyzer.PalaceAuspiciousnessAnalyzer;
import com.chinese.culture.admin.core.iztro.calculator.StarConvergenceCalculator;

import java.util.*;

/**
 * 命盘建议生成器
 */
public class HoroscopeSuggestionGenerator {
    
    /**
     * 生成命盘建议
     * 
     * @param palaces 所有宫位列表
     * @param patterns 命盘格局列表
     * @param age 年龄
     * @return 建议列表
     */
    public static List<Map<String, Object>> generateSuggestions(List<Palace> palaces, 
                                                              List<Map<String, Object>> patterns,
                                                              int age) {
        List<Map<String, Object>> suggestions = new ArrayList<>();
        
        // 1. 根据命盘格局生成建议
        generatePatternSuggestions(patterns, suggestions);
        
        // 2. 根据宫位吉凶生成建议
        generatePalaceSuggestions(palaces, suggestions);
        
        // 3. 根据年龄生成建议
        generateAgeSuggestions(age, suggestions);
        
        // 4. 根据星耀组合生成建议
        generateStarCombinationSuggestions(palaces, suggestions);
        
        return suggestions;
    }
    
    /**
     * 根据命盘格局生成建议
     */
    private static void generatePatternSuggestions(List<Map<String, Object>> patterns, 
                                                 List<Map<String, Object>> suggestions) {
        for (Map<String, Object> pattern : patterns) {
            HoroscopePattern patternType = (HoroscopePattern) pattern.get("pattern");
            String suggestion = getPatternSuggestion(patternType);
            if (suggestion != null) {
                Map<String, Object> suggestionMap = new HashMap<>();
                suggestionMap.put("type", "格局建议");
                suggestionMap.put("content", suggestion);
                suggestions.add(suggestionMap);
            }
        }
    }
    
    /**
     * 获取格局建议
     */
    private static String getPatternSuggestion(HoroscopePattern pattern) {
        switch (pattern) {
            case ZIWEI_MING:
                return "紫微入命，宜发挥领导才能，主动把握机会，但需注意谦逊待人。";
            case TIANFU_MING:
                return "天府入命，宜稳健发展，注重积累，可从事金融财务相关工作。";
            case LUCUN_MING:
                return "禄存入命，宜从事公职或管理工作，注重提升专业能力。";
            case WENCHANG_MING:
                return "文昌入命，宜从事文化教育、创作等工作，注重学习进修。";
            case FULL_MUTAGEN:
                return "科权禄全格，宜积极进取，把握机会，但需注意平衡发展。";
            case THREE_NOBLE:
                return "三奇格，宜发挥才智，从事专业性工作，注重人际关系。";
            case FOUR_EVIL:
                return "四煞格，需谨慎行事，避免冲动，注重化解矛盾。";
            case RED_BEAUTY:
                return "红艳格，人缘运佳，宜从事服务业或公关工作，注意感情稳定。";
            case WEALTH_NOBLE:
                return "富贵格，财运佳，宜从事商业或投资，但需注意风险控制。";
            case BROKEN:
                return "破格，需稳扎稳打，避免冒进，注重自我提升。";
            default:
                return null;
        }
    }
    
    /**
     * 根据宫位吉凶生成建议
     */
    private static void generatePalaceSuggestions(List<Palace> palaces, 
                                                List<Map<String, Object>> suggestions) {
        // 分析所有宫位
        for (Palace palace : palaces) {
            Map<String, Object> result = PalaceAuspiciousnessAnalyzer.judgeAuspiciousness(palace);
            int score = (int) result.get("score");
            String level = (String) result.get("level");
            String suggestion = getPalaceSuggestion(palace.getName(), score, level);
            
            if (suggestion != null) {
                Map<String, Object> suggestionMap = new HashMap<>();
                suggestionMap.put("type", "宫位建议");
                suggestionMap.put("content", suggestion);
                suggestionMap.put("level", level);
                suggestions.add(suggestionMap);
            }
        }
    }
    
    /**
     * 获取宫位建议
     */
    private static String getPalaceSuggestion(String palaceName, int score, String level) {
        StringBuilder suggestion = new StringBuilder();
        suggestion.append(palaceName).append("（").append(level).append("）：");
        
        if (score >= 80) {
            switch (palaceName) {
                case "命宫":
                    suggestion.append("个人发展顺遂，宜积极进取，把握机会。");
                    break;
                case "身宫":
                    suggestion.append("身体状况良好，宜保持规律作息，适度运动。");
                    break;
                case "财帛":
                    suggestion.append("财运亨通，宜把握投资机会，注意资产配置。");
                    break;
                case "官禄":
                    suggestion.append("事业运佳，宜主动争取晋升机会，扩展人脉。");
                    break;
                case "夫妻":
                    suggestion.append("感情运佳，宜增进感情，规划未来。");
                    break;
                case "子女":
                    suggestion.append("子女运佳，宜关注教育，培养兴趣。");
                    break;
                case "迁移":
                    suggestion.append("行动顺遂，宜把握机会，开拓视野。");
                    break;
                case "交友":
                    suggestion.append("人际运佳，宜扩展人脉，建立关系。");
                    break;
                case "田宅":
                    suggestion.append("居住运佳，宜改善环境，投资房产。");
                    break;
                case "福德":
                    suggestion.append("心情愉悦，宜修身养性，培养兴趣。");
                    break;
                case "父母":
                    suggestion.append("长辈缘佳，宜尽孝道，增进感情。");
                    break;
                case "兄弟":
                    suggestion.append("同辈关系和谐，宜加强交流，互帮互助。");
                    break;
            }
        } else if (score >= 60) {
            switch (palaceName) {
                case "命宫":
                    suggestion.append("发展稳定，宜稳中求进，注重自我提升。");
                    break;
                case "身宫":
                    suggestion.append("身体尚可，宜注意保养，定期体检。");
                    break;
                case "财帛":
                    suggestion.append("财运平稳，宜稳健理财，避免冒险。");
                    break;
                case "官禄":
                    suggestion.append("事业平稳，宜踏实工作，积累经验。");
                    break;
                case "夫妻":
                    suggestion.append("感情平稳，宜用心经营，保持沟通。");
                    break;
                case "子女":
                    suggestion.append("子女平安，宜关注成长，耐心教导。");
                    break;
                case "迁移":
                    suggestion.append("变动平稳，宜谨慎决策，循序渐进。");
                    break;
                case "交友":
                    suggestion.append("人际平和，宜维护关系，互帮互助。");
                    break;
                case "田宅":
                    suggestion.append("居住稳定，宜维护保养，适度改善。");
                    break;
                case "福德":
                    suggestion.append("心态平和，宜保持平常心，注重修养。");
                    break;
                case "父母":
                    suggestion.append("长辈关系平稳，宜尽孝道，保持联系。");
                    break;
                case "兄弟":
                    suggestion.append("同辈关系平稳，宜互相理解，保持联系。");
                    break;
            }
        } else {
            switch (palaceName) {
                case "命宫":
                    suggestion.append("发展受阻，宜稳扎稳打，避免冒进。");
                    break;
                case "身宫":
                    suggestion.append("身体需要调养，宜注意休息，避免过劳。");
                    break;
                case "财帛":
                    suggestion.append("财运欠佳，宜量入为出，避免投机。");
                    break;
                case "官禄":
                    suggestion.append("事业有波折，宜沉稳应对，提升能力。");
                    break;
                case "夫妻":
                    suggestion.append("感情有波折，宜加强沟通，互相理解。");
                    break;
                case "子女":
                    suggestion.append("子女需要关注，宜耐心引导，多加关爱。");
                    break;
                case "迁移":
                    suggestion.append("变动不宜，宜安守本分，等待时机。");
                    break;
                case "交友":
                    suggestion.append("人际有阻，宜谨慎交往，选择朋友。");
                    break;
                case "田宅":
                    suggestion.append("居住有变，宜谨慎决策，避免冒进。");
                    break;
                case "福德":
                    suggestion.append("心情不佳，宜调节心态，保持平和。");
                    break;
                case "父母":
                    suggestion.append("长辈关系需要改善，宜多加关心，增进感情。");
                    break;
                case "兄弟":
                    suggestion.append("同辈关系需要改善，宜多加沟通，增进理解。");
                    break;
            }
        }
        
        return suggestion.toString();
    }
    
    /**
     * 根据年龄生成建议
     */
    private static void generateAgeSuggestions(int age, List<Map<String, Object>> suggestions) {
        String suggestion;
        
        if (age < 20) {
            suggestion = "正处于学习成长阶段，宜注重知识积累，培养兴趣爱好，为未来发展打好基础。";
        } else if (age < 30) {
            suggestion = "正处于事业起步阶段，宜积极进取，开拓视野，注重专业能力提升。";
        } else if (age < 40) {
            suggestion = "正处于事业发展阶段，宜把握机会，稳步晋升，注意工作与生活平衡。";
        } else if (age < 50) {
            suggestion = "正处于事业巅峰阶段，宜注重管理能力，合理规划，为未来做准备。";
        } else if (age < 60) {
            suggestion = "正处于经验丰富阶段，宜传承经验，适度放权，注重健康养生。";
        } else {
            suggestion = "正处于回馈社会阶段，宜享受生活，关注健康，培养兴趣爱好。";
        }
        
        Map<String, Object> suggestionMap = new HashMap<>();
        suggestionMap.put("type", "年龄建议");
        suggestionMap.put("content", suggestion);
        suggestions.add(suggestionMap);
    }
    
    /**
     * 根据星耀组合生成建议
     */
    private static void generateStarCombinationSuggestions(List<Palace> palaces, 
                                                         List<Map<String, Object>> suggestions) {
        // 获取所有星耀组合
        List<Map<String, Object>> convergences = StarConvergenceCalculator.findAllConvergences(palaces);
        
        // 分析重要组合
        for (Map<String, Object> convergence : convergences) {
            String effect = (String) convergence.get("effect");
            if (effect.contains("最强") || effect.contains("和谐")) {
                String suggestion = String.format("可以利用%s与%s的组合优势，%s",
                    convergence.get("star1"),
                    convergence.get("star2"),
                    getStarCombinationSuggestion(effect));
                
                Map<String, Object> suggestionMap = new HashMap<>();
                suggestionMap.put("type", "星耀组合建议");
                suggestionMap.put("content", suggestion);
                suggestions.add(suggestionMap);
            }
        }
    }
    
    /**
     * 获取星耀组合建议
     */
    private static String getStarCombinationSuggestion(String effect) {
        if (effect.contains("智慧") || effect.contains("文才")) {
            return "在学术研究或创作领域发展。";
        } else if (effect.contains("权贵") || effect.contains("财运")) {
            return "在商业或管理领域发展。";
        } else if (effect.contains("人缘") || effect.contains("口才")) {
            return "在公关或销售领域发展。";
        } else if (effect.contains("创新") || effect.contains("艺术")) {
            return "在创意或艺术领域发展。";
        } else {
            return "发挥优势，谋求发展。";
        }
    }
} 