package com.chinese.culture.admin.core.iztro.analyzer;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Star;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 星耀组合分析器
 */
@Slf4j
public class StarCombinationAnalyzer {

    // 主星组合
    private static final Map<List<String>, String> MAJOR_COMBINATIONS = new HashMap<>();
    static {
        // 紫微系
        MAJOR_COMBINATIONS.put(Arrays.asList("紫微", "天机"), "智慧超群");
        MAJOR_COMBINATIONS.put(Arrays.asList("紫微", "太阳"), "贵气逼人");
        MAJOR_COMBINATIONS.put(Arrays.asList("紫微", "武曲"), "权威显赫");
        MAJOR_COMBINATIONS.put(Arrays.asList("紫微", "天同"), "温和贵重");
        
        // 天府系
        MAJOR_COMBINATIONS.put(Arrays.asList("天府", "太阴"), "富贵安逸");
        MAJOR_COMBINATIONS.put(Arrays.asList("天府", "贪狼"), "富贵双全");
        MAJOR_COMBINATIONS.put(Arrays.asList("天府", "巨门"), "谋略过人");
        
        // 其他主星组合
        MAJOR_COMBINATIONS.put(Arrays.asList("太阳", "天机"), "才智出众");
        MAJOR_COMBINATIONS.put(Arrays.asList("太阴", "天机"), "灵感敏锐");
        MAJOR_COMBINATIONS.put(Arrays.asList("贪狼", "破军"), "好勇斗狠");
    }

    // 吉星组合
    private static final Map<List<String>, String> LUCKY_COMBINATIONS = new HashMap<>();
    static {
        LUCKY_COMBINATIONS.put(Arrays.asList("文昌", "文曲"), "才华横溢");
        LUCKY_COMBINATIONS.put(Arrays.asList("左辅", "右弼"), "贵人相助");
        LUCKY_COMBINATIONS.put(Arrays.asList("天魁", "天钺"), "贵人提携");
    }

    /**
     * 分析三方四正
     * 
     * @param stars 星耀列表
     * @param position 宫位
     * @return 三方四正组合
     */
    public static Map<String, List<String>> analyzeTrineAndOpposition(List<Star> stars, int position) {
        try {
            Map<String, List<String>> result = new HashMap<>();
            List<String> combinations = new ArrayList<>();

            // 计算三方位置
            int trine1 = (position + 4) % 12;
            int trine2 = (position + 8) % 12;
            
            // 计算对宫位置
            int opposition = (position + 6) % 12;

            // 获取当前宫位星耀
            List<String> currentStars = getStarsAtPosition(stars, position);
            
            // 获取三方星耀
            List<String> trine1Stars = getStarsAtPosition(stars, trine1);
            List<String> trine2Stars = getStarsAtPosition(stars, trine2);
            
            // 获取对宫星耀
            List<String> oppositionStars = getStarsAtPosition(stars, opposition);

            // 分析主星组合
            for (Map.Entry<List<String>, String> entry : MAJOR_COMBINATIONS.entrySet()) {
                List<String> combinationStars = entry.getKey();
                String star1 = combinationStars.get(0);
                String star2 = combinationStars.get(1);
                
                // 检查同宫位组合
                if (currentStars.contains(star1) && currentStars.contains(star2)) {
                    combinations.add(entry.getValue());
                    continue;
                }
                
                // 检查当前宫位与三方四正的组合
                if ((currentStars.contains(star1) && (trine1Stars.contains(star2) || trine2Stars.contains(star2) || oppositionStars.contains(star2))) ||
                    (currentStars.contains(star2) && (trine1Stars.contains(star1) || trine2Stars.contains(star1) || oppositionStars.contains(star1)))) {
                    combinations.add(entry.getValue());
                }
            }

            result.put("三方四正", combinations);
            return result;

        } catch (Exception e) {
            log.error("三方四正分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 分析星耀会合
     * 
     * @param stars 星耀列表
     * @return 会合组合
     */
    public static Map<String, List<String>> analyzeStarConvergence(List<Star> stars) {
        try {
            Map<String, List<String>> result = new HashMap<>();
            Map<Integer, List<String>> positionStars = new HashMap<>();

            // 按位置分组星耀
            for (Star star : stars) {
                positionStars.computeIfAbsent(star.getPosition(), k -> new ArrayList<>())
                        .add(star.getName());
            }

            // 分析每个位置的星耀组合
            for (Map.Entry<Integer, List<String>> entry : positionStars.entrySet()) {
                List<String> starsInPosition = entry.getValue();
                List<String> combinations = new ArrayList<>();

                // 检查主星组合
                for (Map.Entry<List<String>, String> majorEntry : MAJOR_COMBINATIONS.entrySet()) {
                    if (containsAllStars(starsInPosition, majorEntry.getKey())) {
                        combinations.add(majorEntry.getValue());
                    }
                }

                // 检查吉星组合
                for (Map.Entry<List<String>, String> luckyEntry : LUCKY_COMBINATIONS.entrySet()) {
                    if (containsAllStars(starsInPosition, luckyEntry.getKey())) {
                        combinations.add(luckyEntry.getValue());
                    }
                }

                if (!combinations.isEmpty()) {
                    result.put("宫位" + entry.getKey(), combinations);
                }
            }

            return result;

        } catch (Exception e) {
            log.error("星耀会合分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    /**
     * 获取指定位置的星耀
     */
    private static List<String> getStarsAtPosition(List<Star> stars, int position) {
        List<String> result = new ArrayList<>();
        for (Star star : stars) {
            if (star.getPosition() == position) {
                result.add(star.getName());
            }
        }
        return result;
    }

    /**
     * 检查是否包含所有指定的星耀
     */
    private static boolean containsAllStars(List<String> stars, List<String> targetStars) {
        return stars.containsAll(targetStars);
    }
} 