package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.StarConvergenceType;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.core.iztro.data.enums.PalaceRelationType;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;

import java.util.*;

/**
 * 星耀会合效果判断器
 */
public class StarConvergenceCalculator {

    // 主星会合规则
    private static final Map<String, List<String>> MAJOR_CONVERGENCE_RULES = new HashMap<>();
    static {
        // 紫微系会合
        MAJOR_CONVERGENCE_RULES.put("紫微", Arrays.asList("天机", "太阳", "武曲", "天同", "天府"));
        MAJOR_CONVERGENCE_RULES.put("天机", Arrays.asList("紫微", "太阳", "武曲", "天府"));
        MAJOR_CONVERGENCE_RULES.put("太阳", Arrays.asList("紫微", "天机", "武曲", "天府"));
        MAJOR_CONVERGENCE_RULES.put("武曲", Arrays.asList("紫微", "天机", "太阳", "天府"));
        MAJOR_CONVERGENCE_RULES.put("天同", Arrays.asList("紫微", "天府"));

        // 天府系会合
        MAJOR_CONVERGENCE_RULES.put("天府", Arrays.asList("紫微", "太阴", "贪狼", "巨门"));
        MAJOR_CONVERGENCE_RULES.put("太阴", Arrays.asList("天府", "贪狼", "巨门"));
        MAJOR_CONVERGENCE_RULES.put("贪狼", Arrays.asList("天府", "太阴", "巨门"));
        MAJOR_CONVERGENCE_RULES.put("巨门", Arrays.asList("天府", "太阴", "贪狼"));
    }

    // 主星与辅星会合规则
    private static final Map<String, List<String>> MAJOR_MINOR_CONVERGENCE_RULES = new HashMap<>();
    static {
        // 文昌文曲会合
        MAJOR_MINOR_CONVERGENCE_RULES.put("文昌", Arrays.asList("紫微", "天机", "太阳", "武曲", "天同"));
        MAJOR_MINOR_CONVERGENCE_RULES.put("文曲", Arrays.asList("紫微", "天机", "太阳", "武曲", "天同"));

        // 左辅右弼会合
        MAJOR_MINOR_CONVERGENCE_RULES.put("左辅", Arrays.asList("紫微", "天府"));
        MAJOR_MINOR_CONVERGENCE_RULES.put("右弼", Arrays.asList("紫微", "天府"));
    }

    /**
     * 判断两个星耀的会合类型
     *
     * @param star1 星耀1
     * @param star2 星耀2
     * @param palace1 星耀1所在宫位
     * @param palace2 星耀2所在宫位
     * @return 会合类型
     */
    public static StarConvergenceType calculateConvergenceType(Star star1, Star star2, Palace palace1, Palace palace2) {
        // 检查是否在同一宫位
        if (palace1 == palace2) {
            return StarConvergenceType.SAME_PALACE;
        }

        // 获取宫位关系类型
        PalaceRelationType palaceRelation = PalaceRelationCalculator.getRelationType(palace1, palace2);

        switch (palaceRelation) {
            case OPPOSITE:
                return StarConvergenceType.OPPOSITE_PALACE;
            case TRINE:
                return StarConvergenceType.TRINE_PALACE;
            case SEXTILE:
                return StarConvergenceType.HARMONY_PALACE;
            case SQUARE:
                return StarConvergenceType.CONFLICT_PALACE;
            default:
                return StarConvergenceType.NONE;
        }
    }

    /**
     * 计算星耀会合效果
     *
     * @param star1 星耀1
     * @param star2 星耀2
     * @param palace1 星耀1所在宫位
     * @param palace2 星耀2所在宫位
     * @return 会合效果描述
     */
    public static String calculateConvergenceEffect(Star star1, Star star2, Palace palace1, Palace palace2) {
        StarConvergenceType convergenceType = calculateConvergenceType(star1, star2, palace1, palace2);

        // 检查是否有会合规则
        boolean hasConvergenceRule = false;
        if (star1.getType() == StarType.MAJOR && star2.getType() == StarType.MAJOR) {
            List<String> rules = MAJOR_CONVERGENCE_RULES.get(star1.getName());
            hasConvergenceRule = rules != null && rules.contains(star2.getName());
        } else if (star1.getType() == StarType.MAJOR && star2.getType() == StarType.MINOR) {
            List<String> rules = MAJOR_MINOR_CONVERGENCE_RULES.get(star2.getName());
            hasConvergenceRule = rules != null && rules.contains(star1.getName());
        } else if (star1.getType() == StarType.MINOR && star2.getType() == StarType.MAJOR) {
            List<String> rules = MAJOR_MINOR_CONVERGENCE_RULES.get(star1.getName());
            hasConvergenceRule = rules != null && rules.contains(star2.getName());
        }

        if (!hasConvergenceRule) {
            return String.format("%s与%s无特殊会合效果。", star1.getName(), star2.getName());
        }

        // 根据会合类型和星耀组合返回效果
        return getConvergenceEffect(star1, star2, convergenceType);
    }

