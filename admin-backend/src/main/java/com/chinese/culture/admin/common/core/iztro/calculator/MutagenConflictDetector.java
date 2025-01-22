package com.chinese.culture.admin.common.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import java.util.*;

/**
 * 四化冲突检测器
 */
public class MutagenConflictDetector {

    // 四化冲突规则
    private static final Map<String, List<String>> CONFLICT_RULES = new HashMap<>();
    static {
        // 禄化冲突规则
        CONFLICT_RULES.put("禄", Arrays.asList("忌", "权", "科"));

        // 权化冲突规则
        CONFLICT_RULES.put("权", Arrays.asList("忌", "禄"));

        // 科化冲突规则
        CONFLICT_RULES.put("科", Arrays.asList("忌", "禄"));

        // 忌化冲突规则
        CONFLICT_RULES.put("忌", Arrays.asList("禄", "权", "科"));
    }

    /**
     * 检测星耀的四化冲突
     *
     * @param star 待检测的星耀
     * @param palace 所在宫位
     * @return 冲突检测结果
     */
    public static Map<String, Object> detectConflicts(StarBO star, PalaceBO palace) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> conflicts = new ArrayList<>();

        // 获取星耀的四化
        List<Mutagen> mutagens = star.getMutagenInfo().getMutagens();
        if (mutagens.isEmpty()) {
            result.put("hasConflict", false);
            result.put("conflicts", conflicts);
            return result;
        }

        // 检查同宫其他星耀的四化
        List<StarBO> otherStars = palace.getAllStars().stream()
            .filter(s -> !s.equals(star))
            .collect(java.util.stream.Collectors.toList());

        for (StarBO otherStar : otherStars) {
            List<Mutagen> otherMutagens = otherStar.getMutagenInfo().getMutagens();
            if (!otherMutagens.isEmpty()) {
                // 检查四化冲突
                for (Mutagen mutagen : mutagens) {
                    for (Mutagen otherMutagen : otherMutagens) {
                        if (isConflict(mutagen, otherMutagen)) {
                            Map<String, Object> conflict = new HashMap<>();
                            conflict.put("star1", star.getName());
                            conflict.put("mutagen1", mutagen);
                            conflict.put("star2", otherStar.getName());
                            conflict.put("mutagen2", otherMutagen);
                            conflict.put("description", generateConflictDescription(star, mutagen, otherStar, otherMutagen));
                            conflicts.add(conflict);
                        }
                    }
                }
            }
        }

        result.put("hasConflict", !conflicts.isEmpty());
        result.put("conflicts", conflicts);
        return result;
    }

    /**
     * 检测宫位内的所有四化冲突
     *
     * @param palace 宫位
     * @return 冲突检测结果
     */
    public static Map<String, Object> detectPalaceConflicts(PalaceBO palace) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> allConflicts = new ArrayList<>();

        List<StarBO> stars = palace.getAllStars();
        for (StarBO star : stars) {
            Map<String, Object> starConflicts = detectConflicts(star, palace);
            if ((boolean) starConflicts.get("hasConflict")) {
                allConflicts.addAll((List<Map<String, Object>>) starConflicts.get("conflicts"));
            }
        }

        result.put("hasConflict", !allConflicts.isEmpty());
        result.put("conflicts", allConflicts);
        result.put("palaceName", palace.getName());
        return result;
    }

    /**
     * 判断两个四化是否冲突
     *
     * @param mutagen1 四化1
     * @param mutagen2 四化2
     * @return 是否冲突
     */
    private static boolean isConflict(Mutagen mutagen1, Mutagen mutagen2) {
        String key = mutagen1.getDescription();
        List<String> conflicts = CONFLICT_RULES.get(key);
        return conflicts != null && conflicts.contains(mutagen2.getDescription());
    }

    /**
     * 生成冲突描述
     */
    private static String generateConflictDescription(StarBO star1, Mutagen mutagen1,
                                                      StarBO star2, Mutagen mutagen2) {
        return String.format("%s化%s与%s化%s相冲，不利于星耀效果的发挥",
            star1.getName(), mutagen1.getDescription(),
            star2.getName(), mutagen2.getDescription());
    }

    /**
     * 获取四化冲突的严重程度
     *
     * @param mutagen1 四化1
     * @param mutagen2 四化2
     * @return 严重程度(0-100)
     */
    public static int getConflictSeverity(Mutagen mutagen1, Mutagen mutagen2) {
        // 禄化与忌化的冲突最为严重
        if ((mutagen1 == Mutagen.LU && mutagen2 == Mutagen.JI) ||
            (mutagen1 == Mutagen.JI && mutagen2 == Mutagen.LU)) {
            return 100;
        }

        // 权化与忌化的冲突次之
        if ((mutagen1 == Mutagen.QUAN && mutagen2 == Mutagen.JI) ||
            (mutagen1 == Mutagen.JI && mutagen2 == Mutagen.QUAN)) {
            return 80;
        }

        // 科化与忌化的冲突再次
        if ((mutagen1 == Mutagen.KE && mutagen2 == Mutagen.JI) ||
            (mutagen1 == Mutagen.JI && mutagen2 == Mutagen.KE)) {
            return 60;
        }

        // 其他冲突
        return 40;
    }

    /**
     * 获取化解建议
     *
     * @param mutagen1 四化1
     * @param mutagen2 四化2
     * @return 化解建议
     */
    public static String getResolutionAdvice(Mutagen mutagen1, Mutagen mutagen2) {
        if ((mutagen1 == Mutagen.LU && mutagen2 == Mutagen.JI) ||
            (mutagen1 == Mutagen.JI && mutagen2 == Mutagen.LU)) {
            return "禄化与忌化相冲，宜谨慎行事，避免冒进，以化解冲突";
        }

        if ((mutagen1 == Mutagen.QUAN && mutagen2 == Mutagen.JI) ||
            (mutagen1 == Mutagen.JI && mutagen2 == Mutagen.QUAN)) {
            return "权化与忌化相冲，宜韬光养晦，避免锋芒太露";
        }

        if ((mutagen1 == Mutagen.KE && mutagen2 == Mutagen.JI) ||
            (mutagen1 == Mutagen.JI && mutagen2 == Mutagen.KE)) {
            return "科化与忌化相冲，宜稳扎稳打，循序渐进";
        }

        return "四化相冲，宜谨慎行事，趋吉避凶";
    }
}
