package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;

import java.util.HashMap;
import java.util.Map;

/**
 * 星耀亮度计算器
 */
public class BrightnessCalculator {

    // 星耀亮度规则表
    private static final Map<String, Map<EarthlyBranch, Brightness>> BRIGHTNESS_RULES = new HashMap<>();

    static {
        // 紫微星亮度规则
        Map<EarthlyBranch, Brightness> ziWeiRules = new HashMap<>();
        ziWeiRules.put(EarthlyBranch.SHEN, Brightness.TEMPLE); // 申宫庙
        ziWeiRules.put(EarthlyBranch.SI, Brightness.STRONG);   // 巳宫旺
        ziWeiRules.put(EarthlyBranch.WU, Brightness.GAIN);     // 午宫得
        ziWeiRules.put(EarthlyBranch.YIN, Brightness.TRAPPED);    // 寅宫陷
        BRIGHTNESS_RULES.put("紫微", ziWeiRules);

        // 天机星亮度规则
        Map<EarthlyBranch, Brightness> tianJiRules = new HashMap<>();
        tianJiRules.put(EarthlyBranch.YIN, Brightness.TEMPLE); // 寅宫庙
        tianJiRules.put(EarthlyBranch.MAO, Brightness.STRONG); // 卯宫旺
        tianJiRules.put(EarthlyBranch.CHEN, Brightness.GAIN);  // 辰宫得
        tianJiRules.put(EarthlyBranch.XU, Brightness.TRAPPED);    // 戌宫陷
        BRIGHTNESS_RULES.put("天机", tianJiRules);

        // 太阳星亮度规则
        Map<EarthlyBranch, Brightness> sunRules = new HashMap<>();
        sunRules.put(EarthlyBranch.SI, Brightness.TEMPLE);     // 巳宫庙
        sunRules.put(EarthlyBranch.WU, Brightness.STRONG);     // 午宫旺
        sunRules.put(EarthlyBranch.WEI, Brightness.GAIN);      // 未宫得
        sunRules.put(EarthlyBranch.HAI, Brightness.TRAPPED);      // 亥宫陷
        BRIGHTNESS_RULES.put("太阳", sunRules);

        // 武曲星亮度规则
        Map<EarthlyBranch, Brightness> wuQuRules = new HashMap<>();
        wuQuRules.put(EarthlyBranch.SHEN, Brightness.TEMPLE);  // 申宫庙
        wuQuRules.put(EarthlyBranch.YOU, Brightness.STRONG);   // 酉宫旺
        wuQuRules.put(EarthlyBranch.XU, Brightness.GAIN);      // 戌宫得
        wuQuRules.put(EarthlyBranch.CHEN, Brightness.TRAPPED);    // 辰宫陷
        BRIGHTNESS_RULES.put("武曲", wuQuRules);

        // 天同星亮度规则
        Map<EarthlyBranch, Brightness> tianTongRules = new HashMap<>();
        tianTongRules.put(EarthlyBranch.HAI, Brightness.TEMPLE); // 亥宫庙
        tianTongRules.put(EarthlyBranch.ZI, Brightness.STRONG);  // 子宫旺
        tianTongRules.put(EarthlyBranch.CHOU, Brightness.GAIN);  // 丑宫得
        tianTongRules.put(EarthlyBranch.WU, Brightness.TRAPPED);    // 午宫陷
        BRIGHTNESS_RULES.put("天同", tianTongRules);

        // 廉贞星亮度规则
        Map<EarthlyBranch, Brightness> lianZhenRules = new HashMap<>();
        lianZhenRules.put(EarthlyBranch.CHEN, Brightness.TEMPLE); // 辰宫庙
        lianZhenRules.put(EarthlyBranch.SI, Brightness.STRONG);   // 巳宫旺
        lianZhenRules.put(EarthlyBranch.WU, Brightness.GAIN);     // 午宫得
        lianZhenRules.put(EarthlyBranch.XU, Brightness.TRAPPED);     // 戌宫陷
        BRIGHTNESS_RULES.put("廉贞", lianZhenRules);

        // 天府星亮度规则
        Map<EarthlyBranch, Brightness> tianFuRules = new HashMap<>();
        tianFuRules.put(EarthlyBranch.CHOU, Brightness.TEMPLE);   // 丑宫庙
        tianFuRules.put(EarthlyBranch.YIN, Brightness.STRONG);    // 寅宫旺
        tianFuRules.put(EarthlyBranch.MAO, Brightness.GAIN);      // 卯宫得
        tianFuRules.put(EarthlyBranch.WEI, Brightness.TRAPPED);      // 未宫陷
        BRIGHTNESS_RULES.put("天府", tianFuRules);

        // 太阴星亮度规则
        Map<EarthlyBranch, Brightness> moonRules = new HashMap<>();
        moonRules.put(EarthlyBranch.CHOU, Brightness.TEMPLE);     // 丑宫庙
        moonRules.put(EarthlyBranch.ZI, Brightness.STRONG);       // 子宫旺
        moonRules.put(EarthlyBranch.HAI, Brightness.GAIN);        // 亥宫得
        moonRules.put(EarthlyBranch.WU, Brightness.TRAPPED);         // 午宫陷
        BRIGHTNESS_RULES.put("太阴", moonRules);

        // 贪狼星亮度规则
        Map<EarthlyBranch, Brightness> tanLangRules = new HashMap<>();
        tanLangRules.put(EarthlyBranch.ZI, Brightness.TEMPLE);    // 子宫庙
        tanLangRules.put(EarthlyBranch.HAI, Brightness.STRONG);   // 亥宫旺
        tanLangRules.put(EarthlyBranch.XU, Brightness.GAIN);      // 戌宫得
        tanLangRules.put(EarthlyBranch.WU, Brightness.TRAPPED);      // 午宫陷
        BRIGHTNESS_RULES.put("贪狼", tanLangRules);

        // 巨门星亮度规则
        Map<EarthlyBranch, Brightness> juMenRules = new HashMap<>();
        juMenRules.put(EarthlyBranch.SI, Brightness.TEMPLE);      // 巳宫庙
        juMenRules.put(EarthlyBranch.WU, Brightness.STRONG);      // 午宫旺
        juMenRules.put(EarthlyBranch.WEI, Brightness.GAIN);       // 未宫得
        juMenRules.put(EarthlyBranch.HAI, Brightness.TRAPPED);       // 亥宫陷
        BRIGHTNESS_RULES.put("巨门", juMenRules);
    }

