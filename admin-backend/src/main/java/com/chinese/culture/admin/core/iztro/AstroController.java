package com.chinese.culture.admin.core.iztro;

import com.chinese.culture.admin.core.iztro.calculator.*;
import com.chinese.culture.admin.core.iztro.data.RawDate;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.interpreter.StarCombinationInterpreter;
import com.chinese.culture.admin.core.tyme.sixtycycle.EarthBranch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final StarCombinationInterpreter starCombinationInterpreter;
    private final DateCalculator dateCalculator;

    private List<Star> majorStars = new ArrayList<>();

    public AstroController(StarCombinationInterpreter starCombinationInterpreter, DateCalculator dateCalculator) {
        this.starCombinationInterpreter = starCombinationInterpreter;
        this.dateCalculator = dateCalculator;
    }

    /**
     * 生成紫微斗数星盘
     *
     * @param solarDate 阳历日期（yyyy-MM-dd）
     * @param timeIndex 时辰索引（0-11）
     * @param gender 性别（true为阳男阴女，false为阴男阳女）
     * @return 十二宫位列表
     */
    public List<Palace> generateAstrolabe(String solarDate, int timeIndex, boolean gender) {
        // 解析阳历日期
        String[] parts = solarDate.split("-");
        RawDate.SolarDate solar = new RawDate.SolarDate();
        solar.setYear(Integer.parseInt(parts[0]));
        solar.setMonth(Integer.parseInt(parts[1]));
        solar.setDay(Integer.parseInt(parts[2]));

        // 1. 获取农历日期和干支信息
        RawDate.LunarDate lunarDate = dateCalculator.solar2lunar(solar);
        RawDate.ChineseDate chineseDate = dateCalculator.getChineseDate(solar, timeIndex);

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
        EarthlyBranch[] palaceLocations = PalaceCalculator.calculateTwelvePalaces(mingGongLocation);
        List<Palace> palaces = new ArrayList<>(12);

        // 5. 初始化十二宫位
        for (int i = 0; i < 12; i++) {
            Palace palace = new Palace();
            palace.setIndex(i);
            palace.setName(PalaceCalculator.getPalaceName(i));
            palace.setEarthlyBranch(palaceLocations[i]);
            palaces.add(palace);
        }

        // 6. 计算主星位置
        calculateMainStars(palaces, fiveElementsClass, timeIndex, chineseDate);

        // 7. 计算辅星位置
        calculateAuxiliaryStars(palaces, chineseDate);

        // 8. 计算杂耀位置
        calculateMiscStars(palaces, chineseDate);

        // 9. 计算大限
        LocalDateTime birthDate = LocalDateTime.parse(solarDate + "T00:00:00");
        LocalDateTime now = LocalDateTime.now();
        int age = dateCalculator.calculateAge(birthDate, now);
        calculateMajorLimit(palaces, mingGongLocation, gender, fiveElementsClass, age);

        // 10. 计算小限
        int birthYear = Integer.parseInt(solarDate.substring(0, 4));
        calculateMinorLimit(palaces, birthYear, gender, age);

        // 11. 计算流年
        int currentYear = birthYear + age;
        calculateYearlyFlow(palaces, currentYear);

        return palaces;
    }

    /**
     * 计算主星位置
     */
    private void calculateMainStars(List<Palace> palaces, int fiveElementsClass, int timeIndex, RawDate.ChineseDate chineseDate) {
        // 计算紫微星系
        EarthlyBranch ziWeiLocation = StarLocationCalculator.calculateZiWeiLocation(fiveElementsClass, timeIndex);
        EarthlyBranch tianFuLocation = StarLocationCalculator.calculateTianFuLocation(ziWeiLocation);
        EarthlyBranch tianJiLocation = StarLocationCalculator.calculateTianJiLocation(tianFuLocation);

        // 计算日月星系
        EarthlyBranch monthBranch = EarthlyBranch.fromDescription(chineseDate.getMonthEarthlyBranch());
        EarthlyBranch sunLocation = StarLocationCalculator.calculateSunLocation(monthBranch);
        EarthlyBranch moonLocation = StarLocationCalculator.calculateMoonLocation(monthBranch);

        // 计算天府星系
        EarthlyBranch tanLangLocation = StarLocationCalculator.calculateTanLangLocation(tianFuLocation);
        EarthlyBranch juMenLocation = StarLocationCalculator.calculateJuMenLocation(tianFuLocation);

        // 获取年干
        HeavenlyStem yearStem = HeavenlyStem.fromDescription(chineseDate.getYearHeavenlyStem());

        // 将主星添加到对应宫位
        for (Palace palace : palaces) {
            EarthlyBranch branch = palace.getEarthlyBranch();

            // 紫微星系
            if (branch == ziWeiLocation) {
                Star ziWei = new Star("紫微", StarType.MAJOR);
                ziWei.setBrightness(BrightnessCalculator.calculateBrightness(ziWei, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, ziWei);
                if (mutagen != null) {
                    ziWei.addMutagen(mutagen);
                }
                palace.addMajorStar(ziWei);
            }

            if (branch == tianFuLocation) {
                Star tianFu = new Star("天府", StarType.MAJOR);
                tianFu.setBrightness(BrightnessCalculator.calculateBrightness(tianFu, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, tianFu);
                if (mutagen != null) {
                    tianFu.addMutagen(mutagen);
                }
                palace.addMajorStar(tianFu);
            }

            if (branch == tianJiLocation) {
                Star tianJi = new Star("天机", StarType.MAJOR);
                tianJi.setBrightness(BrightnessCalculator.calculateBrightness(tianJi, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, tianJi);
                if (mutagen != null) {
                    tianJi.addMutagen(mutagen);
                }
                palace.addMajorStar(tianJi);
            }

            // 日月星系
            if (branch == sunLocation) {
                Star sun = new Star("太阳", StarType.MAJOR);
                sun.setBrightness(BrightnessCalculator.calculateBrightness(sun, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, sun);
                if (mutagen != null) {
                    sun.addMutagen(mutagen);
                }
                palace.addMajorStar(sun);
            }

            if (branch == moonLocation) {
                Star moon = new Star("太阴", StarType.MAJOR);
                moon.setBrightness(BrightnessCalculator.calculateBrightness(moon, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, moon);
                if (mutagen != null) {
                    moon.addMutagen(mutagen);
                }
                palace.addMajorStar(moon);
            }

            // 天府星系
            if (branch == tanLangLocation) {
                Star tanLang = new Star("贪狼", StarType.MAJOR);
                tanLang.setBrightness(BrightnessCalculator.calculateBrightness(tanLang, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, tanLang);
                if (mutagen != null) {
                    tanLang.addMutagen(mutagen);
                }
                palace.addMajorStar(tanLang);
            }

            if (branch == juMenLocation) {
                Star juMen = new Star("巨门", StarType.MAJOR);
                juMen.setBrightness(BrightnessCalculator.calculateBrightness(juMen, branch));
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, juMen);
                if (mutagen != null) {
                    juMen.addMutagen(mutagen);
                }
                palace.addMajorStar(juMen);
            }
        }
    }

    /**
     * 计算辅星位置
     */
    private void calculateAuxiliaryStars(List<Palace> palaces, RawDate.ChineseDate chineseDate) {
        // 计算文昌文曲
        EarthlyBranch monthBranch = EarthlyBranch.fromDescription(chineseDate.getMonthEarthlyBranch());
        EarthlyBranch wenChangLocation = StarLocationCalculator.calculateWenChangLocation(monthBranch);
        EarthlyBranch wenQuLocation = StarLocationCalculator.calculateWenQuLocation(monthBranch);

        // 计算禄存
        HeavenlyStem yearStem = HeavenlyStem.fromDescription(chineseDate.getYearHeavenlyStem());
        EarthlyBranch luCunLocation = StarLocationCalculator.calculateLuCunLocation(yearStem);

        // 将辅星添加到对应宫位
        for (Palace palace : palaces) {
            EarthlyBranch branch = palace.getEarthlyBranch();

            if (branch == wenChangLocation) {
                Star wenChang = new Star("文昌", StarType.MINOR);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, wenChang);
                if (mutagen != null) {
                    wenChang.addMutagen(mutagen);
                }
                palace.addMinorStar(wenChang);
            }

            if (branch == wenQuLocation) {
                Star wenQu = new Star("文曲", StarType.MINOR);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, wenQu);
                if (mutagen != null) {
                    wenQu.addMutagen(mutagen);
                }
                palace.addMinorStar(wenQu);
            }

            if (branch == luCunLocation) {
                Star luCun = new Star("禄存", StarType.MINOR);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, luCun);
                if (mutagen != null) {
                    luCun.addMutagen(mutagen);
                }
                palace.addMinorStar(luCun);
            }
        }
    }

    /**
     * 计算杂耀位置
     */
    private void calculateMiscStars(List<Palace> palaces, RawDate.ChineseDate chineseDate) {
        // 计算火星铃星
        EarthlyBranch huoXingLocation = MiscStarCalculator.calculateHuoXingLocation(chineseDate.getDayEarthlyBranch());
        EarthlyBranch lingXingLocation = MiscStarCalculator.calculateLingXingLocation(chineseDate.getDayEarthlyBranch());

        // 计算地空地劫
        EarthBranch earthBranch = EarthBranch.fromIndex(chineseDate.getTimeZhi());
        EarthlyBranch diKongLocation = MiscStarCalculator.calculateDiKongLocation(earthBranch.getName());
        EarthlyBranch diJieLocation = MiscStarCalculator.calculateDiJieLocation(earthBranch.getName());

        // 计算天空
        EarthlyBranch tianKongLocation = MiscStarCalculator.calculateTianKongLocation(chineseDate.getYearHeavenlyStem());

        // 计算天刑天姚
        EarthlyBranch tianXingLocation = MiscStarCalculator.calculateTianXingLocation(chineseDate.getMonthEarthlyBranch());
        EarthlyBranch tianYaoLocation = MiscStarCalculator.calculateTianYaoLocation(chineseDate.getMonthEarthlyBranch());

        // 获取年干
        HeavenlyStem yearStem = HeavenlyStem.fromDescription(chineseDate.getYearHeavenlyStem());

        // 将杂耀添加到对应宫位
        for (Palace palace : palaces) {
            EarthlyBranch branch = palace.getEarthlyBranch();

            if (branch == huoXingLocation) {
                Star huoXing = new Star("火星", StarType.ADJECTIVE);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, huoXing);
                if (mutagen != null) {
                    huoXing.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(huoXing);
            }

            if (branch == lingXingLocation) {
                Star lingXing = new Star("铃星", StarType.ADJECTIVE);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, lingXing);
                if (mutagen != null) {
                    lingXing.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(lingXing);
            }

            if (branch == diKongLocation) {
                Star diKong = new Star("地空", StarType.ADJECTIVE);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, diKong);
                if (mutagen != null) {
                    diKong.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(diKong);
            }

            if (branch == diJieLocation) {
                Star diJie = new Star("地劫", StarType.ADJECTIVE);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, diJie);
                if (mutagen != null) {
                    diJie.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(diJie);
            }

            if (branch == tianKongLocation) {
                Star tianKong = new Star("天空", StarType.ADJECTIVE);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, tianKong);
                if (mutagen != null) {
                    tianKong.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(tianKong);
            }

            if (branch == tianXingLocation) {
                Star tianXing = new Star("天刑", StarType.ADJECTIVE);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, tianXing);
                if (mutagen != null) {
                    tianXing.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(tianXing);
            }

            if (branch == tianYaoLocation) {
                Star tianYao = new Star("天姚", StarType.ADJECTIVE);
                Mutagen mutagen = MutagenCalculator.getMutagen(yearStem, tianYao);
                if (mutagen != null) {
                    tianYao.addMutagen(mutagen);
                }
                palace.addAdjectiveStar(tianYao);
            }
        }
    }

    /**
     * 计算大限
     */
    private void calculateMajorLimit(List<Palace> palaces, EarthlyBranch mingGongLocation, boolean gender,
                                   int fiveElementsClass, int age) {
        // 计算大限起始宫位
        Palace mingGong = findPalaceByBranch(palaces, mingGongLocation);
        EarthlyBranch startLocation = LuckCalculator.calculateMajorLimitStartPalace(mingGong, gender);

        // 计算当前大限
        int majorLimitYears = LuckCalculator.calculateMajorLimitYears(fiveElementsClass);
        int currentLimit = LuckCalculator.calculateCurrentMajorLimit(age, majorLimitYears);

        // 设置大限信息
        Palace currentPalace = findPalaceByBranch(palaces, startLocation.getOffset(currentLimit));
        currentPalace.setCurrentMajorLimit(true);
    }

    /**
     * 计算小限
     */
    private void calculateMinorLimit(List<Palace> palaces, int birthYear, boolean gender, int age) {
        // 计算小限起始宫位
        EarthlyBranch startLocation = LuckCalculator.calculateMinorLimitStartPalace(birthYear, gender);

        // 计算当前小限
        int currentLimit = LuckCalculator.calculateCurrentMinorLimit(age);

        // 设置小限信息
        Palace currentPalace = findPalaceByBranch(palaces, startLocation.getOffset(currentLimit));
        currentPalace.setCurrentMinorLimit(true);
    }

    /**
     * 计算流年
     */
    private void calculateYearlyFlow(List<Palace> palaces, int currentYear) {
        // 计算流年地支
        EarthlyBranch yearlyFlow = LuckCalculator.calculateYearlyFlow(currentYear);

        // 设置流年信息
        Palace currentPalace = findPalaceByBranch(palaces, yearlyFlow);
        currentPalace.setCurrentYearlyFlow(true);
    }

    /**
     * 根据地支查找宫位
     */
    private Palace findPalaceByBranch(List<Palace> palaces, EarthlyBranch branch) {
        return palaces.stream()
            .filter(p -> p.getEarthlyBranch() == branch)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Palace not found for branch: " + branch));
    }

    /**
     * 生成星盘解释
     */
    public String generateInterpretation(List<Palace> palaces) {
        StringBuilder interpretation = new StringBuilder();
        interpretation.append("紫微斗数星盘解释：\n\n");

        // 解释每个宫位
        for (Palace palace : palaces) {
            interpretation.append(StarCombinationInterpreter.interpretPalaceCombination(palace))
                         .append("\n-------------------\n");
        }

        return interpretation.toString();
    }

    /**
     * 获取星盘总体评分
     */
    public Map<String, Object> getOverallAuspiciousness(List<Palace> palaces) {
        Map<String, Object> result = new HashMap<>();
        int totalScore = 0;
        int count = 0;

        // 计算总体评分
        for (Palace palace : palaces) {
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
    public List<Map<String, Object>> analyzePalaceRelations(List<Palace> palaces) {
        List<Map<String, Object>> relations = new ArrayList<>();

        // 分析每个宫位的关系
        for (Palace palace : palaces) {
            Map<String, Object> relationAnalysis = PalaceRelationCalculator.analyzeAllRelations(palace, palaces);
            relations.add(relationAnalysis);
        }

        return relations;
    }

    /**
     * 获取特定宫位的关系分析
     */
    public Map<String, Object> analyzePalaceRelation(Palace palace, List<Palace> palaces) {
        return PalaceRelationCalculator.analyzeAllRelations(palace, palaces);
    }

    /**
     * 分析星耀会合
     */
    public List<Map<String, Object>> analyzeStarConvergences(List<Palace> palaces) {
        return StarConvergenceCalculator.findAllConvergences(palaces);
    }

    /**
     * 分析特定星耀的会合
     */
    public List<Map<String, Object>> analyzeStarConvergence(Star star, Palace palace, List<Palace> allPalaces) {
        List<Map<String, Object>> convergences = new ArrayList<>();

        for (Palace otherPalace : allPalaces) {
            for (Star otherStar : otherPalace.getAllStars()) {
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
    public List<Map<String, Object>> analyzeHoroscopePatterns(List<Palace> palaces) {
        return HoroscopePatternCalculator.calculatePatterns(palaces);
    }

    /**
     * 获取命盘完整解释
     */
    public Map<String, Object> getFullInterpretation(List<Palace> palaces) {
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

    public void addMajorStar(Star star) {
        if (majorStars == null) {
            majorStars = new ArrayList<>();
        }
        majorStars.add(star);
    }
}
