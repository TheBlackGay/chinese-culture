package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;

import java.util.*;

/**
 * 学业考试分析器
 */
public class StudyAnalyzer {

    // 学业吉星列表
    private static final List<String> STUDY_LUCKY_STARS = Arrays.asList(
        "文昌", "文曲", "天机", "左辅", "右弼", "天魁", "天钺"
    );

    // 学业煞星列表
    private static final List<String> STUDY_EVIL_STARS = Arrays.asList(
        "七杀", "破军", "火星", "铃星"
    );

    // 学科优势映射
    private static final Map<String, List<String>> SUBJECT_STRENGTHS = new HashMap<>();
    static {
        SUBJECT_STRENGTHS.put("天机", Arrays.asList("数学", "物理", "计算机"));
        SUBJECT_STRENGTHS.put("文昌", Arrays.asList("语文", "文学", "历史"));
        SUBJECT_STRENGTHS.put("文曲", Arrays.asList("外语", "艺术", "音乐"));
        SUBJECT_STRENGTHS.put("太阳", Arrays.asList("物理", "化学", "生物"));
        SUBJECT_STRENGTHS.put("武曲", Arrays.asList("数学", "经济", "管理"));
        SUBJECT_STRENGTHS.put("天同", Arrays.asList("医学", "心理", "教育"));
        SUBJECT_STRENGTHS.put("贪狼", Arrays.asList("体育", "军事", "竞技"));
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
            for (String mutagen : mingGong.getMutagens()) {

                Mutagen mutagen1 = Mutagen.fromString(mutagen);
                if (mutagen1 == Mutagen.LUCKY || mutagen1 == Mutagen.SKILL) {
                    score += 5;
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
            for (String mutagen : wenchangGong.getMutagens()) {

                Mutagen mutagen1 = Mutagen.fromString(mutagen);
                if (mutagen1 == Mutagen.LUCKY || mutagen1 == Mutagen.SKILL) {
                    score += 5;
                }
            }

            // 分析星耀亮度
            for (Star star : wenchangGong.getMajorStars()) {
                score += calculateBrightnessEffect(star);
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
                switch (star.getName()) {
                    case "天机":
                        strengths.add("思维敏捷，善于分析");
                        break;
                    case "文昌":
                    case "文曲":
                        strengths.add("记忆力强，善于表达");
                        break;
                    case "左辅":
                    case "右弼":
                        strengths.add("专注力强，善于钻研");
                        break;
                    case "七杀":
                    case "破军":
                        weaknesses.add("注意力容易分散");
                        break;
                }
            }
        }

        // 2. 分析文昌宫特点
        if (wenchangGong != null) {
            for (Star star : wenchangGong.getAllStars()) {
                switch (star.getName()) {
                    case "天魁":
                    case "天钺":
                        strengths.add("学习态度认真");
                        break;
                    case "火星":
                    case "铃星":
                        weaknesses.add("学习容易浮躁");
                        break;
                }
            }

            // 分析四化
            if (!wenchangGong.getMutagens().isEmpty()) {
                if (wenchangGong.getMutagens().contains(Mutagen.SKILL)) {
                    strengths.add("学习能力突出");
                }
            }
        }

        result.put("strengths", strengths);
        result.put("weaknesses", weaknesses);

        return result;
    }

    /**
     * 生成学业建议
     */
    private static String generateStudySuggestion(int score,
                                                List<String> strongSubjects,
                                                int age) {
        StringBuilder suggestion = new StringBuilder();

        // 1. 根据学业评分给出建议
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
                suggestion.append("建议明确目标，努力提升。");
            } else {
                suggestion.append("建议选择适合的领域发展。");
            }
        } else {
            suggestion.append("学习有一定困难，");
            if (age < 18) {
                suggestion.append("建议找到适合的学习方法，打好基础。");
            } else if (age < 25) {
                suggestion.append("建议发展实践能力，找准方向。");
            } else {
                suggestion.append("建议结合实际，学以致用。");
            }
        }

        // 2. 根据学科优势给出建议
        if (!strongSubjects.isEmpty()) {
            suggestion.append("在");
            for (int i = 0; i < strongSubjects.size(); i++) {
                if (i > 0) {
                    suggestion.append("、");
                }
                suggestion.append(strongSubjects.get(i));
            }
            suggestion.append("等学科有优势，建议重点发展。");
        }

        // 3. 根据年龄给出建议
        if (age < 15) {
            suggestion.append("正处于基础教育阶段，建议打好基础，培养兴趣。");
        } else if (age < 18) {
            suggestion.append("正处于高中阶段，建议明确目标，合理规划。");
        } else if (age < 25) {
            suggestion.append("正处于高等教育阶段，建议发展专业特长，为就业做准备。");
        } else {
            suggestion.append("建议终身学习，不断提升自我。");
        }

        return suggestion.toString();
    }

    private static int calculateBrightnessEffect(Star star) {
        Brightness brightness = (star.getBrightness());
        switch (brightness) {
            case TEMPLE:
                return 2;
            case STRONG:
                return 1;
            case GAIN:
                return -1;
            case TRAPPED:
                return -2;
            default:
                return 0;
        }
    }

    private static Mutagen parseMutagen(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("化气不能为空");
        }
        return Mutagen.fromString(text);
    }
}