    /**
     * 计算星耀亮度
     *
     * @param star 星耀
     * @param branch 所在地支
     * @return 亮度
     */
    public static Brightness calculateBrightness(Star star, EarthlyBranch branch) {

        Map<EarthlyBranch, Brightness> rules = BRIGHTNESS_RULES.get(star.getName());
        if (rules != null) {
            Brightness brightness = rules.get(branch);
            if (brightness != null) {
                return brightness;
            }
        }
        return Brightness.STRONG; // 默认为旺
    }

    /**
     * 计算星耀亮度
     *
     * @param star 星耀
     * @param branch 所在地支
     * @return 亮度
     */
    public static Brightness calculateBrightness(Star star, String branch) {

        EarthlyBranch earthlyBranch = EarthlyBranch.fromDescription(branch);

        Map<EarthlyBranch, Brightness> rules = BRIGHTNESS_RULES.get(star.getName());
        if (rules != null) {
            Brightness brightness = rules.get(earthlyBranch);
            if (brightness != null) {
                return brightness;
            }
        }
        return Brightness.STRONG; // 默认为旺
    }

    /**
     * 判断星耀是否明亮
     *
     * @param star 星耀
     * @param branch 地支
     * @return 是否明亮
     */
    public static boolean isBright(Star star, EarthlyBranch branch) {

        return calculateBrightness(star, branch) == Brightness.TEMPLE;
    }

    /**
     * 判断星耀是否明亮
     *
     * @param star 星耀
     * @param branch 地支
     * @return 是否明亮
     */
    public static boolean isBright(Star star, String branch) {

        return isBright(star,EarthlyBranch.fromDescription(branch));
    }

    /**
     * 判断星耀是否失辉
     *
     * @param star 星耀
     * @param branch 地支
     * @return 是否失辉
     */
    public static boolean isDead(Star star, EarthlyBranch branch) {

        return calculateBrightness(star, branch) == Brightness.TRAPPED;
    }

    /**
     * 判断星耀是否失辉
     *
     * @param star 星耀
     * @param branch 地支
     * @return 是否失辉
     */
    public static boolean isDead(Star star, String branch) {

        return isDead(star,EarthlyBranch.fromDescription(branch));
    }

}
