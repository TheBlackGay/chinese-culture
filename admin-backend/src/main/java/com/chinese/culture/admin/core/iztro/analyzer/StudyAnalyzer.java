package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.StarName;

import java.util.*;

import static com.chinese.culture.admin.core.iztro.data.enums.Mutagen.*;
import static com.chinese.culture.admin.core.iztro.data.enums.StarName.*;

/**
 * 学业考试分析器
 */
public class StudyAnalyzer {

    // 学业吉星列表
    private static final List<StarName> STUDY_LUCKY_STARS = Arrays.asList(
        StarName.TIANJI, StarName.WENCHANG, StarName.WENQU,
        StarName.ZUOFU, StarName.YOUBI, StarName.TAIYANG
    );

    // 学业煞星列表
    private static final List<StarName> STUDY_EVIL_STARS = Arrays.asList(
        StarName.POJUN, StarName.TIANXING, StarName.HUOXING,
        StarName.LINGXING, StarName.DIKONG, StarName.DIJIE
    );

    // 学科优势映射
    private static final Map<StarName, List<String>> SUBJECT_STRENGTHS = new HashMap<>();
    static {
        SUBJECT_STRENGTHS.put(StarName.TIANJI, Arrays.asList("数学", "物理", "计算机"));
        SUBJECT_STRENGTHS.put(StarName.WENCHANG, Arrays.asList("语文", "历史", "文学"));
        SUBJECT_STRENGTHS.put(StarName.WENQU, Arrays.asList("外语", "艺术", "音乐"));
        SUBJECT_STRENGTHS.put(StarName.TAIYANG, Arrays.asList("物理", "化学", "生物"));
        SUBJECT_STRENGTHS.put(StarName.TIANXIANG, Arrays.asList("政治", "哲学", "心理"));
    }

    /**
     * 分析学业考试
     *
     * @param palaces 所有宫位列表
     * @param age 年龄
     * @return 学业考试分析结果
     */
    public static Map<String, Object> analyzeStudy(List<Palace> palaces, int age) {
        Map<String, Object> result = new HashMap<>();

        // 1. 找到命宫和文昌宫
        Palace mingGong = null;
        Palace wenchangGong = null;
        for (Palace palace : palaces) {
            if ("命宫".equals(palace.getName())) {
                mingGong = palace;
            } else if ("文昌".equals(palace.getName())) {
                wenchangGong = palace;
            }
        }

        // 2. 计算学业评分
        int score = calculateStudyScore(mingGong, wenchangGong);
        result.put("score", score);

        // 3. 分析学科优势
        List<String> strongSubjects = analyzeStrongSubjects(mingGong, wenchangGong);
        result.put("strongSubjects", strongSubjects);

        // 4. 分析学习特点
        Map<String, Object> characteristics = analyzeStudyCharacteristics(mingGong, wenchangGong);
        result.put("characteristics", characteristics);

        // 5. 生成学业建议
        String suggestion = generateStudySuggestion(score, strongSubjects, age);
        result.put("suggestion", suggestion);

        return result;
    }

