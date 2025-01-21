package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.core.iztro.data.enums.Mutagen;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 星耀组合分析器
 */
public class StarCombinationAnalyzer {

    // 吉星组合规则
    private static final Map<String, String> LUCKY_COMBINATIONS = new HashMap<>();
    static {
        // 紫微系
        LUCKY_COMBINATIONS.put("紫微,天机", "紫微天机同宫,主聪明智慧,思维敏捷");
        LUCKY_COMBINATIONS.put("紫微,太阳", "紫微太阳同宫,主贵气,有领导才能");
        LUCKY_COMBINATIONS.put("紫微,武曲", "紫微武曲同宫,主财运亨通,善于理财");
        LUCKY_COMBINATIONS.put("紫微,天同", "紫微天同同宫,主人缘好,贵人相助");
        LUCKY_COMBINATIONS.put("紫微,廉贞", "紫微廉贞同宫,主正直清廉,有操守");
        LUCKY_COMBINATIONS.put("紫微,天府", "紫微天府同宫,主大富大贵,财禄双全");
        LUCKY_COMBINATIONS.put("紫微,太阴", "紫微太阴同宫,主智慧聪颖,心思缜密");
        
        // 天府系
        LUCKY_COMBINATIONS.put("天府,太阴", "天府太阴同宫,主富贵,经商有利");
        LUCKY_COMBINATIONS.put("天府,贪狼", "天府贪狼同宫,主事业有成,财运旺盛");
        LUCKY_COMBINATIONS.put("天府,巨门", "天府巨门同宫,主口才好,善于言谈");
        LUCKY_COMBINATIONS.put("天府,天相", "天府天相同宫,主人缘好,贵人扶持");
        LUCKY_COMBINATIONS.put("天府,天梁", "天府天梁同宫,主正直廉洁,位居要职");
        LUCKY_COMBINATIONS.put("天府,七杀", "天府七杀同宫,主权威显赫,能力出众");
        
        // 其他吉星组合
        LUCKY_COMBINATIONS.put("文昌,文曲", "文昌文曲同宫,主学业优秀,才华出众");
        LUCKY_COMBINATIONS.put("左辅,右弼", "左辅右弼同宫,主得贵人扶持,办事顺利");
        LUCKY_COMBINATIONS.put("天魁,天钺", "天魁天钺同宫,主贵人运旺,逢凶化吉");
        LUCKY_COMBINATIONS.put("禄存,科权", "禄存科权同宫,主官运亨通,事业有成");
        LUCKY_COMBINATIONS.put("三台,八座", "三台八座同宫,主名声显赫,地位尊崇");
        LUCKY_COMBINATIONS.put("恩光,天贵", "恩光天贵同宫,主受人恩惠,名利双收");
        LUCKY_COMBINATIONS.put("龙池,凤阁", "龙池凤阁同宫,主文采飞扬,才华横溢");
    }
    
    // 凶星组合规则
    private static final Map<String, String> UNLUCKY_COMBINATIONS = new HashMap<>();
    static {
        UNLUCKY_COMBINATIONS.put("火星,铃星", "火铃同宫,主冲动易怒,易生事非");
        UNLUCKY_COMBINATIONS.put("擎羊,陀罗", "羊陀同宫,主性格偏执,易生困扰");
        UNLUCKY_COMBINATIONS.put("火星,擎羊", "火羊同宫,主暴躁易怒,多灾多难");
        UNLUCKY_COMBINATIONS.put("铃星,陀罗", "铃陀同宫,主是非口舌,易生纠纷");
        UNLUCKY_COMBINATIONS.put("火星,陀罗", "火陀同宫,主固执倔强,易生事端");
        UNLUCKY_COMBINATIONS.put("铃星,擎羊", "铃羊同宫,主冲动任性,易惹祸端");
        UNLUCKY_COMBINATIONS.put("火星,地空", "火空同宫,主虚耗破财,诸事不顺");
        UNLUCKY_COMBINATIONS.put("铃星,地劫", "铃劫同宫,主是非缠身,易生口舌");
        UNLUCKY_COMBINATIONS.put("地空,地劫", "空劫同宫,主虚耗破财,诸事不顺");
    }
    
