package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 健康分析器
 */
@Slf4j
public class HealthAnalyzer {
    
    // 健康吉星列表
    private static final List<String> HEALTH_LUCKY_STARS = Arrays.asList(
        "天同", "太阳", "文昌", "文曲", "左辅", "右弼"
    );
    
    // 健康煞星列表
    private static final List<String> HEALTH_EVIL_STARS = Arrays.asList(
        "七杀", "破军", "天刑", "火星", "铃星", "地空", "地劫"
    );
    
    // 疾病类型映射
    private static final Map<String, List<String>> DISEASE_TYPES = new HashMap<>();
    static {
        DISEASE_TYPES.put("天机", Arrays.asList("神经系统", "呼吸系统"));
        DISEASE_TYPES.put("太阳", Arrays.asList("心脏", "眼睛"));
        DISEASE_TYPES.put("武曲", Arrays.asList("消化系统", "肝胆"));
        DISEASE_TYPES.put("天同", Arrays.asList("心脏", "血液循环"));
        DISEASE_TYPES.put("贪狼", Arrays.asList("泌尿系统", "生殖系统"));
        DISEASE_TYPES.put("巨门", Arrays.asList("消化系统", "口腔"));
        DISEASE_TYPES.put("天府", Arrays.asList("呼吸系统", "肺部"));
        DISEASE_TYPES.put("太阴", Arrays.asList("消化系统", "妇科"));
        DISEASE_TYPES.put("七杀", Arrays.asList("头部", "外伤"));
        DISEASE_TYPES.put("破军", Arrays.asList("骨骼", "关节"));
    }
    
    /**
     * 分析健康状况
     * 
     * @param palaces 所有宫位列表
     * @param age 年龄
     * @param gender 性别
     * @return 健康状况分析结果
     */
    public static Map<String, Object> analyzeHealth(List<Palace> palaces,
                                                  int age,
                                                  boolean gender) {
        Map<String, Object> result = new HashMap<>();
        
        // 1. 找到命宫和疾厄宫
        Palace mingGong = null;
        Palace diseaseGong = null;
        for (Palace palace : palaces) {
            if ("命宫".equals(palace.getName())) {
                mingGong = palace;
            } else if ("疾厄".equals(palace.getName())) {
                diseaseGong = palace;
            }
        }
        
        // 2. 计算健康评分
        int score = calculateHealthScore(mingGong, diseaseGong);
        result.put("score", score);
        
        // 3. 分析易患疾病
        List<String> potentialDiseases = analyzePotentialDiseases(diseaseGong);
        result.put("potentialDiseases", potentialDiseases);
        
        // 4. 分析体质特点
        Map<String, Object> constitution = analyzeConstitution(mingGong, diseaseGong);
        result.put("constitution", constitution);
        
        // 5. 生成健康建议
        String suggestion = generateHealthSuggestion(score, potentialDiseases, age, gender);
        result.put("suggestion", suggestion);
        
        return result;
    }
    
