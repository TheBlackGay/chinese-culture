package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;

/**
 * 星耀位置计算器
 */
public class StarLocationCalculator {
    
    /**
     * 计算紫微星位置
     * 
     * @param fiveElementsClass 五行局数
     * @param timeIndex 时辰索引（0-11）
     * @return 紫微星所在地支
     */
    public static EarthlyBranch calculateZiWeiLocation(int fiveElementsClass, int timeIndex) {
        int offset = ((fiveElementsClass + timeIndex - 1) % 12);
        return EarthlyBranch.values()[offset];
    }
    
    /**
     * 计算天府星位置
     * 
     * @param ziWeiLocation 紫微星位置
     * @return 天府星所在地支
     */
    public static EarthlyBranch calculateTianFuLocation(EarthlyBranch ziWeiLocation) {
        // 天府星位置为紫微星对宫之前第四宫
        int ziWeiIndex = ziWeiLocation.ordinal();
        int oppositeIndex = (ziWeiIndex + 6) % 12;
        int tianFuIndex = (oppositeIndex + 8) % 12;
        return EarthlyBranch.values()[tianFuIndex];
    }
    
    /**
     * 计算天机星位置
     * 
     * @param tianFuLocation 天府星位置
     * @return 天机星所在地支
     */
    public static EarthlyBranch calculateTianJiLocation(EarthlyBranch tianFuLocation) {
        int index = (tianFuLocation.ordinal() + 1) % 12;
        return EarthlyBranch.values()[index];
    }
    
    /**
     * 计算太阳星位置
     * 
     * @param monthBranch 月支
     * @return 太阳星所在地支
     */
    public static EarthlyBranch calculateSunLocation(EarthlyBranch monthBranch) {
        int index = (monthBranch.ordinal() + 2) % 12;
        return EarthlyBranch.values()[index];
    }
    
    /**
     * 计算太阴星位置
     * 
     * @param monthBranch 月支
     * @return 太阴星所在地支
     */
    public static EarthlyBranch calculateMoonLocation(EarthlyBranch monthBranch) {
        int index = (monthBranch.ordinal() + 8) % 12;
        return EarthlyBranch.values()[index];
    }
    
    /**
     * 计算贪狼星位置
     * 
     * @param tianFuLocation 天府星位置
     * @return 贪狼星所在地支
     */
    public static EarthlyBranch calculateTanLangLocation(EarthlyBranch tianFuLocation) {
        int index = (tianFuLocation.ordinal() + 1) % 12;
        return EarthlyBranch.values()[index];
    }
    
    /**
     * 计算巨门星位置
     * 
     * @param tianFuLocation 天府星位置
     * @return 巨门星所在地支
     */
    public static EarthlyBranch calculateJuMenLocation(EarthlyBranch tianFuLocation) {
        int index = (tianFuLocation.ordinal() + 2) % 12;
        return EarthlyBranch.values()[index];
    }
    
    /**
     * 计算禄存星位置
     * 
     * @param yearStem 年干
     * @return 禄存星所在地支
     */
    public static EarthlyBranch calculateLuCunLocation(HeavenlyStem yearStem) {
        switch (yearStem) {
            case JIA:
                return EarthlyBranch.CHEN;
            case YI:
                return EarthlyBranch.SI;
            case BING:
                return EarthlyBranch.WU;
            case DING:
                return EarthlyBranch.WEI;
            case WU:
                return EarthlyBranch.SHEN;
            case JI:
                return EarthlyBranch.YOU;
            case GENG:
                return EarthlyBranch.XU;
            case XIN:
                return EarthlyBranch.HAI;
            case REN:
                return EarthlyBranch.ZI;
            case GUI:
                return EarthlyBranch.CHOU;
            default:
                throw new IllegalArgumentException("Invalid heavenly stem: " + yearStem);
        }
    }
    
    /**
     * 计算文昌星位置
     * 
     * @param monthBranch 月支
     * @return 文昌星所在地支
     */
    public static EarthlyBranch calculateWenChangLocation(EarthlyBranch monthBranch) {
        int index = (monthBranch.ordinal() + 4) % 12;
        return EarthlyBranch.values()[index];
    }
    
    /**
     * 计算文曲星位置
     * 
     * @param monthBranch 月支
     * @return 文曲星所在地支
     */
    public static EarthlyBranch calculateWenQuLocation(EarthlyBranch monthBranch) {
        int index = (monthBranch.ordinal() + 10) % 12;
        return EarthlyBranch.values()[index];
    }
} 