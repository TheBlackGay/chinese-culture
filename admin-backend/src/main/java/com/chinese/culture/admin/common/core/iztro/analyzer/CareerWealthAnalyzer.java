package com.chinese.culture.admin.common.core.iztro.analyzer;

import com.chinese.culture.admin.api.dto.request.CareerWealthAnalysisRequestDTO;
import com.chinese.culture.admin.api.dto.response.CareerWealthAnalysisResponseDTO;
import com.chinese.culture.admin.common.core.iztro.calculator.CoreCalculator;
import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 事业财运分析器
 */
@Slf4j
@Component
public class CareerWealthAnalyzer {

    /**
     * 分析事业和财富
     */
    public CareerWealthAnalysisResponseDTO analyze(CareerWealthAnalysisRequestDTO request) {
        // 计算命盘
        AstrolabeBO astrolabe = CoreCalculator.calculate(
            request.getLunarYear(),
            request.getLunarMonth(),
            request.getLunarDay(),
            request.getBirthHour(),
            request.getGender()
        );

        CareerWealthAnalysisResponseDTO response = new CareerWealthAnalysisResponseDTO();

        // 分析事业
        String careerAnalysis = analyzeCareer(astrolabe);
        response.setCareerAnalysis(careerAnalysis);

        // 分析财富
        String wealthAnalysis = analyzeWealth(astrolabe);
        response.setWealthAnalysis(wealthAnalysis);

        // 计算吉凶指数
        int auspiciousness = calculateAuspiciousness(astrolabe);
        response.setAuspiciousness(auspiciousness);

        // 生成建议
        List<String> suggestions = generateSuggestions(astrolabe);
        response.setSuggestions(suggestions);

        return response;
    }

    /**
     * 分析事业
     */
    private String analyzeCareer(AstrolabeBO astrolabe) {
        StringBuilder analysis = new StringBuilder();
        List<PalaceBO> palaces = astrolabe.getPalaces();

        // 分析官禄宫
        PalaceBO careerPalace = palaces.stream()
            .filter(p -> "官禄".equals(p.getName()))
            .findFirst()
            .orElse(null);

        if (careerPalace != null) {
            analysis.append("官禄宫位于").append(careerPalace.getBranch()).append("，");

            // 分析主星
            List<StarBO> majorStars = careerPalace.getStars().stream()
                .filter(s -> s.getName() == StarName.ZIWEI ||
                           s.getName() == StarName.TIANJI ||
                           s.getName() == StarName.TAIYANG ||
                           s.getName() == StarName.WUQU)
                    .collect(Collectors.toList());

            if (!majorStars.isEmpty()) {
                analysis.append("有");
                majorStars.forEach(star -> analysis.append(star.getName().getDescription()).append("、"));
                analysis.setLength(analysis.length() - 1);  // 移除最后的顿号
                analysis.append("入主，");
            }

            // 分析四化
            if (!careerPalace.getMutagens().isEmpty()) {
                analysis.append("受");
                careerPalace.getMutagens().forEach(m -> analysis.append(m).append("、"));
                analysis.setLength(analysis.length() - 1);
                analysis.append("影响，");
            }
        }

        return analysis.toString();
    }

    /**
     * 分析财富
     */
    private String analyzeWealth(AstrolabeBO astrolabe) {
        StringBuilder analysis = new StringBuilder();
        List<PalaceBO> palaces = astrolabe.getPalaces();

        // 分析财帛宫
        PalaceBO wealthPalace = palaces.stream()
            .filter(p -> "财帛".equals(p.getName()))
            .findFirst()
            .orElse(null);

        if (wealthPalace != null) {
            analysis.append("财帛宫位于").append(wealthPalace.getBranch()).append("，");

            // 分析主星
            List<StarBO> majorStars = wealthPalace.getStars().stream()
                    .filter(s -> s.getName() == StarName.ZIWEI ||
                            s.getName() == StarName.TIANFU ||
                            s.getName() == StarName.TAIYANG ||
                            s.getName() == StarName.WUQU)
                    .collect(Collectors.toList());

            if (!majorStars.isEmpty()) {
                analysis.append("有");
                majorStars.forEach(star -> analysis.append(star.getName().getDescription()).append("、"));
                analysis.setLength(analysis.length() - 1);
                analysis.append("入主，");
            }

            // 分析四化
            if (!wealthPalace.getMutagens().isEmpty()) {
                analysis.append("受");
                wealthPalace.getMutagens().forEach(m -> analysis.append(m).append("、"));
                analysis.setLength(analysis.length() - 1);
                analysis.append("影响，");
            }
        }

        return analysis.toString();
    }

