package com.chinese.culture.admin.common.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;

import java.util.*;

/**
 * 星耀组合效果判断器
 */
public class StarCombinationCalculator {

    // 吉星组合规则
    private static final Map<StarName, List<StarName>> LUCKY_COMBINATIONS = new HashMap<>();
    static {
        // 紫微系组合
        LUCKY_COMBINATIONS.put(StarName.ZIWEI, Arrays.asList(
            StarName.TIANJI, StarName.TAIYANG, StarName.WUQU, StarName.TIANTONG,
            StarName.WENCHANG, StarName.WENQU, StarName.ZUOFU, StarName.YOUBI));
        LUCKY_COMBINATIONS.put(StarName.TIANJI, Arrays.asList(
            StarName.ZIWEI, StarName.TAIYANG, StarName.WUQU, StarName.WENCHANG, StarName.WENQU));
        LUCKY_COMBINATIONS.put(StarName.TAIYANG, Arrays.asList(
            StarName.ZIWEI, StarName.TIANJI, StarName.WUQU, StarName.WENCHANG, StarName.WENQU));
        LUCKY_COMBINATIONS.put(StarName.WUQU, Arrays.asList(
            StarName.ZIWEI, StarName.TIANJI, StarName.TAIYANG, StarName.WENCHANG, StarName.WENQU));
        LUCKY_COMBINATIONS.put(StarName.TIANTONG, Arrays.asList(
            StarName.ZIWEI, StarName.WENCHANG, StarName.WENQU));

        // 天府系组合
        LUCKY_COMBINATIONS.put(StarName.TIANFU, Arrays.asList(
            StarName.TAIYIN, StarName.TANLANG, StarName.JUMEN, StarName.WENCHANG,
            StarName.WENQU, StarName.ZUOFU, StarName.YOUBI));
        LUCKY_COMBINATIONS.put(StarName.TAIYIN, Arrays.asList(
            StarName.TIANFU, StarName.TANLANG, StarName.JUMEN, StarName.WENCHANG, StarName.WENQU));
        LUCKY_COMBINATIONS.put(StarName.TANLANG, Arrays.asList(
            StarName.TIANFU, StarName.TAIYIN, StarName.JUMEN, StarName.WENCHANG, StarName.WENQU));
        LUCKY_COMBINATIONS.put(StarName.JUMEN, Arrays.asList(
            StarName.TIANFU, StarName.TAIYIN, StarName.TANLANG, StarName.WENCHANG, StarName.WENQU));
    }

    // 凶星组合规则
    private static final Map<StarName, List<StarName>> UNLUCKY_COMBINATIONS = new HashMap<>();
    static {
        // 火铃组合
        UNLUCKY_COMBINATIONS.put(StarName.HUOXING, Arrays.asList(
            StarName.LINGXING, StarName.DIKONG, StarName.DIJIE));
        UNLUCKY_COMBINATIONS.put(StarName.LINGXING, Arrays.asList(
            StarName.HUOXING, StarName.DIKONG, StarName.DIJIE));

        // 空劫组合
        UNLUCKY_COMBINATIONS.put(StarName.DIKONG, Arrays.asList(
            StarName.HUOXING, StarName.LINGXING, StarName.DIJIE));
        UNLUCKY_COMBINATIONS.put(StarName.DIJIE, Arrays.asList(
            StarName.HUOXING, StarName.LINGXING, StarName.DIKONG));
    }

