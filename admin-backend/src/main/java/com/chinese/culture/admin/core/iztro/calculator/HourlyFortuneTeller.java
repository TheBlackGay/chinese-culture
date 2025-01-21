package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;

import java.util.*;

/**
 * 流时运势预测器
 */
public class HourlyFortuneTeller {

    /**
     * 预测流时运势
     *
     * @param palaces 所有宫位列表
     * @param dailyFlow 流日地支
     * @param hour 时辰(1-12)
     * @return 流时运势预测结果
     */
    public static Map<String, Object> predictHourlyFortune(List<Palace> palaces,
                                                         EarthlyBranch dailyFlow,
                                                         int hour) {
        Map<String, Object> result = new HashMap<>();

        // 1. 计算流时地支
        EarthlyBranch hourlyFlow = calculateHourlyFlow(dailyFlow, hour);
        result.put("hourlyFlow", hourlyFlow);

        // 2. 找到流时所在宫位
        Palace hourlyPalace = findPalaceByBranch(palaces, hourlyFlow);
        result.put("hourlyPalace", hourlyPalace);

        // 3. 分析流时宫位吉凶
        int score = PalaceAuspiciousnessCalculator.calculateAuspiciousness(hourlyPalace);
        result.put("score", score);

        // 4. 生成运势描述
        String description = generateHourlyDescription(hourlyPalace, score);
        result.put("description", description);

        // 5. 生成建议
        String suggestion = generateHourlySuggestion(hourlyPalace, score);
        result.put("suggestion", suggestion);

        return result;
    }

    /**
     * 计算流时地支
     */
    private static EarthlyBranch calculateHourlyFlow(EarthlyBranch dailyFlow, int hour) {
        int dailyIndex = dailyFlow.ordinal();
        int hourlyIndex = (dailyIndex + hour - 1) % 12;
        return EarthlyBranch.values()[hourlyIndex];
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
     * 生成流时运势描述
     */
    private static String generateHourlyDescription(Palace palace, int score) {
        StringBuilder description = new StringBuilder();
        description.append("此时辰流落").append(palace.getName()).append("宫，");

        // 根据分数评价运势
        if (score >= 80) {
            description.append("时运极佳。");
        } else if (score >= 60) {
            description.append("时运平稳。");
        } else {
            description.append("时运欠佳。");
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
     * 生成流时建议
     */
    private static String generateHourlySuggestion(Palace palace, int score) {
        StringBuilder suggestion = new StringBuilder();

        // 根据宫位和分数给出建议
        if (score >= 80) {
            switch (palace.getName()) {
                case "命宫":
                    suggestion.append("此时适合重要决策，把握机会。");
                    break;
                case "兄弟":
                    suggestion.append("此时适合社交活动，增进友谊。");
                    break;
                case "夫妻":
                    suggestion.append("此时适合处理感情事务。");
                    break;
                case "子女":
                    suggestion.append("此时适合亲子活动。");
                    break;
                case "财帛":
                    suggestion.append("此时适合财务活动，把握机会。");
                    break;
                case "疾厄":
                    suggestion.append("此时身体状况良好，适合运动。");
                    break;
                case "迁移":
                    suggestion.append("此时适合出行，顺利。");
                    break;
                case "交友":
                    suggestion.append("此时适合社交，结识贵人。");
                    break;
                case "官禄":
                    suggestion.append("此时适合工作，事业有利。");
                    break;
                case "田宅":
                    suggestion.append("此时适合处理居住相关事务。");
                    break;
                case "福德":
                    suggestion.append("此时适合休闲娱乐，心情愉悦。");
                    break;
                case "父母":
                    suggestion.append("此时适合与长辈互动。");
                    break;
            }
        } else if (score >= 60) {
            suggestion.append("此时运势平稳，宜按部就班行事。");
        } else {
            suggestion.append("此时运势欠佳，宜谨慎行事，避免冒进。");
        }

        return suggestion.toString();
    }
}
