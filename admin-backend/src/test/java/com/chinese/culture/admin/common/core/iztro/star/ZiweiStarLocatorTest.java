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
        // 例一：2024.1.27 0:30 出生 男 金四局，在卯安紫微 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 27);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 0, false);

        // 验证紫微星位置
        assertEquals(1, star.getPosition(), "紫微星应该在卯位（索引1）");
    }

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testCalculatePosition_Example2() {
        // 例二：2024.1.13 0:30日出生金四局，男，在丑安紫微 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 13);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 0, false);

        assertEquals(11, star.getPosition(), "紫微星应该在丑位（索引11）");
    }

    /**
     * 测试通过 20250126 22:50
     */
    @Test
    public void testCalculatePosition_Example3() {
        // 例三：2024.1.15 0:30 日出生金四局，在子安紫微 -- 已校准
        LocalDate date = LocalDate.of(2024, 1, 15);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 0, false);

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
        StarBO star = locator.getStartIndex(dateStr, 12, false);

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
        StarBO star = locator.getStartIndex(dateStr, 12, false);

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
        StarBO star = locator.getStartIndex(dateStr, 2, false);

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
        StarBO star = locator.getStartIndex(dateStr, 6, false);

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
        StarBO star = locator.getStartIndex(dateStr, 2, false);

        // 验证紫微星位置
        assertEquals(2, star.getPosition(), "紫微星应该在辰位（索引2）");
    }

    @Test
    public void testRealCase4_1997_02_05() {
        // 1997年02月05日 05:18 命宫在戌，
        LocalDate date = LocalDate.of(1997, 2, 5);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 3, false);

        // 验证紫微星位置
        assertEquals(6, star.getPosition(), "紫微星应该在午位（索引6）");
    }

    @Test
    public void testRealCase5_1988_07_23() {
        // 1988年07月23日 13:45 命宫在申
        LocalDate date = LocalDate.of(1988, 7, 23);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 6, false);

        // 验证紫微星位置
        assertEquals(11, star.getPosition(), "紫微星应该在亥位（索引11）");
    }

    @Test
    public void testRealCase6_1995_09_15() {
        // 1995年09月15日 21:30 命宫在子
        LocalDate date = LocalDate.of(1995, 9, 15);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 10, false);

        // 验证紫微星位置
        assertEquals(2, star.getPosition(), "紫微星应该在辰位（索引2）");
    }

    @Test
    public void testRealCase7_2000_03_08() {
        // 2000年03月08日 02:15 命宫在卯
        LocalDate date = LocalDate.of(2000, 3, 8);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 1, false);

        // 验证紫微星位置
        assertEquals(7, star.getPosition(), "紫微星应该在未位（索引7）");
    }

    @Test
    public void testRealCase8_1992_12_25() {
        // 1992年12月25日 17:20 命宫在午
        LocalDate date = LocalDate.of(1992, 12, 25);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 8, false);

        // 验证紫微星位置
        assertEquals(3, star.getPosition(), "紫微星应该在卯位（索引3）");
    }

    @Test
    public void testRealCase9_1990_05_18() {
        // 1990年05月18日 08:40 命宫在巳
        LocalDate date = LocalDate.of(1990, 5, 18);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 4, false);

        // 验证紫微星位置
        assertEquals(9, star.getPosition(), "紫微星应该在戌位（索引9）");
    }

    @Test
    public void testRealCase10_1985_11_30() {
        // 1985年11月30日 23:50 命宫在丑
        LocalDate date = LocalDate.of(1985, 11, 30);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 11, false);

        // 验证紫微星位置
        assertEquals(5, star.getPosition(), "紫微星应该在巳位（索引5）");
    }

    @Test
    public void testRealCase11_1998_08_12() {
        // 1998年08月12日 15:10 命宫在未
        LocalDate date = LocalDate.of(1998, 8, 12);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 7, false);

        // 验证紫微星位置
        assertEquals(2, star.getPosition(), "紫微星应该在寅位（索引2）");
    }

    @Test
    public void testRealCase12_1993_04_05() {
        // 1993年04月05日 19:25 命宫在酉
        LocalDate date = LocalDate.of(1993, 4, 5);
        String dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        StarBO star = locator.getStartIndex(dateStr, 9, false);

        // 验证紫微星位置
        assertEquals(8, star.getPosition(), "紫微星应该在申位（索引8）");
    }
}
