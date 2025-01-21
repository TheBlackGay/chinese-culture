package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.utils.CalendarConverter;
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
     * 计算命宫所在位置
     * 
     * @param lunarMonth 农历月
     * @param lunarHour 时辰（1-12）
     * @return 命宫位置（0-11）
     */
    public static int calculateMingGong(int lunarMonth, int lunarHour) {
        try {
            validateInput(lunarMonth, lunarHour);
            
            // 命宫计算公式：12 - 月数 + 时辰 - 1
            int position = (12 - lunarMonth + lunarHour - 1) % 12;
            return position >= 0 ? position : position + 12;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("命宫计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 计算身宫所在位置
     * 
     * @param lunarMonth 农历月
     * @param lunarHour 时辰（1-12）
     * @return 身宫位置（0-11）
     */
    public static int calculateShenGong(int lunarMonth, int lunarHour) {
        try {
            validateInput(lunarMonth, lunarHour);
            
            // 身宫计算公式：月数 + 时辰 - 1
            int position = (lunarMonth + lunarHour - 1) % 12;
            return position >= 0 ? position : position + 12;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("身宫计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    /**
     * 根据命宫位置计算其他宫位
     * 
     * @param mingGongPosition 命宫位置（0-11）
     * @return 十二宫位置数组
     */
    public static String[] calculateTwelvePalaces(int mingGongPosition) {
        try {
            if (mingGongPosition < 0 || mingGongPosition > 11) {
                throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "命宫位置无效");
            }
            
            String[] palaces = new String[12];
            for (int i = 0; i < 12; i++) {
                int position = (mingGongPosition + i) % 12;
                palaces[position] = PALACE_NAMES[i];
            }
            return palaces;
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("十二宫位计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
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