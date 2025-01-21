package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 事业财运分析器
 */
@Slf4j
public class CareerWealthAnalyzer {
    
    // 事业吉星列表
    private static final List<String> CAREER_LUCKY_STARS = Arrays.asList(
        "紫微", "天机", "太阳", "武曲", "天同", "天府", "贪狼",
        "左辅", "右弼", "文昌", "文曲", "天魁", "天钺"
    );
    
    // 事业煞星列表
    private static final List<String> CAREER_EVIL_STARS = Arrays.asList(
        "七杀", "破军", "天刑", "火星", "铃星", "地空", "地劫"
    );
    
    // 财运吉星列表
    private static final List<String> WEALTH_LUCKY_STARS = Arrays.asList(
        "武曲", "天同", "天府", "太阳", "贪狼", "右弼"
    );
    
    // 财运煞星列表
    private static final List<String> WEALTH_EVIL_STARS = Arrays.asList(
        "七杀", "破军", "火星", "铃星"
    );
    
    /**
     * 分析事业财运
     * 
     * @param palaces 所有宫位列表
     * @param age 年龄
     * @return 事业财运分析结果
     */
    public static Map<String, Object> analyzeCareerAndWealth(List<Palace> palaces, int age) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 分析事业运势
        Map<String, Object> careerAnalysis = analyzeCareer(palaces);
        result.put("career", careerAnalysis);
        
        // 2. 分析财运
        Map<String, Object> wealthAnalysis = analyzeWealth(palaces);
        result.put("wealth", wealthAnalysis);
        
        // 3. 分析事业发展时机
        Map<String, Object> timingAnalysis = analyzeCareerTiming(palaces, age);
        result.put("timing", timingAnalysis);
        
        // 4. 生成综合建议
        String suggestion = generateSuggestion(
            (int)careerAnalysis.get("score"),
            (int)wealthAnalysis.get("score"),
            age
        );
        result.put("suggestion", suggestion);
        
