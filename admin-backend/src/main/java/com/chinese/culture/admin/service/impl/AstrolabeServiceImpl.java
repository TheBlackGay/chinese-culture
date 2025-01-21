package com.chinese.culture.admin.service.impl;

import com.chinese.culture.admin.common.exception.BusinessException;
import com.chinese.culture.admin.common.result.ResultCode;
import com.chinese.culture.admin.core.iztro.calculator.CoreCalculator;
import com.chinese.culture.admin.core.iztro.calculator.HoroscopeCalculator;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.AstrolabeInterpretation;
import com.chinese.culture.admin.core.iztro.interpreter.AstrolabeInterpreter;
import com.chinese.culture.admin.core.iztro.utils.CalendarConverter;
import com.chinese.culture.admin.dto.*;
import com.chinese.culture.admin.service.AstrolabeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 命盘服务实现类
 */
@Slf4j
@Service
public class AstrolabeServiceImpl implements AstrolabeService {
    
    @Override
    public Astrolabe getAstrolabe(AstrolabeQueryDTO queryDTO) {
        try {
            int birthYear = queryDTO.getBirthYear();
            int birthMonth = queryDTO.getBirthMonth();
            int birthDay = queryDTO.getBirthDay();
            int birthHour = queryDTO.getBirthHour();
            boolean isLunar = queryDTO.getIsLunar();
            
            // 获取农历日期
            int lunarYear, lunarMonth, lunarDay;
            if (isLunar) {
                lunarYear = birthYear;
                lunarMonth = birthMonth;
                lunarDay = birthDay;
            } else {
                // 将阳历转换为农历
                int[] lunarDate = CalendarConverter.solarToLunar(
                    birthYear,
                    birthMonth,
                    birthDay
                );
                lunarYear = lunarDate[0];
                lunarMonth = lunarDate[1];
                lunarDay = lunarDate[2];
            }
            
            // 计算命盘
            return CoreCalculator.calculate(
                lunarYear,
                lunarMonth,
                lunarDay,
                birthHour,
                queryDTO.getGender()
            );
            
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("命盘计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    @Override
    public AstrolabeInterpretation interpretAstrolabe(Astrolabe astrolabe) {
        try {
            return AstrolabeInterpreter.interpretAstrolabe(astrolabe);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("命盘解析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }

    @Override
    public Map<String, Object> getHoroscope(HoroscopeQueryDTO queryDTO) {
        try {
            return HoroscopeCalculator.calculate(queryDTO.getAstrolabe());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("运限计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    @Override
    public Map<Integer, Map<String, Object>> getBatchHoroscope(BatchHoroscopeQueryDTO queryDTO) {
        try {
            // TODO: 实现批量运限计算逻辑
            return null;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("批量运限计算失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    @Override
    public Map<String, List<String>> analyzeStarCombinations(StarCombinationQueryDTO queryDTO) {
        try {
            // TODO: 实现星耀组合分析逻辑
            return null;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("星耀组合分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
    
    @Override
    public Map<String, List<String>> analyzePalaceRelations(PalaceRelationQueryDTO queryDTO) {
        try {
            // TODO: 实现宫位关系分析逻辑
            return null;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("宫位关系分析失败：{}", e.getMessage(), e);
            throw new BusinessException(ResultCode.HOROSCOPE_CALC_ERROR);
        }
    }
} 