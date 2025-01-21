package com.chinese.culture.admin.util;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 紫微斗数工具类
 */
@Slf4j
public class IztroUtils {

    private IztroUtils() {
    }

    /**
     * 获取指定宫位
     */
    public static Palace getPalace(Astrolabe astrolabe, String palaceName) {
        if (astrolabe == null || astrolabe.getPalaces() == null) {
            return null;
        }
        
        return astrolabe.getPalaces().stream()
                .filter(p -> palaceName.equals(p.getName()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * 获取指定星耀
     */
    public static Star getStar(Astrolabe astrolabe, String starName) {
        if (astrolabe == null || astrolabe.getStars() == null) {
            return null;
        }
        
        return astrolabe.getStars().stream()
                .filter(s -> starName.equals(s.getName()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * 获取指定宫位的环绕宫位
     */
    public static List<Palace> getSurroundedPalaces(Astrolabe astrolabe, String palaceName) {
        Palace palace = getPalace(astrolabe, palaceName);
        if (palace == null) {
            return null;
        }
        
        return palace.surroundedPalaces();
    }
    
    /**
     * 检查宫位是否包含指定星耀
     */
    public static boolean hasStar(Palace palace, String starName) {
        if (palace == null || palace.getStars() == null) {
            return false;
        }
        
        return palace.hasStar(starName);
    }
    
    /**
     * 检查宫位是否包含指定四化
     */
    public static boolean hasMutagen(Palace palace, String mutagen) {
        if (palace == null || palace.getMutagens() == null) {
            return false;
        }
        
        return palace.hasMutagen(mutagen);
    }
    
    /**
     * 获取运限数据
     */
    public static Map<String, Object> getHoroscope(Astrolabe astrolabe) {
        if (astrolabe == null) {
            return null;
        }
        
        return astrolabe.getHoroscope();
    }
}
