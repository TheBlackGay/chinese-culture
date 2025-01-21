package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.Horoscope;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AstrolabeAnalyzerTest {

    @Test
    void analyzeMingZhuPattern() {
        // 创建测试数据
        Astrolabe astrolabe = new Astrolabe();
        List<Palace> palaces = new ArrayList<>();
        
        // 创建命宫
        Palace mingGong = new Palace();
        mingGong.setName("命宫");
        mingGong.setIndex(0);
        mingGong.setMing(true);
        
        // 添加星耀
        List<Star> stars = new ArrayList<>();
        Star ziwei = new Star();
        ziwei.setName("紫微");
        Star tianfu = new Star();
        tianfu.setName("天府");
        stars.add(ziwei);
        stars.add(tianfu);
        mingGong.setStars(stars);
        
        // 添加四化
        mingGong.setMutagens(Arrays.asList("化禄", "化权", "化科"));
        
        palaces.add(mingGong);
        astrolabe.setPalaces(palaces);
        
        // 测试分析结果
        String pattern = AstrolabeAnalyzer.analyzeMingZhuPattern(astrolabe);
        assertNotNull(pattern);
        assertTrue(pattern.contains("紫微天府同宫格"));
        assertTrue(pattern.contains("禄权科格"));
    }

    @Test
    void analyzeDecadalFortune() {
        // 创建测试数据
        Horoscope horoscope = new Horoscope();
        Horoscope.DecadalHoroscope decadal = new Horoscope.DecadalHoroscope();
        decadal.setStartYear(20);
        decadal.setEndYear(29);
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
        Horoscope horoscope = new Horoscope();
        Horoscope.YearlyHoroscope yearly = new Horoscope.YearlyHoroscope();
        yearly.setYear(2024);
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