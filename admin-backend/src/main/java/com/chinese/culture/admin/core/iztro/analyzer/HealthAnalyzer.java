package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.StarName;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.chinese.culture.admin.core.iztro.data.enums.StarName.*;

/**
 * 健康分析器
 */
@Slf4j
public class HealthAnalyzer {

    // 健康吉星列表
    private static final List<StarName> HEALTH_LUCKY_STARS = Arrays.asList(
        TIANTONG, TAIYANG, WENCHANG, WENQU, ZUOFU, YOUBI
    );

    // 健康煞星列表
    private static final List<StarName> HEALTH_EVIL_STARS = Arrays.asList(
        QISHA, POJUN, TIANXING, HUOXING, LINGXING, DIKONG, DIJIE
    );

    // 疾病类型映射
    private static final Map<StarName, List<String>> DISEASE_TYPES = new HashMap<>();
    static {
        DISEASE_TYPES.put(StarName.TIANJI, Arrays.asList("神经系统", "呼吸系统"));
        DISEASE_TYPES.put(StarName.TAIYANG, Arrays.asList("心脏", "眼睛"));
        DISEASE_TYPES.put(StarName.WUQU, Arrays.asList("消化系统", "肝胆"));
        DISEASE_TYPES.put(StarName.TIANTONG, Arrays.asList("心脏", "血液循环"));
        DISEASE_TYPES.put(StarName.TANLANG, Arrays.asList("泌尿系统", "生殖系统"));
        DISEASE_TYPES.put(StarName.JUMEN, Arrays.asList("消化系统", "口腔"));
        DISEASE_TYPES.put(StarName.TIANFU, Arrays.asList("呼吸系统", "肺部"));
        DISEASE_TYPES.put(StarName.TAIYIN, Arrays.asList("消化系统", "妇科"));
        DISEASE_TYPES.put(StarName.QISHA, Arrays.asList("头部", "外伤"));
        DISEASE_TYPES.put(StarName.POJUN, Arrays.asList("骨骼", "关节"));
    }

    /**
     * 分析健康状况
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
        if (mingGong != null) {
            for (Star star : mingGong.getAllStars()) {
                Brightness brightness = star.getBrightness();
                if (brightness == Brightness.TEMPLE) {
                    score += 10;
                } else if (brightness == Brightness.GAIN) {
                    score -= 5;
                } else if (brightness == Brightness.TRAPPED) {
                    score -= 10;
                }
            }
        }

        // 分析疾厄宫星耀亮度
        if (diseaseGong != null) {
            for (Star star : diseaseGong.getAllStars()) {
                Brightness brightness = star.getBrightness();
                if (brightness == Brightness.TEMPLE) {
                    score += 10;
                } else if (brightness == Brightness.GAIN) {
                    score -= 5;
                } else if (brightness == Brightness.TRAPPED) {
                    score -= 10;
                }
            }
        }

        // 分析四化
        if (mingGong != null) {
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
        }

        if (diseaseGong != null) {
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
                analyzePhysicalCharacteristics(star, result);
            }
        }

        // 2. 分析疾厄宫特点
        if (diseaseGong != null) {
            for (Star star : diseaseGong.getAllStars()) {
                analyzePhysicalCharacteristics(star, result);
                analyzeHealthSuggestions(star, result);
            }
        }

        result.put("strengths", strengths);
        result.put("weaknesses", weaknesses);

        return result;
    }

    /**
     * 分析体质特点
     */
    private static void analyzePhysicalCharacteristics(Star star, Map<String, Object> result) {
        switch (star.getName()) {
            case TAIYANG:
                result.put("yang", true);
                result.put("energetic", true);
                break;
            case TAIYIN:
                result.put("yin", true);
                result.put("sensitive", true);
                break;
            case WUQU:
                result.put("strong", true);
                result.put("resilient", true);
                break;
            case TIANTONG:
                result.put("balanced", true);
                result.put("healthy", true);
                break;
            case TIANFU:
                result.put("stable", true);
                result.put("enduring", true);
                break;
            default:
                break;
        }
    }

    /**
     * 分析健康建议
     */
    private static void analyzeHealthSuggestions(Star star, Map<String, Object> result) {
        switch (star.getName()) {
            case TAIYANG:
                result.put("suggestion", "注意心脑血管，保持充足睡眠");
                break;
            case TAIYIN:
                result.put("suggestion", "调理脾胃，注意饮食规律");
                break;
            case WUQU:
                result.put("suggestion", "保护肝胆，避免过度劳累");
                break;
            case TIANTONG:
                result.put("suggestion", "保持心情舒畅，适量运动");
                break;
            case TIANFU:
                result.put("suggestion", "保护呼吸系统，避免受凉");
                break;
            default:
                break;
        }
    }

    /**
     * 生成健康建议
     */
    private static String generateHealthSuggestion(int score,
                                                 List<String> potentialDiseases,
                                                 int age,
                                                 boolean gender) {
        StringBuilder suggestion = new StringBuilder();

        // 根据得分给出总体建议
        if (score >= 80) {
            suggestion.append("您的体质较好，");
        } else if (score >= 60) {
            suggestion.append("您的体质一般，");
        } else {
            suggestion.append("您的体质较弱，");
        }

        // 根据年龄段给出具体建议
        if (age < 16) {
            suggestion.append("正处于成长发育期，要注意营养均衡，适量运动。");
        } else if (age < 40) {
            suggestion.append("正值精力旺盛期，要注意劳逸结合，不要过度透支。");
        } else if (age < 60) {
            suggestion.append("要开始注意养生保健，定期体检。");
        } else {
            suggestion.append("要特别注意身体，保持适度运动，规律作息。");
        }

        // 根据性别给出补充建议
        if (gender) {  // 男性
            suggestion.append("建议多参加户外运动，增强体质。");
        } else {  // 女性
            suggestion.append("建议注意保暖，适当进行瑜伽等运动。");
        }

        // 如果有潜在疾病风险，给出预防建议
        if (!potentialDiseases.isEmpty()) {
            suggestion.append("需要特别注意").append(String.join("、", potentialDiseases))
                     .append("等方面的保健。");
        }

        return suggestion.toString();
    }
}
