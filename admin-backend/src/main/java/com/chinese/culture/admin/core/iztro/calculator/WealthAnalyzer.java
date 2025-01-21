package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 财运分析器
 */
@Slf4j
public class WealthAnalyzer {

    /**
     * 分析财运状况
     */
    private static Map<String, Object> analyzeWealth(Palace wealthPalace, int age) {
        Map<String, Object> result = new HashMap<>();
        int score = 60;  // 基础分数

        // 分析星耀亮度
        for (Star star : wealthPalace.getAllStars()) {
            Brightness brightness = star.getBrightness();
            if (brightness == Brightness.TEMPLE) {
                score += 10;
            } else if (brightness == Brightness.GAIN) {
                score -= 5;
            } else if (brightness == Brightness.TRAPPED) {
                score -= 10;
            }
        }

        // 分析四化
        for (String mutagen : wealthPalace.getMutagens()) {

            switch (Mutagen.fromString(mutagen)) {
                case LUCKY:
                    score += 5;
                    break;
                case POWER:
                    score += 5;
                    break;
                case SKILL:
                    score += 3;
                    break;
                case WEAK:
                    score -= 5;
                    break;
            }
        }

        // 确保分数在0-100范围内
        score = Math.max(0, Math.min(100, score));
        result.put("score", score);

        return result;
    }

    private static int calculateBrightnessEffect(Star star) {
        Brightness brightness = (star.getBrightness());
        switch (brightness) {
            case TEMPLE:
                return 2;
            case STRONG:
                return 1;
            case GAIN:
                return -1;
            case TRAPPED:
                return -2;
            default:
                return 0;
        }
    }

    private static Brightness toBrightness(Brightness brightness) {
        switch (brightness) {
            case TEMPLE:
                return Brightness.TEMPLE;
            case NORMAL:
                return Brightness.STRONG;
            case WEAK:
                return Brightness.GAIN;
            case TRAPPED:
                return Brightness.TRAPPED;
            default:
                return Brightness.STRONG;
        }
    }

    private static Mutagen parseMutagen(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("化气不能为空");
        }
        return Mutagen.fromString(text);
    }
}