    // 三合组合规则
    private static final Map<String, String> TRIPLE_COMBINATIONS = new HashMap<>();
    static {
        TRIPLE_COMBINATIONS.put("文昌,文曲,天机", "三才同会,主才华横溢,学业优秀");
        TRIPLE_COMBINATIONS.put("紫微,武曲,太阳", "三贵同会,主权贵显达,位居高位");
        TRIPLE_COMBINATIONS.put("天府,太阴,贪狼", "三富同会,主富贵双全,财运亨通");
        TRIPLE_COMBINATIONS.put("左辅,右弼,天魁", "三吉同会,主贵人相助,诸事顺遂");
        TRIPLE_COMBINATIONS.put("禄存,科权,天魁", "三禄同会,主官运亨通,仕途顺遂");
        TRIPLE_COMBINATIONS.put("天机,天梁,天同", "三智同会,主聪明智慧,思维敏捷");
        TRIPLE_COMBINATIONS.put("太阳,武曲,天相", "三权同会,主权威显赫,能力出众");
        TRIPLE_COMBINATIONS.put("太阴,天同,文曲", "三艺同会,主艺术才能,审美出众");
    }
    
    // 六合组合规则
    private static final Map<String, String> HEXAGON_COMBINATIONS = new HashMap<>();
    static {
        HEXAGON_COMBINATIONS.put("文昌,天机", "才智相合,主学业文章两得");
        HEXAGON_COMBINATIONS.put("武曲,太阳", "权禄相合,主官运财运双旺");
        HEXAGON_COMBINATIONS.put("天府,贪狼", "财富相合,主经商获利可期");
        HEXAGON_COMBINATIONS.put("左辅,右弼", "贵人相合,主逢凶化吉有助");
        HEXAGON_COMBINATIONS.put("紫微,天府", "主星相合,主大富大贵可期");
        HEXAGON_COMBINATIONS.put("太阳,太阴", "日月相合,主名利双收可期");
        HEXAGON_COMBINATIONS.put("天机,巨门", "谋略相合,主计谋智慧出众");
        HEXAGON_COMBINATIONS.put("天同,天梁", "仁德相合,主品性贵重高洁");
    }
    
    /**
     * 分析星耀组合
     * 
     * @param palaces 所有宫位列表
     * @return 星耀组合分析结果
     */
    public static Map<String, Object> analyzeStarCombinations(List<Palace> palaces) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> combinations = new ArrayList<>();
        
        // 1. 分析同宫组合
        for (Palace palace : palaces) {
            analyzeSamePalaceCombinations(palace, combinations);
        }
        
        // 2. 分析对宫组合
        for (int i = 0; i < palaces.size(); i++) {
            Palace palace = palaces.get(i);
            Palace oppositePalace = palaces.get((i + 6) % 12);
            analyzeOppositePalaceCombinations(palace, oppositePalace, combinations);
        }
        
        // 3. 分析三合组合
        for (Palace palace : palaces) {
            analyzeTripleCombinations(palace, palaces, combinations);
        }
        
        // 4. 分析六合组合
        for (int i = 0; i < palaces.size(); i++) {
            Palace palace = palaces.get(i);
            Palace hexagonPalace = palaces.get((i + 2) % 12);
            analyzeHexagonCombinations(palace, hexagonPalace, combinations);
        }
        
        // 5. 生成综合分析
        String analysis = generateOverallAnalysis(combinations);
        result.put("combinations", combinations);
        result.put("analysis", analysis);
        
