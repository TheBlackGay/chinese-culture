package com.chinese.culture.admin.common.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;

/**
 * 杂耀计算器
 */
public class MiscStarCalculator {

    /**
     * 计算火星位置
     *
     * @param dayBranch 日支
     * @return 火星所在地支
     */
    public static EarthlyBranch calculateHuoXingLocation(String dayBranch) {
        EarthlyBranch branch = EarthlyBranch.fromDescription(dayBranch);
        switch (branch) {
            case ZI:
            case WU:
                return EarthlyBranch.SI;
            case CHOU:
            case WEI:
                return EarthlyBranch.YIN;
            case YIN:
            case SHEN:
                return EarthlyBranch.MAO;
            case MAO:
            case YOU:
                return EarthlyBranch.CHEN;
            case CHEN:
            case XU:
                return EarthlyBranch.SI;
            case SI:
            case HAI:
                return EarthlyBranch.WU;
            default:
                throw new IllegalArgumentException("Invalid day branch: " + dayBranch);
        }
    }

    /**
     * 计算铃星位置
     *
     * @param dayBranch 日支
     * @return 铃星所在地支
     */
    public static EarthlyBranch calculateLingXingLocation(String dayBranch) {
        EarthlyBranch branch = EarthlyBranch.fromDescription(dayBranch);
        switch (branch) {
            case ZI:
            case WU:
                return EarthlyBranch.CHOU;
            case CHOU:
            case WEI:
                return EarthlyBranch.ZI;
            case YIN:
            case SHEN:
                return EarthlyBranch.HAI;
            case MAO:
            case YOU:
                return EarthlyBranch.XU;
            case CHEN:
            case XU:
                return EarthlyBranch.YOU;
            case SI:
            case HAI:
                return EarthlyBranch.SHEN;
            default:
                throw new IllegalArgumentException("Invalid day branch: " + dayBranch);
        }
    }

    /**
     * 计算地空位置
     *
     * @param hourBranch 时支
     * @return 地空所在地支
     */
    public static EarthlyBranch calculateDiKongLocation(String hourBranch) {
        EarthlyBranch branch = EarthlyBranch.fromDescription(hourBranch);
        int index = (12 - branch.ordinal()) % 12;
        return EarthlyBranch.values()[index];
    }

    /**
     * 计算地劫位置
     *
     * @param hourBranch 时支
     * @return 地劫所在地支
     */
    public static EarthlyBranch calculateDiJieLocation(String hourBranch) {
        EarthlyBranch branch = EarthlyBranch.fromDescription(hourBranch);
        int index = (14 - branch.ordinal()) % 12;
        return EarthlyBranch.values()[index];
    }

    /**
     * 计算天空位置
     *
     * @param yearStem 年干
     * @return 天空所在地支
     */
    public static EarthlyBranch calculateTianKongLocation(String yearStem) {
        HeavenlyStem stem = HeavenlyStem.fromDescription(yearStem);
        switch (stem) {
            case JIA:
            case JI:
                return EarthlyBranch.CHOU;
            case YI:
            case GENG:
                return EarthlyBranch.XU;
            case BING:
            case XIN:
                return EarthlyBranch.YOU;
            case DING:
            case REN:
                return EarthlyBranch.SHEN;
            case WU:
            case GUI:
                return EarthlyBranch.WEI;
            default:
                throw new IllegalArgumentException("Invalid year stem: " + yearStem);
        }
    }

    /**
     * 计算天刑位置
     *
     * @param monthBranch 月支
     * @return 天刑所在地支
     */
    public static EarthlyBranch calculateTianXingLocation(String monthBranch) {
        EarthlyBranch branch = EarthlyBranch.fromDescription(monthBranch);
        switch (branch) {
            case ZI:
                return EarthlyBranch.SHEN;
            case CHOU:
                return EarthlyBranch.SI;
            case YIN:
                return EarthlyBranch.YIN;
            case MAO:
                return EarthlyBranch.HAI;
            case CHEN:
                return EarthlyBranch.WU;
            case SI:
                return EarthlyBranch.CHOU;
            case WU:
                return EarthlyBranch.YOU;
            case WEI:
                return EarthlyBranch.MAO;
            case SHEN:
                return EarthlyBranch.ZI;
            case YOU:
                return EarthlyBranch.WEI;
            case XU:
                return EarthlyBranch.CHEN;
            case HAI:
                return EarthlyBranch.XU;
            default:
                throw new IllegalArgumentException("Invalid month branch: " + monthBranch);
        }
    }

    /**
     * 计算天姚位置
     *
     * @param monthBranch 月支
     * @return 天姚所在地支
     */
    public static EarthlyBranch calculateTianYaoLocation(String monthBranch) {
        EarthlyBranch branch = EarthlyBranch.fromDescription(monthBranch);
        switch (branch) {
            case ZI:
                return EarthlyBranch.SHEN;
            case CHOU:
                return EarthlyBranch.YOU;
            case YIN:
                return EarthlyBranch.XU;
            case MAO:
                return EarthlyBranch.HAI;
            case CHEN:
                return EarthlyBranch.ZI;
            case SI:
                return EarthlyBranch.CHOU;
            case WU:
                return EarthlyBranch.YIN;
            case WEI:
                return EarthlyBranch.MAO;
            case SHEN:
                return EarthlyBranch.CHEN;
            case YOU:
                return EarthlyBranch.SI;
            case XU:
                return EarthlyBranch.WU;
            case HAI:
                return EarthlyBranch.WEI;
            default:
                throw new IllegalArgumentException("Invalid month branch: " + monthBranch);
        }
    }
}
