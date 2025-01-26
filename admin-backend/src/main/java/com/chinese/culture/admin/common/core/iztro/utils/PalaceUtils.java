package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.SoulAndBodyBO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

/**
 * 宫位计算工具类
 */
@Slf4j
public class PalaceUtils {

    /**
     * 五虎遁年起月表
     * 甲己年生起丙寅，乙庚年生起戊寅，
     * 丙辛年生起庚寅，丁壬年生起壬寅，
     * 戊癸年生起甲寅。
     */
    private static final Map<HeavenlyStem, HeavenlyStem> TIGER_RULE = new HashMap<>();
    static {
        // 甲己年生起丙寅
        TIGER_RULE.put(HeavenlyStem.JIA, HeavenlyStem.BING);
        TIGER_RULE.put(HeavenlyStem.JI, HeavenlyStem.BING);
        // 乙庚年生起戊寅
        TIGER_RULE.put(HeavenlyStem.YI, HeavenlyStem.WU);
        TIGER_RULE.put(HeavenlyStem.GENG, HeavenlyStem.WU);
        // 丙辛年生起庚寅
        TIGER_RULE.put(HeavenlyStem.BING, HeavenlyStem.GENG);
        TIGER_RULE.put(HeavenlyStem.XIN, HeavenlyStem.GENG);
        // 丁壬年生起壬寅
        TIGER_RULE.put(HeavenlyStem.DING, HeavenlyStem.REN);
        TIGER_RULE.put(HeavenlyStem.REN, HeavenlyStem.REN);
        // 戊癸年生起甲寅
        TIGER_RULE.put(HeavenlyStem.WU, HeavenlyStem.JIA);
        TIGER_RULE.put(HeavenlyStem.GUI, HeavenlyStem.JIA);
    }

    /**
     * 计算命宫和身宫
     * 
     * 1. 定寅首
     * - 甲己年生起丙寅，乙庚年生起戊寅，
     * - 丙辛年生起庚寅，丁壬年生起壬寅，
     * - 戊癸年生起甲寅。
     * 
     * 2. 安命身宫诀
     * - 寅起正月，顺数至生月，逆数生时为命宫。
     * - 寅起正月，顺数至生月，顺数生时为身宫。
     *
     * @param birthTime 出生时间
     * @param fixLeap 是否修正闰月，若修正，则闰月前15天按上月算，后15天按下月算
     * @return 命宫和身宫数据
     */
    public static SoulAndBodyBO getSoulAndBody(LocalDateTime birthTime, boolean fixLeap) {
        // 获取年干支
        HeavenlyStem yearStem = CalendarUtils.getYearHeavenlyStem(birthTime);
        
        // 获取时支
        EarthlyBranch hourBranch = CalendarUtils.getHourEarthlyBranch(birthTime);
        
        // 获取农历月份(1-12)，如果需要修正闰月则调用修正方法
        int lunarMonth = CalendarUtils.getLunarMonth(birthTime);
        if (fixLeap) {
            lunarMonth = CalendarUtils.fixLunarMonth(birthTime);
        }
        
        // 寅宫为第一个宫位(0)
        int firstIndex = EarthlyBranch.YIN.ordinal();
        
        // 计算生月对应的地支索引
        // 正月对应寅宫，二月对应卯宫，以此类推
        // 需要加上寅宫的索引作为起始点
        int monthBranchIndex = firstIndex;
        
        // 计算命宫和身宫索引
        // 注意：农历月份从1开始，需要减1来匹配0开始的索引
        int monthIndex = lunarMonth - 1;
        int hourIndex = hourBranch.ordinal();
        
        // 命宫索引：从生月地支逆数生时
        // 由于地支是顺时针排列的，所以逆数需要用减法
        // 注意：这里需要加60来确保结果为正数
        int soulIndex = fixIndex(monthBranchIndex - hourIndex + 60);
        
        // 身宫索引：从生月地支顺数生时
        // 由于地支是顺时针排列的，所以顺数需要用加法
        int bodyIndex = fixIndex(monthBranchIndex + hourIndex);
        
        // 用五虎遁取得寅宫的天干
        HeavenlyStem startStem = TIGER_RULE.get(yearStem);
        
        // 计算命宫天干：起始天干索引加上命宫的索引
        int soulStemIndex = fixIndex(startStem.ordinal() + soulIndex, 10);
        HeavenlyStem soulStem = HeavenlyStem.values()[soulStemIndex];
        
        // 计算命宫地支：命宫索引对应的地支
        EarthlyBranch soulBranch = EarthlyBranch.values()[soulIndex];
        
        return SoulAndBodyBO.builder()
                .soulIndex(soulIndex)
                .bodyIndex(bodyIndex)
                .heavenlyStemOfSoul(soulStem)
                .earthlyBranchOfSoul(soulBranch)
                .build();
    }
    
    /**
     * 修正索引，使其在指定范围内循环
     * 
     * @param index 原始索引
     * @param size 循环大小，默认为12(地支)
     * @return 修正后的索引
     */
    private static int fixIndex(int index, int size) {
        size = (size <= 0) ? 12 : size;
        return ((index % size) + size) % size;
    }
    
    /**
     * 修正索引，使其在地支范围内循环(0-11)
     */
    private static int fixIndex(int index) {
        return fixIndex(index, 12);
    }
} 