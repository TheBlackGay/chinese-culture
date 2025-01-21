package com.chinese.culture.admin.core.iztro.interpreter;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.analyzer.PalaceRelationAnalyzer;
import com.chinese.culture.admin.core.iztro.analyzer.PatternAnalyzer;
import com.chinese.culture.admin.core.iztro.analyzer.StarCombinationAnalyzer;
import com.chinese.culture.admin.core.iztro.calculator.MutagenCalculator;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.AstrolabeInterpretation;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 命盘解释器
 * 整合各个分析器的功能，提供完整的命盘解释
 * 
 * 主要功能：
 * 1. 宫位关系分析（相刑、相合、三合）
 * 2. 星耀组合分析（三方四正、星耀会合）
 * 3. 四化关系分析（四化、星耀冲突）
 * 4. 重要格局分析（命主、财帛、官禄、婚姻）
 * 
 * 使用示例：
 * <pre>
 * Astrolabe astrolabe = new Astrolabe();
 * // 设置命盘数据
 * AstrolabeInterpretation result = AstrolabeInterpreter.interpretAstrolabe(astrolabe);
 * </pre>
 */
@Slf4j
public class AstrolabeInterpreter {

    // 有效天干列表
    private static final List<String> VALID_STEMS = Arrays.asList("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸");

    /**
     * 解释完整命盘
     * 
     * @param astrolabe 命盘数据
     * @return 命盘解释结果
     */
    public static AstrolabeInterpretation interpretAstrolabe(Astrolabe astrolabe) {
        try {
            // 验证输入数据
            validateInput(astrolabe);
            
            AstrolabeInterpretation interpretation = new AstrolabeInterpretation();
            
            // 1. 解释宫位关系
            interpretation.setPalaceRelations(interpretPalaceRelations(astrolabe.getPalaces()));
            
            // 2. 解释星耀组合
            interpretation.setStarCombinations(interpretStarCombinations(astrolabe.getStars()));
            
            // 3. 解释四化关系
            interpretation.setMutagenRelations(interpretMutagenRelations(astrolabe.getYearStem(), astrolabe.getStars()));
            
            // 4. 解释重要格局
            interpretation.setMajorPatterns(interpretMajorPatterns(astrolabe));
            
            return interpretation;
            
        } catch (Exception e) {
            log.error("命盘解释失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 验证输入数据
     */
    private static void validateInput(Astrolabe astrolabe) {
        if (astrolabe == null) {
            throw new BusinessException(ResultCode.INVALID_PARAM, "命盘数据不能为空");
        }
        if (astrolabe.getPalaces() == null || astrolabe.getPalaces().size() != 12) {
            throw new BusinessException(ResultCode.INVALID_PARAM, "宫位数量必须为12");
        }
        if (astrolabe.getStars() == null || astrolabe.getStars().isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_PARAM, "星耀列表不能为空");
        }
        if (astrolabe.getYearStem() == null || astrolabe.getYearStem().trim().isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_PARAM, "年干不能为空");
        }
        if (!VALID_STEMS.contains(astrolabe.getYearStem())) {
            throw new BusinessException(ResultCode.INVALID_PARAM, "无效的年干");
        }
        // 检查宫位索引是否连续
        for (int i = 0; i < astrolabe.getPalaces().size(); i++) {
            if (astrolabe.getPalaces().get(i).getIndex() != i) {
                throw new BusinessException(ResultCode.INVALID_PARAM, "宫位索引不连续");
            }
        }
    }
    
    /**
     * 解释宫位关系
     */
    private static AstrolabeInterpretation.PalaceRelations interpretPalaceRelations(List<Palace> palaces) {
        AstrolabeInterpretation.PalaceRelations relations = new AstrolabeInterpretation.PalaceRelations();
        
        // 分析宫位关系
        Map<String, Map<String, List<String>>> palaceRelations = PalaceRelationAnalyzer.analyzePalaceRelations(palaces);
        
        // 设置相刑关系
        relations.setPunishments(palaceRelations.get("相刑"));
        
        // 设置相合关系
        relations.setHarmonies(palaceRelations.get("相合"));
        
        // 设置三合局
        relations.setTrineFormations(palaceRelations.get("三合"));
        
        return relations;
    }
    
    /**
     * 解释星耀组合
     */
    private static AstrolabeInterpretation.StarCombinations interpretStarCombinations(List<Star> stars) {
        AstrolabeInterpretation.StarCombinations combinations = new AstrolabeInterpretation.StarCombinations();
        
        // 分析三方四正
        Map<String, List<String>> trineAndOpposition = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            Map<String, List<String>> result = StarCombinationAnalyzer.analyzeTrineAndOpposition(stars, i);
            if (!result.get("三方四正").isEmpty()) {
                trineAndOpposition.put("宫位" + i, result.get("三方四正"));
            }
        }
        combinations.setTrineAndOpposition(trineAndOpposition);
        
        // 分析星耀会合
        combinations.setConvergence(StarCombinationAnalyzer.analyzeStarConvergence(stars));
        
        return combinations;
    }
    