    /**
     * 判断星耀组合效果
     *
     * @param palace 宫位
     * @return 组合效果描述
     */
    public static String calculateCombinationEffect(PalaceBO palace) {
        List<StarBO> allStars = palace.getAllStars();
        if (allStars.isEmpty()) {
            return "无主要星耀";
        }

        StringBuilder effect = new StringBuilder();

        // 检查吉星组合
        for (StarBO star : allStars) {
            List<StarName> luckyPartners = LUCKY_COMBINATIONS.get(star.getName());
            if (luckyPartners != null) {
                for (StarName partner : luckyPartners) {
                    if (palace.hasStar(partner.getDescription())) {
                        effect.append(star.getName().getDescription()).append("与")
                              .append(partner.getDescription())
                              .append("同宫，主")
                              .append(getLuckyCombinationEffect(star.getName(), partner))
                              .append("；");
                    }
                }
            }
        }

        // 检查凶星组合
        for (StarBO star : allStars) {
            List<StarName> unluckyPartners = UNLUCKY_COMBINATIONS.get(star.getName());
            if (unluckyPartners != null) {
                for (StarName partner : unluckyPartners) {
                    if (palace.hasStar(partner.getDescription())) {
                        effect.append(star.getName().getDescription()).append("与")
                              .append(partner.getDescription())
                              .append("同宫，主")
                              .append(getUnluckyCombinationEffect(star.getName(), partner))
                              .append("；");
                    }
                }
            }
        }

        // 检查四化组合
        for (StarBO star : allStars) {
            for (Mutagen mutagen : star.getMutagenInfo().getMutagens()) {
                effect.append(star.getName().getDescription())
                      .append(mutagen.getDescription())
                      .append("，主")
                      .append(getMutagenEffect(star.getName(), mutagen))
                      .append("；");
            }
        }

        // 检查星耀亮度
        for (StarBO star : allStars) {
            Brightness brightness = star.getBrightnessInfo().getBrightness();
            if (brightness == Brightness.MIAO || brightness == Brightness.XIAN) {
                effect.append(star.getName().getDescription())
                      .append(brightness.getDescription())
                      .append("，主")
                      .append(getBrightnessEffect(star.getName(), brightness))
                      .append("；");
            }
        }

        return effect.length() > 0 ? effect.toString() : "无特殊组合效果";
    }

    /**
     * 获取吉星组合效果
     */
    private static String getLuckyCombinationEffect(StarName star1, StarName star2) {
        String key = star1.name() + "-" + star2.name();
        switch (key) {
            case "ZIWEI-TIANJI":
                return "智慧超群，思维敏捷";
            case "ZIWEI-TAIYANG":
                return "地位尊贵，名声显赫";
            case "ZIWEI-WUQU":
                return "权力地位，财运亨通";
            case "TIANJI-WENCHANG":
                return "学识优秀，文才出众";
            case "TAIYANG-WENQU":
                return "声名显达，才华横溢";
            default:
                return "吉星相会，主吉利";
        }
    }

    /**
     * 获取凶星组合效果
     */
    private static String getUnluckyCombinationEffect(StarName star1, StarName star2) {
        String key = star1.name() + "-" + star2.name();
        switch (key) {
            case "HUOXING-LINGXING":
                return "易有意外灾祸";
            case "DIANKONG-DIANJI":
                return "易有损失破财";
            default:
                return "凶星相会，主不利";
        }
    }

    /**
     * 获取四化效果
     */
    private static String getMutagenEffect(StarName star, Mutagen mutagen) {
        switch (mutagen) {
            case LU:
                return "禄权显达，财运亨通";
            case QUAN:
                return "权力地位，能力出众";
            case KE:
                return "学识才华，技能专长";
            case JI:
                return "阻滞不利，损耗破财";
            default:
                return "四化影响";
        }
    }

    /**
     * 获取亮度效果
     */
    private static String getBrightnessEffect(StarName star, Brightness brightness) {
        switch (brightness) {
            case MIAO:
                return "星耀最旺，发挥最佳";
            case XIAN:
                return "星耀失势，效果受限";
            default:
                return "一般表现";
        }
    }

    private static int calculateBrightnessEffect(StarBO star1, StarBO star2) {

        Brightness brightness1 = (star1.getBrightnessInfo().getBrightness());
        Brightness brightness2 = (star2.getBrightnessInfo().getBrightness());

        if (brightness1 == Brightness.MIAO && brightness2 == Brightness.MIAO) {
            return 10;
        } else if (brightness1 == Brightness.DE || brightness2 == Brightness.DE) {
            return -5;
        } else if (brightness1 == Brightness.XIAN || brightness2 == Brightness.XIAN) {
            return -10;
        }
        return 0;
    }
}
