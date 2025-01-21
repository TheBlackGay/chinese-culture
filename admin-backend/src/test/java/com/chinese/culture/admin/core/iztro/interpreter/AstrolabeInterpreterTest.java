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
        assertTrue(patterns.stream().anyMatch(p -> p.contains("命宫根基稳固")), "应包含命宫根基稳固格局");
        assertTrue(patterns.stream().anyMatch(p -> p.contains("财运亨通")), "应包含财运亨通格局");
        assertTrue(patterns.stream().anyMatch(p -> p.contains("官运亨通")), "应包含官运亨通格局");
        assertTrue(patterns.stream().anyMatch(p -> p.contains("婚姻美满")), "应包含婚姻美满格局");
        
        // 验证扩展格局
        assertTrue(patterns.stream().anyMatch(p -> p.contains("智慧超群")), "应包含智慧超群格局");
        assertTrue(patterns.stream().anyMatch(p -> p.contains("心思细腻")), "应包含心思细腻格局");
        assertTrue(patterns.stream().anyMatch(p -> p.contains("偏财多得")), "应包含偏财多得格局");
        assertTrue(patterns.stream().anyMatch(p -> p.contains("学术优秀")), "应包含学术优秀格局");
        assertTrue(patterns.stream().anyMatch(p -> p.contains("情感丰富")), "应包含情感丰富格局");
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
        List<Palace> palaces = new ArrayList<>();
        List<Star> stars = new ArrayList<>();
        
        // 创建命宫
        Palace mingGong = new Palace();
        mingGong.setName("命宫");
        mingGong.setIndex(0);
        mingGong.setMing(true);
        mingGong.setStars(new ArrayList<>());
        mingGong.setMutagens(new ArrayList<>());
        
        // 添加主星到命宫
        Star ziwei = createStar("紫微", Arrays.asList("化禄"));
        Star tianji = createStar("天机", Arrays.asList("化权"));
        mingGong.getStars().add(ziwei);
        mingGong.getStars().add(tianji);
        stars.add(ziwei);
        stars.add(tianji);
        
        // 创建财帛宫
        Palace caiGong = new Palace();
        caiGong.setName("财帛");
        caiGong.setIndex(1);
        caiGong.setStars(new ArrayList<>());
        caiGong.setMutagens(new ArrayList<>());
        
        // 添加吉星到财帛宫
        Star wuqu = createStar("武曲", Arrays.asList("化科"));
        Star taiyang = createStar("太阳", null);
        caiGong.getStars().add(wuqu);
        caiGong.getStars().add(taiyang);
        stars.add(wuqu);
        stars.add(taiyang);
        
        // 创建官禄宫
        Palace guanGong = new Palace();
        guanGong.setName("官禄");
        guanGong.setIndex(2);
        guanGong.setStars(new ArrayList<>());
        guanGong.setMutagens(new ArrayList<>());
        
        // 添加吉星到官禄宫
        Star tianfu = createStar("天府", Arrays.asList("化禄"));
        Star pojun = createStar("破军", Arrays.asList("化权"));
        Star tianxiang = createStar("天相", Arrays.asList("化科"));
        guanGong.getStars().add(tianfu);
        guanGong.getStars().add(pojun);
        guanGong.getStars().add(tianxiang);
        stars.add(tianfu);
        stars.add(pojun);
        stars.add(tianxiang);
        
        // 创建夫妻宫
        Palace fuqiGong = new Palace();
        fuqiGong.setName("夫妻");
        fuqiGong.setIndex(3);
        fuqiGong.setStars(new ArrayList<>());
        fuqiGong.setMutagens(new ArrayList<>());
        
        // 添加吉星到夫妻宫
        Star wenchang = createStar("文昌", null);
        Star wenqu = createStar("文曲", null);
        fuqiGong.getStars().add(wenchang);
        fuqiGong.getStars().add(wenqu);
        stars.add(wenchang);
        stars.add(wenqu);
        
        // 添加宫位到命盘
        palaces.add(mingGong);
        palaces.add(caiGong);
        palaces.add(guanGong);
        palaces.add(fuqiGong);
        
        // 补充其他宫位到12个，确保索引连续
        for (int i = palaces.size(); i < 12; i++) {
            Palace palace = new Palace();
            palace.setName("宫位" + i);
            palace.setIndex(i);
            palace.setStars(new ArrayList<>());
            palace.setMutagens(new ArrayList<>());
            palaces.add(palace);
        }
        
        // 设置命盘数据
        astrolabe.setPalaces(palaces);
        astrolabe.setStars(stars);
        astrolabe.setYearStem("甲");
        
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