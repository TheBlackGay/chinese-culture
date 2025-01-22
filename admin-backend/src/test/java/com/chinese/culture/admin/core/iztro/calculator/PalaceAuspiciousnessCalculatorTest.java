package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.core.iztro.data.enums.StarName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PalaceAuspiciousnessCalculatorTest {
    
    private Palace palace;
    private Star purpleStar; // 紫微
    private Star sunStar;    // 太阳
    private Star marsStar;   // 火星
    private Star bellStar;   // 铃星
    
    @BeforeEach
    void setUp() {
        palace = new Palace(EarthlyBranch.CHEN);
        purpleStar = new Star(StarName.ZIWEI, StarType.MAJOR, Brightness.TEMPLE);
        palace.addMajorStar(purpleStar);

        sunStar = new Star(StarName.TAIYANG, StarType.MAJOR, Brightness.STRONG);
        palace.addMajorStar(sunStar);

        marsStar = new Star(StarName.HUOXING, StarType.ADJECTIVE, Brightness.GAIN);
        palace.addAdjectiveStar(marsStar);

        bellStar = new Star(StarName.LINGXING, StarType.ADJECTIVE, Brightness.TRAPPED);
        palace.addAdjectiveStar(bellStar);

        calculator = new PalaceAuspiciousnessCalculator();
    }
    
    @Test
    void testCalculateAuspiciousness_AllGoodStars() {
        // 添加吉星
        palace.getMajorStars().add(purpleStar);
        palace.getMajorStars().add(sunStar);
        
        int score = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        assertTrue(score >= 70, "带有吉星的宫位分数应该较高");
    }
    
    @Test
    void testCalculateAuspiciousness_AllBadStars() {
        // 添加凶星
        palace.getAdjectiveStars().add(marsStar);
        palace.getAdjectiveStars().add(bellStar);
        
        int score = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        assertTrue(score <= 40, "带有凶星的宫位分数应该较低");
    }
    
    @Test
    void testCalculateAuspiciousness_MixedStars() {
        // 添加混合星耀
        palace.getMajorStars().add(purpleStar);
        palace.getAdjectiveStars().add(marsStar);
        
        int score = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        assertTrue(score >= 40 && score <= 70, "混合星耀的宫位分数应该在中等范围");
    }
    
    @Test
    void testGetDetailedAnalysis() {
        // 添加混合星耀
        palace.getMajorStars().add(purpleStar);
        palace.getMajorStars().add(sunStar);
        palace.getAdjectiveStars().add(marsStar);
        
        Map<String, Object> analysis = PalaceAuspiciousnessCalculator.getDetailedAnalysis(palace);
        
        assertNotNull(analysis.get("score"), "分数不应为空");
        assertNotNull(analysis.get("level"), "等级不应为空");
        assertNotNull(analysis.get("auspiciousStars"), "吉星列表不应为空");
        assertNotNull(analysis.get("inauspiciousStars"), "凶星列表不应为空");
        assertNotNull(analysis.get("mutagens"), "四化列表不应为空");
        assertNotNull(analysis.get("interpretation"), "解释文本不应为空");
    }
    
    @Test
    void testGetAuspiciousnessLevel() {
        assertEquals("上上", PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(95));
        assertEquals("上", PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(85));
        assertEquals("中上", PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(75));
        assertEquals("中", PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(65));
        assertEquals("中下", PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(55));
        assertEquals("下", PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(45));
        assertEquals("下下", PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(35));
    }
    
    @Test
    void testBrightnessEffect() {
        // 测试明亮星耀
        palace.getMajorStars().add(purpleStar); // BRIGHT
        int score1 = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        
        palace.getMajorStars().clear();
        palace.getMajorStars().add(sunStar); // NORMAL
        int score2 = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        
        assertTrue(score1 > score2, "明亮星耀应该比普通星耀得分高");
    }
    
    @Test
    void testMutagenEffect() {
        // 测试四化影响
        palace.getMajorStars().add(purpleStar); // 禄化
        int score1 = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        
        palace.getMajorStars().clear();
        palace.getAdjectiveStars().add(marsStar); // 忌化
        int score2 = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        
        assertTrue(score1 > score2, "禄化星耀应该比忌化星耀得分高");
    }
} 