    /**
     * 计算吉凶指数
     */
    private int calculateAuspiciousness(AstrolabeBO astrolabe) {
        int score = 50;  // 基础分

        List<PalaceBO> palaces = astrolabe.getPalaces();
        PalaceBO careerPalace = palaces.stream()
            .filter(p -> "官禄".equals(p.getName()))
            .findFirst()
            .orElse(null);

        PalaceBO wealthPalace = palaces.stream()
            .filter(p -> "财帛".equals(p.getName()))
            .findFirst()
            .orElse(null);

        if (careerPalace != null) {
            // 根据主星加分
            score += careerPalace.getStars().stream()
                .filter(s -> s.getName() == StarName.ZIWEI ||
                           s.getName() == StarName.TIANJI ||
                           s.getName() == StarName.TAIYANG ||
                           s.getName() == StarName.WUQU)
                .count() * 10;

            // 根据四化加分
            score += careerPalace.getMutagens().size() * 5;
        }

        if (wealthPalace != null) {
            // 根据主星加分
            score += wealthPalace.getStars().stream()
                .filter(s -> s.getName() == StarName.ZIWEI ||
                           s.getName() == StarName.TIANFU ||
                           s.getName() == StarName.TAIYANG ||
                           s.getName() == StarName.WUQU)
                .count() * 10;

            // 根据四化加分
            score += wealthPalace.getMutagens().size() * 5;
        }

        return Math.min(100, Math.max(0, score));  // 确保分数在0-100之间
    }

    /**
     * 生成建议
     */
    private List<String> generateSuggestions(AstrolabeBO astrolabe) {
        List<String> suggestions = new ArrayList<>();
        List<PalaceBO> palaces = astrolabe.getPalaces();

        // 分析官禄宫
        PalaceBO careerPalace = palaces.stream()
            .filter(p -> "官禄".equals(p.getName()))
            .findFirst()
            .orElse(null);

        if (careerPalace != null) {
            // 根据主星生成建议
            careerPalace.getStars().stream()
                .filter(s -> s.getName() == StarName.ZIWEI ||
                           s.getName() == StarName.TIANJI ||
                           s.getName() == StarName.TAIYANG ||
                           s.getName() == StarName.WUQU)
                .forEach(star -> {
                    switch (star.getName()) {
                        case ZIWEI -> suggestions.add("紫微星入官禄，宜从事管理、领导工作");
                        case TIANJI -> suggestions.add("天机星入官禄，宜从事技术、研发工作");
                        case TAIYANG -> suggestions.add("太阳星入官禄，宜从事公职、教育工作");
                        case WUQU -> suggestions.add("武曲星入官禄，宜从事金融、商业工作");
                    }
                });
        }

        // 分析财帛宫
        PalaceBO wealthPalace = palaces.stream()
            .filter(p -> "财帛".equals(p.getName()))
            .findFirst()
            .orElse(null);

        if (wealthPalace != null) {
            // 根据主星生成建议
            wealthPalace.getStars().stream()
                .filter(s -> s.getName() == StarName.ZIWEI ||
                           s.getName() == StarName.TIANFU ||
                           s.getName() == StarName.TAIYANG ||
                           s.getName() == StarName.WUQU)
                .forEach(star -> {
                    switch (star.getName()) {
                        case ZIWEI -> suggestions.add("紫微星入财帛，宜通过管理职位积累财富");
                        case TIANFU -> suggestions.add("天府星入财帛，宜通过稳健投资积累财富");
                        case TAIYANG -> suggestions.add("太阳星入财帛，宜通过正当职业积累财富");
                        case WUQU -> suggestions.add("武曲星入财帛，宜通过商业经营积累财富");
                    }
                });
        }

        return suggestions;
    }
}