    /**
     * 解释四化关系
     */
    private static AstrolabeInterpretation.MutagenRelations interpretMutagenRelations(String yearStem, List<Star> stars) {
        AstrolabeInterpretation.MutagenRelations relations = new AstrolabeInterpretation.MutagenRelations();
        
        // 转换星耀列表为名称列表
        List<String> starNames = stars.stream()
                .map(Star::getName)
                .collect(Collectors.toList());
        
        // 转换星耀列表为位置映射
        Map<String, Integer> starPositions = stars.stream()
                .collect(Collectors.toMap(Star::getName, Star::getPosition));
        
        // 计算四化关系
        relations.setMutagenRelations(MutagenCalculator.calculateMutagenRelations(yearStem, starNames));
        
        // 计算星耀冲突
        relations.setStarConflicts(MutagenCalculator.calculateStarConflicts(starPositions));
        
        return relations;
    }
    
    /**
     * 解释重要格局
     */
    private static List<String> interpretMajorPatterns(Astrolabe astrolabe) {
        List<String> patterns = new ArrayList<>();
        
        // 分析命主格局
        analyzeMasterPattern(astrolabe, patterns);
        
        // 分析财帛格局
        analyzeWealthPattern(astrolabe, patterns);
        
        // 分析官禄格局
        analyzeCareerPattern(astrolabe, patterns);
        
        // 分析婚姻格局
        analyzeMarriagePattern(astrolabe, patterns);
        
        // 分析智慧格局
        analyzeWisdomPattern(astrolabe, patterns);
        
        // 分析学术格局
        analyzeAcademicPattern(astrolabe, patterns);
        
        // 分析情感格局
        analyzeEmotionalPattern(astrolabe, patterns);
        
        return patterns;
    }
    
    /**
     * 分析命主格局
     */
    private static void analyzeMasterPattern(Astrolabe astrolabe, List<String> patterns) {
        Palace masterPalace = astrolabe.getPalaces().get(0); // 命宫
        List<Star> masterStars = masterPalace.getStars();
        
        // 使用格局分析器进行分析
        if (PatternAnalyzer.isNoblePattern(masterStars)) {
            patterns.add("命宫根基稳固");
            patterns.add("命主贵重");
        }
        if (PatternAnalyzer.isWisdomPattern(masterStars)) {
            patterns.add("智慧超群");
        }
        if (PatternAnalyzer.isMindPattern(masterStars)) {
            patterns.add("心思细腻");
        }
        if (PatternAnalyzer.isAuthorityPattern(masterStars)) {
            patterns.add("权威显赫");
        }
    }
    
    /**
     * 分析财帛格局
     */
    private static void analyzeWealthPattern(Astrolabe astrolabe, List<String> patterns) {
        // 查找财帛宫
        Palace wealthPalace = astrolabe.getPalaces().stream()
                .filter(palace -> "财帛".equals(palace.getName()))
                .findFirst()
                .orElse(null);
        
        if (wealthPalace != null) {
            List<Star> wealthStars = wealthPalace.getStars();
            
            // 使用格局分析器进行分析
            if (PatternAnalyzer.isWealthPattern(wealthStars)) {
                patterns.add("财运亨通");
                patterns.add("正财旺盛");
            }
            if (PatternAnalyzer.isSideWealthPattern(wealthStars)) {
                patterns.add("偏财多得");
            }
            if (PatternAnalyzer.isBusinessPattern(wealthStars)) {
                patterns.add("经商有道");
            }
        }
    }
    
