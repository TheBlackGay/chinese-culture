package com.chinese.culture.admin.common.core.iztro;

import com.chinese.culture.admin.common.core.iztro.calculator.*;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.RawDateBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import com.chinese.culture.admin.common.core.iztro.interpreter.StarCombinationInterpreter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 紫微斗数主控制器
 */
@Slf4j
@Component
public class AstroController {

    @Resource
    private StarCombinationInterpreter starCombinationInterpreter;

    @Resource
    private DateCalculator dateCalculator;

    /**
     * 生成紫微斗数星盘
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @param timeIndex 时辰索引（0-11）
     * @param gender 性别（1为阳男阴女，0为阴男阳女）
     * @return 十二宫位列表
     */
    public List<PalaceBO> generateAstrolabe(int year, int month, int day, int timeIndex, Integer gender) {
        // 解析阳历日期
        RawDateBO.SolarDate solar = new RawDateBO.SolarDate();
        solar.setYear(year);
        solar.setMonth(month);
        solar.setDay(day);

        // 1. 获取农历日期和干支信息
        RawDateBO.LunarDate lunar = dateCalculator.solar2lunar(solar);
        RawDateBO.ChineseDate chineseDate = dateCalculator.getChineseDate(solar, timeIndex);

        // 2. 计算五行局
        int fiveElementsClass = FiveElementsCalculator.calculate(
                chineseDate.getYearGan(),
                chineseDate.getYearZhi()
        );

        // 3. 计算命宫和身宫
        EarthlyBranch monthBranch = EarthlyBranch.fromIndex(chineseDate.getMonthZhi());
        EarthlyBranch mingGongLocation = PalaceCalculator.calculateMingGongLocation(monthBranch, timeIndex);
        EarthlyBranch shenGongLocation = PalaceCalculator.calculateShenGongLocation(monthBranch, timeIndex);

        // 4. 计算十二宫位
        List<PalaceBO> palaces = new ArrayList<>(12);
        EarthlyBranch[] palaceLocations = PalaceCalculator.calculateTwelvePalaces(mingGongLocation);

        // 5. 初始化十二宫位
        for (int i = 0; i < 12; i++) {
            PalaceBO palace = new PalaceBO();
            palace.setIndex(i);
            palace.setName(PalaceCalculator.getPalaceName(i));
            palace.setEarthlyBranch(palaceLocations[i]);
            palaces.add(palace);
        }

        // 6. 计算主星位置
        calculateMajorStars(palaces, fiveElementsClass, lunar.getDay(), chineseDate);

        // 7. 计算辅星位置
        calculateMinorStars(palaces, lunar.getYear(), lunar.getMonth(), lunar.getDay(), timeIndex);

        // 8. 计算杂耀位置
        calculateMiscStars(palaces, chineseDate);

        // 9. 设置命宫和身宫标记
        for (PalaceBO palace : palaces) {
            if (palace.getEarthlyBranch() == mingGongLocation) {
                palace.setMingGong(true);
            }
            if (palace.getEarthlyBranch() == shenGongLocation) {
                palace.setShenGong(true);
            }
        }

        // 10. 计算大限
        calculateMajorLimit(palaces, gender == 1, year, fiveElementsClass);

        // 11. 计算小限
        calculateMinorLimit(palaces, gender == 1, year);

        // 12. 计算流年
        int currentYear = LocalDateTime.now().getYear();
        calculateYearlyFlow(palaces, currentYear);

        return palaces;
    }

