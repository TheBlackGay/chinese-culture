package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Gender;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.utils.CalendarConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 核心计算器
 */
@Slf4j
public class CoreCalculator {

    private static final String[] EARTHLY_BRANCHES = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    private static final String[] PALACE_NAMES = {"命宫", "兄弟", "夫妻", "子女", "财帛", "疾厄", "迁移", "交友", "官禄", "田宅", "福德", "父母"};

    /**
     * 计算命盘
     */
    public static Astrolabe calculate(int lunarYear, int lunarMonth, int lunarDay, int birthHour, String genderStr) {
        // 验证输入
        validateInput(lunarYear, lunarMonth, lunarDay, birthHour, genderStr);

        try {
            Astrolabe astrolabe = new Astrolabe();

            // 设置农历日期
            String lunarDate = String.format("%d年%d月%d日", lunarYear, lunarMonth, lunarDay);
            astrolabe.setLunarDate(lunarDate);

            // 计算并设置年干
            String yearStem = CalendarConverter.getYearStem(lunarYear);
            astrolabe.setYearStem(yearStem);

            // 设置阳历日期
            int[] solarDate = CalendarConverter.lunarToSolar(lunarYear, lunarMonth, lunarDay);
            String solarDateStr = String.format("%d年%d月%d日", solarDate[0], solarDate[1], solarDate[2]);
            astrolabe.setSolarDate(solarDateStr);

            // 设置性别和时辰
            Gender gender = parseGender(genderStr);
            astrolabe.setGender(gender);
            astrolabe.setBirthHour(birthHour);

            // 计算命宫位置
            int mingGongPosition = calculateMingGongPosition(lunarMonth, birthHour);

            // 创建十二宫
            List<Palace> palaces = createTwelvePalaces(mingGongPosition);
            astrolabe.setPalaces(palaces);

            // 计算星耀位置
            List<Star> stars = calculateStarPositions(lunarYear, lunarMonth, lunarDay, mingGongPosition);
            astrolabe.setStars(stars);

            // 分配星耀到宫位
            distributeStars(stars, palaces);

            // 计算四化
            calculateTransformations(astrolabe);

            return astrolabe;

        } catch (Exception e) {
            log.error("计算命盘时发生错误", e);
            throw new BusinessException(ResultCode.ERROR, "计算命盘时发生错误");
        }
    }

    /**
     * 验证输入参数
     */
    private static void validateInput(int lunarYear, int lunarMonth, int lunarDay, int birthHour, String genderStr) {
        if (lunarYear < 1900 || lunarYear > 2100) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "年份必须在1900-2100之间");
        }
        if (lunarMonth < 1 || lunarMonth > 12) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "月份必须在1-12之间");
        }
        if (lunarDay < 1 || lunarDay > 30) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "日期必须在1-30之间");
        }
        if (birthHour < 1 || birthHour > 12) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "时辰必须在1-12之间");
        }
        if (genderStr == null || !genderStr.equals("男") && !genderStr.equals("女")) {
            throw new BusinessException(ResultCode.PARAM_ERROR, "性别只能是男或女");
        }
    }

    /**
     * 计算命宫位置
     * 命宫公式：以寅宫为首，寅宫为0，卯宫为1，依次类推
     * 命宫 = 12 - ((月数 + 时辰 - 1) % 12)
     * 如果结果为12，则为0
     */
    private static int calculateMingGongPosition(int lunarMonth, int birthHour) {
        int position = 12 - ((lunarMonth + birthHour - 1) % 12);
        if (position == 12) {
            position = 0;
        }
        return position;
    }

    /**
     * 创建十二宫
     */
    private static List<Palace> createTwelvePalaces(int mingGongPosition) {
        List<Palace> palaces = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Palace palace = new Palace();
            palace.setIndex(i);

            // 计算宫位地支，从寅宫开始
            int branchIndex = (mingGongPosition + i + 2) % 12;  // +2 是因为寅宫在地支中的索引是2
            palace.setBranch(EARTHLY_BRANCHES[branchIndex]);

            // 设置宫位名称
            palace.setName(PALACE_NAMES[i]);

            // 设置是否为命宫
            palace.setMing(i == 0);

            // 初始化星耀和四化列表
            palace.setStars(new ArrayList<>());
            palace.setMutagens(new ArrayList<>());

            palaces.add(palace);
        }

        return palaces;
    }

    /**
     * 计算星耀位置
     */
    private static List<Star> calculateStarPositions(int year, int month, int day, int mingGongPosition) {
        List<Star> stars = new ArrayList<>();

        // 计算紫微星系
        stars.addAll(StarCalculator.calculateZiWeiStars(year, month, day, mingGongPosition));

        // 计算天府星系
        stars.addAll(StarCalculator.calculateTianFuStars(year, month, day, mingGongPosition));

        // 计算其他星耀
        stars.addAll(StarCalculator.calculateOtherStars(year, month, day, mingGongPosition));

        return stars;
    }

    /**
     * 分配星耀到宫位
     */
    private static void distributeStars(List<Star> stars, List<Palace> palaces) {
        for (Star star : stars) {
            int position = star.getPosition();
            if (position >= 0 && position < 12) {
                Palace palace = palaces.get(position);
                star.setBranch(palace.getBranch());
                palace.getStars().add(star);
            }
        }
    }

    /**
     * 计算四化
     */
    private static void calculateTransformations(Astrolabe astrolabe) {
        List<Palace> palaces = astrolabe.getPalaces();
        List<Star> stars = astrolabe.getStars();

        // 获取年干
        String yearStem = astrolabe.getYearStem();
        if (yearStem == null || yearStem.isEmpty()) {
            log.error("年干不能为空");
            throw new BusinessException(ResultCode.ERROR, "年干不能为空");
        }

        // 根据年干确定四化星
        Map<String, List<String>> transformations = MutagenCalculator.calculateTransformations(HeavenlyStem.fromDescription(yearStem));

        // 为每个宫位的星耀添加四化信息
        for (Palace palace : palaces) {
            for (Star star : palace.getStars()) {
                List<String> transformation = transformations.get(star.getName());
                if (transformation != null && !transformation.isEmpty()) {
                    palace.getMutagens().addAll(transformation);
                }
            }
        }
    }

    private static Gender parseGender(String genderStr) {
        if (genderStr == null || genderStr.trim().isEmpty()) {
            throw new IllegalArgumentException("性别不能为空");
        }
        return Gender.valueOf(genderStr.toUpperCase());
    }

    private static HeavenlyStem parseHeavenlyStem(String stemStr) {
        if (stemStr == null || stemStr.trim().isEmpty()) {
            throw new IllegalArgumentException("天干不能为空");
        }
        return HeavenlyStem.valueOf(stemStr.toUpperCase());
    }
}
