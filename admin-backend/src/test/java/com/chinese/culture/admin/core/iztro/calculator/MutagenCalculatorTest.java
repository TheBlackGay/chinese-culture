package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.calculator.MutagenCalculator;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MutagenCalculatorTest {

    @Resource
    private MutagenCalculator calculator;

    @Test
    void testCalculateMutagenRelationsForJia() {
        // 准备测试数据
        HeavenlyStem yearStem = HeavenlyStem.JIA;
        List<StarBO> stars = Arrays.asList(
            new StarBO(StarName.LIANZHEN, StarType.MAJOR),
            new StarBO(StarName.POJUN, StarType.MAJOR),
            new StarBO(StarName.WUQU, StarType.MAJOR),
            new StarBO(StarName.TAIYANG, StarType.MAJOR)
        );

        // 执行测试
        Map<StarBO, Mutagen> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.containsKey(stars.get(0)));
        assertEquals(Mutagen.LU, relations.get(stars.get(0)));
        assertTrue(relations.containsKey(stars.get(1)));
        assertEquals(Mutagen.QUAN, relations.get(stars.get(1)));
        assertTrue(relations.containsKey(stars.get(2)));
        assertEquals(Mutagen.KE, relations.get(stars.get(2)));
        assertTrue(relations.containsKey(stars.get(3)));
        assertEquals(Mutagen.JI, relations.get(stars.get(3)));
    }

    @Test
    void testCalculateMutagenRelationsForYi() {
        // 准备测试数据
        HeavenlyStem yearStem = HeavenlyStem.YI;
        List<StarBO> stars = Arrays.asList(
            new StarBO(StarName.TIANJI, StarType.MAJOR),
            new StarBO(StarName.TIANXIANG, StarType.MAJOR),
            new StarBO(StarName.ZIWEI, StarType.MAJOR),
            new StarBO(StarName.TAIYIN, StarType.MAJOR)
        );

        // 执行测试
        Map<StarBO, Mutagen> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.containsKey(stars.get(0)));
        assertEquals(Mutagen.LU, relations.get(stars.get(0)));
        assertTrue(relations.containsKey(stars.get(1)));
        assertEquals(Mutagen.QUAN, relations.get(stars.get(1)));
        assertTrue(relations.containsKey(stars.get(2)));
        assertEquals(Mutagen.KE, relations.get(stars.get(2)));
        assertTrue(relations.containsKey(stars.get(3)));
        assertEquals(Mutagen.JI, relations.get(stars.get(3)));
    }

    @Test
    void testCalculateStarConflictsWithConflict() {
        // 准备测试数据
        Map<Mutagen, Integer> starPositions = new HashMap<>();
        starPositions.put(Mutagen.LU, 0);
        starPositions.put(Mutagen.JI, 0);
        starPositions.put(Mutagen.QUAN, 0);

        // 执行测试
        Map<Mutagen, List<Mutagen>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        // 验证结果
        assertNotNull(conflicts);
        assertTrue(conflicts.containsKey(Mutagen.LU));
        assertTrue(conflicts.get(Mutagen.LU).contains(Mutagen.JI));
        assertTrue(conflicts.containsKey(Mutagen.QUAN));
        assertTrue(conflicts.get(Mutagen.QUAN).contains(Mutagen.JI));
        assertTrue(conflicts.containsKey(Mutagen.JI));
        assertTrue(conflicts.get(Mutagen.JI).containsAll(Arrays.asList(Mutagen.LU, Mutagen.QUAN)));
    }

    @Test
    void testCalculateStarConflictsWithoutConflict() {
        // 准备测试数据
        Map<Mutagen, Integer> starPositions = new HashMap<>();
        starPositions.put(Mutagen.LU, 0);
        starPositions.put(Mutagen.QUAN, 1);
        starPositions.put(Mutagen.KE, 2);
        starPositions.put(Mutagen.JI, 3);

        // 执行测试
        Map<Mutagen, List<Mutagen>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        // 验证结果
        assertNotNull(conflicts);
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void testCalculateMutagenRelationsWithInvalidYearStem() {
        // 准备测试数据
        HeavenlyStem yearStem = null;
        List<StarBO> stars = Arrays.asList(
            new StarBO(StarName.LIANZHEN, StarType.MAJOR),
            new StarBO(StarName.POJUN, StarType.MAJOR),
            new StarBO(StarName.WUQU, StarType.MAJOR),
            new StarBO(StarName.TAIYANG, StarType.MAJOR)
        );

        // 执行测试
        Map<StarBO, Mutagen> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.isEmpty());
    }

    @Test
    void testCalculateMutagenRelationsWithEmptyStars() {
        // 准备测试数据
        HeavenlyStem yearStem = HeavenlyStem.JIA;
        List<StarBO> stars = new ArrayList<>();

        // 执行测试
        Map<StarBO, Mutagen> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        // 验证结果
        assertNotNull(relations);
        assertTrue(relations.isEmpty());
    }

    @Test
    void testCalculateStarConflictsWithEmptyPositions() {
        // 准备测试数据
        Map<Mutagen, Integer> starPositions = new HashMap<>();

        // 执行测试
        Map<Mutagen, List<Mutagen>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        // 验证结果
        assertNotNull(conflicts);
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void calculateMutagenRelations() {
        // 测试甲年四化
        HeavenlyStem yearStem = HeavenlyStem.JIA;
        List<StarBO> stars = Arrays.asList(
            new StarBO(StarName.LIANZHEN, StarType.MAJOR),
            new StarBO(StarName.POJUN, StarType.MAJOR),
            new StarBO(StarName.WUQU, StarType.MAJOR),
            new StarBO(StarName.TAIYANG, StarType.MAJOR),
            new StarBO(StarName.TIANJI, StarType.MAJOR)
        );

        Map<StarBO, Mutagen> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        assertNotNull(relations);
        assertTrue(relations.containsKey(stars.get(0)));
        assertEquals(Mutagen.LU, relations.get(stars.get(0)));
        assertTrue(relations.containsKey(stars.get(1)));
        assertEquals(Mutagen.QUAN, relations.get(stars.get(1)));
        assertTrue(relations.containsKey(stars.get(2)));
        assertEquals(Mutagen.KE, relations.get(stars.get(2)));
        assertTrue(relations.containsKey(stars.get(3)));
        assertEquals(Mutagen.JI, relations.get(stars.get(3)));
    }

    @Test
    void calculateMutagenRelationsWithInvalidYear() {
        // 测试无效年干
        HeavenlyStem yearStem = null;
        List<StarBO> stars = Arrays.asList(
            new StarBO(StarName.LIANZHEN, StarType.MAJOR),
            new StarBO(StarName.POJUN, StarType.MAJOR),
            new StarBO(StarName.WUQU, StarType.MAJOR),
            new StarBO(StarName.TAIYANG, StarType.MAJOR),
            new StarBO(StarName.TIANJI, StarType.MAJOR)
        );

        Map<StarBO, Mutagen> relations = MutagenCalculator.calculateMutagenRelations(yearStem, stars);

        assertNotNull(relations);
        assertTrue(relations.isEmpty());
    }

    @Test
    void calculateStarConflicts() {
        // 创建测试数据
        Map<Mutagen, Integer> starPositions = new HashMap<>();

        // 设置同宫四化冲突
        starPositions.put(Mutagen.LU, 0);
        starPositions.put(Mutagen.JI, 0);
        starPositions.put(Mutagen.QUAN, 0);

        Map<Mutagen, List<Mutagen>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        assertNotNull(conflicts);
        assertTrue(conflicts.containsKey(Mutagen.LU));
        assertTrue(conflicts.get(Mutagen.LU).contains(Mutagen.JI));
        assertTrue(conflicts.containsKey(Mutagen.QUAN));
        assertTrue(conflicts.get(Mutagen.QUAN).contains(Mutagen.JI));
        assertTrue(conflicts.containsKey(Mutagen.JI));
        assertTrue(conflicts.get(Mutagen.JI).containsAll(Arrays.asList(Mutagen.LU, Mutagen.QUAN)));
    }

    @Test
    void calculateStarConflictsWithNoConflicts() {
        // 创建测试数据
        Map<Mutagen, Integer> starPositions = new HashMap<>();

        // 设置无冲突的位置
        starPositions.put(Mutagen.LU, 0);
        starPositions.put(Mutagen.QUAN, 1);
        starPositions.put(Mutagen.KE, 2);
        starPositions.put(Mutagen.JI, 3);

        Map<Mutagen, List<Mutagen>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        assertNotNull(conflicts);
        assertTrue(conflicts.isEmpty());
    }

    @Test
    void calculateMultipleStarConflicts() {
        // 创建测试数据
        Map<Mutagen, Integer> starPositions = new HashMap<>();

        // 设置多重四化冲突
        starPositions.put(Mutagen.LU, 0);
        starPositions.put(Mutagen.JI, 0);
        starPositions.put(Mutagen.QUAN, 0);
        starPositions.put(Mutagen.KE, 0);

        Map<Mutagen, List<Mutagen>> conflicts = MutagenCalculator.calculateStarConflicts(starPositions);

        assertNotNull(conflicts);
        assertTrue(conflicts.containsKey(Mutagen.LU));
        assertTrue(conflicts.get(Mutagen.LU).contains(Mutagen.JI));
        assertTrue(conflicts.containsKey(Mutagen.QUAN));
        assertTrue(conflicts.get(Mutagen.QUAN).contains(Mutagen.JI));
        assertTrue(conflicts.containsKey(Mutagen.KE));
        assertTrue(conflicts.get(Mutagen.KE).contains(Mutagen.JI));
        assertTrue(conflicts.containsKey(Mutagen.JI));
        assertTrue(conflicts.get(Mutagen.JI).containsAll(Arrays.asList(Mutagen.LU, Mutagen.QUAN, Mutagen.KE)));
    }

    @Test
    public void testCalculateMutagenCounts() {
        // 准备测试数据
        List<StarBO> stars = Arrays.asList(
            createStarWithMutagen(StarName.LIANZHEN, Mutagen.LU),
            createStarWithMutagen(StarName.POJUN, Mutagen.QUAN),
            createStarWithMutagen(StarName.WUQU, Mutagen.KE),
            createStarWithMutagen(StarName.TAIYANG, Mutagen.JI)
        );

        // 执行测试
        Map<Mutagen, Integer> counts = MutagenCalculator.calculateMutagenCounts(stars);

        // 验证结果
        assertNotNull(counts);
        assertEquals(1, counts.get(Mutagen.LU));
        assertEquals(1, counts.get(Mutagen.QUAN));
        assertEquals(1, counts.get(Mutagen.KE));
        assertEquals(1, counts.get(Mutagen.JI));
    }

    @Test
    public void testCalculateMutagenCountsWithMultipleMutagens() {
        // 准备测试数据
        List<StarBO> stars = Arrays.asList(
            createStarWithMutagen(StarName.LIANZHEN, Mutagen.LU),
            createStarWithMutagen(StarName.POJUN, Mutagen.LU),
            createStarWithMutagen(StarName.WUQU, Mutagen.KE),
            createStarWithMutagen(StarName.TAIYANG, Mutagen.JI)
        );

        // 执行测试
        Map<Mutagen, Integer> counts = MutagenCalculator.calculateMutagenCounts(stars);

        // 验证结果
        assertNotNull(counts);
        assertEquals(2, counts.get(Mutagen.LU));
        assertEquals(1, counts.get(Mutagen.KE));
        assertEquals(1, counts.get(Mutagen.JI));
    }

    @Test
    public void testCalculateMutagenCountsWithEmptyStars() {
        // 准备测试数据
        List<StarBO> stars = new ArrayList<>();

        // 执行测试
        Map<Mutagen, Integer> counts = MutagenCalculator.calculateMutagenCounts(stars);

        // 验证结果
        assertNotNull(counts);
        assertTrue(counts.isEmpty());
    }

    @Test
    public void testCalculateMutagenCountsWithNullMutagens() {
        // 准备测试数据
        List<StarBO> stars = Arrays.asList(
            new StarBO(StarName.LIANZHEN, StarType.MAJOR),
            new StarBO(StarName.POJUN, StarType.MAJOR)
        );

        // 执行测试
        Map<Mutagen, Integer> counts = MutagenCalculator.calculateMutagenCounts(stars);

        // 验证结果
        assertNotNull(counts);
        assertTrue(counts.isEmpty());
    }

    private StarBO createStarWithMutagen(StarName name, Mutagen mutagen) {
        StarBO star = new StarBO(name, StarType.MAJOR);
        star.setMutagens(Arrays.asList(mutagen));
        return star;
    }
}
