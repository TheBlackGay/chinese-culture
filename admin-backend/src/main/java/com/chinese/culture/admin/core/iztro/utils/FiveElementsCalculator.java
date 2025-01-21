package com.chinese.culture.admin.core.iztro.utils;

import lombok.extern.slf4j.Slf4j;
import java.util.*;

/**
 * 五行计算工具类
 */
@Slf4j
public class FiveElementsCalculator {
    
    // 五行
    private static final String[] FIVE_ELEMENTS = {"金", "木", "水", "火", "土"};
    
    // 天干五行
    private static final Map<String, String> STEM_FIVE_ELEMENTS = new HashMap<>();
    
    // 地支五行
    private static final Map<String, String> BRANCH_FIVE_ELEMENTS = new HashMap<>();
    
    // 五行生克关系
    private static final Map<String, String> GENERATING = new HashMap<>();
    private static final Map<String, String> OVERCOMING = new HashMap<>();
    
    // 五行局
    private static final String[] FIVE_ELEMENTS_PATTERNS = {"水二局", "木三局", "金四局", "土五局", "火六局"};
    
    // 五行局数
    private static final Map<String, Integer> PATTERN_NUMBERS = new HashMap<>();
    
    // 命宫五行局对应表
    private static final Map<String, String> MING_GONG_PATTERNS = new HashMap<>();
    
    static {
        // 初始化天干五行
        STEM_FIVE_ELEMENTS.put("甲", "木");
        STEM_FIVE_ELEMENTS.put("乙", "木");
        STEM_FIVE_ELEMENTS.put("丙", "火");
        STEM_FIVE_ELEMENTS.put("丁", "火");
        STEM_FIVE_ELEMENTS.put("戊", "土");
        STEM_FIVE_ELEMENTS.put("己", "土");
        STEM_FIVE_ELEMENTS.put("庚", "金");
        STEM_FIVE_ELEMENTS.put("辛", "金");
        STEM_FIVE_ELEMENTS.put("壬", "水");
        STEM_FIVE_ELEMENTS.put("癸", "水");
        
        // 初始化地支五行
        BRANCH_FIVE_ELEMENTS.put("子", "水");
        BRANCH_FIVE_ELEMENTS.put("丑", "土");
        BRANCH_FIVE_ELEMENTS.put("寅", "木");
        BRANCH_FIVE_ELEMENTS.put("卯", "木");
        BRANCH_FIVE_ELEMENTS.put("辰", "土");
        BRANCH_FIVE_ELEMENTS.put("巳", "火");
        BRANCH_FIVE_ELEMENTS.put("午", "火");
        BRANCH_FIVE_ELEMENTS.put("未", "土");
        BRANCH_FIVE_ELEMENTS.put("申", "金");
        BRANCH_FIVE_ELEMENTS.put("酉", "金");
        BRANCH_FIVE_ELEMENTS.put("戌", "土");
        BRANCH_FIVE_ELEMENTS.put("亥", "水");
        
        // 初始化五行生克关系
        // 生
        GENERATING.put("木", "火");
        GENERATING.put("火", "土");
        GENERATING.put("土", "金");
        GENERATING.put("金", "水");
        GENERATING.put("水", "木");
        
        // 克
        OVERCOMING.put("木", "土");
        OVERCOMING.put("土", "水");
        OVERCOMING.put("水", "火");
        OVERCOMING.put("火", "金");
        OVERCOMING.put("金", "木");
        
        // 初始化五行局数
        PATTERN_NUMBERS.put("水二局", 2);
        PATTERN_NUMBERS.put("木三局", 3);
        PATTERN_NUMBERS.put("金四局", 4);
        PATTERN_NUMBERS.put("土五局", 5);
        PATTERN_NUMBERS.put("火六局", 6);
        
        // 初始化命宫五行局对应表
        initMingGongPatterns();
    }
    