    /**
     * 分析官禄格局
     */
    private static void analyzeCareerPattern(Astrolabe astrolabe, List<String> patterns) {
        // 查找官禄宫
        Palace careerPalace = astrolabe.getPalaces().stream()
                .filter(palace -> "官禄".equals(palace.getName()))
                .findFirst()
                .orElse(null);
        
        if (careerPalace != null) {
            List<Star> careerStars = careerPalace.getStars();
            
            // 使用格局分析器进行分析
            if (PatternAnalyzer.isCareerPattern(careerStars)) {
                patterns.add("官运亨通");
            }
            if (PatternAnalyzer.isAcademicPattern(careerStars)) {
                patterns.add("学术优秀");
            }
            if (PatternAnalyzer.isLeadershipPattern(careerStars)) {
                patterns.add("领导才能");
            }
        }
    }
    
    /**
     * 分析婚姻格局
     */
    private static void analyzeMarriagePattern(Astrolabe astrolabe, List<String> patterns) {
        // 查找夫妻宫
        Palace marriagePalace = astrolabe.getPalaces().stream()
                .filter(palace -> "夫妻".equals(palace.getName()))
                .findFirst()
                .orElse(null);
        
        if (marriagePalace != null) {
            List<Star> marriageStars = marriagePalace.getStars();
            
            // 使用格局分析器进行分析
            if (PatternAnalyzer.isMarriagePattern(marriageStars)) {
                patterns.add("婚姻美满");
                patterns.add("姻缘和顺");
            }
            if (PatternAnalyzer.isEmotionPattern(marriageStars)) {
                patterns.add("情感丰富");
            }
            if (PatternAnalyzer.isSpousePattern(marriageStars)) {
                patterns.add("配偶贤良");
            }
        }
    }
    
    private static void analyzeWisdomPattern(Astrolabe astrolabe, List<String> patterns) {
        List<Palace> palaces = astrolabe.getPalaces();
        Palace mingGong = palaces.get(0);  // 命宫
        
        // 检查命宫是否有智慧星耀
        boolean hasWisdomStars = mingGong.getStars().stream()
                .anyMatch(star -> Arrays.asList("天机", "文昌", "文曲").contains(star.getName()));
        
        if (hasWisdomStars) {
            patterns.add("智慧超群");
            patterns.add("心思细腻");
        }
    }
    
    private static void analyzeAcademicPattern(Astrolabe astrolabe, List<String> patterns) {
        List<Palace> palaces = astrolabe.getPalaces();
        
        // 检查文昌文曲组合
        boolean hasAcademicCombination = palaces.stream()
                .anyMatch(palace -> palace.getStars().stream()
                        .anyMatch(star -> "文昌".equals(star.getName())) &&
                        palace.getStars().stream()
                        .anyMatch(star -> "文曲".equals(star.getName())));
        
        if (hasAcademicCombination) {
            patterns.add("学术优秀");
        }
    }
    
    private static void analyzeEmotionalPattern(Astrolabe astrolabe, List<String> patterns) {
        List<Palace> palaces = astrolabe.getPalaces();
        Palace qingGong = null;  // 情感宫
        
        // 找到情感宫
        for (Palace palace : palaces) {
            if ("夫妻".equals(palace.getName())) {
                qingGong = palace;
                break;
            }
        }
        
        if (qingGong != null) {
            // 检查情感宫的星耀组合
            boolean hasEmotionalStars = qingGong.getStars().stream()
                    .anyMatch(star -> Arrays.asList("天同", "太阴", "文昌", "文曲").contains(star.getName()));
            
            if (hasEmotionalStars) {
                patterns.add("情感丰富");
            }
        }
    }
} 