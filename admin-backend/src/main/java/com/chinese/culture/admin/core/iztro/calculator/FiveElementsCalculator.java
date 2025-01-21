package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.FiveElements;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;

/**
 * 五行局计算器
 */
public class FiveElementsCalculator {

    /**
     * 计算五行局
     *
     * @param yearGanIndex 年干索引
     * @param yearZhiIndex 年支索引
     * @return 五行局数字（2-6）
     */
    public static int calculate(int yearGanIndex, int yearZhiIndex) {
        HeavenlyStem stem = HeavenlyStem.fromIndex(yearGanIndex);
        EarthlyBranch branch = EarthlyBranch.fromIndex(yearZhiIndex);

        // 获取年干五行
        FiveElements stemElement = stem.getFiveElements();
        // 获取年支五行
        FiveElements branchElement = branch.getFiveElements();

        // 计算五行局数
        return calculateFiveElementsClass(stemElement, branchElement);
    }

    /**
     * 根据年干支五行计算五行局数
     */
    private static int calculateFiveElementsClass(FiveElements stem, FiveElements branch) {
        // 如果年干五行生年支五行
        if (stem.getGeneratedElement() == branch) {
            return 6; // 六局
        }
        // 如果年支五行生年干五行
        else if (branch.getGeneratedElement() == stem) {
            return 2; // 二局
        }
        // 如果年干五行克年支五行
        else if (stem.getRestrictedElement() == branch) {
            return 5; // 五局
        }
        // 如果年支五行克年干五行
        else if (branch.getRestrictedElement() == stem) {
            return 3; // 三局
        }
        // 如果年干支五行相同
        else if (stem == branch) {
            return 4; // 四局
        }
        // 默认返回四局
        return 4;
    }

    /**
     * 获取五行局名称
     */
    public static String getFiveElementsClassName(int fiveElementsClass) {
        switch (fiveElementsClass) {
            case 2:
                return "水二局";
            case 3:
                return "木三局";
            case 4:
                return "金四局";
            case 5:
                return "土五局";
            case 6:
                return "火六局";
            default:
                throw new IllegalArgumentException("Invalid five elements class: " + fiveElementsClass);
        }
    }

    /**
     * 获取五行局对应的五行
     */
    public static FiveElements getFiveElementsByClass(int fiveElementsClass) {
        switch (fiveElementsClass) {
            case 2:
                return FiveElements.WATER;
            case 3:
                return FiveElements.WOOD;
            case 4:
                return FiveElements.METAL;
            case 5:
                return FiveElements.EARTH;
            case 6:
                return FiveElements.FIRE;
            default:
                throw new IllegalArgumentException("Invalid five elements class: " + fiveElementsClass);
        }
    }
}
