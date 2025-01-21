//package com.chinese.culture.admin.core.iztro.calculator;
//
//import com.chinese.culture.admin.core.iztro.data.Star;
//import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
//import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
//import java.util.*;
//
///**
// * 星耀亮度计算器
// */
//public class BrightnessCalculator {
//
//    // 紫微系星耀亮度规则
//    private static final Map<String, Map<EarthlyBranch, Brightness>> ZIWEI_BRIGHTNESS_RULES = new HashMap<>();
//    static {
//        // 紫微星
//        Map<EarthlyBranch, Brightness> ziweiRules = new HashMap<>();
//        ziweiRules.put(EarthlyBranch.SHEN, Brightness.BRIGHT);  // 申宫
//        ziweiRules.put(EarthlyBranch.CHEN, Brightness.BRIGHT);  // 辰宫
//        ziweiRules.put(EarthlyBranch.ZI, Brightness.NORMAL);    // 子宫
//        ziweiRules.put(EarthlyBranch.WU, Brightness.WEAK);      // 午宫
//        ZIWEI_BRIGHTNESS_RULES.put("紫微", ziweiRules);
//
//        // 天机星
//        Map<EarthlyBranch, Brightness> tianjiRules = new HashMap<>();
//        tianjiRules.put(EarthlyBranch.SHEN, Brightness.BRIGHT); // 申宫
//        tianjiRules.put(EarthlyBranch.ZI, Brightness.BRIGHT);   // 子宫
//        tianjiRules.put(EarthlyBranch.WU, Brightness.NORMAL);   // 午宫
//        tianjiRules.put(EarthlyBranch.CHEN, Brightness.WEAK);   // 辰宫
//        ZIWEI_BRIGHTNESS_RULES.put("天机", tianjiRules);
//    }
//
//    // 天府系星耀亮度规则
//    private static final Map<String, Map<EarthlyBranch, Brightness>> TIANFU_BRIGHTNESS_RULES = new HashMap<>();
//    static {
//        // 天府星
//        Map<EarthlyBranch, Brightness> tianfuRules = new HashMap<>();
//        tianfuRules.put(EarthlyBranch.SI, Brightness.BRIGHT);   // 巳宫
//        tianfuRules.put(EarthlyBranch.HAI, Brightness.BRIGHT);  // 亥宫
//        tianfuRules.put(EarthlyBranch.MAO, Brightness.NORMAL);  // 卯宫
//        tianfuRules.put(EarthlyBranch.YOU, Brightness.WEAK);    // 酉宫
//        TIANFU_BRIGHTNESS_RULES.put("天府", tianfuRules);
//
//        // 太阴星
//        Map<EarthlyBranch, Brightness> taiyinRules = new HashMap<>();
//        taiyinRules.put(EarthlyBranch.HAI, Brightness.BRIGHT);  // 亥宫
//        taiyinRules.put(EarthlyBranch.MAO, Brightness.BRIGHT);  // 卯宫
//        taiyinRules.put(EarthlyBranch.YOU, Brightness.NORMAL);  // 酉宫
//        taiyinRules.put(EarthlyBranch.SI, Brightness.WEAK);     // 巳宫
//        TIANFU_BRIGHTNESS_RULES.put("太阴", taiyinRules);
//    }
//
//    /**
//     * 计算星耀亮度
//     *
//     * @param star 星耀
//     * @param branch 所在地支
//     * @return 星耀亮度
//     */
//    public static Brightness calculateBrightness(Star star, EarthlyBranch branch) {
//        String starName = star.getName();
//
//        // 检查紫微系星耀
//        if (ZIWEI_BRIGHTNESS_RULES.containsKey(starName)) {
//            Map<EarthlyBranch, Brightness> rules = ZIWEI_BRIGHTNESS_RULES.get(starName);
//            return rules.getOrDefault(branch, Brightness.NORMAL);
//        }
//
//        // 检查天府系星耀
//        if (TIANFU_BRIGHTNESS_RULES.containsKey(starName)) {
//            Map<EarthlyBranch, Brightness> rules = TIANFU_BRIGHTNESS_RULES.get(starName);
//            return rules.getOrDefault(branch, Brightness.NORMAL);
//        }
//
//        // 其他星耀默认为普通亮度
//        return Brightness.NORMAL;
//    }
//
//    /**
//     * 获取星耀在特定地支的亮度描述
//     *
//     * @param star 星耀
//     * @param branch 地支
//     * @return 亮度描述
//     */
//    public static String getBrightnessDescription(Star star, EarthlyBranch branch) {
//        Brightness brightness = calculateBrightness(star, branch);
//        switch (brightness) {
//            case TEMPLE:
//                return String.format("%s在%s宫，光芒璀璨", star.getName(), branch.getDescription());
//            case NORMAL:
//                return String.format("%s在%s宫，光芒一般", star.getName(), branch.getDescription());
//            case WEAK:
//                return String.format("%s在%s宫，光芒微弱", star.getName(), branch.getDescription());
//            case TRAPPED:
//                return String.format("%s在%s宫，光芒暗淡", star.getName(), branch.getDescription());
//            default:
//                return String.format("%s在%s宫", star.getName(), branch.getDescription());
//        }
//    }
//
//    /**
//     * 判断星耀是否明亮
//     *
//     * @param star 星耀
//     * @param branch 地支
//     * @return 是否明亮
//     */
//    public static boolean isBright(Star star, EarthlyBranch branch) {
//        return calculateBrightness(star, branch) == Brightness.BRIGHT;
//    }
//
//    /**
//     * 判断星耀是否失辉
//     *
//     * @param star 星耀
//     * @param branch 地支
//     * @return 是否失辉
//     */
//    public static boolean isDead(Star star, EarthlyBranch branch) {
//        return calculateBrightness(star, branch) == Brightness.DEAD;
//    }
//}