    /**
     * 计算健康评分
     */
    private static int calculateHealthScore(Palace mingGong, Palace diseaseGong) {
        int score = 60;  // 基础分数

        // 分析命宫星耀亮度
        for (Star star : mingGong.getAllStars()) {
            Brightness Brightness = (star.getBrightness());
            if (Brightness == Brightness.TEMPLE) {
                score += 10;
            } else if (Brightness == Brightness.GAIN) {
                score -= 5;
            } else if (Brightness == Brightness.TRAPPED) {
                score -= 10;
            }
        }

        // 分析疾厄宫星耀亮度
        for (Star star : diseaseGong.getAllStars()) {
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
        for (String mutagenStr : mingGong.getMutagens()) {
            try {
                Mutagen mutagen = Mutagen.fromString(mutagenStr);
                switch (mutagen) {
                    case LUCKY:
                        score += 5;
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

        for (String mutagenStr : diseaseGong.getMutagens()) {
            try {
                Mutagen mutagen = Mutagen.fromString(mutagenStr);
                switch (mutagen) {
                    case LUCKY:
                        score += 5;
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
        score = Math.max(0, Math.min(100, score));
        return score;
    }
    
    /**
     * 分析易患疾病
     */
    private static List<String> analyzePotentialDiseases(Palace diseaseGong) {
        Set<String> diseases = new HashSet<>();
        
        if (diseaseGong != null) {
            // 分析疾厄宫星耀
            for (Star star : diseaseGong.getAllStars()) {
                List<String> types = DISEASE_TYPES.get(star.getName());
                if (types != null) {
                    diseases.addAll(types);
                }
            }
        }
        
        return new ArrayList<>(diseases);
    }
    
    /**
     * 分析体质特点
     */
    private static Map<String, Object> analyzeConstitution(Palace mingGong,
                                                         Palace diseaseGong) {
        Map<String, Object> result = new HashMap<>();
        List<String> strengths = new ArrayList<>();
        List<String> weaknesses = new ArrayList<>();
        
        // 1. 分析命宫特点
        if (mingGong != null) {
            for (Star star : mingGong.getAllStars()) {
                switch (star.getName()) {
                    case "太阳":
                        strengths.add("体质阳刚，精力充沛");
                        break;
                    case "天同":
                        strengths.add("心肺功能良好");
                        break;
                    case "武曲":
                        strengths.add("体格健壮，耐力好");
                        break;
                    case "贪狼":
                        strengths.add("新陈代谢旺盛");
                        break;
                }
            }
        }
        
        // 2. 分析疾厄宫特点
        if (diseaseGong != null) {
            for (Star star : diseaseGong.getAllStars()) {
                switch (star.getName()) {
                    case "七杀":
                        weaknesses.add("易受外伤");
                        break;
                    case "破军":
                        weaknesses.add("骨骼关节易损");
                        break;
                    case "天刑":
                        weaknesses.add("手术体质");
                        break;
                    case "火星":
                    case "铃星":
                        weaknesses.add("易有炎症");
                        break;
                }
            }
        }
        
        result.put("strengths", strengths);
        result.put("weaknesses", weaknesses);
        
        return result;
    }
    
    /**
     * 生成健康建议
     */
    private static String generateHealthSuggestion(int score,
                                                 List<String> potentialDiseases,
                                                 int age,
                                                 boolean gender) {
        StringBuilder suggestion = new StringBuilder();
        
        // 1. 根据健康评分给出建议
        if (score >= 80) {
            suggestion.append("体质较好，");
            if (age < 40) {
                suggestion.append("建议保持良好的作息习惯，适度运动。");
            } else {
                suggestion.append("建议定期体检，保持运动习惯。");
            }
        } else if (score >= 60) {
            suggestion.append("体质一般，");
            if (age < 40) {
                suggestion.append("建议加强锻炼，注意作息规律。");
            } else {
                suggestion.append("建议定期体检，注意保养。");
            }
        } else {
            suggestion.append("体质较弱，");
            if (age < 40) {
                suggestion.append("建议加强营养，规律作息。");
            } else {
                suggestion.append("建议及时就医，加强保健。");
            }
        }
        
        // 2. 根据潜在疾病给出建议
        if (!potentialDiseases.isEmpty()) {
            suggestion.append("\n需要注意：");
            for (String disease : potentialDiseases) {
                suggestion.append(disease).append("、");
            }
            suggestion.setLength(suggestion.length() - 1);
            suggestion.append("等方面的保健。");
        }
        
        // 3. 根据性别和年龄给出特殊建议
        if (gender) { // 男性
            if (age >= 40) {
                suggestion.append("\n建议关注前列腺健康。");
            }
        } else { // 女性
            if (age >= 35) {
                suggestion.append("\n建议关注妇科健康。");
            }
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
} 
