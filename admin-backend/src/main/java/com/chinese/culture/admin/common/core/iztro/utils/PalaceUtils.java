package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.PalaceName;
import com.chinese.culture.admin.common.core.iztro.data.SoulAndBodyBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.SurroundedPalacesBO;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static final int FIRST_INDEX = EarthlyBranch.YIN.ordinal();

    /**
     * 根据年干获取寅宫天干
     */
    private static HeavenlyStem getTigerRule(LocalDateTime birthTime) {
        HeavenlyStem yearStem = CalendarUtils.getYearHeavenlyStem(birthTime);
        return TIGER_RULE.get(yearStem);
    }

    /**
     * 计算命宫和身宫
     *
     * @param birthTime 出生时间
     * @param isLeapMonth 是否修正闰月，若修正，则闰月前15天按上月算，后15天按下月算
     * @return 命宫和身宫数据
     */
    public static SoulAndBodyBO getSoulAndBody(LocalDateTime birthTime, boolean isLeapMonth) {
        // 获取农历月份
        int lunarMonth = CalendarUtils.getLunarMonth(birthTime);
        if (isLeapMonth) {
            lunarMonth = CalendarUtils.fixLunarMonth(birthTime);
        }

        // 检查是否是节气日
        String solarTerm = LunarUtils.getSolarTerm(birthTime.toLocalDate());
        if (solarTerm != null) {
            // 如果是节气日，使用节气所在的月份
            // 节气对应的月份规则：
            // 小寒、大寒：二月
            // 立春、雨水：三月
            // 惊蛰、春分：四月
            // 清明、谷雨：五月
            // 立夏、小满：六月
            // 芒种、夏至：七月
            // 小暑、大暑：八月
            // 立秋、处暑：九月
            // 白露、秋分：十月
            // 寒露、霜降：十一月
            // 立冬、小雪：十二月
            // 大雪、冬至：一月
            switch (solarTerm) {
                case "小寒":
                case "大寒":
                    lunarMonth = 2;
                    break;
                case "立春":
                case "雨水":
                    lunarMonth = 3;
                    break;
                case "惊蛰":
                case "春分":
                    lunarMonth = 4;
                    break;
                case "清明":
                case "谷雨":
                    lunarMonth = 5;
                    break;
                case "立夏":
                case "小满":
                    lunarMonth = 6;
                    break;
                case "芒种":
                case "夏至":
                    lunarMonth = 7;
                    break;
                case "小暑":
                case "大暑":
                    lunarMonth = 8;
                    break;
                case "立秋":
                case "处暑":
                    lunarMonth = 9;
                    break;
                case "白露":
                case "秋分":
                    lunarMonth = 10;
                    break;
                case "寒露":
                case "霜降":
                    lunarMonth = 11;
                    break;
                case "立冬":
                case "小雪":
                    lunarMonth = 12;
                    break;
                case "大雪":
                case "冬至":
                    lunarMonth = 1;
                    break;
            }
        }

        // 获取时辰地支索引
        int timeIndex = CalendarUtils.getHourEarthlyBranch(birthTime).ordinal();

        // 计算月支索引，以寅宫为0
        int monthBranchIndex = (lunarMonth + 2) % 12;

        // 计算命宫索引：
        // 1. 从月支开始
        // 2. 逆时针数到时支（逆时针数就是减去时辰数）
        int soulIndex = fixIndex(monthBranchIndex - timeIndex);

        // 计算身宫索引：
        // 1. 从月支开始
        // 2. 顺时针数到时支（顺时针数就是加上时辰数）
        int bodyIndex = fixIndex(monthBranchIndex + timeIndex);

        // 获取年干支以确定寅宫天干
        HeavenlyStem yearStem = CalendarUtils.getYearHeavenlyStem(birthTime);
        HeavenlyStem startStem = TIGER_RULE.get(yearStem);

        // 计算命宫天干：从寅宫天干开始，顺数到命宫位置
        int soulStemIndex = fixIndex(startStem.ordinal() + soulIndex, 10);
        HeavenlyStem soulStem = HeavenlyStem.values()[soulStemIndex];

        // 计算命宫地支：命宫索引对应的地支
        EarthlyBranch soulBranch = EarthlyBranch.values()[fixIndex(soulIndex + FIRST_INDEX)];

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

    /**
     * 按照顺序排列十二宫
     */
    public static List<PalaceBO> arrangePalaces(SoulAndBodyBO soulAndBody) {
        List<PalaceBO> palaces = new ArrayList<>(12);

        // 获取命宫的天干地支
        HeavenlyStem soulStem = soulAndBody.getHeavenlyStemOfSoul();
        EarthlyBranch soulBranch = soulAndBody.getEarthlyBranchOfSoul();

        // 计算起始索引
        int currentIndex = soulBranch.ordinal();
        int startStemIndex = soulStem.ordinal();

        // 按照兄弟、夫妻、子女、财帛、疾厄、迁移、交友、官禄、田宅、福德、命宫、父母的顺序排列
        PalaceName[] palaceOrder = {
            PalaceName.XIONG_DI, PalaceName.FU_QI, PalaceName.ZI_NV,
            PalaceName.CAI_BO, PalaceName.JI_E, PalaceName.QIAN_YI,
            PalaceName.JIAO_YOU, PalaceName.GUAN_LU, PalaceName.TIAN_ZHE,
            PalaceName.FU_DE, PalaceName.MING_GONG, PalaceName.FU_MU
        };

        for (int i = 0; i < 12; i++) {
            // 计算当前宫位的地支索引
            int branchIndex = fixIndex(currentIndex + i);
            EarthlyBranch currentBranch = EarthlyBranch.values()[branchIndex];

            // 计算当前宫位的天干索引
            int currentStemIndex = fixIndex(startStemIndex + i, 10);  // 天干只有10个
            HeavenlyStem currentStem = HeavenlyStem.values()[currentStemIndex];

            // 创建宫位对象
            PalaceBO palace = PalaceBO.builder()
                    .name(palaceOrder[i])
                    .earthlyBranch(currentBranch)
                    .heavenlyStem(currentStem)
                    .isOriginalPalace(i == 10)  // 命宫放在倒数第二个位置
                    .index(branchIndex)  // 设置宫位索引
                    .build();

            System.out.println("Palace " + i + ": " + palaceOrder[i] + ", Branch: " + currentBranch);
            palaces.add(palace);
        }

        return palaces;
    }

    /**
     * 获取宫位的三方四正
     * 三方：本宫、对宫、左右三合宫
     * 四正：本宫、对宫、前后三合宫
     */
    public static SurroundedPalacesBO getSurroundedPalaces(PalaceBO targetPalace, List<PalaceBO> allPalaces) {
        if (targetPalace == null || allPalaces == null || allPalaces.isEmpty()) {
            return null;
        }

        int targetIndex = targetPalace.getIndex();

        // 对宫：相隔六个地支
        int oppositeIndex = fixIndex(targetIndex + 6);
        PalaceBO oppositePalace = findPalaceByIndex(allPalaces, oppositeIndex);

        // 三合：相隔四个地支
        int wealthIndex = fixIndex(targetIndex + 4);
        int careerIndex = fixIndex(targetIndex + 8);
        PalaceBO wealthPalace = findPalaceByIndex(allPalaces, wealthIndex);
        PalaceBO careerPalace = findPalaceByIndex(allPalaces, careerIndex);

        return SurroundedPalacesBO.builder()
                .target(targetPalace)
                .opposite(oppositePalace)
                .wealth(wealthPalace)
                .career(careerPalace)
                .build();
    }

    private static PalaceBO findPalaceByIndex(List<PalaceBO> palaces, int index) {
        return palaces.stream()
                .filter(p -> p.getIndex() == index)
                .findFirst()
                .orElse(null);
    }

    /**
     * 判断两宫位是否相冲
     */
    public static boolean isOpposite(PalaceBO palace1, PalaceBO palace2) {
        return Math.abs(palace1.getIndex() - palace2.getIndex()) == 6;
    }

    /**
     * 判断两宫位是否三合
     */
    public static boolean isTriangle(PalaceBO palace1, PalaceBO palace2, PalaceBO palace3) {
        if (palace1 == null || palace2 == null || palace3 == null) {
            return false;
        }

        EarthlyBranch branch1 = palace1.getEarthlyBranch();
        EarthlyBranch branch2 = palace2.getEarthlyBranch();
        EarthlyBranch branch3 = palace3.getEarthlyBranch();

        // 如果有相同的地支，则不构成三合
        if (branch1 == branch2 || branch2 == branch3 || branch1 == branch3) {
            return false;
        }

        // 寅午戌三合火
        if (isTriangleGroup(branch1, branch2, branch3,
                EarthlyBranch.YIN, EarthlyBranch.WU, EarthlyBranch.XU)) {
            return true;
        }

        // 巳酉丑三合金
        if (isTriangleGroup(branch1, branch2, branch3,
                EarthlyBranch.SI, EarthlyBranch.YOU, EarthlyBranch.CHOU)) {
            return true;
        }

        // 亥卯未三合木
        if (isTriangleGroup(branch1, branch2, branch3,
                EarthlyBranch.HAI, EarthlyBranch.MAO, EarthlyBranch.WEI)) {
            return true;
        }

        // 申子辰三合水
        return isTriangleGroup(branch1, branch2, branch3,
                EarthlyBranch.SHEN, EarthlyBranch.ZI, EarthlyBranch.CHEN);
    }

    /**
     * 判断两个地支是否可能构成三合局的一部分
     */
    private static boolean isPotentialTriangle(EarthlyBranch b1, EarthlyBranch b2,
            EarthlyBranch g1, EarthlyBranch g2, EarthlyBranch g3) {
        // 如果两个地支相同，则不可能构成三合的一部分
        if (b1 == b2) {
            return false;
        }

        // 检查两个地支是否都在指定的三合组中
        return (b1 == g1 || b1 == g2 || b1 == g3) &&
                (b2 == g1 || b2 == g2 || b2 == g3);
    }

    /**
     * 判断三个地支是否构成指定的三合组
     */
    private static boolean isTriangleGroup(EarthlyBranch b1, EarthlyBranch b2, EarthlyBranch b3,
            EarthlyBranch g1, EarthlyBranch g2, EarthlyBranch g3) {
        // 如果有相同的地支，则不构成三合
        if (b1 == b2 || b2 == b3 || b1 == b3) {
            return false;
        }

        // 检查是否包含所有指定的地支
        return (b1 == g1 || b1 == g2 || b1 == g3) &&
                (b2 == g1 || b2 == g2 || b2 == g3) &&
                (b3 == g1 || b3 == g2 || b3 == g3);
    }

    /**
     * 判断两个宫位是否为合关系
     * 子丑合、寅亥合、卯戌合、辰酉合、巳申合、午未合
     */
    public static boolean isHarmony(PalaceBO palace1, PalaceBO palace2) {
        if (palace1 == null || palace2 == null) {
            return false;
        }

        EarthlyBranch branch1 = palace1.getEarthlyBranch();
        EarthlyBranch branch2 = palace2.getEarthlyBranch();

        // 检查是否为合关系
        return (branch1 == EarthlyBranch.ZI && branch2 == EarthlyBranch.CHOU) ||
                (branch1 == EarthlyBranch.CHOU && branch2 == EarthlyBranch.ZI) ||
                (branch1 == EarthlyBranch.YIN && branch2 == EarthlyBranch.HAI) ||
                (branch1 == EarthlyBranch.HAI && branch2 == EarthlyBranch.YIN) ||
                (branch1 == EarthlyBranch.MAO && branch2 == EarthlyBranch.XU) ||
                (branch1 == EarthlyBranch.XU && branch2 == EarthlyBranch.MAO) ||
                (branch1 == EarthlyBranch.CHEN && branch2 == EarthlyBranch.YOU) ||
                (branch1 == EarthlyBranch.YOU && branch2 == EarthlyBranch.CHEN) ||
                (branch1 == EarthlyBranch.SI && branch2 == EarthlyBranch.SHEN) ||
                (branch1 == EarthlyBranch.SHEN && branch2 == EarthlyBranch.SI) ||
                (branch1 == EarthlyBranch.WU && branch2 == EarthlyBranch.WEI) ||
                (branch1 == EarthlyBranch.WEI && branch2 == EarthlyBranch.WU);
    }
}
