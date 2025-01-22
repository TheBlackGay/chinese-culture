package com.chinese.culture.admin.service;

import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
import com.chinese.culture.admin.persist.bo.AstrolabeQueryBO;
import com.chinese.culture.admin.service.impl.AstrolabeBizServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AstrolabeBizServiceTest {

    private AstrolabeBizService astrolabeBizService;

    @BeforeEach
    void setUp() {
        astrolabeBizService = new AstrolabeBizServiceImpl();
    }

    @Test
    void testGetAstrolabe_WithSolarDate() {
        // 准备测试数据 - 阳历1990年8月15日寅时(3-5点)出生的男性
        AstrolabeQueryBO queryDTO = new AstrolabeQueryBO();
        queryDTO.setBirthYear(1990);
        queryDTO.setBirthMonth(8);
        queryDTO.setBirthDay(15);
        queryDTO.setBirthHour(3); // 寅时
        queryDTO.setGender(1); // 男
        queryDTO.setIsLunar(false);

        // 执行测试
        AstrolabeBO astrolabe = astrolabeBizService.getAstrolabe(queryDTO);

        // 验证结果
        assertNotNull(astrolabe);
        assertEquals("1990年8月15日", astrolabe.getSolarDate());
        assertEquals("男", astrolabe.getGender());
        assertEquals(3, astrolabe.getBirthHour());
        assertNotNull(astrolabe.getPalaces());
        assertEquals(12, astrolabe.getPalaces().size());
    }

    @Test
    void testGetAstrolabe_WithLunarDate() {
        // 准备测试数据 - 农历1990年七月廿五日寅时出生的女性
        AstrolabeQueryBO queryDTO = new AstrolabeQueryBO();
        queryDTO.setBirthYear(1990);
        queryDTO.setBirthMonth(7);
        queryDTO.setBirthDay(25);
        queryDTO.setBirthHour(3);
        queryDTO.setGender(2); // 女
        queryDTO.setIsLunar(true);

        // 执行测试
        AstrolabeBO astrolabe = astrolabeBizService.getAstrolabe(queryDTO);

        // 验证结果
        assertNotNull(astrolabe);
        assertEquals("1990年7月25日", astrolabe.getLunarDate());
        assertEquals("女", astrolabe.getGender());
        assertEquals(3, astrolabe.getBirthHour());
        assertNotNull(astrolabe.getPalaces());
        assertEquals(12, astrolabe.getPalaces().size());
    }

    @Test
    void testGetAstrolabe_InvalidBirthHour() {
        // 准备测试数据 - 无效的时辰
        AstrolabeQueryBO queryDTO = new AstrolabeQueryBO();
        queryDTO.setBirthYear(1990);
        queryDTO.setBirthMonth(8);
        queryDTO.setBirthDay(15);
        queryDTO.setBirthHour(13); // 无效的时辰
        queryDTO.setGender(1);
        queryDTO.setIsLunar(false);

        // 验证异常
        assertThrows(Exception.class, () -> {
            astrolabeBizService.getAstrolabe(queryDTO);
        });
    }

    @Test
    void testGetAstrolabe_InvalidGender() {
        // 准备测试数据 - 无效的性别
        AstrolabeQueryBO queryDTO = new AstrolabeQueryBO();
        queryDTO.setBirthYear(1990);
        queryDTO.setBirthMonth(8);
        queryDTO.setBirthDay(15);
        queryDTO.setBirthHour(3);
        queryDTO.setGender(3); // 无效的性别
        queryDTO.setIsLunar(false);

        // 验证异常
        assertThrows(Exception.class, () -> {
            astrolabeBizService.getAstrolabe(queryDTO);
        });
    }
}
