package com.chinese.culture.admin.core.iztro.interpreter;

import com.chinese.culture.admin.common.core.iztro.interpreter.AstrolabeInterpreter;
import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
import com.chinese.culture.admin.common.core.iztro.data.AstrolabeInterpretationBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AstrolabeInterpreterTest {

    @Resource
    private AstrolabeInterpreter interpreter;

    @Test
    void interpretAstrolabe() {
        // 创建测试数据
        AstrolabeBO astrolabe = createTestAstrolabe();

        // 解释命盘
        AstrolabeInterpretationBO interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);

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
        AstrolabeBO astrolabe = createTestAstrolabe();

        // 解释命盘
        AstrolabeInterpretationBO interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);

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
        AstrolabeBO astrolabe = createTestAstrolabe();

        // 解释命盘
        AstrolabeInterpretationBO interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);

        // 获取宫位关系
        AstrolabeInterpretationBO.PalaceRelations relations = interpretation.getPalaceRelations();

        // 验证结果
        assertNotNull(relations);
        assertNotNull(relations.getPunishments());
        assertNotNull(relations.getHarmonies());
        assertNotNull(relations.getTrineFormations());
    }

    @Test
    void interpretStarCombinations() {
        // 创建测试数据
        AstrolabeBO astrolabe = createTestAstrolabe();

        // 解释命盘
        AstrolabeInterpretationBO interpretation = AstrolabeInterpreter.interpretAstrolabe(astrolabe);

        // 获取星耀组合
        AstrolabeInterpretationBO.StarCombinations combinations = interpretation.getStarCombinations();

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
        AstrolabeBO astrolabe = new AstrolabeBO();
        astrolabe.setYearStem("甲");
        astrolabe.setStars(new ArrayList<>());

        // 验证异常
        assertThrows(BusinessException.class, () ->
            AstrolabeInterpreter.interpretAstrolabe(astrolabe));
    }

    @Test
    void interpretAstrolabeWithInvalidYearStem() {
        // 创建测试数据
        AstrolabeBO astrolabe = createTestAstrolabe();
        astrolabe.setYearStem("X");

        // 验证异常
        assertThrows(BusinessException.class, () ->
            AstrolabeInterpreter.interpretAstrolabe(astrolabe));
    }

    @Test
    void interpretAstrolabeWithEmptyYearStem() {
        // 创建测试数据
        AstrolabeBO astrolabe = createTestAstrolabe();
        astrolabe.setYearStem("");

        // 验证异常
        assertThrows(BusinessException.class, () ->
            AstrolabeInterpreter.interpretAstrolabe(astrolabe));
    }

    @Test
    public void testInterpretPalace() {
        // 创建测试宫位
        PalaceBO palace = new PalaceBO();
        palace.setEarthlyBranch(EarthlyBranch.ZI);

        // 添加主星
        StarBO majorStar = new StarBO(StarName.ZIWEI, StarType.MAJOR);
        palace.addMajorStar(majorStar);

        // 添加辅星
        StarBO minorStar = new StarBO(StarName.TIANFU, StarType.MINOR);
        palace.addMinorStar(minorStar);

        // 添加四化
        majorStar.getMutagenInfo().addMutagen(Mutagen.LUCKY);

        // 解释宫位
        Map<String, Object> interpretation = interpreter.interpretPalace(palace);

        // 验证结果
        assertNotNull(interpretation);
        assertTrue(interpretation.containsKey("stars"));
        assertTrue(interpretation.containsKey("mutagens"));
        assertTrue(interpretation.containsKey("description"));
    }

    @Test
    public void testInterpretPalaceWithNoStars() {
        PalaceBO palace = new PalaceBO();
        palace.setEarthlyBranch(EarthlyBranch.ZI);

        Map<String, Object> interpretation = interpreter.interpretPalace(palace);

        assertNotNull(interpretation);
        assertTrue(((List<?>) interpretation.get("stars")).isEmpty());
        assertTrue(((List<?>) interpretation.get("mutagens")).isEmpty());
        assertNotNull(interpretation.get("description"));
    }

    @Test
    public void testInterpretPalaceWithComplexConfiguration() {
        PalaceBO palace = new PalaceBO();
        palace.setEarthlyBranch(EarthlyBranch.ZI);

        // 添加多个星耀
        StarBO majorStar1 = new StarBO(StarName.ZIWEI, StarType.MAJOR);
        StarBO majorStar2 = new StarBO(StarName.TIANFU, StarType.MAJOR);
        StarBO minorStar = new StarBO(StarName.POJUN, StarType.MINOR);

        palace.addMajorStar(majorStar1);
        palace.addMajorStar(majorStar2);
        palace.addMinorStar(minorStar);

        // 添加多个四化
        majorStar1.getMutagenInfo().addMutagen(Mutagen.LUCKY);
        majorStar2.getMutagenInfo().addMutagen(Mutagen.POWER);

        Map<String, Object> interpretation = interpreter.interpretPalace(palace);

        assertNotNull(interpretation);
        assertTrue(((List<?>) interpretation.get("stars")).size() > 2);
        assertTrue(((List<?>) interpretation.get("mutagens")).size() > 1);
        assertTrue(((String) interpretation.get("description")).length() > 0);
    }

    /**
     * 创建测试用命盘数据
     */
    private AstrolabeBO createTestAstrolabe() {
        AstrolabeBO astrolabe = new AstrolabeBO();
        List<PalaceBO> palaces = new ArrayList<>();
        List<StarBO> stars = new ArrayList<>();

        // 创建命宫
        PalaceBO mingGong = new PalaceBO();
        mingGong.setName("命宫");
        mingGong.setIndex(0);
        mingGong.setMingGong(true);
        mingGong.setStars(new ArrayList<>());
        mingGong.setMutagens(new ArrayList<>());

        // 添加主星到命宫
        StarBO ziwei = createStar("紫微", Arrays.asList("化禄"));
        StarBO tianji = createStar("天机", Arrays.asList("化权"));
        mingGong.getStars().add(ziwei);
        mingGong.getStars().add(tianji);
        stars.add(ziwei);
        stars.add(tianji);

        // 创建财帛宫
        PalaceBO caiGong = new PalaceBO();
        caiGong.setName("财帛");
        caiGong.setIndex(1);
        caiGong.setStars(new ArrayList<>());
        caiGong.setMutagens(new ArrayList<>());

        // 添加吉星到财帛宫
        StarBO wuqu = createStar("武曲", Arrays.asList("化科"));
        StarBO taiyang = createStar("太阳", null);
        caiGong.getStars().add(wuqu);
        caiGong.getStars().add(taiyang);
        stars.add(wuqu);
        stars.add(taiyang);

        // 创建官禄宫
        PalaceBO guanGong = new PalaceBO();
        guanGong.setName("官禄");
        guanGong.setIndex(2);
        guanGong.setStars(new ArrayList<>());
        guanGong.setMutagens(new ArrayList<>());

        // 添加吉星到官禄宫
        StarBO tianfu = createStar("天府", Arrays.asList("化禄"));
        StarBO pojun = createStar("破军", Arrays.asList("化权"));
        StarBO tianxiang = createStar("天相", Arrays.asList("化科"));
        guanGong.getStars().add(tianfu);
        guanGong.getStars().add(pojun);
        guanGong.getStars().add(tianxiang);
        stars.add(tianfu);
        stars.add(pojun);
        stars.add(tianxiang);

        // 创建夫妻宫
        PalaceBO fuqiGong = new PalaceBO();
        fuqiGong.setName("夫妻");
        fuqiGong.setIndex(3);
        fuqiGong.setStars(new ArrayList<>());
        fuqiGong.setMutagens(new ArrayList<>());

        // 添加吉星到夫妻宫
        StarBO wenchang = createStar("文昌", null);
        StarBO wenqu = createStar("文曲", null);
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
            PalaceBO palace = new PalaceBO();
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
    private StarBO createStar(String name, List<String> mutagens) {
        StarBO star = new StarBO(StarName.valueOf(name), StarType.MAJOR);
        star.setMutagens(mutagens);
        return star;
    }
}
