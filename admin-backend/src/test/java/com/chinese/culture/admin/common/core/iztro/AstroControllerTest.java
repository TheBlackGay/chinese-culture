 package com.chinese.culture.admin.common.core.iztro;

 import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
 import org.junit.jupiter.api.Test;
 import org.springframework.boot.test.context.SpringBootTest;

 import javax.annotation.Resource;
 import java.util.List;
 import java.util.Map;

 import static org.junit.jupiter.api.Assertions.*;

 @SpringBootTest
 public class AstroControllerTest {

     @Resource
     private AstroController astroController;

     @Test
     public void testGenerateAstrolabe() {
         // 测试用例：1990年8月15日 午时(11-13点，索引为6)，阳男
         List<PalaceBO> palaces = astroController.generateAstrolabe(1990, 8, 15, 6, 1);

         // 验证基本信息
         assertNotNull(palaces);
         assertEquals(12, palaces.size());  // 应该有12个宫位

         // 验证每个宫位都有基本信息
         for (PalaceBO palace : palaces) {
             assertNotNull(palace.getBranch());  // 地支不为空
             assertNotNull(palace.getStars());   // 星耀列表不为空
         }
     }

     @Test
     public void testGenerateInterpretation() {
         // 先生成星盘
         List<PalaceBO> palaces = astroController.generateAstrolabe(1990, 8, 15, 6, 1);

         // 测试解释生成
         String interpretation = astroController.generateInterpretation(palaces);
         assertNotNull(interpretation);
         assertFalse(interpretation.isEmpty());
     }

     @Test
     public void testAnalyzeHoroscopePatterns() {
         // 先生成星盘
         List<PalaceBO> palaces = astroController.generateAstrolabe(1990, 8, 15, 6, 1);

         // 测试格局分析
         List<Map<String, Object>> patterns = astroController.analyzeHoroscopePatterns(palaces);
         assertNotNull(patterns);
         assertFalse(patterns.isEmpty());
     }

     @Test
     public void testGetOverallAuspiciousness() {
         // 先生成星盘
         List<PalaceBO> palaces = astroController.generateAstrolabe(1990, 8, 15, 6, 1);

         // 测试吉凶分析
         Map<String, Object> auspiciousness = astroController.getOverallAuspiciousness(palaces);
         assertNotNull(auspiciousness);
         assertTrue(auspiciousness.containsKey("score"));
     }
 }
