package com.chinese.culture.admin.util;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

/**
 * 紫微斗数工具类
 */
@Slf4j
public class IztroUtils {

    private IztroUtils() {
    }

    /**
     * 获取宫位列表
     */
    public static List<Palace> getPalaces(Astrolabe astrolabe) {
        if (astrolabe == null) {
            throw new IllegalArgumentException("命盘数据不能为空");
        }
        
        return new ArrayList<>(astrolabe.getPalaces());
    }
    
    /**
     * 获取星耀列表
     */
    public static List<Star> getStars(Astrolabe astrolabe) {
        if (astrolabe == null) {
            throw new IllegalArgumentException("命盘数据不能为空");
        }
        
        return new ArrayList<>(astrolabe.getStars());
    }
    
    /**
     * 获取宫位中的星耀
     */
    public static List<Star> getStarsInPalace(Palace palace, StarType type) {
        if (palace == null) {
            return Collections.emptyList();
        }
        
        return palace.getStarsByType(type);
    }
    
    /**
     * 获取宫位中的所有星耀
     */
    public static List<Star> getAllStarsInPalace(Palace palace) {
        if (palace == null) {
            return Collections.emptyList();
        }
        
        return palace.getAllStars();
    }
    
    /**
     * 获取命盘数据
     */
    public static Map<String, Object> getHoroscope(Astrolabe astrolabe) {
        if (astrolabe == null) {
            throw new IllegalArgumentException("命盘数据不能为空");
        }
        
        Map<String, Object> horoscope = new HashMap<>();
        horoscope.put("solarDate", astrolabe.getSolarDate());
        horoscope.put("lunarDate", astrolabe.getLunarDate());
        horoscope.put("gender", astrolabe.getGender());
        horoscope.put("birthHour", astrolabe.getBirthHour());
        
        return horoscope;
    }
    
    /**
     * 获取宫位名称
     */
    public static String getPalaceName(Palace palace) {
        return palace != null ? palace.getName() : "";
    }
    
    /**
     * 获取星耀名称
     */
    public static String getStarName(Star star) {
        return star != null ? star.getName() : "";
    }
    
    /**
     * 获取四化列表
     */
    public static List<String> getMutagens(Palace palace) {
        return palace != null ? new ArrayList<>(palace.getMutagens()) : Collections.emptyList();
    }
    
    /**
     * 检查宫位是否包含指定星耀
     */
    public static boolean hasStar(Palace palace, String starName) {
        return palace != null && palace.hasStar(starName);
    }
    
    /**
     * 检查宫位是否包含指定四化
     */
    public static boolean hasMutagen(Palace palace, String mutagen) {
        return palace != null && palace.hasMutagen(mutagen);
    }
}
