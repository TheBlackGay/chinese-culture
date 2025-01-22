package com.chinese.culture.admin.common.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.HoroscopePattern;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;

import java.util.*;

import static com.chinese.culture.admin.common.core.iztro.data.enums.StarName.*;

/**
 * 命盘格局判断器
 */
public class HoroscopePatternCalculator {

    /**
     * 判断命盘格局
     *
     * @param palaces 所有宫位列表
     * @return 格局列表及其解释
     */
    public static List<Map<String, Object>> calculatePatterns(List<PalaceBO> palaces) {
        List<Map<String, Object>> patterns = new ArrayList<>();

        // 获取命宫
        PalaceBO mingGong = findMingGong(palaces);
        if (mingGong == null) {
            return patterns;
        }

        // 检查命宫格局
        checkMingGongPattern(mingGong, patterns);

        // 检查科权禄全格
        checkFullMutagenPattern(palaces, patterns);

        // 检查三奇格
        checkThreeNoblePattern(palaces, patterns);

        // 检查四煞格
        checkFourEvilPattern(palaces, patterns);

        // 检查红艳格
        checkRedBeautyPattern(palaces, patterns);

        // 检查富贵格
        checkWealthNoblePattern(palaces, patterns);

        // 如果没有任何特殊格局，则为普通格局
        if (patterns.isEmpty()) {
            Map<String, Object> pattern = new HashMap<>();
            pattern.put("pattern", HoroscopePattern.NORMAL);
            pattern.put("description", "命盘为普通格局，无特殊格局组合。");
            patterns.add(pattern);
        }

        return patterns;
    }

    /**
     * 查找命宫
     */
    private static PalaceBO findMingGong(List<PalaceBO> palaces) {
        return palaces.stream()
            .filter(p -> "命宫".equals(p.getName()))
            .findFirst()
            .orElse(null);
    }

    /**
     * 检查命宫格局
     */
    private static void checkMingGongPattern(PalaceBO mingGong, List<Map<String, Object>> patterns) {
        // 检查命宫是否有主星
        boolean hasMajorStar = false;
        for (StarBO star : mingGong.getMajorStars()) {
            hasMajorStar = true;
            switch (star.getName()) {
                case ZIWEI:
                    addPattern(patterns, HoroscopePattern.ZIWEI_MING,
                        "紫微星入命，主一生尊贵，具领导才能。");
                    break;
                case TIANFU:
                    addPattern(patterns, HoroscopePattern.TIANFU_MING,
                        "天府星入命，主一生富贵安稳，财运亨通。");
                    break;
            }
        }

        // 检查命宫辅星
        for (StarBO star : mingGong.getMinorStars()) {
            if (star.getName() == LUCUN) {
                addPattern(patterns, HoroscopePattern.LUCUN_MING,
                    "禄存星入命，主一生衣禄无忧，官运亨通。");
            } else if (star.getName() == WENCHANG) {
                addPattern(patterns, HoroscopePattern.WENCHANG_MING,
                    "文昌星入命，主一生文章显达，学识优秀。");
            }
        }

        // 检查破格
        if (!hasMajorStar) {
            addPattern(patterns, HoroscopePattern.BROKEN,
                "命宫无主星入主，为破格，主一生起伏较大。");
        }
    }

    /**
     * 检查科权禄全格
     */
    private static void checkFullMutagenPattern(List<PalaceBO> palaces, List<Map<String, Object>> patterns) {
        boolean hasKe = false;
        boolean hasQuan = false;
        boolean hasLu = false;

        // 检查重要宫位（命宫、身宫、财帛、官禄）的星耀四化
        for (PalaceBO palace : palaces) {
            if (isImportantPalace(palace)) {
                for (StarBO star : palace.getAllStars()) {
                    for (Mutagen mutagen : star.getMutagenInfo().getMutagens()) {
                        switch (mutagen) {
                            case KE:
                                hasKe = true;
                                break;
                            case QUAN:
                                hasQuan = true;
                                break;
                            case LU:
                                hasLu = true;
                                break;
                        }
                    }
                }
            }
        }

        if (hasKe && hasQuan && hasLu) {
            addPattern(patterns, HoroscopePattern.FULL_MUTAGEN,
                "命盘中科、权、禄三颗星齐聚重要宫位，为科权禄全格，主一生功名利禄双收。");
        }
    }

