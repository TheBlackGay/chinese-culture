package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;

import java.util.*;

import org.springframework.stereotype.Component;

/**
 * 命盘格局分析器
 */
@Component
public class HoroscopePatternAnalyzer {

    /**
     * 判定命盘格局
     *
     * @param palaces 宫位列表
     * @return 格局判定结果
     */
    public static Map<String, Object> judgePattern(List<Palace> palaces) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> patterns = new ArrayList<>();

        // 1. 检查紫微命格
        checkZiweiPattern(palaces, patterns);

        // 2. 检查天府命格
        checkTianfuPattern(palaces, patterns);

        // 3. 检查禄存命格
        checkLucunPattern(palaces, patterns);

        // 4. 检查文昌命格
        checkWenchangPattern(palaces, patterns);

        // 5. 检查科权禄全格
        checkFullMutagenPattern(palaces, patterns);

        // 6. 检查三奇贵人格
        checkThreeNoblePattern(palaces, patterns);

        // 7. 检查四煞聚会格
        checkFourEvilPattern(palaces, patterns);

        // 8. 检查红艳格
        checkRedBeautyPattern(palaces, patterns);

        // 9. 检查富贵格
        checkWealthNoblePattern(palaces, patterns);

        // 10. 检查破格
        checkBrokenPattern(palaces, patterns);

        // 生成总体评价
        String analysis = generateOverallAnalysis(patterns);

