package com.chinese.culture.admin.core.iztro.data;

import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 命盘
 */
@Data
public class Astrolabe {
    /**
     * 阳历生日
     */
    private String solarDate;
    
    /**
     * 农历生日
     */
    private String lunarDate;
    
    /**
     * 出生时辰（1-12）
     */
    private int birthHour;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 年龄
     */
    private int age;
    
    /**
     * 宫位列表
     */
    private List<Palace> palaces;
    
    /**
     * 星耀列表
     */
    private List<Star> stars;
    
    /**
     * 命主
     */
    private String soul;
    
    /**
     * 身主
     */
    private String body;
    
    /**
     * 五行局
     */
    private String fiveElements;
    
    /**
     * 运限数据
     */
    private Map<String, Object> horoscope;

    /**
     * 年干
     */
    private String yearStem;

    /**
     * 获取年干
     */
    public String getYearStem() {
        return yearStem;
    }

    /**
     * 根据宫位名称获取宫位
     */
    public Palace getPalace(String name) {
        return palaces.stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据星耀名称获取星耀
     */
    public Star getStar(String name) {
        return stars.stream()
                .filter(s -> name.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取运限数据
     */
    public Map<String, Object> getHoroscope() {
        return horoscope;
    }

    /**
     * 获取农历年份
     */
    public int getLunarYear() {
        return Integer.parseInt(lunarDate.substring(0, 4));
    }
    
    /**
     * 设置四化
     */
    public void setTransformations(Map<String, List<String>> transformations) {
        if (transformations != null && !transformations.isEmpty()) {
            for (Star star : stars) {
                if (transformations.containsKey(star.getName())) {
                    star.setMutagens(transformations.get(star.getName()));
                }
            }
        }
    }
} 