        return result;
    }
    
    /**
     * 分析事业运势
     */
    private static Map<String, Object> analyzeCareer(List<Palace> palaces) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 找到事业相关宫位
        Palace officePalace = null;
        Palace careerPalace = null;
        for (Palace palace : palaces) {
            if ("官禄".equals(palace.getName())) {
                officePalace = palace;
            } else if ("事业".equals(palace.getName())) {
                careerPalace = palace;
            }
        }
        
        // 2. 计算事业运势评分
        int score = calculateCareerScore(officePalace, careerPalace);
        result.put("score", score);
        
        // 3. 分析有利行业
        List<String> suitableIndustries = analyzeSuitableIndustries(officePalace, careerPalace);
        result.put("suitableIndustries", suitableIndustries);
        
        // 4. 生成事业运描述
        String description = generateCareerDescription(score, officePalace, careerPalace);
        result.put("description", description);
        
        return result;
    }
    
    /**
     * 计算事业运势评分
     */
    private static int calculateCareerScore(Palace officePalace, Palace careerPalace) {
        int score = 60;  // 基础分数

        // 分析官禄宫星耀亮度
        for (Star star : officePalace.getAllStars()) {
            Brightness Brightness = (star.getBrightness());
            if (Brightness == Brightness.TEMPLE) {
                score += 10;
            } else if (Brightness == Brightness.GAIN) {
                score -= 5;
            } else if (Brightness == Brightness.TRAPPED) {
                score -= 10;
            }
        }

        // 分析事业宫星耀亮度
        for (Star star : careerPalace.getAllStars()) {
            Brightness Brightness = (star.getBrightness());
            if (Brightness == Brightness.TEMPLE) {
                score += 10;
            } else if (Brightness == Brightness.GAIN) {
                score -= 5;
            } else if (Brightness == Brightness.TRAPPED) {
                score -= 10;
            }
        }

        // 分析四化
        for (String mutagenStr : officePalace.getMutagens()) {
            try {
                Mutagen mutagen = Mutagen.fromString(mutagenStr);
                switch (mutagen) {
                    case LUCKY:
                        score -= 5;
                        break;
                    case POWER:
                        score += 5;
                        break;
                    case SKILL:
                        score += 3;
                        break;
                    case WEAK:
                        score -= 5;
                        break;
                }
            } catch (IllegalArgumentException e) {
                // 忽略无效的四化值
            }
        }

        for (String mutagenStr : careerPalace.getMutagens()) {
            try {
                Mutagen mutagen = Mutagen.fromString(mutagenStr);
                switch (mutagen) {
                    case LUCKY:
                        score -= 5;
                        break;
                    case POWER:
                        score += 5;
                        break;
                    case SKILL:
                        score += 3;
                        break;
                    case WEAK:
                        score -= 5;
                        break;
                }
            } catch (IllegalArgumentException e) {
                // 忽略无效的四化值
            }
        }

        // 确保分数在0-100范围内
        return Math.max(0, Math.min(100, score));
    }
    
    /**
     * 分析适合的行业
     */
    private static List<String> analyzeSuitableIndustries(Palace officePalace, 
                                                        Palace careerPalace) {
        Set<String> industries = new HashSet<>();
        
        // 分析官禄宫星耀
        if (officePalace != null) {
            for (Star star : officePalace.getAllStars()) {
                switch (star.getName()) {
                    case "紫微":
                    case "天府":
                        industries.add("管理");
                        industries.add("行政");
                        break;
                    case "天机":
                    case "文昌":
                    case "文曲":
                        industries.add("教育");
                        industries.add("文化");
                        industries.add("科技");
                        break;
                    case "太阳":
                    case "武曲":
                        industries.add("金融");
                        industries.add("商业");
                        break;
                    case "天同":
                        industries.add("医疗");
                        industries.add("服务");
                        break;
                    case "贪狼":
                        industries.add("销售");
                        industries.add("传媒");
                        break;
                }
            }
        }
        
        // 分析事业宫星耀
        if (careerPalace != null) {
            for (Star star : careerPalace.getAllStars()) {
                switch (star.getName()) {
                    case "左辅":
                    case "右弼":
                        industries.add("咨询");
                        industries.add("服务");
                        break;
                    case "天魁":
                    case "天钺":
                        industries.add("公务");
                        industries.add("事业单位");
                        break;
                }
            }
        }
        
        return new ArrayList<>(industries);
    }
    
    /**
     * 生成事业运描述
     */
    private static String generateCareerDescription(int score, 
                                                  Palace officePalace,
                                                  Palace careerPalace) {
        StringBuilder description = new StringBuilder();
        
        // 1. 根据分数评价总体运势
        if (score >= 80) {
            description.append("事业运势极佳，具有较强的发展潜力。");
        } else if (score >= 60) {
            description.append("事业运势平稳，发展空间尚可。");
        } else {
            description.append("事业运势欠佳，需要更多努力。");
        }
        
        // 2. 分析具体特点
        if (officePalace != null && !officePalace.getAllStars().isEmpty()) {
            description.append("官禄宫星耀较多，");
            if (score >= 70) {
                description.append("有望获得领导赏识。");
            } else {
                description.append("需要把握机会表现。");
            }
        }
        
        if (careerPalace != null && !careerPalace.getAllStars().isEmpty()) {
            description.append("事业宫星耀活跃，");
            if (score >= 70) {
                description.append("事业发展空间广阔。");
            } else {
                description.append("需要积累专业能力。");
            }
        }
        
        return description.toString();
    }
    
    /**
     * 分析财运
     */
    private static Map<String, Object> analyzeWealth(List<Palace> palaces) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 找到财帛宫
        Palace wealthPalace = null;
        for (Palace palace : palaces) {
            if ("财帛".equals(palace.getName())) {
                wealthPalace = palace;
                break;
            }
        }
        
        // 2. 计算财运评分
        int score = calculateWealthScore(wealthPalace);
        result.put("score", score);
        
        // 3. 分析财运来源
        List<String> wealthSources = analyzeWealthSources(wealthPalace);
        result.put("sources", wealthSources);
        
        // 4. 生成财运描述
        String description = generateWealthDescription(score, wealthPalace);
        result.put("description", description);
        
        return result;
    }
    
    /**
     * 计算财运评分
     */
    private static int calculateWealthScore(Palace wealthPalace) {
        int score = 60;  // 基础分数
        StringBuilder description = new StringBuilder();

        // 分析财帛宫星耀亮度
        for (Star star : wealthPalace.getAllStars()) {
            Brightness Brightness = (star.getBrightness());
            if (Brightness == Brightness.TEMPLE) {
                score += 10;
            } else if (Brightness == Brightness.GAIN) {
                score -= 5;
            } else if (Brightness == Brightness.TRAPPED) {
                score -= 10;
            }
        }

        // 分析四化
        for (String mutagenStr : wealthPalace.getMutagens()) {
            try {
                Mutagen mutagen = Mutagen.fromString(mutagenStr);
                switch (mutagen) {
                    case LUCKY:
                        score += 3;
                        description.append("，有化禄星，财运强盛");
                        break;
                    case POWER:
                        score += 2;
                        break;
                    case SKILL:
                        score += 1;
                        break;
                    case WEAK:
                        score -= 5;
                        break;
                }
            } catch (IllegalArgumentException e) {
                // 忽略无效的四化值
            }
        }

        // 确保分数在0-100范围内
        return Math.max(0, Math.min(100, score));
    }
    
    /**
     * 分析财运来源
     */
    private static List<String> analyzeWealthSources(Palace wealthPalace) {
        Set<String> sources = new HashSet<>();
        
        if (wealthPalace != null) {
            for (Star star : wealthPalace.getAllStars()) {
                switch (star.getName()) {
                    case "武曲":
                        sources.add("工作收入");
                        sources.add("经商收益");
                        break;
                    case "天同":
                        sources.add("偶然收入");
                        sources.add("意外之财");
                        break;
                    case "天府":
                        sources.add("固定收入");
                        sources.add("投资收益");
                        break;
                    case "太阳":
                        sources.add("正财");
                        sources.add("事业收入");
                        break;
                    case "贪狼":
                        sources.add("销售收入");
                        sources.add("佣金提成");
                        break;
                }
            }
        }
        
        return new ArrayList<>(sources);
    }
    
    /**
     * 生成财运描述
     */
    private static String generateWealthDescription(int score, Palace wealthPalace) {
        StringBuilder description = new StringBuilder();
        
        // 1. 根据分数评价总体财运
        if (score >= 80) {
            description.append("财运极佳，收入可观。");
        } else if (score >= 60) {
            description.append("财运平稳，收入稳定。");
        } else {
            description.append("财运欠佳，需要开源节流。");
        }
        
        // 2. 分析具体特点
        if (wealthPalace != null) {
            List<Star> stars = wealthPalace.getAllStars();
            if (!stars.isEmpty()) {
                description.append("财帛宫星耀较多，");
                if (score >= 70) {
                    description.append("理财能力较强。");
                } else {
                    description.append("需要谨慎理财。");
                }
            }
            
            // 分析四化
            for (String mutagenStr : wealthPalace.getMutagens()) {
                try {
                    Mutagen mutagen = Mutagen.fromString(mutagenStr);
                    switch (mutagen) {
                        case LUCKY:
                            description.append("有财运星入主，");
                            description.append("财运有望提升。");
                            break;
                        case POWER:
                            description.append("有偏财运星入主，");
                            description.append("投资收益可观。");
                            break;
                        case SKILL:
                            description.append("有理财能力，");
                            description.append("理财能力较强。");
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    // 忽略无效的四化值
                }
            }
        }
        
        return description.toString();
    }
    
    /**
     * 分析事业发展时机
     */
    private static Map<String, Object> analyzeCareerTiming(List<Palace> palaces, int age) {
        Map<String, Object> result = new HashMap<>();
        
        // 基础判断
        int peakAge;
        if (age < 30) {
            peakAge = 35;
        } else if (age < 40) {
            peakAge = 45;
        } else {
            peakAge = 55;
        }
        
        // 根据星耀调整高峰年龄
        Palace officePalace = null;
        for (Palace palace : palaces) {
            if ("官禄".equals(palace.getName())) {
                officePalace = palace;
                break;
            }
        }
        
        if (officePalace != null) {
            for (Star star : officePalace.getAllStars()) {
                if (CAREER_LUCKY_STARS.contains(star.getName())) {
                    peakAge -= 1;
                }
                if (CAREER_EVIL_STARS.contains(star.getName())) {
                    peakAge += 1;
                }
            }
        }
        
        result.put("peakAge", peakAge);
        
        // 生成时机建议
        String suggestion;
        if (age < peakAge - 5) {
            suggestion = String.format("事业高峰期在%d岁左右，目前应着重积累经验。", peakAge);
        } else if (age <= peakAge + 5) {
            suggestion = "正处于事业上升期，应把握机会谋求发展。";
        } else {
            suggestion = "已过事业高峰期，可考虑适度放权，培养接班人。";
        }
        result.put("suggestion", suggestion);
        
        return result;
    }
    
    /**
     * 生成综合建议
     */
    private static String generateSuggestion(int careerScore, 
                                           int wealthScore,
                                           int age) {
        StringBuilder suggestion = new StringBuilder();
        
        // 1. 根据事业运势给出建议
        if (careerScore >= 80) {
            suggestion.append("事业发展潜力巨大，");
            if (age < 35) {
                suggestion.append("建议积极进取，把握机会。");
            } else {
                suggestion.append("建议稳扎稳打，持续发展。");
            }
        } else if (careerScore >= 60) {
            suggestion.append("事业发展平稳，");
            if (age < 35) {
                suggestion.append("建议提升能力，积累经验。");
            } else {
                suggestion.append("建议巩固优势，稳中求进。");
            }
        } else {
            suggestion.append("事业发展有阻力，");
            if (age < 35) {
                suggestion.append("建议夯实基础，提升竞争力。");
            } else {
                suggestion.append("建议调整方向，寻找突破。");
            }
        }
        
        // 2. 根据财运给出建议
        if (wealthScore >= 80) {
            suggestion.append("财运优异，建议把握投资机会，注意资产配置。");
        } else if (wealthScore >= 60) {
            suggestion.append("财运平稳，建议稳健理财，适度投资。");
        } else {
            suggestion.append("财运欠佳，建议开源节流，谨慎投资。");
        }
        
        // 3. 根据年龄给出建议
        if (age < 30) {
            suggestion.append("年轻有为，建议以事业发展为重，积累财富基础。");
        } else if (age < 45) {
            suggestion.append("正值黄金期，建议平衡事业与财富，注重长远规划。");
        } else {
            suggestion.append("经验丰富，建议合理配置资源，为未来做准备。");
        }
        
        return suggestion.toString();
    }

    private static int calculateMutagenEffect(List<Star> stars) {
        int effect = 0;
        for (Star star : stars) {
            for (Mutagen mutagen : star.getMutagens()) {
                switch (mutagen) {
                    case LUCKY:
                        effect += 10;
                        break;
                    case POWER:
                        effect += 5;
                        break;
                    case WEAK:
                        effect -= 5;
                        break;
                }
            }
        }
        return effect;
    }

    /**
     * 分析财富状况
     */
    private static Map<String, Object> analyzeWealth(Palace wealthPalace, int age) {
        Map<String, Object> result = new HashMap<>();
        int score = 60; // 基础分
        
        // 分析财帛宫
        if (wealthPalace != null) {
            for (Star star : wealthPalace.getAllStars()) {
                if (WEALTH_LUCKY_STARS.contains(star.getName())) {
                    score += 5;
                }
                if (WEALTH_EVIL_STARS.contains(star.getName())) {
                    score -= 5;
                }
                
                // 计算星耀亮度
                Brightness Brightness = (star.getBrightness());
                if (Brightness == Brightness.TEMPLE) {
                    score += 10;
                } else if (Brightness == Brightness.GAIN) {
                    score -= 5;
                } else if (Brightness == Brightness.TRAPPED) {
                    score -= 10;
                }
            }
            
            // 分析四化
            for (String mutagenStr : wealthPalace.getMutagens()) {
                try {
                    Mutagen mutagen = Mutagen.fromString(mutagenStr);
                    switch (mutagen) {
                        case LUCKY:
                        case POWER:
                            score += 5;
                            break;
                        case SKILL:
                            score += 3;
                            break;
                        case WEAK:
                            score -= 5;
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    // 忽略无效的四化值
                }
            }
        }
        
        // 确保分数在0-100之间
        score = Math.max(0, Math.min(100, score));
        result.put("score", score);
        
        return result;
    }
} 
