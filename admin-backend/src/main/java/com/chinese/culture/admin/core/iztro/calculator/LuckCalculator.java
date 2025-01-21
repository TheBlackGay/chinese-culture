package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.data.Palace;

/**
 * 运限计算器
 */
public class LuckCalculator {

    /**
     * 计算大限起始宫位
     * 
     * @param mingGong 命宫
     * @param gender 性别（true为阳男阴女，false为阴男阳女）
     * @return 大限起始宫位
     */
    public static EarthlyBranch calculateMajorLimitStartPalace(Palace mingGong, boolean gender) {
        EarthlyBranch branch = mingGong.getEarthlyBranch();
        int startIndex = branch.ordinal();
        
        // 阳男阴女顺行，阴男阳女逆行
        if (!gender) {
            startIndex = (startIndex + 11) % 12; // 逆行一步等于顺行11步
        }
        
        return EarthlyBranch.values()[startIndex];
    }
    
    /**
     * 计算小限起始宫位
     * 
     * @param birthYear 出生年
     * @param gender 性别（true为阳男阴女，false为阴男阳女）
     * @return 小限起始宫位
     */
    public static EarthlyBranch calculateMinorLimitStartPalace(int birthYear, boolean gender) {
        // 计算出生年份的地支
        int yearBranchIndex = (birthYear - 4) % 12;
        
        // 阳男阴女从本生地支起子时顺行
        // 阴男阳女从本生地支起子时逆行
        if (!gender) {
            yearBranchIndex = (yearBranchIndex + 11) % 12;
        }
        
        return EarthlyBranch.values()[yearBranchIndex];
    }
    
    /**
     * 计算流年
     * 
     * @param currentYear 当前年份
     * @return 流年地支
     */
    public static EarthlyBranch calculateYearlyFlow(int currentYear) {
        int yearBranchIndex = (currentYear - 4) % 12;
        return EarthlyBranch.values()[yearBranchIndex];
    }
    
    /**
     * 计算流月
     * 
     * @param yearlyFlow 流年地支
     * @param month 当前月份（1-12）
     * @return 流月地支
     */
    public static EarthlyBranch calculateMonthlyFlow(EarthlyBranch yearlyFlow, int month) {
        int yearIndex = yearlyFlow.ordinal();
        // 流月从流年起子月顺行
        int monthIndex = (yearIndex + month - 1) % 12;
        return EarthlyBranch.values()[monthIndex];
    }
    
    /**
     * 计算流日
     * 
     * @param monthlyFlow 流月地支
     * @param day 当前日期（1-31）
     * @return 流日地支
     */
    public static EarthlyBranch calculateDailyFlow(EarthlyBranch monthlyFlow, int day) {
        int monthIndex = monthlyFlow.ordinal();
        // 流日从流月起子日顺行
        int dayIndex = (monthIndex + day - 1) % 12;
        return EarthlyBranch.values()[dayIndex];
    }
    
    /**
     * 计算流时
     * 
     * @param dailyFlow 流日地支
     * @param hour 当前时辰索引（0-11）
     * @return 流时地支
     */
    public static EarthlyBranch calculateHourlyFlow(EarthlyBranch dailyFlow, int hour) {
        int dayIndex = dailyFlow.ordinal();
        // 流时从流日起子时顺行
        int hourIndex = (dayIndex + hour) % 12;
        return EarthlyBranch.values()[hourIndex];
    }
    
    /**
     * 计算大限年数
     * 
     * @param fiveElementsClass 五行局数
     * @return 每个大限的年数
     */
    public static int calculateMajorLimitYears(int fiveElementsClass) {
        return fiveElementsClass + 5;
    }
    
    /**
     * 计算当前大限
     * 
     * @param age 年龄
     * @param majorLimitYears 每个大限的年数
     * @return 当前大限序号（0-11）
     */
    public static int calculateCurrentMajorLimit(int age, int majorLimitYears) {
        return age / majorLimitYears;
    }
    
    /**
     * 计算当前小限
     * 
     * @param age 年龄
     * @return 当前小限序号（0-11）
     */
    public static int calculateCurrentMinorLimit(int age) {
        return (age - 1) % 12;
    }
} 