    /**
     * 获取会合效果描述
     */
    private static String getConvergenceEffect(Star star1, Star star2, StarConvergenceType convergenceType) {
        String baseEffect = getBaseConvergenceEffect(star1, star2);
        if (baseEffect == null) {
            return String.format("%s与%s%s，无特殊效果。",
                star1.getName(), star2.getName(), convergenceType.getDescription());
        }

        // 根据会合类型调整效果强度
        switch (convergenceType) {
            case SAME_PALACE:
                return String.format("%s与%s同宫，%s，效果最强。",
                    star1.getName(), star2.getName(), baseEffect);
            case OPPOSITE_PALACE:
                return String.format("%s与%s对宫，%s，效果对立。",
                    star1.getName(), star2.getName(), baseEffect);
            case TRINE_PALACE:
                return String.format("%s与%s三合，%s，效果和谐。",
                    star1.getName(), star2.getName(), baseEffect);
            case HARMONY_PALACE:
                return String.format("%s与%s六合，%s，效果温和。",
                    star1.getName(), star2.getName(), baseEffect);
            case CONFLICT_PALACE:
                return String.format("%s与%s相刑，%s，效果受阻。",
                    star1.getName(), star2.getName(), baseEffect);
            default:
                return String.format("%s与%s无特殊会合。", star1.getName(), star2.getName());
        }
    }

    /**
     * 获取基础会合效果
     */
    private static String getBaseConvergenceEffect(Star star1, Star star2) {
        String key = star1.getName() + "-" + star2.getName();
        switch (key) {
            case "紫微-天机":
                return "主智慧超群，思维敏捷";
            case "紫微-太阳":
                return "主权贵显达，名声显赫";
            case "紫微-武曲":
                return "主权力地位，财运亨通";
            case "天机-文昌":
                return "主学识优秀，文才出众";
            case "太阳-文曲":
                return "主声名显达，才华横溢";
            case "天府-太阴":
                return "主富贵安稳，积累丰厚";
            case "贪狼-巨门":
                return "主事业发达，口才出众";
            // ... 其他组合效果
            default:
                return null;
        }
    }

    /**
     * 查找所有星耀会合
     *
     * @param palaces 所有宫位列表
     * @return 会合列表
     */
    public static List<Map<String, Object>> findAllConvergences(List<Palace> palaces) {
        List<Map<String, Object>> convergences = new ArrayList<>();

        // 遍历所有宫位组合
        for (int i = 0; i < palaces.size(); i++) {
            Palace palace1 = palaces.get(i);
            List<Star> stars1 = palace1.getAllStars();

            for (int j = i; j < palaces.size(); j++) {
                Palace palace2 = palaces.get(j);
                List<Star> stars2 = palace2.getAllStars();

                // 检查两个宫位中的星耀会合
                for (Star star1 : stars1) {
                    for (Star star2 : stars2) {
                        // 避免自己和自己比较
                        if (star1 == star2) continue;

                        String effect = calculateConvergenceEffect(star1, star2, palace1, palace2);
                        if (!effect.contains("无特殊")) {
                            Map<String, Object> convergence = new HashMap<>();
                            convergence.put("star1", star1.getName());
                            convergence.put("star2", star2.getName());
                            convergence.put("palace1", palace1.getName());
                            convergence.put("palace2", palace2.getName());
                            convergence.put("type", calculateConvergenceType(star1, star2, palace1, palace2));
                            convergence.put("effect", effect);
                            convergences.add(convergence);
                        }
                    }
                }
            }
        }

        return convergences;
    }

    private static int calculateBrightnessEffect(Star star) {
        Brightness Brightness = (star.getBrightness());
        switch (Brightness) {
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
}
