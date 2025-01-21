package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.iztro.data.Palace;
import lombok.extern.slf4j.Slf4j;

/**
 * 紫微斗数宫位计算器
 */
@Slf4j
public class PalaceCalculator {
    
    // 十二宫名称
    private static final String[] PALACE_NAMES = {
        "命宫", "兄弟", "夫妻", "子女", 
        "财帛", "疾厄", "迁移", "交友",
        "官禄", "田宅", "福德", "父母"
    };
    
    // 地支对应的时辰
    private static final int[] BRANCH_HOURS = {23, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
    
    /**
     * 计算命宫位置
     * 
     * @param monthBranch 月支
     * @param timeIndex 时辰索引（0-11）
     * @return 命宫所在地支
     */
    public static EarthlyBranch calculateMingGongLocation(EarthlyBranch monthBranch, int timeIndex) {
        int monthIndex = monthBranch.ordinal();
        // 命宫 = 月支 + (12 - 时辰)
        int mingGongIndex = (monthIndex + (12 - timeIndex)) % 12;
        return EarthlyBranch.values()[mingGongIndex];
    }
    
    /**
     * 计算身宫位置
     * 
     * @param monthBranch 月支
     * @param timeIndex 时辰索引（0-11）
     * @return 身宫所在地支
     */
    public static EarthlyBranch calculateShenGongLocation(EarthlyBranch monthBranch, int timeIndex) {
        int monthIndex = monthBranch.ordinal();
        // 身宫 = 月支 + 时辰
        int shenGongIndex = (monthIndex + timeIndex) % 12;
        return EarthlyBranch.values()[shenGongIndex];
    }
    
    /**
     * 计算三方四正宫位
     * 
     * @param palace 宫位
     * @return 三方四正宫位数组 [对宫, 三合1, 三合2, 六合]
     */
    public static EarthlyBranch[] calculateSurroundedPalaces(Palace palace) {
        EarthlyBranch branch = palace.getEarthlyBranch();
        int branchIndex = branch.ordinal();
        
        // 计算对宫（相隔6个地支）
        int oppositeIndex = (branchIndex + 6) % 12;
        
        // 计算三合宫（相隔4个地支）
        int sanhe1Index = (branchIndex + 4) % 12;
        int sanhe2Index = (branchIndex + 8) % 12;
        
        // 计算六合宫
        int liuheIndex = branch.getSixHarmonyBranch().ordinal();
        
        return new EarthlyBranch[] {
            EarthlyBranch.values()[oppositeIndex],
            EarthlyBranch.values()[sanhe1Index],
            EarthlyBranch.values()[sanhe2Index],
            EarthlyBranch.values()[liuheIndex]
        };
    }
    
    /**
     * 计算十二宫位顺序
     * 
     * @param mingGongLocation 命宫位置
     * @return 十二宫位顺序（从命宫开始顺时针）
     */
    public static EarthlyBranch[] calculateTwelvePalaces(EarthlyBranch mingGongLocation) {
        EarthlyBranch[] palaces = new EarthlyBranch[12];
        int startIndex = mingGongLocation.ordinal();
        
        // 从命宫开始顺时针排列十二宫
        for (int i = 0; i < 12; i++) {
            int index = (startIndex + i) % 12;
            palaces[i] = EarthlyBranch.values()[index];
        }
        
        return palaces;
    }
    
    /**
     * 获取宫位名称
     * 
     * @param index 宫位索引（0-11，0代表命宫）
     * @return 宫位名称
     */
    public static String getPalaceName(int index) {
        switch (index) {
            case 0:
                return "命宫";
            case 1:
                return "兄弟";
            case 2:
                return "夫妻";
            case 3:
                return "子女";
            case 4:
                return "财帛";
            case 5:
                return "疾厄";
            case 6:
                return "迁移";
            case 7:
                return "交友";
            case 8:
                return "官禄";
            case 9:
                return "田宅";
            case 10:
                return "福德";
            case 11:
                return "父母";
            default:
                throw new IllegalArgumentException("Invalid palace index: " + index);
        }
    }
    
    /**
     * 获取宫位对应的天干地支
     * 
     * @param position 宫位位置（0-11）
     * @param yearGanZhi 年干支
     * @return 宫位天干地支
     */
    public static String getPalaceGanZhi(int position, String yearGanZhi) {
        try {
            if (position < 0 || position > 11) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "宫位位置无效");
            }
            if (yearGanZhi == null || yearGanZhi.length() != 2) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "年干支格式无效");
            }
            
            // 地支从寅宫开始
            String[] earthlyBranches = {"寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥", "子", "丑"};
            String branch = earthlyBranches[position];
            
            // 天干推算
            String[] heavenlyStems = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
            int yearStemIndex = "甲乙丙丁戊己庚辛壬癸".indexOf(yearGanZhi.charAt(0));
            int branchIndex = "子丑寅卯辰巳午未申酉戌亥".indexOf(branch);
            
            // 根据地支推算天干
            int stemIndex = (yearStemIndex + branchIndex) % 10;
            String stem = heavenlyStems[stemIndex];
            
            return stem + branch;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("宫位天干地支计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 验证输入参数
     */
    private static void validateInput(int month, int hour) {
        if (month < 1 || month > 12) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "月份必须在1-12之间");
        }
        if (hour < 1 || hour > 12) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "时辰必须在1-12之间");
        }
    }
} 