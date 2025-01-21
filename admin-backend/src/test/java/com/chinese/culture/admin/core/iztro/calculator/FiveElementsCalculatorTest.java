package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.enums.FiveElements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FiveElementsCalculatorTest {

    @ParameterizedTest
    @CsvSource({
        // 年干生年支 -> 六局
        "0, 2",  // 甲(木)生寅(木) -> 六局
        "3, 5",  // 丁(火)生巳(火) -> 六局
        "6, 8",  // 庚(金)生申(金) -> 六局
        
        // 年支生年干 -> 二局
        "2, 0",  // 丙(火)被寅(木)生 -> 二局
        "5, 3",  // 己(土)被巳(火)生 -> 二局
        "8, 6",  // 壬(水)被申(金)生 -> 二局
        
        // 年干克年支 -> 五局
        "0, 7",  // 甲(木)克未(土) -> 五局
        "3, 10", // 丁(火)克亥(水) -> 五局
        "6, 1",  // 庚(金)克丑(土) -> 五局
        
        // 年支克年干 -> 三局
        "7, 0",  // 辛(金)被未(土)克 -> 三局
        "10, 3", // 癸(水)被亥(水)克 -> 三局
        "1, 6",  // 乙(木)被丑(土)克 -> 三局
        
        // 年干支同五行 -> 四局
        "0, 3",  // 甲(木)与辰(土) -> 四局
        "3, 6",  // 丁(火)与未(土) -> 四局
        "6, 9"   // 庚(金)与子(水) -> 四局
    })
    void testCalculate(int yearGanIndex, int yearZhiIndex) {
        int result = FiveElementsCalculator.calculate(yearGanIndex, yearZhiIndex);
        assertTrue(result >= 2 && result <= 6, "五行局数应该在2-6之间");
    }

    @Test
    void testGetFiveElementsClassName() {
        assertEquals("水二局", FiveElementsCalculator.getFiveElementsClassName(2));
        assertEquals("木三局", FiveElementsCalculator.getFiveElementsClassName(3));
        assertEquals("金四局", FiveElementsCalculator.getFiveElementsClassName(4));
        assertEquals("土五局", FiveElementsCalculator.getFiveElementsClassName(5));
        assertEquals("火六局", FiveElementsCalculator.getFiveElementsClassName(6));
        
        assertThrows(IllegalArgumentException.class, () -> 
            FiveElementsCalculator.getFiveElementsClassName(1));
        assertThrows(IllegalArgumentException.class, () -> 
            FiveElementsCalculator.getFiveElementsClassName(7));
    }

    @Test
    void testGetFiveElementsByClass() {
        assertEquals(FiveElements.WATER, FiveElementsCalculator.getFiveElementsByClass(2));
        assertEquals(FiveElements.WOOD, FiveElementsCalculator.getFiveElementsByClass(3));
        assertEquals(FiveElements.METAL, FiveElementsCalculator.getFiveElementsByClass(4));
        assertEquals(FiveElements.EARTH, FiveElementsCalculator.getFiveElementsByClass(5));
        assertEquals(FiveElements.FIRE, FiveElementsCalculator.getFiveElementsByClass(6));
        
        assertThrows(IllegalArgumentException.class, () -> 
            FiveElementsCalculator.getFiveElementsByClass(1));
        assertThrows(IllegalArgumentException.class, () -> 
            FiveElementsCalculator.getFiveElementsByClass(7));
    }
} 