    /**
     * 初始化命宫五行局对应表
     */
    private static void initMingGongPatterns() {
        // 寅宫
        MING_GONG_PATTERNS.put("甲寅", "木三局");
        MING_GONG_PATTERNS.put("丙寅", "火六局");
        MING_GONG_PATTERNS.put("戊寅", "土五局");
        MING_GONG_PATTERNS.put("庚寅", "金四局");
        MING_GONG_PATTERNS.put("壬寅", "水二局");
        
        // 卯宫
        MING_GONG_PATTERNS.put("乙卯", "木三局");
        MING_GONG_PATTERNS.put("丁卯", "火六局");
        MING_GONG_PATTERNS.put("己卯", "土五局");
        MING_GONG_PATTERNS.put("辛卯", "金四局");
        MING_GONG_PATTERNS.put("癸卯", "水二局");
        
        // 辰宫
        MING_GONG_PATTERNS.put("甲辰", "土五局");
        MING_GONG_PATTERNS.put("丙辰", "火六局");
        MING_GONG_PATTERNS.put("戊辰", "土五局");
        MING_GONG_PATTERNS.put("庚辰", "金四局");
        MING_GONG_PATTERNS.put("壬辰", "水二局");
        
        // 巳宫
        MING_GONG_PATTERNS.put("乙巳", "火六局");
        MING_GONG_PATTERNS.put("丁巳", "火六局");
        MING_GONG_PATTERNS.put("己巳", "土五局");
        MING_GONG_PATTERNS.put("辛巳", "金四局");
        MING_GONG_PATTERNS.put("癸巳", "水二局");
        
        // 午宫
        MING_GONG_PATTERNS.put("甲午", "火六局");
        MING_GONG_PATTERNS.put("丙午", "火六局");
        MING_GONG_PATTERNS.put("戊午", "土五局");
        MING_GONG_PATTERNS.put("庚午", "金四局");
        MING_GONG_PATTERNS.put("壬午", "水二局");
        
        // 未宫
        MING_GONG_PATTERNS.put("乙未", "土五局");
        MING_GONG_PATTERNS.put("丁未", "火六局");
        MING_GONG_PATTERNS.put("己未", "土五局");
        MING_GONG_PATTERNS.put("辛未", "金四局");
        MING_GONG_PATTERNS.put("癸未", "水二局");
        
        // 申宫
        MING_GONG_PATTERNS.put("甲申", "金四局");
        MING_GONG_PATTERNS.put("丙申", "火六局");
        MING_GONG_PATTERNS.put("戊申", "土五局");
        MING_GONG_PATTERNS.put("庚申", "金四局");
        MING_GONG_PATTERNS.put("壬申", "水二局");
        
        // 酉宫
        MING_GONG_PATTERNS.put("乙酉", "金四局");
        MING_GONG_PATTERNS.put("丁酉", "火六局");
        MING_GONG_PATTERNS.put("己酉", "土五局");
        MING_GONG_PATTERNS.put("辛酉", "金四局");
        MING_GONG_PATTERNS.put("癸酉", "水二局");
        
        // 戌宫
        MING_GONG_PATTERNS.put("甲戌", "土五局");
        MING_GONG_PATTERNS.put("丙戌", "火六局");
        MING_GONG_PATTERNS.put("戊戌", "土五局");
        MING_GONG_PATTERNS.put("庚戌", "金四局");
        MING_GONG_PATTERNS.put("壬戌", "水二局");
        
        // 亥宫
        MING_GONG_PATTERNS.put("乙亥", "水二局");
        MING_GONG_PATTERNS.put("丁亥", "火六局");
        MING_GONG_PATTERNS.put("己亥", "土五局");
        MING_GONG_PATTERNS.put("辛亥", "金四局");
        MING_GONG_PATTERNS.put("癸亥", "水二局");
        
        // 子宫
        MING_GONG_PATTERNS.put("甲子", "水二局");
        MING_GONG_PATTERNS.put("丙子", "火六局");
        MING_GONG_PATTERNS.put("戊子", "土五局");
        MING_GONG_PATTERNS.put("庚子", "金四局");
        MING_GONG_PATTERNS.put("壬子", "水二局");
        
        // 丑宫
        MING_GONG_PATTERNS.put("乙丑", "土五局");
        MING_GONG_PATTERNS.put("丁丑", "火六局");
        MING_GONG_PATTERNS.put("己丑", "土五局");
        MING_GONG_PATTERNS.put("辛丑", "金四局");
        MING_GONG_PATTERNS.put("癸丑", "水二局");
    }
    
