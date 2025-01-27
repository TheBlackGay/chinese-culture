package com.chinese.culture.admin.common.core.iztro.star;

import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZiweiStarLocatorTest {

    private final ZiweiStarLocator locator = new ZiweiStarLocator();

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testCalculatePosition_Example1() {
        // 例一：2024.1.27 0:30 出生 男 金四局，在卯安紫微，紫微星旺 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 27);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 0, false);

        // 验证紫微星位置
        assertEquals(1, star.getPosition(), "紫微星应该在卯位（索引1）");
        assertEquals("旺", star.getBrightness().getChinese(), "紫微星应该旺");
    }

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testCalculatePosition_Example2() {
        // 例二：2024.1.13 0:30日出生金四局，男，在丑安紫微，紫微星庙 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 13);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 0, false);

        assertEquals(11, star.getPosition(), "紫微星应该在丑位（索引11）");
        assertEquals("庙", star.getBrightness().getChinese(), "紫微星应该庙");
    }

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testCalculatePosition_Example3() {
        // 例三：2024.1.15 0:30 日出生金四局，在子安紫微 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 15);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 0, false);

        assertEquals(10, star.getPosition(), "紫微星应该在子位（索引10）");
    }

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testCalculatePosition_LateZiHour() {
        // 测试晚子时的情况，2024.1.15 23:30 日出生金四局，在巳安紫微 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 15);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 12, false);

        // 由于是晚子时，日数会加1，变成16日
        assertEquals(3, star.getPosition());
    }

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testCalculatePosition_CrossMonth() {
        // 测试跨月的情况，2024.1.30 23:30 日出生金四局，在辰安紫微 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 30);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 12, false);

        // 由于是晚子时且是月末，日数会变成1
        assertEquals(2, star.getPosition());
    }

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testRealCase1_1994_12_08() {
        // 阳历1994年12月8日 9点05分出生
        // 农历甲戌年十一月初六日 巳时
        // 五行局：土五局
        LocalDate date = LocalDate.of(1994, 12, 8);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 2, false);

        assertEquals(5, star.getPosition(), "紫微星应该在未位（索引5）");
    }

    /**
     * 测试通过 20250126 23:15
     */
    @Test
    public void testRealCase2_1999_04_18() {
        // 阳历1999年4月18日 12点30分出生，
        // 农历己卯年三月初三日 午时
        // 五行局：火六局
        LocalDate date = LocalDate.of(1999, 4, 18);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 6, false);

        assertEquals(9, star.getPosition(), "紫微星应该在亥位（索引9）");
    }

    /**
     * 测试通过 20250126 23:15
     */
    @Test
    public void testRealCase3_2024_11_15() {
        // 阳历2024年11月15日 03:30 命宫在酉，紫微在辰
        // 五行局：金四局
        LocalDate date = LocalDate.of(2024, 11, 15);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getZiweiStarPosition(dateStr, 2, false);

        // 验证紫微星位置
        assertEquals(2, star.getPosition(), "紫微星应该在辰位（索引2）");
    }

}
