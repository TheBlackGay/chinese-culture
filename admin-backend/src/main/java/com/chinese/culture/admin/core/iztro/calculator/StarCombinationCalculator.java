package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;

import java.util.*;

/**
 * 星耀组合效果判断器
 */
public class StarCombinationCalculator {
    
    // 吉星组合规则
    private static final Map<String, List<String>> LUCKY_COMBINATIONS = new HashMap<>();
    static {
        // 紫微系组合
        LUCKY_COMBINATIONS.put("紫微", Arrays.asList("天机", "太阳", "武曲", "天同", "文昌", "文曲", "左辅", "右弼"));
        LUCKY_COMBINATIONS.put("天机", Arrays.asList("紫微", "太阳", "武曲", "文昌", "文曲"));
        LUCKY_COMBINATIONS.put("太阳", Arrays.asList("紫微", "天机", "武曲", "文昌", "文曲"));
        LUCKY_COMBINATIONS.put("武曲", Arrays.asList("紫微", "天机", "太阳", "文昌", "文曲"));
        LUCKY_COMBINATIONS.put("天同", Arrays.asList("紫微", "文昌", "文曲"));
        
        // 天府系组合
        LUCKY_COMBINATIONS.put("天府", Arrays.asList("太阴", "贪狼", "巨门", "文昌", "文曲", "左辅", "右弼"));
        LUCKY_COMBINATIONS.put("太阴", Arrays.asList("天府", "贪狼", "巨门", "文昌", "文曲"));
        LUCKY_COMBINATIONS.put("贪狼", Arrays.asList("天府", "太阴", "巨门", "文昌", "文曲"));
        LUCKY_COMBINATIONS.put("巨门", Arrays.asList("天府", "太阴", "贪狼", "文昌", "文曲"));
    }
    
    // 凶星组合规则
    private static final Map<String, List<String>> UNLUCKY_COMBINATIONS = new HashMap<>();
    static {
        // 火铃组合
        UNLUCKY_COMBINATIONS.put("火星", Arrays.asList("铃星", "地空", "地劫"));
        UNLUCKY_COMBINATIONS.put("铃星", Arrays.asList("火星", "地空", "地劫"));
        
        // 空劫组合
        UNLUCKY_COMBINATIONS.put("地空", Arrays.asList("火星", "铃星", "地劫"));
        UNLUCKY_COMBINATIONS.put("地劫", Arrays.asList("火星", "铃星", "地空"));
    }
    
    /**
     * 判断星耀组合效果
     * 
     * @param palace 宫位
     * @return 组合效果描述
     */
    public static String calculateCombinationEffect(Palace palace) {
        List<Star> allStars = palace.getAllStars();
        if (allStars.isEmpty()) {
            return "无主要星耀";
        }
        
        StringBuilder effect = new StringBuilder();
        
        // 检查吉星组合
        for (Star star : allStars) {
            List<String> luckyPartners = LUCKY_COMBINATIONS.get(star.getName());
            if (luckyPartners != null) {
                for (String partner : luckyPartners) {
                    if (palace.hasStar(partner)) {
                        effect.append(star.getName()).append("与").append(partner)
                              .append("同宫，主").append(getLuckyCombinationEffect(star.getName(), partner))
                              .append("；");
                    }
                }
            }
        }
        
        // 检查凶星组合
        for (Star star : allStars) {
            List<String> unluckyPartners = UNLUCKY_COMBINATIONS.get(star.getName());
            if (unluckyPartners != null) {
                for (String partner : unluckyPartners) {
                    if (palace.hasStar(partner)) {
                        effect.append(star.getName()).append("与").append(partner)
                              .append("同宫，主").append(getUnluckyCombinationEffect(star.getName(), partner))
                              .append("；");
                    }
                }
            }
        }
        
        // 检查四化组合
        for (Star star : allStars) {
            for (Mutagen mutagen : star.getMutagens()) {
                effect.append(star.getName()).append(mutagen.getDescription())
                      .append("，主").append(getMutagenEffect(star.getName(), mutagen))
                      .append("；");
            }
        }
        
        // 检查星耀亮度
        for (Star star : allStars) {
            Brightness brightness = star.getBrightness();
            if (brightness == Brightness.TEMPLE || brightness == Brightness.TRAPPED) {
                effect.append(star.getName()).append(brightness.getDescription())
                      .append("，主").append(getBrightnessEffect(star.getName(), brightness))
                      .append("；");
            }
        }
        
        return effect.length() > 0 ? effect.toString() : "无特殊组合效果";
    }
    
    /**
     * 获取吉星组合效果
     */
    private static String getLuckyCombinationEffect(String star1, String star2) {
        switch (star1 + "-" + star2) {
            case "紫微-天机":
                return "智慧超群，思维敏捷";
            case "紫微-太阳":
                return "地位尊贵，名声显赫";
            case "紫微-武曲":
                return "权力地位，财运亨通";
            case "天机-文昌":
                return "学识优秀，文才出众";
            case "太阳-文曲":
                return "声名显达，才华横溢";
            // ... 其他组合效果
            default:
                return "吉星相会，主吉利";
        }
    }
    
    /**
     * 获取凶星组合效果
     */
    private static String getUnluckyCombinationEffect(String star1, String star2) {
        switch (star1 + "-" + star2) {
            case "火星-铃星":
                return "易有意外灾祸";
            case "地空-地劫":
                return "易有损失破财";
            // ... 其他组合效果
            default:
                return "凶星相会，主不利";
        }
    }
    
    /**
     * 获取四化效果
     */
    private static String getMutagenEffect(String star, Mutagen mutagen) {
        switch (mutagen) {
            case LUCKY:
                return "禄权显达，财运亨通";
            case POWER:
                return "权力地位，能力出众";
            case SKILL:
                return "学识才华，技能专长";
            case WEAK:
                return "阻滞不利，损耗破财";
            default:
                return "四化影响";
        }
    }
    
    /**
     * 获取亮度效果
     */
    private static String getBrightnessEffect(String star, Brightness brightness) {
        switch (brightness) {
            case TEMPLE:
                return "星耀最旺，发挥最佳";
            case TRAPPED:
                return "星耀失势，效果受限";
            default:
                return "一般表现";
        }
    }

    private static int calculateBrightnessEffect(Star star1, Star star2) {
        Brightness brightness1 = (star1.getBrightness());
        Brightness brightness2 = (star2.getBrightness());
        
        if (brightness1 == Brightness.TEMPLE && brightness2 == Brightness.TEMPLE) {
            return 10;
        } else if (brightness1 == Brightness.GAIN || brightness2 == Brightness.GAIN) {
            return -5;
        } else if (brightness1 == Brightness.TRAPPED || brightness2 == Brightness.TRAPPED) {
            return -10;
        }
        return 0;
    }
} 