    /**
     * 计算主星位置
     */
    private void calculateMajorStars(List<PalaceBO> palaces, int fiveElementsClass, int lunarDay, RawDateBO.ChineseDate chineseDate) {
        // 计算紫微星系
        EarthlyBranch ziWeiLocation = StarLocationCalculator.calculateZiWeiLocation(fiveElementsClass, lunarDay);

        // 计算天府星系
        EarthlyBranch tianFuLocation = StarLocationCalculator.calculateTianFuLocation(ziWeiLocation);

        // 获取年干
        HeavenlyStem yearStem = HeavenlyStem.fromDescription(chineseDate.getYearHeavenlyStem());

        // 将主星添加到对应宫位
        for (PalaceBO palace : palaces) {
            EarthlyBranch branch = palace.getEarthlyBranch();

            // 设置紫微星
            if (branch == ziWeiLocation) {
                StarBO ziWei = new StarBO(StarName.ZIWEI, StarType.MAJOR);
                ziWei.setBrightness(BrightnessCalculator.calculateBrightness(ziWei, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, ziWei);
                if (mutagen != null) {
                    ziWei.addMutagen(mutagen);
                }
                palace.addMajorStar(ziWei);
            }

            // 设置天府星
            if (branch == tianFuLocation) {
                StarBO tianFu = new StarBO(StarName.TIANFU, StarType.MAJOR);
                tianFu.setBrightness(BrightnessCalculator.calculateBrightness(tianFu, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, tianFu);
                if (mutagen != null) {
                    tianFu.addMutagen(mutagen);
                }
                palace.addMajorStar(tianFu);
            }
        }
    }

    /**
     * 计算辅星位置
     */
    private void calculateMinorStars(List<PalaceBO> palaces, int lunarYear, int lunarMonth, int lunarDay, int timeIndex) {
        // 计算文昌文曲
        EarthlyBranch wenChangLocation = EarthlyBranch.fromIndex((lunarYear - 1) % 12);
        EarthlyBranch wenQuLocation = EarthlyBranch.fromIndex((lunarYear + 5) % 12);

        // 计算左辅右弼
        EarthlyBranch zuoFuLocation = EarthlyBranch.fromIndex((lunarMonth - 1) % 12);
        EarthlyBranch youBiLocation = EarthlyBranch.fromIndex((lunarMonth + 5) % 12);

        // 将辅星添加到对应宫位
        for (PalaceBO palace : palaces) {
            EarthlyBranch branch = palace.getEarthlyBranch();

            // 设置文昌星
            if (branch.equals(wenChangLocation)) {
                StarBO wenChang = new StarBO(StarName.WENCHANG, StarType.MINOR);
                wenChang.setBrightness(BrightnessCalculator.calculateBrightness(wenChang, branch));
                wenChang.setPalace(palace);
                palace.addMinorStar(wenChang);
            }

            // 设置文曲星
            if (branch.equals(wenQuLocation)) {
                StarBO wenQu = new StarBO(StarName.WENQU, StarType.MINOR);
                wenQu.setBrightness(BrightnessCalculator.calculateBrightness(wenQu, branch));
                wenQu.setPalace(palace);
                palace.addMinorStar(wenQu);
            }

            // 设置左辅星
            if (branch.equals(zuoFuLocation)) {
                StarBO zuoFu = new StarBO(StarName.ZUOFU, StarType.MINOR);
                zuoFu.setBrightness(BrightnessCalculator.calculateBrightness(zuoFu, branch));
                zuoFu.setPalace(palace);
                palace.addMinorStar(zuoFu);
            }

            // 设置右弼星
            if (branch.equals(youBiLocation)) {
                StarBO youBi = new StarBO(StarName.YOUBI, StarType.MINOR);
                youBi.setBrightness(BrightnessCalculator.calculateBrightness(youBi, branch));
                youBi.setPalace(palace);
                palace.addMinorStar(youBi);
            }
        }
    }

    /**
     * 计算杂耀位置
     */
    private void calculateMiscStars(List<PalaceBO> palaces, RawDateBO.ChineseDate chineseDate) {
        // 计算火星铃星
        EarthlyBranch huoXingLocation = EarthlyBranch.fromDescription(chineseDate.getMonthEarthlyBranch());
        EarthlyBranch lingXingLocation = EarthlyBranch.fromDescription(chineseDate.getMonthEarthlyBranch());

        // 计算地空地劫
        EarthlyBranch diKongLocation = EarthlyBranch.fromDescription(chineseDate.getTimeEarthlyBranch());
        EarthlyBranch diJieLocation = EarthlyBranch.fromDescription(chineseDate.getTimeEarthlyBranch());

        // 获取年干
        HeavenlyStem yearStem = HeavenlyStem.fromDescription(chineseDate.getYearHeavenlyStem());

        // 将杂耀添加到对应宫位
        for (PalaceBO palace : palaces) {
            EarthlyBranch branch = palace.getEarthlyBranch();

            // 设置火星
            if (branch.equals(huoXingLocation)) {
                StarBO huoXing = new StarBO(StarName.HUOXING, StarType.ADJECTIVE);
                huoXing.setBrightness(BrightnessCalculator.calculateBrightness(huoXing, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, huoXing);
                if (mutagen != null) {
                    huoXing.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(huoXing);
            }

            // 设置铃星
            if (branch.equals(lingXingLocation)) {
                StarBO lingXing = new StarBO(StarName.LINGXING, StarType.ADJECTIVE);
                lingXing.setBrightness(BrightnessCalculator.calculateBrightness(lingXing, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, lingXing);
                if (mutagen != null) {
                    lingXing.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(lingXing);
            }

            // 设置地空星
            if (branch.equals(diKongLocation)) {
                StarBO diKong = new StarBO(StarName.DIKONG, StarType.ADJECTIVE);
                diKong.setBrightness(BrightnessCalculator.calculateBrightness(diKong, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, diKong);
                if (mutagen != null) {
                    diKong.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(diKong);
            }

            // 设置地劫星
            if (branch.equals(diJieLocation)) {
                StarBO diJie = new StarBO(StarName.DIJIE, StarType.ADJECTIVE);
                diJie.setBrightness(BrightnessCalculator.calculateBrightness(diJie, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, diJie);
                if (mutagen != null) {
                    diJie.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(diJie);
            }
        }
    }

    /**
     * 计算大限
     * @param palaces 宫位列表
     * @param gender 性别（true为阳男阴女，false为阴男阳女）
     * @param birthYear 出生年
     * @param fiveElementsClass 五行局数（2-6，对应水二局到火六局）
     */
    private void calculateMajorLimit(List<PalaceBO> palaces, boolean gender, int birthYear, int fiveElementsClass) {
        // 获取命宫位置
        PalaceBO mingGong = palaces.stream()
                .filter(PalaceBO::isMingGong)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("命宫未找到"));

        // 计算大限起始宫位
        int startIndex = gender ?
            mingGong.getEarthlyBranch().getOffset(EarthlyBranch.ZI) :
            (mingGong.getEarthlyBranch().getOffset(EarthlyBranch.ZI) + 6) % 12;

        // 计算当前年龄
        int age = LocalDateTime.now().getYear() - birthYear;

        // 计算大限年数（根据五行局）
        int majorLimitYears;
        switch (fiveElementsClass) {
            case 2: // 水二局
                majorLimitYears = 6;
                break;
            case 3: // 木三局
                majorLimitYears = 7;
                break;
            case 4: // 金四局
                majorLimitYears = 8;
                break;
            case 5: // 土五局
                majorLimitYears = 9;
                break;
            case 6: // 火六局
                majorLimitYears = 10;
                break;
            default:
                majorLimitYears = 10; // 默认使用火六局
                break;
        }

        // 计算当前大限
        int currentLimit = age / majorLimitYears;

        // 计算目标宫位索引
        int targetIndex = (startIndex + currentLimit) % 12;

        // 设置大限信息
        for (PalaceBO palace : palaces) {
            if (palace.getEarthlyBranch().getOffset(EarthlyBranch.ZI) == targetIndex) {
                palace.setCurrentMajorLimit(true);
                break;
            }
        }
    }

    /**
     * 计算小限
     */
    private void calculateMinorLimit(List<PalaceBO> palaces, boolean gender, int birthYear) {
        // 计算小限起始宫位
        EarthlyBranch startLocation = LuckCalculator.calculateMinorLimitStartPalace(birthYear, gender);

        // 计算当前年龄
        int age = LocalDateTime.now().getYear() - birthYear;

        // 计算当前小限（每年一个小限）
        int currentLimit = age % 12;

        // 设置小限信息
        PalaceBO currentPalace = findPalaceByBranch(palaces, startLocation.getOffset(currentLimit));
        currentPalace.setCurrentMinorLimit(true);
    }

    /**
     * 计算流年
     */
    private void calculateYearlyFlow(List<PalaceBO> palaces, int currentYear) {
        // 计算流年地支
        EarthlyBranch yearlyFlow = LuckCalculator.calculateYearlyFlow(currentYear);

        // 设置流年信息
        PalaceBO currentPalace = findPalaceByBranch(palaces, yearlyFlow);

        currentPalace.setCurrentYearlyFlow(true);
    }

    /**
     * 根据地支查找宫位
     */
    private PalaceBO findPalaceByBranch(List<PalaceBO> palaces, EarthlyBranch branch) {

        return palaces.stream()
                .filter(p -> p.getEarthlyBranch() == branch)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Palace not found for branch: " + branch));
    }

    /**
     * 生成星盘解释
     */
    public String generateInterpretation(List<PalaceBO> palaces) {

        StringBuilder interpretation = new StringBuilder();
        interpretation.append("紫微斗数星盘解释：\n\n");

        // 解释每个宫位
        for (PalaceBO palace : palaces) {
            interpretation.append(StarCombinationInterpreter.interpretPalaceCombination(palace))
                    .append("\n-------------------\n");
        }

        return interpretation.toString();
    }

    /**
     * 获取星盘总体评分
     */
    public Map<String, Object> getOverallAuspiciousness(List<PalaceBO> palaces) {

        Map<String, Object> result = new HashMap<>();
        int totalScore = 0;
        int count = 0;

        // 计算总体评分
        for (PalaceBO palace : palaces) {
            int score = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
            totalScore += score;
            count++;
        }

        // 计算平均分
        int averageScore = count > 0 ? totalScore / count : 0;
        String level = PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(averageScore);

        result.put("totalScore", totalScore);
        result.put("averageScore", averageScore);
        result.put("level", level);

        return result;
    }

    /**
     * 分析宫位关系
     */
    public List<Map<String, Object>> analyzePalaceRelations(List<PalaceBO> palaces) {

        List<Map<String, Object>> relations = new ArrayList<>();

        // 分析每个宫位的关系
        for (PalaceBO palace : palaces) {
            Map<String, Object> relationAnalysis = PalaceRelationCalculator.analyzeAllRelations(palace, palaces);
            relations.add(relationAnalysis);
        }

        return relations;
    }

    /**
     * 获取特定宫位的关系分析
     */
    public Map<String, Object> analyzePalaceRelation(PalaceBO palace, List<PalaceBO> palaces) {

        return PalaceRelationCalculator.analyzeAllRelations(palace, palaces);
    }

    /**
     * 分析星耀会合
     */
    public List<Map<String, Object>> analyzeStarConvergences(List<PalaceBO> palaces) {

        return StarConvergenceCalculator.findAllConvergences(palaces);
    }

    /**
     * 分析特定星耀的会合
     */
    public List<Map<String, Object>> analyzeStarConvergence(StarBO star, PalaceBO palace, List<PalaceBO> allPalaces) {

        List<Map<String, Object>> convergences = new ArrayList<>();

        for (PalaceBO otherPalace : allPalaces) {
            for (StarBO otherStar : otherPalace.getAllStars()) {
                if (star != otherStar) {
                    String effect = StarConvergenceCalculator.calculateConvergenceEffect(
                            star, otherStar, palace, otherPalace);

                    if (!effect.contains("无特殊")) {
                        Map<String, Object> convergence = new HashMap<>();
                        convergence.put("star1", star.getName());
                        convergence.put("star2", otherStar.getName());
                        convergence.put("palace1", palace.getName());
                        convergence.put("palace2", otherPalace.getName());
                        convergence.put("type", StarConvergenceCalculator.calculateConvergenceType(
                                star, otherStar, palace, otherPalace));
                        convergence.put("effect", effect);
                        convergences.add(convergence);
                    }
                }
            }
        }

        return convergences;
    }

    /**
     * 分析命盘格局
     */
    public List<Map<String, Object>> analyzeHoroscopePatterns(List<PalaceBO> palaces) {

        return HoroscopePatternCalculator.calculatePatterns(palaces);
    }

    /**
     * 获取命盘完整解释
     */
    public Map<String, Object> getFullInterpretation(List<PalaceBO> palaces) {

        Map<String, Object> interpretation = new HashMap<>();

        // 获取格局
        List<Map<String, Object>> patterns = analyzeHoroscopePatterns(palaces);
        interpretation.put("patterns", patterns);

        // 获取星耀会合
        List<Map<String, Object>> convergences = analyzeStarConvergences(palaces);
        interpretation.put("convergences", convergences);

        // 获取宫位关系
        List<Map<String, Object>> relations = analyzePalaceRelations(palaces);
        interpretation.put("relations", relations);

        // 获取吉凶评分
        Map<String, Object> auspiciousness = getOverallAuspiciousness(palaces);
        interpretation.put("auspiciousness", auspiciousness);

        return interpretation;
    }

}