        result.put("patterns", patterns);
        result.put("analysis", analysis);
        return result;
    }

    /**
     * 检查紫微命格
     */
    private static void checkZiweiPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasStarByName(mingGong, "紫微")) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "紫微命格");
            pattern.put("level", getZiweiPatternLevel(mingGong));
            pattern.put("description", "紫微星入命，为最贵重之格");
            pattern.put("stars", Arrays.asList("紫微"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查天府命格
     */
    private static void checkTianfuPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasStarByName(mingGong, "天府")) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "天府命格");
            pattern.put("level", getTianfuPatternLevel(mingGong));
            pattern.put("description", "天府星入命，主富贵安稳");
            pattern.put("stars", Arrays.asList("天府"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查禄存命格
     */
    private static void checkLucunPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasStarByName(mingGong, "禄存")) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "禄存命格");
            pattern.put("level", getLucunPatternLevel(mingGong));
            pattern.put("description", "禄存星入命，主官禄显达");
            pattern.put("stars", Arrays.asList("禄存"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查文昌命格
     */
    private static void checkWenchangPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && (hasStarByName(mingGong, "文昌") || hasStarByName(mingGong, "文曲"))) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "文昌命格");
            pattern.put("level", getWenchangPatternLevel(mingGong));
            pattern.put("description", "文昌文曲入命，主文章显达");
            pattern.put("stars", Arrays.asList("文昌", "文曲"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查科权禄全格
     */
    private static void checkFullMutagenPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasMutagens(mingGong, Arrays.asList(Mutagen.LUCKY, Mutagen.POWER, Mutagen.SKILL))) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "科权禄全格");
            pattern.put("level", "上格");
            pattern.put("description", "命宫三化俱全，主大贵大富");
            pattern.put("mutagens", Arrays.asList("禄", "权", "科"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查三奇贵人格
     */
    private static void checkThreeNoblePattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasThreeNobleStars(mingGong)) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "三奇贵人格");
            pattern.put("level", "上格");
            pattern.put("description", "三奇星会命，主贵不可言");
            pattern.put("stars", Arrays.asList("左辅", "右弼", "天魁", "天钺"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查四煞聚会格
     */
    private static void checkFourEvilPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasFourEvilStars(mingGong)) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "四煞聚会格");
            pattern.put("level", "下格");
            pattern.put("description", "四煞会命，主波折坎坷");
            pattern.put("stars", Arrays.asList("擎羊", "陀罗", "火星", "铃星"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查红艳格
     */
    private static void checkRedBeautyPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasRedBeautyStars(mingGong)) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "红艳格");
            pattern.put("level", "中格");
            pattern.put("description", "红艳星会命，主人缘桃花");
            pattern.put("stars", Arrays.asList("天魁", "天钺", "红鸾", "天喜"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查富贵格
     */
    private static void checkWealthNoblePattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasWealthNobleStars(mingGong)) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "富贵格");
            pattern.put("level", "上格");
            pattern.put("description", "富贵星会命，主大富大贵");
            pattern.put("stars", Arrays.asList("紫微", "天府", "禄存", "科权"));
            patterns.add(pattern);
        }
    }

    /**
     * 检查破格
     */
    private static void checkBrokenPattern(List<Palace> palaces, List<Map<String, Object>> patterns) {
        Palace mingGong = findMingGong(palaces);
        if (mingGong != null && hasBrokenPattern(mingGong)) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("name", "破格");
            pattern.put("level", "下格");
            pattern.put("description", "命宫失陷，主运途多舛");
            pattern.put("stars", Arrays.asList("火星", "铃星", "地空", "地劫"));
            patterns.add(pattern);
        }
    }

    /**
     * 生成总体分析
     */
    private static String generateOverallAnalysis(List<Map<String, Object>> patterns) {
        if (patterns.isEmpty()) {
            return "命盘无特殊格局，为普通格局。";
        }

        StringBuilder analysis = new StringBuilder();
        int upperCount = 0;
        int middleCount = 0;
        int lowerCount = 0;

        for (Map<String, Object> pattern : patterns) {
            String level = (String) pattern.get("level");
            switch (level) {
                case "上格":
                    upperCount++;
                    break;
                case "中格":
                    middleCount++;
                    break;
                case "下格":
                    lowerCount++;
                    break;
            }
        }

        analysis.append(String.format("命盘共有%d个格局，其中上格%d个，中格%d个，下格%d个。\n",
            patterns.size(), upperCount, middleCount, lowerCount));

        if (upperCount > 0) {
            analysis.append("上格当权，主大富大贵；");
        }
        if (middleCount > 0) {
            analysis.append("中格得中，主平稳发展；");
        }
        if (lowerCount > 0) {
            analysis.append("下格在位，需谨慎行事。");
        }

        return analysis.toString();
    }

    /**
     * 查找命宫
     */
    private static Palace findMingGong(List<Palace> palaces) {
        return palaces.stream()
            .filter(p -> p.getName().equals("命宫"))
            .findFirst()
            .orElse(null);
    }

    /**
     * 判断宫位是否包含指定星耀
     */
    private static boolean hasStarByName(Palace palace, String starName) {
        return palace.getAllStars().stream()
            .anyMatch(s -> s.getName().equals(starName));
    }

    /**
     * 判断宫位是否包含指定四化
     */
    private static boolean hasMutagens(Palace palace, List<Mutagen> mutagens) {
        Set<Mutagen> palaceMutagens = new HashSet<>();
        for (Star star : palace.getAllStars()) {
            palaceMutagens.addAll(star.getMutagens());
        }
        return palaceMutagens.containsAll(mutagens);
    }

    /**
     * 判断是否有三奇贵人星
     */
    private static boolean hasThreeNobleStars(Palace palace) {
        List<String> nobleStars = Arrays.asList("左辅", "右弼", "天魁", "天钺");
        int count = 0;
        for (Star star : palace.getAllStars()) {
            if (nobleStars.contains(star.getName())) {
                count++;
            }
        }
        return count >= 3;
    }

    /**
     * 判断是否有四煞星
     */
    private static boolean hasFourEvilStars(Palace palace) {
        List<String> evilStars = Arrays.asList("擎羊", "陀罗", "火星", "铃星");
        int count = 0;
        for (Star star : palace.getAllStars()) {
            if (evilStars.contains(star.getName())) {
                count++;
            }
        }
        return count >= 3;
    }

    /**
     * 判断是否有红艳星
     */
    private static boolean hasRedBeautyStars(Palace palace) {
        List<String> beautyStars = Arrays.asList("天魁", "天钺", "红鸾", "天喜");
        int count = 0;
        for (Star star : palace.getAllStars()) {
            if (beautyStars.contains(star.getName())) {
                count++;
            }
        }
        return count >= 2;
    }

    /**
     * 判断是否有富贵星
     */
    private static boolean hasWealthNobleStars(Palace palace) {
        List<String> wealthStars = Arrays.asList("紫微", "天府", "禄存");
        int count = 0;
        for (Star star : palace.getAllStars()) {
            if (wealthStars.contains(star.getName())) {
                count++;
            }
        }
        return count >= 2;
    }

    /**
     * 判断是否为破格
     */
    private static boolean hasBrokenPattern(Palace palace) {
        List<String> brokenStars = Arrays.asList("火星", "铃星", "地空", "地劫");
        int count = 0;
        for (Star star : palace.getAllStars()) {
            if (brokenStars.contains(star.getName())) {
                count++;
            }
        }
        return count >= 3;
    }

    /**
     * 获取紫微命格等级
     */
    private static String getZiweiPatternLevel(Palace palace) {
        Star ziwei = palace.getAllStars().stream()
            .filter(s -> s.getName().equals("紫微"))
            .findFirst()
            .orElse(null);

        if (ziwei != null) {
            if (BrightnessCalculator.isBright(ziwei, palace.getBranch())) {
                return "上格";
            } else if (BrightnessCalculator.isDead(ziwei, palace.getBranch())) {
                return "下格";
            }
        }
        return "中格";
    }

    /**
     * 获取天府命格等级
     */
    private static String getTianfuPatternLevel(Palace palace) {
        Star tianfu = palace.getAllStars().stream()
            .filter(s -> s.getName().equals("天府"))
            .findFirst()
            .orElse(null);

        if (tianfu != null) {
            if (BrightnessCalculator.isBright(tianfu, palace.getBranch())) {
                return "上格";
            } else if (BrightnessCalculator.isDead(tianfu, palace.getBranch())) {
                return "下格";
            }
        }
        return "中格";
    }

    /**
     * 获取禄存命格等级
     */
    private static String getLucunPatternLevel(Palace palace) {
        Star lucun = palace.getAllStars().stream()
            .filter(s -> s.getName().equals("禄存"))
            .findFirst()
            .orElse(null);

        if (lucun != null && !lucun.getMutagens().isEmpty()) {
            return "上格";
        }
        return "中格";
    }

    /**
     * 获取文昌命格等级
     */
    private static String getWenchangPatternLevel(Palace palace) {
        boolean hasWenchang = hasStarByName(palace, "文昌");
        boolean hasWenqu = hasStarByName(palace, "文曲");

        if (hasWenchang && hasWenqu) {
            return "上格";
        } else if (hasWenchang || hasWenqu) {
            return "中格";
        }
        return "下格";
    }
}