    /**
     * 获取天干的五行属性
     */
    public static String getStemFiveElement(String stem) {
        return STEM_FIVE_ELEMENTS.get(stem);
    }
    
    /**
     * 获取地支的五行属性
     */
    public static String getBranchFiveElement(String branch) {
        return BRANCH_FIVE_ELEMENTS.get(branch);
    }
    
    /**
     * 判断两个五行是否相生
     */
    public static boolean isGenerating(String element1, String element2) {
        return GENERATING.get(element1) != null && 
               GENERATING.get(element1).equals(element2);
    }
    
    /**
     * 判断两个五行是否相克
     */
    public static boolean isOvercoming(String element1, String element2) {
        return OVERCOMING.get(element1) != null && 
               OVERCOMING.get(element1).equals(element2);
    }
    
    /**
     * 获取生我的五行
     */
    public static String getGeneratingMe(String element) {
        for (Map.Entry<String, String> entry : GENERATING.entrySet()) {
            if (entry.getValue().equals(element)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    /**
     * 获取克我的五行
     */
    public static String getOvercomingMe(String element) {
        for (Map.Entry<String, String> entry : OVERCOMING.entrySet()) {
            if (entry.getValue().equals(element)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    /**
     * 计算五行局
     */
    public static String calculateFiveElementsPattern(String mingGongStem, String mingGongBranch) {
        // 1. 首先查表获取五行局
        String pattern = MING_GONG_PATTERNS.get(mingGongStem + mingGongBranch);
        if (pattern != null) {
            return pattern;
        }
        
        // 2. 如果表中没有，则根据天干地支五行计算
        String stemElement = getStemFiveElement(mingGongStem);
        String branchElement = getBranchFiveElement(mingGongBranch);
        
        // 根据天干五行确定基础局
        String basePattern = null;
        switch (stemElement) {
            case "水":
                basePattern = "水二局";
                break;
            case "木":
                basePattern = "木三局";
                break;
            case "金":
                basePattern = "金四局";
                break;
            case "土":
                basePattern = "土五局";
                break;
            case "火":
                basePattern = "火六局";
                break;
        }
        
        // 如果地支五行与天干五行相同或相生，维持基础局
        // 如果地支五行克天干五行，取地支五行的局
        if (isOvercoming(branchElement, stemElement)) {
            switch (branchElement) {
                case "水":
                    return "水二局";
                case "木":
                    return "木三局";
                case "金":
                    return "金四局";
                case "土":
                    return "土五局";
                case "火":
                    return "火六局";
            }
        }
        
        return basePattern;
    }
    
    /**
     * 获取五行局数
     */
    public static int getPatternNumber(String pattern) {
        return PATTERN_NUMBERS.getOrDefault(pattern, 0);
    }
    
    /**
     * 获取五行强弱
     */
    public static Map<String, Integer> calculateElementStrength(List<String> elements) {
        Map<String, Integer> strength = new HashMap<>();
        for (String element : FIVE_ELEMENTS) {
            strength.put(element, 0);
        }
        
        // 计算每个五行的数量
        for (String element : elements) {
            strength.put(element, strength.get(element) + 1);
        }
        
        return strength;
    }
    
    /**
     * 判断五行是否旺相
     */
    public static boolean isElementStrong(String element, String season) {
        // 春木旺，夏火旺，秋金旺，冬水旺，土四季皆旺
        switch (season) {
            case "春":
                return "木".equals(element) || "土".equals(element);
            case "夏":
                return "火".equals(element) || "土".equals(element);
            case "秋":
                return "金".equals(element) || "土".equals(element);
            case "冬":
                return "水".equals(element) || "土".equals(element);
            default:
                return false;
        }
    }
} 