    /**
     * 计算学业评分
     */
    private static int calculateStudyScore(Palace mingGong, Palace wenchangGong) {
        int score = 60; // 基础分

        // 1. 分析命宫
        if (mingGong != null) {
            for (Star star : mingGong.getAllStars()) {
                if (STUDY_LUCKY_STARS.contains(star.getName())) {
                    score += 5;
                }
                if (STUDY_EVIL_STARS.contains(star.getName())) {
                    score -= 5;
                }
            }

            // 分析四化
            for (String mutagenStr : mingGong.getMutagens()) {
                try {
                    Mutagen mutagen = Mutagen.fromString(mutagenStr);
                    if (mutagen == LUCKY || mutagen == SKILL) {
                        score += 5;
                    }
                } catch (IllegalArgumentException e) {
                    // 忽略无效的四化值
                }
            }
        }

        // 2. 分析文昌宫
        if (wenchangGong != null) {
            for (Star star : wenchangGong.getAllStars()) {
                if (STUDY_LUCKY_STARS.contains(star.getName())) {
                    score += 5;
                }
                if (STUDY_EVIL_STARS.contains(star.getName())) {
                    score -= 5;
                }
            }

            // 分析四化
            for (String mutagenStr : wenchangGong.getMutagens()) {
                try {
                    Mutagen mutagen = Mutagen.fromString(mutagenStr);
                    if (mutagen == LUCKY || mutagen == SKILL) {
                        score += 5;
                    }
                } catch (IllegalArgumentException e) {
                    // 忽略无效的四化值
                }
            }

            // 分析星耀亮度
            for (Star star : wenchangGong.getMajorStars()) {
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

        // 确保分数在0-100之间
        return Math.max(0, Math.min(100, score));
    }

    /**
     * 分析学科优势
     */
    private static List<String> analyzeStrongSubjects(Palace mingGong,
                                                    Palace wenchangGong) {
        Set<String> subjects = new HashSet<>();

        // 分析命宫星耀
        if (mingGong != null) {
            for (Star star : mingGong.getAllStars()) {
                List<String> strengths = SUBJECT_STRENGTHS.get(star.getName());
                if (strengths != null) {
                    subjects.addAll(strengths);
                }
            }
        }

        // 分析文昌宫星耀
        if (wenchangGong != null) {
            for (Star star : wenchangGong.getAllStars()) {
                List<String> strengths = SUBJECT_STRENGTHS.get(star.getName());
                if (strengths != null) {
                    subjects.addAll(strengths);
                }
            }
        }

        return new ArrayList<>(subjects);
    }

    /**
     * 分析学习特点
     */
    private static Map<String, Object> analyzeStudyCharacteristics(Palace mingGong,
                                                                 Palace wenchangGong) {
        Map<String, Object> result = new HashMap<>();
        List<String> strengths = new ArrayList<>();
        List<String> weaknesses = new ArrayList<>();

        // 1. 分析命宫特点
        if (mingGong != null) {
            for (Star star : mingGong.getAllStars()) {
                analyzeStudyCharacteristics(star, result);
            }
        }

        // 2. 分析文昌宫特点
        if (wenchangGong != null) {
            for (Star star : wenchangGong.getAllStars()) {
                analyzeStudyCharacteristics(star, result);
            }
        }

        result.put("strengths", strengths);
        result.put("weaknesses", weaknesses);

        return result;
    }

    /**
     * 分析学习方法
     */
    private static void analyzeStudyMethods(Star star, Map<String, Object> result) {
        switch (star.getName()) {
            case TIANJI:
                result.put("method", "善于思考，擅长逻辑推理");
                break;
            case WENCHANG:
                result.put("method", "文笔优秀，善于表达");
                break;
            case WENQU:
                result.put("method", "悟性较高，善于理解");
                break;
            case TAIYANG:
                result.put("method", "实践能力强，善于实验");
                break;
            case TIANXIANG:
                result.put("method", "思维深入，善于分析");
                break;
            default:
                break;
        }
    }

    /**
     * 分析学习特点
     */
    private static void analyzeStudyCharacteristics(Star star, Map<String, Object> result) {
        switch (star.getName()) {
            case TIANJI:
                result.put("logical", true);
                result.put("analytical", true);
                break;
            case WENCHANG:
                result.put("literary", true);
                result.put("creative", true);
                break;
            case WENQU:
                result.put("artistic", true);
                result.put("linguistic", true);
                break;
            case TAIYANG:
                result.put("scientific", true);
                result.put("experimental", true);
                break;
            case TIANXIANG:
                result.put("philosophical", true);
                result.put("psychological", true);
                break;
            default:
                break;
        }
    }

    /**
     * 生成学业建议
     */
    private static String generateStudySuggestion(int score,
                                                List<String> strongSubjects,
                                                int age) {
        StringBuilder suggestion = new StringBuilder();

        // 根据学业评分给出建议
        if (score >= 80) {
            suggestion.append("学习天赋优异，");
            if (age < 18) {
                suggestion.append("建议积极参加竞赛，提升水平。");
            } else if (age < 25) {
                suggestion.append("建议继续深造，发展专业特长。");
            } else {
                suggestion.append("建议持续学习，保持竞争优势。");
            }
        } else if (score >= 60) {
            suggestion.append("学习能力尚可，");
            if (age < 18) {
                suggestion.append("建议制定合理计划，循序渐进。");
            } else if (age < 25) {
                suggestion.append("建议选择适合的专业方向。");
            } else {
                suggestion.append("建议根据兴趣选择学习内容。");
            }
        } else {
            suggestion.append("学习能力有待提升，");
            if (age < 18) {
                suggestion.append("建议多加练习，打好基础。");
            } else if (age < 25) {
                suggestion.append("建议选择实践性较强的方向。");
            } else {
                suggestion.append("建议从兴趣爱好入手，培养学习习惯。");
            }
        }

        // 根据学科优势给出建议
        if (!strongSubjects.isEmpty()) {
            suggestion.append("\n优势学科包括：").append(String.join("、", strongSubjects))
                     .append("，建议重点发展这些方向。");
        }

        return suggestion.toString();
    }
}
