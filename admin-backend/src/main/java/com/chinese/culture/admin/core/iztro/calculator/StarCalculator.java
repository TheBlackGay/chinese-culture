package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.StarName;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.core.iztro.utils.CalendarConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 星耀计算器
 */
@Slf4j
public class StarCalculator {

    // 主星
    private static final String[] MAIN_STARS = {
            "紫微", "天机", "太阳", "武曲", "天同",
            "廉贞", "天府", "太阴", "贪狼", "巨门",
            "天相", "天梁", "七杀", "破军"
    };

    // 辅星
    private static final String[] AUXILIARY_STARS = {
            "文昌", "文曲", "左辅", "右弼", "天魁", "天钺",
            "禄存", "天马", "擎羊", "陀罗", "火星", "铃星",
            "地空", "地劫",
    };

    // 杂耀
    private static final String[] MINOR_STARS = {
            "台辅", "封诰", "龙池", "凤阁",
            "天喜", "天姚", "红鸾", "天月", "天刑"
    };

    /**
     * 计算紫微星系主星位置
     *
     * @param mingGongPosition 命宫位置
     * @param lunarYear 农历年
     * @return 主星位置映射
     */
    public static Map<String, Integer> calculateMainStars(int mingGongPosition, int lunarYear) {

        try {
            // 参数验证
            if (mingGongPosition < 0 || mingGongPosition > 11) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命宫位置无效");
            }
            if (lunarYear < 1900 || lunarYear > 2100) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "年份超出计算范围");
            }

            Map<String, Integer> starPositions = new HashMap<>();

            // 计算紫微星位置
            int ziWeiPosition = calculateZiWeiPosition(lunarYear);
            starPositions.put("紫微", ziWeiPosition);

            // 计算其他主星位置
            calculateOtherMainStars(starPositions, ziWeiPosition);

            return starPositions;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("主星计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 计算辅星位置
     *
     * @param mingGongPosition 命宫位置
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param lunarDay 农历日
     * @return 辅星位置映射
     */
    public static Map<String, Integer> calculateAuxiliaryStars(
            int mingGongPosition, int lunarYear, int lunarMonth, int lunarDay) {

        try {
            // 参数验证
            if (mingGongPosition < 0 || mingGongPosition > 11) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命宫位置无效");
            }
            if (lunarYear < 1900 || lunarYear > 2100) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "年份超出计算范围");
            }
            if (lunarMonth < 1 || lunarMonth > 12) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "月份必须在1-12之间");
            }
            if (lunarDay < 1 || lunarDay > 30) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "日期必须在1-30之间");
            }

            Map<String, Integer> starPositions = new HashMap<>();

            // 计算文昌星位置：寅宫起子年，逆数到生年支
            int wenChangBase = 2; // 寅宫
            String yearBranch = getYearBranch(lunarYear);
            int yearIndex = "子丑寅卯辰巳午未申酉戌亥".indexOf(yearBranch);
            int wenChangPosition = (wenChangBase - yearIndex + 12) % 12;
            starPositions.put("文昌", wenChangPosition);

            // 计算文曲星位置：戌宫起子年，顺数到生年支
            int wenQuBase = 10; // 戌宫
            int wenQuPosition = (wenQuBase + yearIndex) % 12;
            starPositions.put("文曲", wenQuPosition);

            // 计算左辅星位置：以寅宫起正月，逆数到生月
            int zuoFuPosition = (2 - (lunarMonth - 1) + 12) % 12;
            starPositions.put("左辅", zuoFuPosition);

            // 计算右弼星位置：以戌宫起正月，顺数到生月
            int youBiPosition = (10 + (lunarMonth - 1)) % 12;
            starPositions.put("右弼", youBiPosition);

            // 计算天魁天钺位置（根据年干）
            String yearStem = CalendarConverter.getYearGanZhi(lunarYear).substring(0, 1);
            int[] kuiYuePositions = getKuiYuePositions(yearStem);
            starPositions.put("天魁", kuiYuePositions[0]);
            starPositions.put("天钺", kuiYuePositions[1]);

            // 计算禄存位置（根据年干）
            int luCunPosition = getLuCunPosition(yearStem);
            starPositions.put("禄存", luCunPosition);

            // 计算天马位置（根据年支）
            int tianMaPosition = getTianMaPosition(yearBranch);
            starPositions.put("天马", tianMaPosition);

            // 计算擎羊陀罗位置
            starPositions.put("擎羊", (luCunPosition + 1) % 12);
            starPositions.put("陀罗", (luCunPosition + 11) % 12);

            // 计算火星铃星位置（根据日干支）
            String dayGanZhi = CalendarConverter.getDayGanZhi(lunarYear, lunarMonth, lunarDay);
            int[] huoLingPositions = getHuoLingPositions(dayGanZhi);
            starPositions.put("火星", huoLingPositions[0]);
            starPositions.put("铃星", huoLingPositions[1]);

            return starPositions;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("辅星计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 计算杂耀星位置
     *
     * @param mingGongPosition 命宫位置
     * @param lunarYear 农历年
     * @param lunarMonth 农历月
     * @param lunarDay 农历日
     * @return 杂耀星位置映射
     */
    public static Map<String, Integer> calculateMinorStars(
            int mingGongPosition, int lunarYear, int lunarMonth, int lunarDay) {

        try {
            // 参数验证
            if (mingGongPosition < 0 || mingGongPosition > 11) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命宫位置无效");
            }
            if (lunarYear < 1900 || lunarYear > 2100) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "年份超出计算范围");
            }
            if (lunarMonth < 1 || lunarMonth > 12) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "月份必须在1-12之间");
            }
            if (lunarDay < 1 || lunarDay > 30) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "日期必须在1-30之间");
            }

            Map<String, Integer> starPositions = new HashMap<>();

            // 计算地空地劫位置（根据年支）
            String yearBranch = CalendarConverter.getYearGanZhi(lunarYear).substring(1);
            int[] diKongJiePositions = getDiKongJiePositions(yearBranch);
            starPositions.put("地空", diKongJiePositions[0]);
            starPositions.put("地劫", diKongJiePositions[1]);

            // 计算台辅封诰位置（以命宫起子时，顺数到生时）
            String dayGanZhi = CalendarConverter.getDayGanZhi(lunarYear, lunarMonth, lunarDay);
            int hourIndex = "子丑寅卯辰巳午未申酉戌亥".indexOf(dayGanZhi.substring(1));
            int taiFuPosition = (mingGongPosition + hourIndex) % 12;
            starPositions.put("台辅", taiFuPosition);
            starPositions.put("封诰", (taiFuPosition + 6) % 12);

            // 计算龙池凤阁位置（以命宫起正月，顺数到生月）
            int longChiPosition = (mingGongPosition + lunarMonth - 1) % 12;
            starPositions.put("龙池", longChiPosition);
            starPositions.put("凤阁", (longChiPosition + 6) % 12);

            // 计算天喜天姚位置（根据月支）
            String monthBranch = CalendarConverter.getMonthGanZhi(lunarYear, lunarMonth).substring(1);
            int[] xiYaoPositions = getXiYaoPositions(monthBranch);
            starPositions.put("天喜", xiYaoPositions[0]);
            starPositions.put("天姚", xiYaoPositions[1]);

            // 计算红鸾天月位置（根据年支）
            int[] hongLuanYuePositions = getHongLuanYuePositions(yearBranch);
            starPositions.put("红鸾", hongLuanYuePositions[0]);
            starPositions.put("天月", hongLuanYuePositions[1]);

            // 计算天刑位置（根据年支）
            int tianXingPosition = getTianXingPosition(yearBranch);
            starPositions.put("天刑", tianXingPosition);

            return starPositions;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("杂耀星计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 计算星耀四化
     */
    public static Map<String, List<String>> calculateTransformations(String yearGanZhi) {

        try {
            if (yearGanZhi == null || yearGanZhi.length() < 1) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "年干支不能为空");
            }

            Map<String, List<String>> transformations = new HashMap<>();
            transformations.put("化禄", new ArrayList<>());
            transformations.put("化权", new ArrayList<>());
            transformations.put("化科", new ArrayList<>());
            transformations.put("化忌", new ArrayList<>());

            String yearStem = yearGanZhi.substring(0, 1);

            // 根据年干获取四化
            Map<String, String[]> transformationMap = new HashMap<>();
            transformationMap.put("甲", new String[]{"廉贞", "破军", "武曲", "太阳"});  // 修正：七杀 -> 太阳
            transformationMap.put("乙", new String[]{"天机", "天同", "太阳", "贪狼"});
            transformationMap.put("丙", new String[]{"天同", "太阴", "武曲", "巨门"});
            transformationMap.put("丁", new String[]{"太阳", "武曲", "天机", "天梁"});
            transformationMap.put("戊", new String[]{"武曲", "天机", "天梁", "太阴"});
            transformationMap.put("己", new String[]{"太阴", "天梁", "紫微", "天同"});
            transformationMap.put("庚", new String[]{"天梁", "紫微", "文昌", "天机"});
            transformationMap.put("辛", new String[]{"破军", "太阳", "天同", "文曲"});
            transformationMap.put("壬", new String[]{"巨门", "文曲", "天梁", "武曲"});
            transformationMap.put("癸", new String[]{"贪狼", "武曲", "破军", "太阳"});

            String[] fourTransformations = transformationMap.get(yearStem);
            if (fourTransformations != null) {
                transformations.get("化禄").add(fourTransformations[0]);
                transformations.get("化权").add(fourTransformations[1]);
                transformations.get("化科").add(fourTransformations[2]);
                transformations.get("化忌").add(fourTransformations[3]);
            }

            return transformations;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("四化计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 计算星耀亮度
     *
     * @param star 星耀名称
     * @param position 所在位置
     * @param yearGanZhi 年干支
     * @return 亮度等级（0-4）
     */
    public static int calculateBrightness(String star, int position, String yearGanZhi) {

        try {
            // 获取年干和地支
            String yearStem = yearGanZhi.substring(0, 1);
            String yearBranch = yearGanZhi.substring(1);

            // 获取星耀所在宫位地支
            String[] earthlyBranches = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
            String palaceBranch = earthlyBranches[position];

            // 判断星耀类型
            if (isMainStar(star)) {
                return calculateMainBrightness(star, yearStem, palaceBranch);
            } else if (isAuxiliaryStar(star)) {
                return calculateAuxiliaryBrightness(star, yearStem, palaceBranch);
            } else {
                return 2; // 杂耀默认中等亮度
            }

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("星耀亮度计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 判断是否为主星
     */
    private static boolean isMainStar(String star) {

        return Arrays.asList(MAIN_STARS).contains(star);
    }

    /**
     * 判断是否为辅星
     */
    private static boolean isAuxiliaryStar(String star) {

        return Arrays.asList(AUXILIARY_STARS).contains(star);
    }

    /**
     * 计算主星亮度
     */
    private static int calculateMainBrightness(String star, String yearStem, String palaceBranch) {
        // 紫微星系
        Map<String, Map<String, Integer>> ziWeiSystem = new HashMap<>();
        ziWeiSystem.put("紫微", createBrightnessMap("午", "子", "卯酉"));
        ziWeiSystem.put("天机", createBrightnessMap("巳", "亥", "寅申"));
        ziWeiSystem.put("太阳", createBrightnessMap("巳", "亥", "寅申"));
        ziWeiSystem.put("武曲", createBrightnessMap("申", "寅", "巳亥"));
        ziWeiSystem.put("天同", createBrightnessMap("午", "子", "卯酉"));
        ziWeiSystem.put("廉贞", createBrightnessMap("午", "子", "卯酉"));

        // 天府星系
        Map<String, Map<String, Integer>> tianFuSystem = new HashMap<>();
        tianFuSystem.put("天府", createBrightnessMap("亥", "巳", "寅申"));
        tianFuSystem.put("太阴", createBrightnessMap("酉", "卯", "子午"));
        tianFuSystem.put("贪狼", createBrightnessMap("寅", "申", "巳亥"));
        tianFuSystem.put("巨门", createBrightnessMap("辰", "戌", "丑未"));
        tianFuSystem.put("天相", createBrightnessMap("未", "丑", "寅申"));
        tianFuSystem.put("天梁", createBrightnessMap("戌", "辰", "丑未"));
        tianFuSystem.put("七杀", createBrightnessMap("申", "寅", "巳亥"));
        tianFuSystem.put("破军", createBrightnessMap("午", "子", "卯酉"));

        Map<String, Map<String, Integer>> starSystem =
                ziWeiSystem.containsKey(star) ? ziWeiSystem : tianFuSystem;

        Map<String, Integer> brightnessMap = starSystem.get(star);
        if (brightnessMap != null) {
            if (brightnessMap.containsKey(palaceBranch)) {
                return brightnessMap.get(palaceBranch);
            }
            for (Map.Entry<String, Integer> entry : brightnessMap.entrySet()) {
                if (entry.getKey().contains(palaceBranch)) {
                    return entry.getValue();
                }
            }
        }

        return 2; // 默认中等亮度
    }

    /**
     * 计算辅星亮度
     */
    private static int calculateAuxiliaryBrightness(String star, String yearStem, String palaceBranch) {

        Map<String, Map<String, Integer>> auxiliarySystem = new HashMap<>();

        // 文昌文曲
        auxiliarySystem.put("文昌", createBrightnessMap("午", "子", "卯酉"));
        auxiliarySystem.put("文曲", createBrightnessMap("午", "子", "卯酉"));

        // 左辅右弼
        auxiliarySystem.put("左辅", createBrightnessMap("午", "子", "卯酉"));
        auxiliarySystem.put("右弼", createBrightnessMap("午", "子", "卯酉"));

        // 天魁天钺
        auxiliarySystem.put("天魁", createBrightnessMap("寅申", "巳亥", "子午卯酉"));
        auxiliarySystem.put("天钺", createBrightnessMap("寅申", "巳亥", "子午卯酉"));

        // 禄存
        auxiliarySystem.put("禄存", createBrightnessMap(getLuCunBrightPosition(yearStem), "子午", "寅申"));

        // 擎羊陀罗
        auxiliarySystem.put("擎羊", createBrightnessMap("寅申", "巳亥", "子午"));
        auxiliarySystem.put("陀罗", createBrightnessMap("寅申", "巳亥", "子午"));

        // 火星铃星
        auxiliarySystem.put("火星", createBrightnessMap("寅午戌", "申子辰", "巳酉丑未"));
        auxiliarySystem.put("铃星", createBrightnessMap("巳酉丑", "亥卯未", "寅午戌申"));

        Map<String, Integer> brightnessMap = auxiliarySystem.get(star);
        if (brightnessMap != null) {
            if (brightnessMap.containsKey(palaceBranch)) {
                return brightnessMap.get(palaceBranch);
            }
            for (Map.Entry<String, Integer> entry : brightnessMap.entrySet()) {
                if (entry.getKey().contains(palaceBranch)) {
                    return entry.getValue();
                }
            }
        }

        return 2; // 默认中等亮度
    }

    /**
     * 获取禄存亮度位置
     */
    private static String getLuCunBrightPosition(String yearStem) {

        Map<String, String> brightMap = new HashMap<>();
        brightMap.put("甲", "寅");
        brightMap.put("乙", "卯");
        brightMap.put("丙", "巳");
        brightMap.put("丁", "午");
        brightMap.put("戊", "巳");
        brightMap.put("己", "午");
        brightMap.put("庚", "申");
        brightMap.put("辛", "酉");
        brightMap.put("壬", "亥");
        brightMap.put("癸", "子");

        return brightMap.getOrDefault(yearStem, "午");
    }

    /**
     * 创建亮度映射
     */
    private static Map<String, Integer> createBrightnessMap(String strong, String weak, String normal) {

        Map<String, Integer> map = new HashMap<>();
        map.put(strong, 4);  // 最亮
        map.put(weak, 0);    // 最暗
        map.put(normal, 2);  // 中等
        return map;
    }

    /**
     * 计算紫微星位置(单参数版本)
     */
    private static int calculateZiWeiPosition(int lunarYear) {

        return calculateZiWeiPosition(lunarYear, 0);
    }

    /**
     * 计算紫微星位置(双参数版本)
     */
    private static int calculateZiWeiPosition(int lunarDay, int offset) {

        int position = ((lunarDay + offset) % 12) + 1;
        return position == 0 ? 12 : position;
    }

    /**
     * 计算其他主星位置
     *
     * @param starPositions 星耀位置映射
     * @param ziWeiPosition 紫微星位置
     */
    private static void calculateOtherMainStars(Map<String, Integer> starPositions, int ziWeiPosition) {
        // 天机星位置：紫微顺数2宫
        starPositions.put("天机", (ziWeiPosition + 2) % 12);

        // 太阳星位置：紫微顺数3宫
        starPositions.put("太阳", (ziWeiPosition + 3) % 12);

        // 武曲星位置：紫微顺数4宫
        starPositions.put("武曲", (ziWeiPosition + 4) % 12);

        // 天同星位置：紫微顺数5宫
        starPositions.put("天同", (ziWeiPosition + 5) % 12);

        // 廉贞星位置：紫微顺数6宫
        starPositions.put("廉贞", (ziWeiPosition + 6) % 12);

        // 天府星位置：紫微对宫
        int tianFuPosition = (ziWeiPosition + 6) % 12;
        starPositions.put("天府", tianFuPosition);

        // 太阴星位置：天府顺数1宫
        starPositions.put("太阴", (tianFuPosition + 1) % 12);

        // 贪狼星位置：天府顺数2宫
        starPositions.put("贪狼", (tianFuPosition + 2) % 12);

        // 巨门星位置：天府顺数3宫
        starPositions.put("巨门", (tianFuPosition + 3) % 12);

        // 天相星位置：天府顺数4宫
        starPositions.put("天相", (tianFuPosition + 4) % 12);

        // 天梁星位置：天府顺数5宫
        starPositions.put("天梁", (tianFuPosition + 5) % 12);

        // 七杀星位置：天府顺数6宫
        starPositions.put("七杀", (tianFuPosition + 6) % 12);

        // 破军星位置：天府逆数2宫
        starPositions.put("破军", (tianFuPosition - 2 + 12) % 12);
    }

    /**
     * 获取天魁天钺位置
     */
    private static int[] getKuiYuePositions(String yearStem) {

        Map<String, int[]> kuiYueMap = new HashMap<>();
        kuiYueMap.put("甲", new int[]{2, 8});  // 寅申
        kuiYueMap.put("乙", new int[]{3, 7});  // 卯未
        kuiYueMap.put("丙", new int[]{5, 11}); // 巳亥
        kuiYueMap.put("丁", new int[]{5, 11}); // 巳亥
        kuiYueMap.put("戊", new int[]{5, 11}); // 巳亥
        kuiYueMap.put("己", new int[]{5, 11}); // 巳亥
        kuiYueMap.put("庚", new int[]{8, 2});  // 申寅
        kuiYueMap.put("辛", new int[]{7, 3});  // 未卯
        kuiYueMap.put("壬", new int[]{11, 5}); // 亥巳
        kuiYueMap.put("癸", new int[]{11, 5}); // 亥巳

        return kuiYueMap.getOrDefault(yearStem, new int[]{0, 0});
    }

    /**
     * 获取禄存位置
     */
    private static int getLuCunPosition(String yearStem) {

        Map<String, Integer> luCunMap = new HashMap<>();
        luCunMap.put("甲", 2);  // 寅宫
        luCunMap.put("乙", 3);  // 卯宫
        luCunMap.put("丙", 5);  // 巳宫
        luCunMap.put("丁", 6);  // 午宫
        luCunMap.put("戊", 5);  // 巳宫
        luCunMap.put("己", 6);  // 午宫
        luCunMap.put("庚", 8);  // 申宫
        luCunMap.put("辛", 9);  // 酉宫
        luCunMap.put("壬", 11); // 亥宫
        luCunMap.put("癸", 0);  // 子宫

        return luCunMap.getOrDefault(yearStem, 0);
    }

    /**
     * 获取火星铃星位置
     *
     * @param dayGanZhi 日干支
     * @return 火星铃星位置数组 [火星位置, 铃星位置]
     */
    private static int[] getHuoLingPositions(String dayGanZhi) {

        try {
            String dayStem = dayGanZhi.substring(0, 1);
            String dayBranch = dayGanZhi.substring(1);

            // 火星位置映射
            Map<String, Integer> huoXingMap = new HashMap<>();
            // 甲日
            huoXingMap.put("甲子", 2);  // 寅
            huoXingMap.put("甲戌", 10); // 戌
            huoXingMap.put("甲申", 8);  // 申
            huoXingMap.put("甲午", 6);  // 午
            huoXingMap.put("甲辰", 4);  // 辰
            huoXingMap.put("甲寅", 2);  // 寅
            // 乙日
            huoXingMap.put("乙丑", 1);  // 丑
            huoXingMap.put("乙亥", 11); // 亥
            huoXingMap.put("乙酉", 9);  // 酉
            huoXingMap.put("乙未", 7);  // 未
            huoXingMap.put("乙巳", 5);  // 巳
            huoXingMap.put("乙卯", 3);  // 卯
            // 丙日
            huoXingMap.put("丙寅", 2);  // 寅
            huoXingMap.put("丙子", 0);  // 子
            huoXingMap.put("丙戌", 10); // 戌
            huoXingMap.put("丙申", 8);  // 申
            huoXingMap.put("丙午", 6);  // 午
            huoXingMap.put("丙辰", 4);  // 辰

            // 铃星位置映射
            Map<String, Integer> lingXingMap = new HashMap<>();
            // 甲日
            lingXingMap.put("甲子", 3);  // 卯
            lingXingMap.put("甲戌", 11); // 亥
            lingXingMap.put("甲申", 9);  // 酉
            lingXingMap.put("甲午", 7);  // 未
            lingXingMap.put("甲辰", 5);  // 巳
            lingXingMap.put("甲寅", 3);  // 卯
            // 乙日
            lingXingMap.put("乙丑", 2);  // 寅
            lingXingMap.put("乙亥", 0);  // 子
            lingXingMap.put("乙酉", 10); // 戌
            lingXingMap.put("乙未", 8);  // 申
            lingXingMap.put("乙巳", 6);  // 午
            lingXingMap.put("乙卯", 4);  // 辰
            // 丙日
            lingXingMap.put("丙寅", 3);  // 卯
            lingXingMap.put("丙子", 1);  // 丑
            lingXingMap.put("丙戌", 11); // 亥
            lingXingMap.put("丙申", 9);  // 酉
            lingXingMap.put("丙午", 7);  // 未
            lingXingMap.put("丙辰", 5);  // 巳

            // 计算甲级日
            int stemIndex = "甲乙丙丁戊己庚辛壬癸".indexOf(dayStem);
            int branchIndex = "子丑寅卯辰巳午未申酉戌亥".indexOf(dayBranch);
            int jiaDay = (stemIndex * 12 + branchIndex) % 60;

            // 获取对应的日干支类型
            String[] jiaTypes = {"甲子", "甲戌", "甲申", "甲午", "甲辰", "甲寅"};
            String baseType = jiaTypes[jiaDay % 6];

            // 根据日干调整位置
            int huoXingOffset = "甲乙丙丁戊己庚辛壬癸".indexOf(dayStem) * 2;
            int lingXingOffset = huoXingOffset;

            // 获取基础位置
            int huoXingBase = huoXingMap.getOrDefault(baseType, 0);
            int lingXingBase = lingXingMap.getOrDefault(baseType, 0);

            // 计算最终位置
            int huoXingPosition = (huoXingBase + huoXingOffset) % 12;
            int lingXingPosition = (lingXingBase + lingXingOffset) % 12;

            return new int[]{huoXingPosition, lingXingPosition};

        } catch (Exception e) {
            log.error("火星铃星位置计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 获取地空地劫位置
     */
    private static int[] getDiKongJiePositions(String yearBranch) {

        Map<String, int[]> positionMap = new HashMap<>();
        positionMap.put("子", new int[]{7, 1});   // 未丑
        positionMap.put("丑", new int[]{6, 0});   // 午子
        positionMap.put("寅", new int[]{5, 11});  // 巳亥
        positionMap.put("卯", new int[]{4, 10});  // 辰戌
        positionMap.put("辰", new int[]{3, 9});   // 卯酉
        positionMap.put("巳", new int[]{2, 8});   // 寅申
        positionMap.put("午", new int[]{1, 7});   // 丑未
        positionMap.put("未", new int[]{0, 6});   // 子午
        positionMap.put("申", new int[]{11, 5});  // 亥巳
        positionMap.put("酉", new int[]{10, 4});  // 戌辰
        positionMap.put("戌", new int[]{9, 3});   // 酉卯
        positionMap.put("亥", new int[]{8, 2});   // 申寅

        return positionMap.getOrDefault(yearBranch, new int[]{0, 0});
    }

    /**
     * 获取天喜天姚位置
     */
    private static int[] getXiYaoPositions(String monthBranch) {

        Map<String, int[]> positionMap = new HashMap<>();
        positionMap.put("子", new int[]{3, 9});   // 卯酉
        positionMap.put("丑", new int[]{2, 8});   // 寅申
        positionMap.put("寅", new int[]{1, 7});   // 丑未
        positionMap.put("卯", new int[]{0, 6});   // 子午
        positionMap.put("辰", new int[]{11, 5});  // 亥巳
        positionMap.put("巳", new int[]{10, 4});  // 戌辰
        positionMap.put("午", new int[]{9, 3});   // 酉卯
        positionMap.put("未", new int[]{8, 2});   // 申寅
        positionMap.put("申", new int[]{7, 1});   // 未丑
        positionMap.put("酉", new int[]{6, 0});   // 午子
        positionMap.put("戌", new int[]{5, 11});  // 巳亥
        positionMap.put("亥", new int[]{4, 10});  // 辰戌

        return positionMap.getOrDefault(monthBranch, new int[]{0, 0});
    }

    /**
     * 获取红鸾天月位置
     */
    private static int[] getHongLuanYuePositions(String yearBranch) {

        Map<String, int[]> positionMap = new HashMap<>();
        positionMap.put("子", new int[]{3, 9});   // 卯酉
        positionMap.put("丑", new int[]{2, 8});   // 寅申
        positionMap.put("寅", new int[]{1, 7});   // 丑未
        positionMap.put("卯", new int[]{0, 6});   // 子午
        positionMap.put("辰", new int[]{11, 5});  // 亥巳
        positionMap.put("巳", new int[]{10, 4});  // 戌辰
        positionMap.put("午", new int[]{9, 3});   // 酉卯
        positionMap.put("未", new int[]{8, 2});   // 申寅
        positionMap.put("申", new int[]{7, 1});   // 未丑
        positionMap.put("酉", new int[]{6, 0});   // 午子
        positionMap.put("戌", new int[]{5, 11});  // 巳亥
        positionMap.put("亥", new int[]{4, 10});  // 辰戌

        return positionMap.getOrDefault(yearBranch, new int[]{0, 0});
    }

    /**
     * 获取天刑位置
     */
    private static int getTianXingPosition(String yearBranch) {

        Map<String, Integer> positionMap = new HashMap<>();
        positionMap.put("子", 2);   // 寅
        positionMap.put("丑", 1);   // 丑
        positionMap.put("寅", 0);   // 子
        positionMap.put("卯", 11);  // 亥
        positionMap.put("辰", 10);  // 戌
        positionMap.put("巳", 9);   // 酉
        positionMap.put("午", 8);   // 申
        positionMap.put("未", 7);   // 未
        positionMap.put("申", 6);   // 午
        positionMap.put("酉", 5);   // 巳
        positionMap.put("戌", 4);   // 辰
        positionMap.put("亥", 3);   // 卯

        return positionMap.getOrDefault(yearBranch, 0);
    }

    /**
     * 获取天马位置
     */
    private static int getTianMaPosition(String yearBranch) {

        Map<String, Integer> tianMaMap = new HashMap<>();
        tianMaMap.put("申", 2);  // 寅
        tianMaMap.put("子", 5);  // 巳
        tianMaMap.put("辰", 8);  // 申
        tianMaMap.put("寅", 11); // 戌
        tianMaMap.put("午", 2);  // 寅
        tianMaMap.put("戌", 5);  // 巳
        tianMaMap.put("酉", 8);  // 申
        tianMaMap.put("丑", 11); // 戌
        tianMaMap.put("巳", 2);  // 寅
        tianMaMap.put("亥", 5);  // 巳
        tianMaMap.put("卯", 8);  // 申
        tianMaMap.put("未", 11); // 戌

        Integer position = tianMaMap.get(yearBranch);
        if (position == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "无效的年支");
        }
        return position;
    }

    /**
     * 计算长生十二神
     *
     * @param yearGanZhi 年干支
     * @return 十二宫位对应的长生十二神
     */
    public static Map<Integer, String> calculateTwelveGods(String yearGanZhi) {

        try {
            String yearStem = yearGanZhi.substring(0, 1);
            Map<Integer, String> twelveGods = new HashMap<>();

            // 定义长生十二神顺序
            String[] gods = {"长生", "沐浴", "冠带", "临官", "帝旺", "衰",
                    "病", "死", "墓", "绝", "胎", "养"};

            // 定义阳干和阴干的起始宫位
            Map<String, Integer> startPositions = new HashMap<>();
            // 阳干
            startPositions.put("甲", 2);  // 长生在寅
            startPositions.put("丙", 5);  // 长生在巳
            startPositions.put("戊", 5);  // 长生在巳
            startPositions.put("庚", 8);  // 长生在申
            startPositions.put("壬", 11); // 长生在亥
            // 阴干
            startPositions.put("乙", 8);  // 长生在申
            startPositions.put("丁", 11); // 长生在亥
            startPositions.put("己", 11); // 长生在亥
            startPositions.put("辛", 2);  // 长生在寅
            startPositions.put("癸", 5);  // 长生在巳

            // 获取起始宫位
            int startPos = startPositions.getOrDefault(yearStem, 0);

            // 判断阴阳干
            boolean isYang = "甲丙戊庚壬".contains(yearStem);

            // 填充十二神
            for (int i = 0; i < 12; i++) {
                int position;
                if (isYang) {
                    // 阳干顺行
                    position = (startPos + i) % 12;
                } else {
                    // 阴干逆行
                    position = (startPos - i + 12) % 12;
                }
                twelveGods.put(position, gods[i]);
            }

            return twelveGods;

        } catch (Exception e) {
            log.error("长生十二神计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 计算命主身主
     *
     * @param mingGongPosition 命宫位置
     * @param yearGanZhi 年干支
     * @return 命主身主信息
     */
    public static Map<String, String> calculateMasters(int mingGongPosition, String yearGanZhi) {

        try {
            Map<String, String> masters = new HashMap<>();
            String yearStem = yearGanZhi.substring(0, 1);
            String yearBranch = yearGanZhi.substring(1);

            // 获取命宫地支
            String[] branches = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
            String mingGongBranch = branches[mingGongPosition];

            // 计算命主
            String mingZhu = calculateMingZhu(mingGongBranch);
            masters.put("命主", mingZhu);

            // 计算身主
            String shenZhu = calculateShenZhu(yearStem);
            masters.put("身主", shenZhu);

            // 计算五行属性
            String wuXing = calculateWuXing(mingZhu, shenZhu);
            masters.put("五行", wuXing);

            return masters;

        } catch (Exception e) {
            log.error("命主身主计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 计算命主
     */
    private static String calculateMingZhu(String mingGongBranch) {

        Map<String, String> mingZhuMap = new HashMap<>();
        mingZhuMap.put("寅", "太阳");
        mingZhuMap.put("卯", "太阴");
        mingZhuMap.put("辰", "廉贞");
        mingZhuMap.put("巳", "天机");
        mingZhuMap.put("午", "天同");
        mingZhuMap.put("未", "天梁");
        mingZhuMap.put("申", "天同");
        mingZhuMap.put("酉", "太阴");
        mingZhuMap.put("戌", "贪狼");
        mingZhuMap.put("亥", "巨门");
        mingZhuMap.put("子", "天机");
        mingZhuMap.put("丑", "武曲");

        return mingZhuMap.getOrDefault(mingGongBranch, "紫微");
    }

    /**
     * 计算身主
     */
    private static String calculateShenZhu(String yearStem) {

        Map<String, String> shenZhuMap = new HashMap<>();
        shenZhuMap.put("甲", "廉贞");
        shenZhuMap.put("乙", "天机");
        shenZhuMap.put("丙", "天同");
        shenZhuMap.put("丁", "太阳");
        shenZhuMap.put("戊", "武曲");
        shenZhuMap.put("己", "贪狼");
        shenZhuMap.put("庚", "太阴");
        shenZhuMap.put("辛", "巨门");
        shenZhuMap.put("壬", "天梁");
        shenZhuMap.put("癸", "七杀");

        return shenZhuMap.getOrDefault(yearStem, "紫微");
    }

    /**
     * 计算五行属性
     */
    private static String calculateWuXing(String mingZhu, String shenZhu) {
        // 定义星耀五行属性
        Map<String, String> starWuXing = new HashMap<>();
        starWuXing.put("紫微", "土");
        starWuXing.put("天机", "木");
        starWuXing.put("太阳", "火");
        starWuXing.put("武曲", "金");
        starWuXing.put("天同", "水");
        starWuXing.put("廉贞", "火");
        starWuXing.put("天府", "土");
        starWuXing.put("太阴", "水");
        starWuXing.put("贪狼", "水");
        starWuXing.put("巨门", "水");
        starWuXing.put("天相", "木");
        starWuXing.put("天梁", "火");
        starWuXing.put("七杀", "金");
        starWuXing.put("破军", "水");

        // 获取命主和身主的五行
        String mingWuXing = starWuXing.getOrDefault(mingZhu, "土");
        String shenWuXing = starWuXing.getOrDefault(shenZhu, "土");

        // 返回组合五行
        return mingWuXing + shenWuXing;
    }

    /**
     * 分析星耀组合
     */
    public static Map<String, List<String>> analyzeStarCombinations(Map<String, Integer> starPositions) {

        try {
            Map<String, List<String>> combinations = new HashMap<>();
            combinations.put("trine", new ArrayList<>());
            combinations.put("opposition", new ArrayList<>());

            // 检查三方组合
            for (Map.Entry<String, Integer> entry1 : starPositions.entrySet()) {
                for (Map.Entry<String, Integer> entry2 : starPositions.entrySet()) {
                    if (entry1.getKey().equals(entry2.getKey())) continue;

                    int pos1 = entry1.getValue();
                    int pos2 = entry2.getValue();

                    // 三方位置相差4个宫位
                    if (Math.abs(pos1 - pos2) == 4 || Math.abs(pos1 - pos2) == 8) {
                        combinations.get("trine").add(entry1.getKey() + "-" + entry2.getKey());
                    }
                    // 对宫位置相差6个宫位
                    else if (Math.abs(pos1 - pos2) == 6) {
                        combinations.get("opposition").add(entry1.getKey() + "-" + entry2.getKey());
                    }
                }
            }

            return combinations;

        } catch (Exception e) {
            log.error("星耀组合分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析星耀冲会关系
     *
     * @param starPositions 星耀位置映射
     * @return 冲会关系列表
     */
    public static List<String> analyzeStarAspects(Map<String, Integer> starPositions) {

        try {
            List<String> aspects = new ArrayList<>();

            // 分析对宫关系
            analyzeOppositions(starPositions, aspects);

            // 分析三合关系
            analyzeGrandTrines(starPositions, aspects);

            // 分析六合关系
            analyzeSixHarmonies(starPositions, aspects);

            return aspects;

        } catch (Exception e) {
            log.error("星耀冲会关系分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析对宫关系
     */
    private static void analyzeOppositions(
            Map<String, Integer> starPositions, List<String> aspects) {
        // 定义对宫关系
        Map<Set<String>, String> oppositionPairs = new HashMap<>();
        oppositionPairs.put(new HashSet<>(Arrays.asList("紫微", "天府")), "紫府对冲");
        oppositionPairs.put(new HashSet<>(Arrays.asList("太阳", "太阴")), "日月照对");
        oppositionPairs.put(new HashSet<>(Arrays.asList("天机", "巨门")), "机巨相冲");
        oppositionPairs.put(new HashSet<>(Arrays.asList("武曲", "天相")), "武相对照");

        // 检查每对星耀
        for (Map.Entry<String, Integer> entry1 : starPositions.entrySet()) {
            for (Map.Entry<String, Integer> entry2 : starPositions.entrySet()) {
                if (entry1.getKey().compareTo(entry2.getKey()) < 0) {
                    // 检查是否对宫
                    if (Math.abs(entry1.getValue() - entry2.getValue()) == 6) {
                        Set<String> pair = new HashSet<>(Arrays.asList(entry1.getKey(), entry2.getKey()));
                        String aspect = oppositionPairs.get(pair);
                        if (aspect != null) {
                            aspects.add(aspect);
                        }
                    }
                }
            }
        }
    }

    /**
     * 分析三合关系
     */
    private static void analyzeGrandTrines(
            Map<String, Integer> starPositions, List<String> aspects) {
        // 定义三合关系
        int[][] trineGroups = {
                {0, 4, 8},   // 子辰申
                {1, 5, 9},   // 丑巳酉
                {2, 6, 10},  // 寅午戌
                {3, 7, 11}   // 卯未亥
        };

        // 定义特殊三合组合
        Map<Set<String>, String> trineAspects = new HashMap<>();
        trineAspects.put(new HashSet<>(Arrays.asList("紫微", "武曲", "贪狼")), "紫武贪合局");
        trineAspects.put(new HashSet<>(Arrays.asList("天机", "天梁", "破军")), "机梁破合局");

        // 检查每个三合组
        for (int[] trineGroup : trineGroups) {
            Set<String> trineStars = new HashSet<>();
            for (int position : trineGroup) {
                for (Map.Entry<String, Integer> entry : starPositions.entrySet()) {
                    if (entry.getValue() == position) {
                        trineStars.add(entry.getKey());
                    }
                }
            }

            // 检查是否形成特殊三合
            for (Map.Entry<Set<String>, String> entry : trineAspects.entrySet()) {
                if (trineStars.containsAll(entry.getKey())) {
                    aspects.add(entry.getValue());
                }
            }
        }
    }

    /**
     * 分析六合关系
     */
    private static void analyzeSixHarmonies(
            Map<String, Integer> starPositions, List<String> aspects) {
        // 定义六合关系
        int[][] harmonyPairs = {
                {0, 11},  // 子亥
                {1, 10},  // 丑戌
                {2, 9},   // 寅酉
                {3, 8},   // 卯申
                {4, 7},   // 辰未
                {5, 6}    // 巳午
        };

        // 定义特殊六合组合
        Map<Set<String>, String> harmonyAspects = new HashMap<>();
        harmonyAspects.put(new HashSet<>(Arrays.asList("紫微", "天机")), "紫机合照");
        harmonyAspects.put(new HashSet<>(Arrays.asList("太阳", "武曲")), "日武同辉");
        harmonyAspects.put(new HashSet<>(Arrays.asList("天府", "太阴")), "府阴合明");

        // 检查每对六合位置
        for (int[] pair : harmonyPairs) {
            Set<String> harmonyStars = new HashSet<>();
            for (int position : pair) {
                for (Map.Entry<String, Integer> entry : starPositions.entrySet()) {
                    if (entry.getValue() == position) {
                        harmonyStars.add(entry.getKey());
                    }
                }
            }

            // 检查是否形成特殊六合
            for (Map.Entry<Set<String>, String> entry : harmonyAspects.entrySet()) {
                if (harmonyStars.containsAll(entry.getKey())) {
                    aspects.add(entry.getValue());
                }
            }
        }
    }

    /**
     * 分析星耀庙旺
     *
     * @param starPositions 星耀位置映射
     * @param yearGanZhi 年干支
     * @return 星耀庙旺状态
     */
    public static Map<String, String> analyzeStarStrength(
            Map<String, Integer> starPositions, String yearGanZhi) {

        try {
            Map<String, String> strength = new HashMap<>();

            // 获取地支
            String[] branches = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

            // 分析每个星耀的庙旺
            for (Map.Entry<String, Integer> entry : starPositions.entrySet()) {
                String star = entry.getKey();
                int position = entry.getValue();
                String branch = branches[position];

                // 获取星耀庙旺状态
                String status = getStarStrength(star, branch);
                strength.put(star, status);
            }

            return strength;

        } catch (Exception e) {
            log.error("星耀庙旺分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 获取星耀庙旺状态
     */
    private static String getStarStrength(String star, String branch) {
        // 定义星耀庙旺
        Map<String, Map<String, String>> strengthMap = new HashMap<>();

        // 紫微星系
        Map<String, String> ziWei = new HashMap<>();
        ziWei.put("午", "庙");
        ziWei.put("巳", "旺");
        ziWei.put("申", "陷");
        strengthMap.put("紫微", ziWei);

        Map<String, String> tianJi = new HashMap<>();
        tianJi.put("巳", "庙");
        tianJi.put("午", "旺");
        tianJi.put("申", "陷");
        strengthMap.put("天机", tianJi);

        Map<String, String> taiYang = new HashMap<>();
        taiYang.put("巳", "庙");
        taiYang.put("午", "旺");
        taiYang.put("亥", "陷");
        strengthMap.put("太阳", taiYang);

        Map<String, String> wuQu = new HashMap<>();
        wuQu.put("申", "庙");
        wuQu.put("酉", "旺");
        wuQu.put("寅", "陷");
        strengthMap.put("武曲", wuQu);

        Map<String, String> tianTong = new HashMap<>();
        tianTong.put("午", "庙");
        tianTong.put("巳", "旺");
        tianTong.put("子", "陷");
        strengthMap.put("天同", tianTong);

        // 天府星系
        Map<String, String> tianFu = new HashMap<>();
        tianFu.put("亥", "庙");
        tianFu.put("子", "旺");
        tianFu.put("巳", "陷");
        strengthMap.put("天府", tianFu);

        Map<String, String> taiYin = new HashMap<>();
        taiYin.put("酉", "庙");
        taiYin.put("申", "旺");
        taiYin.put("卯", "陷");
        strengthMap.put("太阴", taiYin);

        Map<String, String> tanLang = new HashMap<>();
        tanLang.put("寅", "庙");
        tanLang.put("卯", "旺");
        tanLang.put("申", "陷");
        strengthMap.put("贪狼", tanLang);

        Map<String, String> juMen = new HashMap<>();
        juMen.put("辰", "庙");
        juMen.put("巳", "旺");
        juMen.put("戌", "陷");
        strengthMap.put("巨门", juMen);

        Map<String, String> tianXiang = new HashMap<>();
        tianXiang.put("未", "庙");
        tianXiang.put("申", "旺");
        tianXiang.put("丑", "陷");
        strengthMap.put("天相", tianXiang);

        Map<String, String> tianLiang = new HashMap<>();
        tianLiang.put("戌", "庙");
        tianLiang.put("亥", "旺");
        tianLiang.put("辰", "陷");
        strengthMap.put("天梁", tianLiang);

        Map<String, String> qiSha = new HashMap<>();
        qiSha.put("申", "庙");
        qiSha.put("酉", "旺");
        qiSha.put("寅", "陷");
        strengthMap.put("七杀", qiSha);

        Map<String, String> poJun = new HashMap<>();
        poJun.put("午", "庙");
        poJun.put("未", "旺");
        poJun.put("子", "陷");
        strengthMap.put("破军", poJun);

        // 获取星耀庙旺状态
        Map<String, String> starStrength = strengthMap.get(star);
        if (starStrength != null) {
            return starStrength.getOrDefault(branch, "平");
        }

        return "平";
    }

    /**
     * 计算星耀关系
     */
    public static Map<String, List<String>> calculateRelations(Map<String, Integer> starPositions) {

        try {
            Map<String, List<String>> relations = new HashMap<>();
            relations.put("trine", new ArrayList<>());
            relations.put("opposition", new ArrayList<>());
            relations.put("convergence", new ArrayList<>());

            // 检查三方、对宫和会合关系
            for (Map.Entry<String, Integer> entry1 : starPositions.entrySet()) {
                for (Map.Entry<String, Integer> entry2 : starPositions.entrySet()) {
                    if (entry1.getKey().equals(entry2.getKey())) continue;

                    int pos1 = entry1.getValue();
                    int pos2 = entry2.getValue();

                    // 三方位置相差4个宫位
                    if (Math.abs(pos1 - pos2) == 4 || Math.abs(pos1 - pos2) == 8) {
                        relations.get("trine").add(entry1.getKey() + "与" + entry2.getKey() + "三方");
                    }
                    // 对宫位置相差6个宫位
                    else if (Math.abs(pos1 - pos2) == 6) {
                        relations.get("opposition").add(entry1.getKey() + "与" + entry2.getKey() + "对宫");
                    }
                    // 同宫会合
                    else if (pos1 == pos2) {
                        relations.get("convergence").add(entry1.getKey() + "与" + entry2.getKey() + "会合");
                    }
                }
            }

            return relations;

        } catch (Exception e) {
            log.error("星耀关系计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 获取年支
     */
    private static String getYearBranch(int lunarYear) {

        String[] branches = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        return branches[(lunarYear - 4) % 12];
    }

    /**
     * 计算紫微星系星耀位置
     */
    public static List<Star> calculateZiWeiStars(int lunarYear, int lunarMonth, int lunarDay, int birthHour) {

        List<Star> stars = new ArrayList<>();

        // 计算紫微星位置
        int ziWeiPosition = calculateZiWeiPosition(lunarYear, birthHour);
        Star ziWei = new Star(StarName.ZIWEI, StarType.MAJOR);
        ziWei.setPosition(ziWeiPosition);
        stars.add(ziWei);

        // 计算天机星位置（紫微顺三）
        Star tianJi = new Star(StarName.TIANJI, StarType.MAJOR);
        tianJi.setPosition((ziWeiPosition + 3) % 12);
        stars.add(tianJi);

        // 计算太阳星位置（天机顺六）
        Star taiYang = new Star(StarName.TAIYANG, StarType.MAJOR);
        taiYang.setPosition((ziWeiPosition + 9) % 12);
        stars.add(taiYang);

        // 计算武曲星位置（太阳顺四）
        Star wuQu = new Star(StarName.WUQU, StarType.MAJOR);
        wuQu.setPosition((ziWeiPosition + 1) % 12);
        stars.add(wuQu);

        // 计算天同星位置（武曲顺一）
        Star tianTong = new Star(StarName.TIANTONG, StarType.MAJOR);
        tianTong.setPosition((ziWeiPosition + 2) % 12);
        stars.add(tianTong);

        // 计算廉贞星位置（天同顺六）
        Star lianZhen = new Star(StarName.LIANZHEN, StarType.MAJOR);
        lianZhen.setPosition((ziWeiPosition + 8) % 12);
        stars.add(lianZhen);

        return stars;
    }

    /**
     * 计算天府星系星耀位置
     */
    public static List<Star> calculateTianFuStars(int lunarYear, int lunarMonth, int lunarDay, int birthHour) {

        List<Star> stars = new ArrayList<>();

        // 计算天府星位置（与紫微星对宫）
        int ziWeiPosition = calculateZiWeiPosition(lunarYear);
        int tianFuPosition = (ziWeiPosition + 6) % 12;
        Star tianFu = new Star(StarName.TIANFU, StarType.MAJOR);
        tianFu.setPosition(tianFuPosition);
        stars.add(tianFu);

        // 计算太阴星位置（天府逆四）
        Star taiYin = new Star(StarName.TAIYIN, StarType.MAJOR);
        taiYin.setPosition((tianFuPosition + 8) % 12);
        stars.add(taiYin);

        // 计算贪狼星位置（太阴逆二）
        Star tanLang = new Star(StarName.TANLANG, StarType.MAJOR);
        tanLang.setPosition((tianFuPosition + 6) % 12);
        stars.add(tanLang);

        // 计算巨门星位置（贪狼逆二）
        Star juMen = new Star(StarName.JUMEN, StarType.MAJOR);
        juMen.setPosition((tianFuPosition + 4) % 12);
        stars.add(juMen);

        // 计算天相星位置（巨门逆三）
        Star tianXiang = new Star(StarName.TIANXIANG, StarType.MAJOR);
        tianXiang.setPosition((tianFuPosition + 1) % 12);
        stars.add(tianXiang);

        // 计算天梁星位置（天相逆三）
        Star tianLiang = new Star(StarName.TIANLIANG, StarType.MAJOR);
        tianLiang.setPosition((tianFuPosition + 10) % 12);
        stars.add(tianLiang);

        // 计算七杀星位置（天梁逆三）
        Star qiSha = new Star(StarName.QISHA, StarType.MAJOR);
        qiSha.setPosition((tianFuPosition + 7) % 12);
        stars.add(qiSha);

        // 计算破军星位置（七杀逆三）
        Star poJun = new Star(StarName.POJUN, StarType.MAJOR);
        poJun.setPosition((tianFuPosition + 4) % 12);
        stars.add(poJun);

        return stars;
    }

    /**
     * 计算其他星耀位置
     */
    public static List<Star> calculateOtherStars(int lunarYear, int lunarMonth, int lunarDay, int birthHour) {

        List<Star> stars = new ArrayList<>();

        // 计算文昌星位置
        String yearBranch = getYearBranch(lunarYear);
        int yearIndex = "子丑寅卯辰巳午未申酉戌亥".indexOf(yearBranch);
        int wenChangPosition = (2 - yearIndex + 12) % 12;
        Star wenChang = new Star(StarName.WENCHANG, StarType.MINOR);
        wenChang.setPosition(wenChangPosition);
        stars.add(wenChang);

        // 计算文曲星位置
        int wenQuPosition = (10 + yearIndex) % 12;
        Star wenQu = new Star(StarName.WENQU, StarType.MINOR);
        wenQu.setPosition(wenQuPosition);
        stars.add(wenQu);

        // 计算左辅星位置
        int zuoFuPosition = (2 - (lunarMonth - 1) + 12) % 12;
        Star zuoFu = new Star(StarName.ZUOFU, StarType.MINOR);
        zuoFu.setPosition(zuoFuPosition);
        stars.add(zuoFu);

        // 计算右弼星位置
        int youBiPosition = (10 + (lunarMonth - 1)) % 12;
        Star youBi = new Star(StarName.YOUBI, StarType.MINOR);
        youBi.setPosition(youBiPosition);
        stars.add(youBi);

        return stars;
    }

    /**
     * 计算紫微星系
     */
    private static List<Star> calculateZiWeiStars(int fiveElementsClass, int timeIndex) {

        List<Star> stars = new ArrayList<>();

        // 计算紫微星位置
        Star ziWei = new Star(StarName.ZIWEI, StarType.MAJOR);
        ziWei.setName(StarName.ZIWEI);
        stars.add(ziWei);

        // 计算天机星位置
        Star tianJi = new Star(StarName.TIANJI, StarType.MAJOR);
        tianJi.setName(StarName.TIANJI);
        stars.add(tianJi);

        // 计算太阳星位置
        Star taiYang = new Star(StarName.TAIYANG, StarType.MAJOR);
        taiYang.setName(StarName.TAIYANG);
        stars.add(taiYang);

        // 计算武曲星位置
        Star wuQu = new Star(StarName.WUQU, StarType.MAJOR);
        wuQu.setName(StarName.WUQU);
        stars.add(wuQu);

        // 计算天同星位置
        Star tianTong = new Star(StarName.TIANTONG, StarType.MAJOR);
        tianTong.setName(StarName.TIANTONG);
        stars.add(tianTong);

        // 计算廉贞星位置
        Star lianZhen = new Star(StarName.LIANZHEN, StarType.MAJOR);
        lianZhen.setName(StarName.LIANZHEN);
        stars.add(lianZhen);

        return stars;
    }

    /**
     * 计算天府星系
     */
    private static List<Star> calculateTianFuStars(EarthlyBranch ziWeiLocation) {

        List<Star> stars = new ArrayList<>();

        // 计算天府星位置
        Star tianFu = new Star(StarName.TIANFU, StarType.MAJOR);
        tianFu.setName(StarName.TIANFU);
        stars.add(tianFu);

        // 计算太阴星位置
        Star taiYin = new Star(StarName.TAIYIN, StarType.MAJOR);
        taiYin.setName(StarName.TAIYIN);
        stars.add(taiYin);

        // 计算贪狼星位置
        Star tanLang = new Star(StarName.TANLANG, StarType.MAJOR);
        tanLang.setName(StarName.TANLANG);
        stars.add(tanLang);

        // 计算巨门星位置
        Star juMen = new Star(StarName.JUMEN, StarType.MAJOR);
        juMen.setName(StarName.JUMEN);
        stars.add(juMen);

        // 计算天相星位置
        Star tianXiang = new Star(StarName.TIANXIANG, StarType.MAJOR);
        tianXiang.setName(StarName.TIANXIANG);
        stars.add(tianXiang);

        // 计算天梁星位置
        Star tianLiang = new Star(StarName.TIANLIANG, StarType.MAJOR);
        tianLiang.setName(StarName.TIANLIANG);
        stars.add(tianLiang);

        // 计算七杀星位置
        Star qiSha = new Star(StarName.QISHA, StarType.MAJOR);
        qiSha.setName(StarName.QISHA);
        stars.add(qiSha);

        // 计算破军星位置
        Star poJun = new Star(StarName.POJUN, StarType.MAJOR);
        poJun.setName(StarName.POJUN);
        stars.add(poJun);

        return stars;
    }

    /**
     * 计算其他星耀
     */
    private static List<Star> calculateOtherStars(EarthlyBranch monthBranch) {

        List<Star> stars = new ArrayList<>();

        // 计算文昌星位置
        Star wenChang = new Star(StarName.WENCHANG, StarType.MINOR);
        wenChang.setName(StarName.WENCHANG);
        stars.add(wenChang);

        // 计算文曲星位置
        Star wenQu = new Star(StarName.WENQU, StarType.MINOR);
        wenQu.setName(StarName.WENQU);
        stars.add(wenQu);

        // 计算左辅星位置
        Star zuoFu = new Star(StarName.ZUOFU, StarType.MINOR);
        zuoFu.setName(StarName.ZUOFU);
        stars.add(zuoFu);

        // 计算右弼星位置
        Star youBi = new Star(StarName.YOUBI, StarType.MINOR);
        youBi.setName(StarName.YOUBI);
        stars.add(youBi);

        return stars;
    }

}
