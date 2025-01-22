package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.common.core.iztro.analyzer.AstrolabeAnalyzer;
import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
import com.chinese.culture.admin.common.core.iztro.data.HoroscopeBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AstrolabeAnalyzerTest {

    private AstrolabeBO astrolabe;
    private List<PalaceBO> palaces;
    private List<StarBO> stars;
    private PalaceBO mingGong;
    private StarBO ziwei;
    private StarBO tianfu;

    @BeforeEach
    void setUp() {
        astrolabe = new AstrolabeBO();
        palaces = new ArrayList<>();
        stars = new ArrayList<>();

        // 创建命宫
        mingGong = new PalaceBO();
        mingGong.setName("命宫");
        mingGong.setIndex(0);
        mingGong.setMingGong(true);

        // 创建紫微星
        ziwei = new StarBO(StarName.ZIWEI, StarType.MAJOR);
        mingGong.addMajorStar(ziwei);

        // 创建天府星
        tianfu = new StarBO(StarName.TIANFU, StarType.MAJOR);
        mingGong.addMajorStar(tianfu);

        // 添加四化
        mingGong.setMutagens(Arrays.asList("化禄", "化权", "化科"));

        // 添加命宫到宫位列表
        palaces.add(mingGong);

        // 补充其他宫位到12个
        for (int i = 1; i < 12; i++) {
            PalaceBO palace = new PalaceBO();
            palace.setName("宫位" + i);
            palace.setIndex(i);
            palace.setStars(new ArrayList<>());
            palace.setMutagens(new ArrayList<>());
            palaces.add(palace);
        }

        astrolabe.setPalaces(palaces);
        astrolabe.setStars(stars);  // 设置命盘的星耀列表
        astrolabe.setYearStem("甲"); // 设置年干
    }

    @Test
    void analyzeMingZhuPattern() {
        // 测试分析结果
        String pattern = AstrolabeAnalyzer.analyzeMingZhuPattern(astrolabe);
        assertNotNull(pattern);
        assertTrue(pattern.contains("紫微天府同宫格"));
        assertTrue(pattern.contains("禄权科格"));
    }

    @Test
    void analyzeDecadalFortune() {
        // 创建测试数据
        HoroscopeBO horoscope = new HoroscopeBO();
        HoroscopeBO.DecadalHoroscope decadal = new HoroscopeBO.DecadalHoroscope();
        decadal.setStartAge(20);
        decadal.setEndAge(29);
        decadal.setHeavenlyStem("甲");
        decadal.setEarthlyBranch("子");
        decadal.setStars(Arrays.asList("紫微", "天机", "文昌"));
        decadal.setMutagens(Arrays.asList("化禄", "化科"));
        horoscope.setDecadal(decadal);

        // 测试分析结果
        String fortune = AstrolabeAnalyzer.analyzeDecadalFortune(horoscope);
        assertNotNull(fortune);
        assertTrue(fortune.contains("20-29岁"));
        assertTrue(fortune.contains("甲子"));
        assertTrue(fortune.contains("紫微"));
        assertTrue(fortune.contains("大吉"));
    }

    @Test
    void analyzeYearlyFortune() {
        // 创建测试数据
        HoroscopeBO horoscope = new HoroscopeBO();
        HoroscopeBO.YearlyHoroscope yearly = new HoroscopeBO.YearlyHoroscope();
        yearly.setAge(2024);
        yearly.setHeavenlyStem("甲");
        yearly.setEarthlyBranch("辰");
        yearly.setStars(Arrays.asList("天梁", "天相", "七杀"));
        yearly.setMutagens(Arrays.asList("化权", "化忌"));
        yearly.setYearlyDecStars(Arrays.asList("文昌", "左辅"));
        horoscope.setYearly(yearly);

        // 测试分析结果
        String fortune = AstrolabeAnalyzer.analyzeYearlyFortune(horoscope);
        assertNotNull(fortune);
        assertTrue(fortune.contains("2024年"));
        assertTrue(fortune.contains("甲辰"));
        assertTrue(fortune.contains("天梁"));
        assertTrue(fortune.contains("文昌"));
    }
}