    /**
     * 检查三奇格
     */
    private static void checkThreeNoblePattern(List<PalaceBO> palaces, List<Map<String, Object>> patterns) {
        for (PalaceBO palace : palaces) {
            int nobleCount = 0;
            for (StarBO star : palace.getMinorStars()) {
                if (isNobleMinorStar(star.getName())) {
                    nobleCount++;
                }
            }

            if (nobleCount >= 3) {
                addPattern(patterns, HoroscopePattern.THREE_NOBLE,
                    String.format("%s聚集三颗以上奇星，为三奇格，主一生才智超群，声名显达。", palace.getName()));
                break;
            }
        }
    }

    /**
     * 检查四煞格
     */
    private static void checkFourEvilPattern(List<PalaceBO> palaces, List<Map<String, Object>> patterns) {
        for (PalaceBO palace : palaces) {
            int evilCount = 0;
            for (StarBO star : palace.getAdjectiveStars()) {
                if (isEvilStar(star.getName())) {
                    evilCount++;
                }
            }

            if (evilCount >= 4) {
                addPattern(patterns, HoroscopePattern.FOUR_EVIL,
                    String.format("%s聚集四煞星，为四煞格，主一生波折较多，需谨慎行事。", palace.getName()));
                break;
            }
        }
    }

    /**
     * 检查红艳格
     */
    private static void checkRedBeautyPattern(List<PalaceBO> palaces, List<Map<String, Object>> patterns) {
        for (PalaceBO palace : palaces) {
            int beautyCount = 0;
            for (StarBO star : palace.getMajorStars()) {
                if (isBeautyStar(star.getName())) {
                    beautyCount++;
                }
            }

            if (beautyCount >= 3) {
                addPattern(patterns, HoroscopePattern.RED_BEAUTY,
                    String.format("%s聚集三颗以上红艳星，为红艳格，主一生桃花运旺，人缘极好。", palace.getName()));
                break;
            }
        }
    }

    /**
     * 检查富贵格
     */
    private static void checkWealthNoblePattern(List<PalaceBO> palaces, List<Map<String, Object>> patterns) {
        for (PalaceBO palace : palaces) {
            int nobleCount = 0;
            for (StarBO star : palace.getMajorStars()) {
                if (isWealthNobleStar(star.getName())) {
                    nobleCount++;
                }
            }

            if (nobleCount >= 3) {
                addPattern(patterns, HoroscopePattern.WEALTH_NOBLE,
                    String.format("%s聚集三颗以上富贵星，为富贵格，主一生富贵双全，财运亨通。", palace.getName()));
                break;
            }
        }
    }

    /**
     * 添加格局
     */
    private static void addPattern(List<Map<String, Object>> patterns, HoroscopePattern pattern, String description) {
        Map<String, Object> patternMap = new HashMap<>();
        patternMap.put("pattern", pattern);
        patternMap.put("description", description);
        patterns.add(patternMap);
    }

    /**
     * 判断是否为重要宫位
     */
    private static boolean isImportantPalace(PalaceBO palace) {
        return "命宫".equals(palace.getName()) ||
               "身宫".equals(palace.getName()) ||
               "财帛".equals(palace.getName()) ||
               "官禄".equals(palace.getName());
    }

    /**
     * 判断是否为奇星
     */
    private static boolean isNobleMinorStar(StarName starName) {
        return starName == WENCHANG ||
               starName == WENQU ||
               starName == ZUOFU ||
               starName == YOUBI;
    }

    /**
     * 判断是否为煞星
     */
    private static boolean isEvilStar(StarName starName) {
        return starName == HUOXING ||
               starName == LINGXING ||
               starName == DIKONG ||
               starName == DIJIE;
    }

    /**
     * 判断是否为红艳星
     */
    private static boolean isBeautyStar(StarName starName) {
        return starName == TAIYANG ||
               starName == TAIYIN ||
               starName == TIANTONG ||
               starName == TIANJI;
    }

    /**
     * 判断是否为富贵星
     */
    private static boolean isWealthNobleStar(StarName starName) {
        return starName == ZIWEI ||
               starName == TIANFU ||
               starName == WUQU ||
               starName == TANLANG;
    }
}
