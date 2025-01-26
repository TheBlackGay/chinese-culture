package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElements;
import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElementsClass;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import lombok.experimental.UtilityClass;

/**
 * 五行计算工具类
 */
@UtilityClass
public class FiveElementsUtils {

    /**
     * 获取五行局
     * 
     * 纳音五行计算取数口诀：
     * - 甲乙丙丁一到五，子丑午未一来数，
     * - 寅卯申酉二上走，辰巳戌亥三为足。
     * - 干支相加多减五，五行木金水火土。
     * 
     * 注解：
     * 1、五行取数：木1 金2 水3 火4 土5
     * 
     * 天干取数：
     * - 甲乙 ——> 1
     * - 丙丁 ——> 2
     * - 戊己 ——> 3
     * - 庚辛 ——> 4
     * - 壬癸 ——> 5
     * 
     * 地支取数：
     * - 子午丑未 ——> 1
     * - 寅申卯酉 ——> 2
     * - 辰戌巳亥 ——> 3
     * 
     * 计算方法：
     * 干支数相加，超过5者减去5，以差论之。
     * - 若差为1则五行属木
     * - 若差为2则五行属金
     * - 若差为3则五行属水
     * - 若差为4则五行属火
     * - 若差为5则五行属土
     * 
     * @param heavenlyStem 天干
     * @param earthlyBranch 地支
     * @return 五行局
     */
    public FiveElementsClass getFiveElementsClass(String heavenlyStem, String earthlyBranch) {
        // 根据天干地支计算五行局
        // 具体规则待实现
        switch (heavenlyStem) {
            case "甲":
            case "乙":
                return FiveElementsClass.WOOD;   // 木三局
            case "丙":
            case "丁":
                return FiveElementsClass.FIRE;   // 火六局
            case "戊":
            case "己":
                return FiveElementsClass.EARTH;  // 土五局
            case "庚":
            case "辛":
                return FiveElementsClass.METAL;  // 金四局
            case "壬":
            case "癸":
                return FiveElementsClass.WATER;  // 水二局
            default:
                throw new IllegalArgumentException("无效的天干：" + heavenlyStem);
        }
    }

    /**
     * 获取地支五行
     * @param earthlyBranch 地支
     * @return 五行
     */
    public static FiveElements getEarthlyBranchFiveElements(EarthlyBranch earthlyBranch) {
        switch (earthlyBranch) {
            case ZI:
            case HAI:
                return FiveElements.WATER;
            case YIN:
            case MAO:
                return FiveElements.WOOD;
            case SI:
            case WU:
                return FiveElements.FIRE;
            case SHEN:
            case YOU:
                return FiveElements.METAL;
            case CHOU:
            case CHEN:
            case XU:
            case WEI:
                return FiveElements.EARTH;
            default:
                throw new IllegalArgumentException("Invalid earthly branch");
        }
    }

    /**
     * 获取天干五行
     * @param heavenlyStem 天干
     * @return 五行
     */
    public static FiveElements getHeavenlyStemFiveElements(HeavenlyStem heavenlyStem) {
        switch (heavenlyStem) {
            case JIA:
            case YI:
                return FiveElements.WOOD;
            case BING:
            case DING:
                return FiveElements.FIRE;
            case WU:
            case JI:
                return FiveElements.EARTH;
            case GENG:
            case XIN:
                return FiveElements.METAL;
            case REN:
            case GUI:
                return FiveElements.WATER;
            default:
                throw new IllegalArgumentException("Invalid heavenly stem");
        }
    }

    /**
     * 判断两个五行是否相生
     * 木生火，火生土，土生金，金生水，水生木
     * @param source 源五行
     * @param target 目标五行
     * @return 是否相生
     */
    public static boolean isReinforce(FiveElements source, FiveElements target) {
        switch (source) {
            case WOOD:
                return target == FiveElements.FIRE;
            case FIRE:
                return target == FiveElements.EARTH;
            case EARTH:
                return target == FiveElements.METAL;
            case METAL:
                return target == FiveElements.WATER;
            case WATER:
                return target == FiveElements.WOOD;
            default:
                return false;
        }
    }

    /**
     * 判断两个五行是否相克
     * 木克土，土克水，水克火，火克金，金克木
     * @param source 源五行
     * @param target 目标五行
     * @return 是否相克
     */
    public static boolean isRestrain(FiveElements source, FiveElements target) {
        switch (source) {
            case WOOD:
                return target == FiveElements.EARTH;
            case EARTH:
                return target == FiveElements.WATER;
            case WATER:
                return target == FiveElements.FIRE;
            case FIRE:
                return target == FiveElements.METAL;
            case METAL:
                return target == FiveElements.WOOD;
            default:
                return false;
        }
    }
} 