package com.chinese.culture.admin.core.iztro.interpreter;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.AstrolabeInterpretation;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AstrolabeInterpreterTest {

    @Test
    void interpretAstrolabe() {
        // 创建测试数据
        Astrolabe astrolabe = createTestAstrolabe();
        
        // 解释命盘
        AstrolabeInterpretation interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);
        
        // 验证结果
        assertNotNull(interpretation);
        assertNotNull(interpretation.getPalaceRelations());
        assertNotNull(interpretation.getStarCombinations());
        assertNotNull(interpretation.getMutagenRelations());
        assertNotNull(interpretation.getMajorPatterns());
    }
    
    @Test
    void interpretMajorPatterns() {
        // 创建测试数据
        Astrolabe astrolabe = createTestAstrolabe();
        
        // 解释命盘
        AstrolabeInterpretation interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);
        
        // 获取主要格局
        List<String> patterns = interpretation.getMajorPatterns();
        
        // 验证基本格局
        assertNotNull(patterns);
        assertTrue(patterns.stream().anyMatch(p -> p.contains("命宫根基稳固")));
        assertTrue(patterns.stream().anyMatch(p -> p.contains("财运亨通")));
        assertTrue(patterns.stream().anyMatch(p -> p.contains("官运亨通")));
        assertTrue(patterns.stream().anyMatch(p -> p.contains("婚姻美满")));
        
        // 验证扩展格局
        assertTrue(patterns.stream().anyMatch(p -> p.contains("智慧超群")));
        assertTrue(patterns.stream().anyMatch(p -> p.contains("心思细腻")));
        assertTrue(patterns.stream().anyMatch(p -> p.contains("偏财多得")));
        assertTrue(patterns.stream().anyMatch(p -> p.contains("学术优秀")));
        assertTrue(patterns.stream().anyMatch(p -> p.contains("情感丰富")));
    }
    
    @Test
    void interpretPalaceRelations() {
        // 创建测试数据
        Astrolabe astrolabe = createTestAstrolabe();
        
        // 解释命盘
        AstrolabeInterpretation interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);
        
        // 获取宫位关系
        AstrolabeInterpretation.PalaceRelations relations = interpretation.getPalaceRelations();
        
        // 验证结果
        assertNotNull(relations);
        assertNotNull(relations.getPunishments());
        assertNotNull(relations.getHarmonies());
        assertNotNull(relations.getTrineFormations());
    }
    
    @Test
    void interpretStarCombinations() {
        // 创建测试数据
        Astrolabe astrolabe = createTestAstrolabe();
        
        // 解释命盘
        AstrolabeInterpretation interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);
        
        // 获取星耀组合
        AstrolabeInterpretation.StarCombinations combinations = interpretation.getStarCombinations();
        
        // 验证结果
        assertNotNull(combinations);
        assertNotNull(combinations.getTrineAndOpposition());
        assertNotNull(combinations.getConvergence());
    }
    
    @Test
    void interpretAstrolabeWithNullData() {
        // 测试空命盘数据
        assertThrows(BusinessException.class, () -> 
            AstrolabeInterpreter.interpretAstrolabe(null));
    }
    
    @Test
    void interpretAstrolabeWithEmptyPalaces() {
        // 创建空宫位的命盘
        Astrolabe astrolabe = new Astrolabe();
        astrolabe.setYearStem("甲");
        astrolabe.setStars(new ArrayList<>());
        
        // 验证异常
        assertThrows(BusinessException.class, () -> 
            AstrolabeInterpreter.interpretAstrolabe(astrolabe));
    }
    
    @Test
    void interpretAstrolabeWithInvalidYearStem() {
        // 创建测试数据
        Astrolabe astrolabe = createTestAstrolabe();
        astrolabe.setYearStem("X");
        
        // 验证异常
        assertThrows(BusinessException.class, () -> 
            AstrolabeInterpreter.interpretAstrolabe(astrolabe));
    }
    
    @Test
    void interpretAstrolabeWithEmptyYearStem() {
        // 创建测试数据
        Astrolabe astrolabe = createTestAstrolabe();
        astrolabe.setYearStem("");
        
        // 验证异常
        assertThrows(BusinessException.class, () -> 
            AstrolabeInterpreter.interpretAstrolabe(astrolabe));
    }
    
    /**
     * 创建测试用命盘数据
     */
    private Astrolabe createTestAstrolabe() {
        Astrolabe astrolabe = new Astrolabe();
        
        // 设置年干
        astrolabe.setYearStem("甲");
        
        // 创建宫位列表
        List<Palace> palaces = new ArrayList<>();
        
        // 创建命宫
        Palace masterPalace = new Palace();
        masterPalace.setName("命宫");
        masterPalace.setIndex(0);
        masterPalace.setBranch("寅");
        List<Star> masterStars = new ArrayList<>();
        masterStars.add(createStar("紫微", Arrays.asList("禄", "权")));
        masterStars.add(createStar("破军", Arrays.asList("科", "忌")));
        masterStars.add(createStar("天府", Arrays.asList("权", "科")));
        masterStars.add(createStar("太阴", Arrays.asList("禄", "权")));
        masterPalace.setStars(masterStars);
        palaces.add(masterPalace);
        
        // 创建兄弟宫
        Palace brotherPalace = new Palace();
        brotherPalace.setName("兄弟");
        brotherPalace.setIndex(1);
        brotherPalace.setBranch("卯");
        List<Star> brotherStars = new ArrayList<>();
        brotherStars.add(createStar("天机", Arrays.asList("禄", "科")));
        brotherPalace.setStars(brotherStars);
        palaces.add(brotherPalace);
        
        // 创建夫妻宫
        Palace spousePalace = new Palace();
        spousePalace.setName("夫妻");
        spousePalace.setIndex(2);
        spousePalace.setBranch("辰");
        List<Star> spouseStars = new ArrayList<>();
        spouseStars.add(createStar("太阳", Arrays.asList("权", "科")));
        spousePalace.setStars(spouseStars);
        palaces.add(spousePalace);
        
        // 创建子女宫
        Palace childrenPalace = new Palace();
        childrenPalace.setName("子女");
        childrenPalace.setIndex(3);
        childrenPalace.setBranch("巳");
        List<Star> childrenStars = new ArrayList<>();
        childrenStars.add(createStar("武曲", Arrays.asList("禄", "权")));
        childrenPalace.setStars(childrenStars);
        palaces.add(childrenPalace);
        
        // 创建财帛宫
        Palace wealthPalace = new Palace();
        wealthPalace.setName("财帛");
        wealthPalace.setIndex(4);
        wealthPalace.setBranch("午");
        List<Star> wealthStars = new ArrayList<>();
        wealthStars.add(createStar("贪狼", Arrays.asList("科", "忌")));
        wealthPalace.setStars(wealthStars);
        palaces.add(wealthPalace);
        
        // 创建疾厄宫
        Palace healthPalace = new Palace();
        healthPalace.setName("疾厄");
        healthPalace.setIndex(5);
        healthPalace.setBranch("未");
        List<Star> healthStars = new ArrayList<>();
        healthStars.add(createStar("巨门", Arrays.asList("权", "科")));
        healthPalace.setStars(healthStars);
        palaces.add(healthPalace);
        
        // 创建迁移宫
        Palace travelPalace = new Palace();
        travelPalace.setName("迁移");
        travelPalace.setIndex(6);
        travelPalace.setBranch("申");
        List<Star> travelStars = new ArrayList<>();
        travelStars.add(createStar("天同", Arrays.asList("禄", "科")));
        travelPalace.setStars(travelStars);
        palaces.add(travelPalace);
        
        // 创建仆役宫
        Palace servantPalace = new Palace();
        servantPalace.setName("仆役");
        servantPalace.setIndex(7);
        servantPalace.setBranch("酉");
        List<Star> servantStars = new ArrayList<>();
        servantStars.add(createStar("廉贞", Arrays.asList("权", "科")));
        servantPalace.setStars(servantStars);
        palaces.add(servantPalace);
        
        // 创建官禄宫
        Palace careerPalace = new Palace();
        careerPalace.setName("官禄");
        careerPalace.setIndex(8);
        careerPalace.setBranch("戌");
        List<Star> careerStars = new ArrayList<>();
        careerStars.add(createStar("天梁", Arrays.asList("禄", "权")));
        careerPalace.setStars(careerStars);
        palaces.add(careerPalace);
        
        // 创建田宅宫
        Palace housePalace = new Palace();
        housePalace.setName("田宅");
        housePalace.setIndex(9);
        housePalace.setBranch("亥");
        List<Star> houseStars = new ArrayList<>();
        houseStars.add(createStar("七杀", Arrays.asList("科", "忌")));
        housePalace.setStars(houseStars);
        palaces.add(housePalace);
        
        // 创建福德宫
        Palace fortunePalace = new Palace();
        fortunePalace.setName("福德");
        fortunePalace.setIndex(10);
        fortunePalace.setBranch("子");
        List<Star> fortuneStars = new ArrayList<>();
        fortuneStars.add(createStar("天相", Arrays.asList("权", "科")));
        fortunePalace.setStars(fortuneStars);
        palaces.add(fortunePalace);
        
        // 创建父母宫
        Palace parentPalace = new Palace();
        parentPalace.setName("父母");
        parentPalace.setIndex(11);
        parentPalace.setBranch("丑");
        List<Star> parentStars = new ArrayList<>();
        parentStars.add(createStar("天魁", Arrays.asList("禄", "科")));
        parentPalace.setStars(parentStars);
        palaces.add(parentPalace);
        
        astrolabe.setPalaces(palaces);
        
        // 设置所有星耀列表
        List<Star> allStars = new ArrayList<>();
        palaces.forEach(palace -> allStars.addAll(palace.getStars()));
        astrolabe.setStars(allStars);
        
        return astrolabe;
    }
    
    /**
     * 创建测试用星耀数据
     */
    private Star createStar(String name, List<String> mutagens) {
        Star star = new Star();
        star.setName(name);
        star.setMutagens(mutagens);
        return star;
    }
} 