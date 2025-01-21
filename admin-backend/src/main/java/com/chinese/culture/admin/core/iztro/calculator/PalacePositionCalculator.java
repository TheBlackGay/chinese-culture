package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.RawDate;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;

/**
 * 命宫身宫推算器
 */
public class PalacePositionCalculator {

    /**
     * 计算命宫位置
     * 
     * @param lunarMonth 农历月
     * @param timeIndex 时辰索引(0-11)
     * @return 命宫地支
     */
    public static EarthlyBranch calculateMingGong(int lunarMonth, int timeIndex) {
        // 命宫推算公式: 12 - 月数 + 时辰 = 命宫地支
        int index = (12 - lunarMonth + timeIndex) % 12;
        if (index < 0) {
            index += 12;
        }
        return EarthlyBranch.values()[index];
    }

    /**
     * 计算身宫位置
     * 
     * @param lunarMonth 农历月
     * @param timeIndex 时辰索引(0-11)
     * @return 身宫地支
     */
    public static EarthlyBranch calculateShenGong(int lunarMonth, int timeIndex) {
        // 身宫推算公式: 月数 + 时辰 = 身宫地支
        int index = (lunarMonth + timeIndex) % 12;
        return EarthlyBranch.values()[index];
    }

    /**
     * 根据农历日期计算命宫
     * 
     * @param date 农历日期
     * @param timeIndex 时辰索引
     * @return 命宫地支
     */
    public static EarthlyBranch getMingGong(RawDate.LunarDate date, int timeIndex) {
        return calculateMingGong(date.getMonth(), timeIndex);
    }

    /**
     * 根据农历日期计算身宫
     * 
     * @param date 农历日期
     * @param timeIndex 时辰索引
     * @return 身宫地支
     */
    public static EarthlyBranch getShenGong(RawDate.LunarDate date, int timeIndex) {
        return calculateShenGong(date.getMonth(), timeIndex);
    }

    /**
     * 获取宫位索引
     * 
     * @param branch 地支
     * @return 宫位索引(0-11)
     */
    public static int getPalaceIndex(EarthlyBranch branch) {
        return branch.ordinal();
    }

    /**
     * 根据宫位索引获取地支
     * 
     * @param index 宫位索引(0-11)
     * @return 地支
     */
    public static EarthlyBranch getEarthlyBranch(int index) {
        index = index % 12;
        if (index < 0) {
            index += 12;
        }
        return EarthlyBranch.values()[index];
    }
} 