        return result;
    }
    
    /**
     * 分析同宫组合
     */
    private static void analyzeSamePalaceCombinations(Palace palace,
                                                    List<Map<String, Object>> combinations) {
        List<Star> stars = palace.getAllStars();
        
        // 检查吉星组合
        for (Map.Entry<String, String> entry : LUCKY_COMBINATIONS.entrySet()) {
            String[] starNames = entry.getKey().split(",");
            if (hasStars(stars, starNames)) {
                Map<String, Object> combination = new HashMap<>();
                combination.put("type", "同宫");
                combination.put("palace", palace.getName());
                combination.put("stars", Arrays.asList(starNames));
                combination.put("description", entry.getValue());
                combination.put("isLucky", true);
                combination.put("strength", calculateCombinationStrength(stars, starNames));
                combinations.add(combination);
            }
        }
        
        // 检查凶星组合
        for (Map.Entry<String, String> entry : UNLUCKY_COMBINATIONS.entrySet()) {
            String[] starNames = entry.getKey().split(",");
            if (hasStars(stars, starNames)) {
                Map<String, Object> combination = new HashMap<>();
                combination.put("type", "同宫");
                combination.put("palace", palace.getName());
                combination.put("stars", Arrays.asList(starNames));
                combination.put("description", entry.getValue());
                combination.put("isLucky", false);
                combination.put("strength", calculateCombinationStrength(stars, starNames));
                combinations.add(combination);
            }
        }
    }
    
    /**
     * 分析对宫组合
     */
    private static void analyzeOppositePalaceCombinations(Palace palace,
                                                        Palace oppositePalace,
                                                        List<Map<String, Object>> combinations) {
        List<Star> stars1 = palace.getAllStars();
        List<Star> stars2 = oppositePalace.getAllStars();
        
        // 分析对宫星耀关系
        for (Star star1 : stars1) {
            for (Star star2 : stars2) {
                String combination = analyzeTwoStars(star1, star2);
                if (combination != null) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("type", "对宫");
                    result.put("palaces", Arrays.asList(palace.getName(), oppositePalace.getName()));
                    result.put("stars", Arrays.asList(star1.getName(), star2.getName()));
                    result.put("description", combination);
                    result.put("isLucky", isLuckyCombination(star1, star2));
                    result.put("strength", calculateTwoStarsStrength(star1, star2));
                    combinations.add(result);
                }
            }
        }
    }
    
    /**
     * 分析三合组合
     */
    private static void analyzeTripleCombinations(Palace palace,
                                                List<Palace> allPalaces,
                                                List<Map<String, Object>> combinations) {
        // 获取三合宫位
        List<Palace> triplePalaces = findTriplePalaces(palace, allPalaces);
        if (triplePalaces.size() == 3) {
            // 分析三合宫位中的星耀组合
            List<Star> allStars = new ArrayList<>();
            Map<Palace, List<Star>> palaceStarsMap = new HashMap<>();
            
            // 收集所有星耀并建立宫位-星耀映射
            for (Palace p : triplePalaces) {
                List<Star> stars = p.getAllStars();
                allStars.addAll(stars);
                palaceStarsMap.put(p, stars);
            }
            
            // 检查预定义的三合组合
            for (Map.Entry<String, String> entry : TRIPLE_COMBINATIONS.entrySet()) {
                String[] starNames = entry.getKey().split(",");
                if (hasStarsInTriplePalaces(palaceStarsMap, starNames)) {
                    Map<String, Object> combination = new HashMap<>();
                    combination.put("type", "三合");
                    combination.put("palaces", triplePalaces.stream()
                        .map(Palace::getName)
                        .collect(Collectors.toList()));
                    combination.put("stars", Arrays.asList(starNames));
                    combination.put("description", entry.getValue());
                    combination.put("isLucky", true);
                    combination.put("strength", calculateTripleCombinationStrength(allStars, starNames, palaceStarsMap));
                    combinations.add(combination);
                }
            }
            
            // 检查其他可能的三合组合
            analyzeCustomTripleCombinations(triplePalaces, palaceStarsMap, combinations);
        }
    }
    
    /**
     * 检查星耀是否分布在三合宫位中
     */
    private static boolean hasStarsInTriplePalaces(Map<Palace, List<Star>> palaceStarsMap,
                                                 String[] starNames) {
        if (starNames.length != 3) {
            return false;
        }
        
        // 检查每个星耀是否在任一宫位中
        for (String starName : starNames) {
            boolean found = false;
            for (List<Star> stars : palaceStarsMap.values()) {
                if (stars.stream().anyMatch(s -> s.getName().equals(starName))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        
        // 确保星耀分布在不同宫位
        return isStarsDistributed(palaceStarsMap, starNames);
    }
    
    /**
     * 检查星耀是否分布在不同宫位
     */
    private static boolean isStarsDistributed(Map<Palace, List<Star>> palaceStarsMap,
                                            String[] starNames) {
        Map<Palace, List<String>> distribution = new HashMap<>();
        
        // 统计每个宫位包含的目标星耀
        for (Map.Entry<Palace, List<Star>> entry : palaceStarsMap.entrySet()) {
            List<String> foundStars = new ArrayList<>();
            for (Star star : entry.getValue()) {
                for (String name : starNames) {
                    if (star.getName().equals(name)) {
                        foundStars.add(name);
                    }
                }
            }
            if (!foundStars.isEmpty()) {
                distribution.put(entry.getKey(), foundStars);
            }
        }
        
        // 检查是否有星耀重复出现在同一宫位
        Set<String> usedStars = new HashSet<>();
        for (List<String> stars : distribution.values()) {
            for (String star : stars) {
                if (!usedStars.add(star)) {
                    return false;
                }
            }
        }
        
        return usedStars.size() == starNames.length;
    }
    
    /**
     * 分析自定义三合组合
     */
    private static void analyzeCustomTripleCombinations(List<Palace> triplePalaces,
                                                      Map<Palace, List<Star>> palaceStarsMap,
                                                      List<Map<String, Object>> combinations) {
        // 获取所有主星
        List<Star> majorStars = new ArrayList<>();
        for (List<Star> stars : palaceStarsMap.values()) {
            stars.stream()
                .filter(s -> s.getType() == StarType.MAJOR)
                .forEach(majorStars::add);
        }
        
        // 如果三合宫位中有三颗以上主星，检查是否形成特殊组合
        if (majorStars.size() >= 3) {
            // 检查主星组合的吉凶
            boolean isLucky = checkMajorStarsCombination(majorStars);
            if (isLucky) {
                Map<String, Object> combination = new HashMap<>();
                combination.put("type", "三合");
                combination.put("palaces", triplePalaces.stream()
                    .map(Palace::getName)
                    .collect(Collectors.toList()));
                combination.put("stars", majorStars.stream()
                    .map(Star::getName)
                    .collect(Collectors.toList()));
                combination.put("description", "三方主星会照,主运势兴旺");
                combination.put("isLucky", true);
                combination.put("strength", calculateMajorStarsStrength(majorStars));
                combinations.add(combination);
            }
        }
    }
    
    /**
     * 检查主星组合的吉凶
     */
    private static boolean checkMajorStarsCombination(List<Star> majorStars) {
        // 统计吉星和凶星的数量
        long luckyCount = majorStars.stream()
            .filter(star -> {
                String name = star.getName();
                return name.contains("紫微") || name.contains("天府") ||
                       name.contains("太阳") || name.contains("太阴") ||
                       name.contains("天机") || name.contains("天同") ||
                       name.contains("武曲") || name.contains("天梁");
            })
            .count();
            
        return luckyCount >= 2; // 如果吉星数量大于等于2，则认为是吉利组合
    }
    
    /**
     * 计算主星组合强度
     */
    private static int calculateMajorStarsStrength(List<Star> majorStars) {
        int strength = 70; // 主星组合基础分较高
        
        // 根据主星数量增加强度
        strength += (majorStars.size() - 3) * 10;
        
        // 根据主星亮度和四化调整强度
        for (Star star : majorStars) {
            switch (star.getBrightness()) {
                case TEMPLE:
                    strength += 15;
                    break;
                case NORMAL:
                    strength += 10;
                    break;
                case WEAK:
                    strength += 5;
                    break;
                case TRAPPED:
                    break;
            }
            
            // 考虑四化的影响
            for (Mutagen mutagen : star.getMutagens()) {
                switch (mutagen) {
                    case LUCKY:
                    case POWER:
                    case SKILL:
                        strength += 8;
                        break;
                    case WEAK:
                        strength -= 8;
                        break;
                }
            }
        }
        
        return Math.min(100, Math.max(0, strength));
    }
    
    /**
     * 计算三合组合强度
     */
    private static int calculateTripleCombinationStrength(List<Star> allStars,
                                                        String[] starNames,
                                                        Map<Palace, List<Star>> palaceStarsMap) {
        int strength = calculateCombinationStrength(allStars, starNames);
        
        // 考虑星耀分布的均匀性
        if (isStarsEvenlyDistributed(palaceStarsMap, starNames)) {
            strength += 10;
        }
        
        // 考虑宫位相生关系
        if (arePalacesCompatible(palaceStarsMap.keySet())) {
            strength += 10;
        }
        
        return Math.min(100, strength);
    }
    
    /**
     * 检查星耀分布是否均匀
     */
    private static boolean isStarsEvenlyDistributed(Map<Palace, List<Star>> palaceStarsMap,
                                                  String[] starNames) {
        Map<Palace, Integer> distribution = new HashMap<>();
        
        // 统计每个宫位的星耀数量
        for (Map.Entry<Palace, List<Star>> entry : palaceStarsMap.entrySet()) {
            int count = 0;
            for (Star star : entry.getValue()) {
                for (String name : starNames) {
                    if (star.getName().equals(name)) {
                        count++;
                    }
                }
            }
            if (count > 0) {
                distribution.put(entry.getKey(), count);
            }
        }
        
        // 检查分布是否均匀(每个宫位的星耀数量相近)
        return distribution.values().stream()
            .max(Integer::compareTo)
            .orElse(0) -
            distribution.values().stream()
            .min(Integer::compareTo)
            .orElse(0) <= 1;
    }
    
    /**
     * 检查宫位是否相生
     */
    private static boolean arePalacesCompatible(Set<Palace> palaces) {
        // TODO: 实现宫位相生关系的判断
        return true;
    }
    
    /**
     * 分析六合组合
     */
    private static void analyzeHexagonCombinations(Palace palace,
                                                 Palace hexagonPalace,
                                                 List<Map<String, Object>> combinations) {
        List<Star> stars1 = palace.getAllStars();
        List<Star> stars2 = hexagonPalace.getAllStars();
        
        // 检查六合组合
        for (Map.Entry<String, String> entry : HEXAGON_COMBINATIONS.entrySet()) {
            String[] starNames = entry.getKey().split(",");
            if (hasStarsInTwoPalaces(stars1, stars2, starNames)) {
                Map<String, Object> combination = new HashMap<>();
                combination.put("type", "六合");
                combination.put("palaces", Arrays.asList(palace.getName(), hexagonPalace.getName()));
                combination.put("stars", Arrays.asList(starNames));
                combination.put("description", entry.getValue());
                combination.put("isLucky", true);
                List<Star> allStars = new ArrayList<>(stars1);
                allStars.addAll(stars2);
                combination.put("strength", calculateCombinationStrength(allStars, starNames));
                combinations.add(combination);
            }
        }
    }
    
    /**
     * 检查两个宫位是否包含指定星耀
     */
    private static boolean hasStarsInTwoPalaces(List<Star> stars1,
                                              List<Star> stars2,
                                              String[] starNames) {
        if (starNames.length != 2) {
            return false;
        }
        
        boolean firstStarInPalace1 = false;
        boolean secondStarInPalace2 = false;
        boolean firstStarInPalace2 = false;
        boolean secondStarInPalace1 = false;
        
        for (Star star : stars1) {
            if (star.getName().equals(starNames[0])) {
                firstStarInPalace1 = true;
            }
            if (star.getName().equals(starNames[1])) {
                secondStarInPalace1 = true;
            }
        }
        
        for (Star star : stars2) {
            if (star.getName().equals(starNames[0])) {
                firstStarInPalace2 = true;
            }
            if (star.getName().equals(starNames[1])) {
                secondStarInPalace2 = true;
            }
        }
        
        return (firstStarInPalace1 && secondStarInPalace2) ||
               (firstStarInPalace2 && secondStarInPalace1);
    }
    
    /**
     * 计算组合强度
     */
    private static int calculateCombinationStrength(List<Star> stars, String[] starNames) {
        int strength = 0;
        int baseStrength = 60; // 基础强度
        
        // 1. 计算星耀本身强度
        for (String name : starNames) {
            for (Star star : stars) {
                if (star.getName().equals(name)) {
                    // 根据星耀类型增加强度
                    switch (star.getType()) {
                        case MAJOR:
                            strength += 20; // 主星权重更高
                            break;
                        case MINOR:
                            strength += 15; // 辅星次之
                            break;
                        case ADJECTIVE:
                            strength += 10; // 杂耀再次
                            break;
                    }
                    
                    // 根据星耀亮度调整强度
                    switch (star.getBrightness()) {
                        case TEMPLE:
                            strength += 20;
                            break;
                        case NORMAL:
                            strength += 15;
                            break;
                        case WEAK:
                            strength += 10;
                            break;
                        case TRAPPED:
                            strength += 5;
                            break;
                    }
                    
                    // 根据四化调整强度
                    for (Mutagen mutagen : star.getMutagens()) {
                        switch (mutagen) {
                            case LUCKY:
                                strength += 15; // 禄存化禄最重要
                                break;
                            case POWER:
                                strength += 12; // 权科次之
                                break;
                            case SKILL:
                                strength += 12;
                                break;
                            case WEAK:
                                strength -= 15; // 化忌减分
                                break;
                        }
                    }
                }
            }
        }
        
        // 2. 根据组合类型调整强度
        if (starNames.length == 2) {
            baseStrength = 50; // 两星组合基础分低一些
        } else if (starNames.length == 3) {
            baseStrength = 70; // 三合组合基础分高一些
            strength *= 1.2; // 三合组合额外加成
        }
        
        // 3. 根据宫位属性调整强度(如果有宫位信息)
        // TODO: 后续可以添加宫位属性对强度的影响
        
        // 4. 计算最终强度
        int finalStrength = baseStrength + strength;
        
        // 5. 确保强度在0-100之间
        return Math.min(100, Math.max(0, finalStrength));
    }
    
    /**
     * 计算两颗星耀的组合强度
     */
    private static int calculateTwoStarsStrength(Star star1, Star star2) {
        int strength = calculateCombinationStrength(
            Arrays.asList(star1, star2),
            new String[]{star1.getName(), star2.getName()}
        );
        
        // 对宫组合额外考虑星耀相性
        if (isStarsCompatible(star1, star2)) {
            strength += 10;
        }
        
        return Math.min(100, strength);
    }
    
    /**
     * 判断两颗星耀是否相性相合
     */
    private static boolean isStarsCompatible(Star star1, Star star2) {
        // 根据星耀五行判断相性
        // TODO: 后续可以添加更详细的相性判断逻辑
        return true;
    }
    
    /**
     * 判断是否为吉利组合
     */
    private static boolean isLuckyCombination(Star star1, Star star2) {
        String key = star1.getName() + "," + star2.getName();
        String reverseKey = star2.getName() + "," + star1.getName();
        return LUCKY_COMBINATIONS.containsKey(key) ||
               LUCKY_COMBINATIONS.containsKey(reverseKey);
    }
    
    /**
     * 生成综合分析
     */
    private static String generateOverallAnalysis(List<Map<String, Object>> combinations) {
        if (combinations.isEmpty()) {
            return "未发现明显的星耀组合";
        }
        
        StringBuilder analysis = new StringBuilder();
        int luckyCount = 0;
        int unluckyCount = 0;
        int totalStrength = 0;
        int maxStrength = 0;
        String strongestCombination = "";
        
        // 按类型分类组合
        Map<String, List<Map<String, Object>>> typeGroups = new HashMap<>();
        for (Map<String, Object> combination : combinations) {
            String type = (String) combination.get("type");
            typeGroups.computeIfAbsent(type, k -> new ArrayList<>()).add(combination);
            
            int strength = (int) combination.get("strength");
            if ((boolean) combination.get("isLucky")) {
                luckyCount++;
                totalStrength += strength;
                if (strength > maxStrength) {
                    maxStrength = strength;
                    strongestCombination = (String) combination.get("description");
                }
            } else {
                unluckyCount++;
                totalStrength -= strength;
            }
        }
        
        // 1. 总体概述
        analysis.append("共发现").append(combinations.size()).append("组星耀组合，");
        analysis.append("其中吉利组合").append(luckyCount).append("组，");
        analysis.append("凶险组合").append(unluckyCount).append("组。\n");
        
        // 2. 最强组合
        if (!strongestCombination.isEmpty()) {
            analysis.append("最强组合为：").append(strongestCombination).append("。\n");
        }
        
        // 3. 按类型分析
        if (typeGroups.containsKey("同宫")) {
            analysis.append("同宫组合：\n");
            analyzeTypeGroup(typeGroups.get("同宫"), analysis);
        }
        
        if (typeGroups.containsKey("三合")) {
            analysis.append("三合组合：\n");
            analyzeTypeGroup(typeGroups.get("三合"), analysis);
        }
        
        if (typeGroups.containsKey("六合")) {
            analysis.append("六合组合：\n");
            analyzeTypeGroup(typeGroups.get("六合"), analysis);
        }
        
        // 4. 总体评价
        analysis.append("\n总体评价：");
        if (totalStrength > 50) {
            if (totalStrength > 80) {
                analysis.append("星耀组合极为有利，大吉大利之象。诸事皆宜，可大展宏图。");
            } else {
                analysis.append("吉利组合占优，运势向好。宜积极进取，把握机会。");
            }
        } else if (totalStrength < -50) {
            if (totalStrength < -80) {
                analysis.append("凶险组合较多，需特别谨慎。诸事宜守，暂避锋芒。");
            } else {
                analysis.append("凶险组合较多，需谨慎行事。宜稳健发展，避免冒进。");
            }
        } else {
            analysis.append("吉凶相抵，运势平稳。宜稳中求进，循序渐进。");
        }
        
        // 5. 运势建议
        analysis.append("\n\n运势建议：\n");
        generateAdvice(combinations, analysis);
        
        return analysis.toString();
    }
    
    /**
     * 分析特定类型的组合
     */
    private static void analyzeTypeGroup(List<Map<String, Object>> group, StringBuilder analysis) {
        // 按强度排序
        group.sort((a, b) -> Integer.compare((int)b.get("strength"), (int)a.get("strength")));
        
        for (Map<String, Object> combination : group) {
            analysis.append("- ").append(combination.get("description"));
            int strength = (int) combination.get("strength");
            if (strength >= 80) {
                analysis.append("(极强)");
            } else if (strength >= 60) {
                analysis.append("(较强)");
            } else if (strength >= 40) {
                analysis.append("(中等)");
            } else {
                analysis.append("(较弱)");
            }
            analysis.append("\n");
        }
        analysis.append("\n");
    }
    
    /**
     * 生成运势建议
     */
    private static void generateAdvice(List<Map<String, Object>> combinations, StringBuilder analysis) {
        // 统计各领域的组合
        boolean hasCareer = false;
        boolean hasWealth = false;
        boolean hasLove = false;
        boolean hasStudy = false;
        boolean hasHealth = false;
        
        for (Map<String, Object> combination : combinations) {
            String desc = (String) combination.get("description");
            if (desc.contains("事业") || desc.contains("官运")) {
                hasCareer = true;
            }
            if (desc.contains("财运") || desc.contains("富贵")) {
                hasWealth = true;
            }
            if (desc.contains("姻缘") || desc.contains("桃花")) {
                hasLove = true;
            }
            if (desc.contains("学业") || desc.contains("文昌")) {
                hasStudy = true;
            }
            if (desc.contains("健康") || desc.contains("疾厄")) {
                hasHealth = true;
            }
        }
        
        // 生成各领域建议
        if (hasCareer) {
            analysis.append("事业方面：宜积极进取，把握机会，开创事业。\n");
        }
        if (hasWealth) {
            analysis.append("财运方面：宜稳健投资，开源节流，积累财富。\n");
        }
        if (hasLove) {
            analysis.append("感情方面：宜主动表达，把握机缘，珍惜缘分。\n");
        }
        if (hasStudy) {
            analysis.append("学业方面：宜勤奋用功，博览群书，提升才华。\n");
        }
        if (hasHealth) {
            analysis.append("健康方面：宜注意保养，规律作息，保持健康。\n");
        }
    }
} 
