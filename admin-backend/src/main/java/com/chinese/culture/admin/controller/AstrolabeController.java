package com.chinese.culture.admin.controller;

import com.chinese.culture.admin.common.result.Result;
import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import com.chinese.culture.admin.core.iztro.data.AstrolabeInterpretation;
import com.chinese.culture.admin.dto.*;
import com.chinese.culture.admin.service.AstrolabeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 命盘查询接口
 */
@Api(tags = "命盘查询接口")
@RestController
@RequestMapping("/api/v1/astrolabe")
@RequiredArgsConstructor
public class AstrolabeController {
    
    private final AstrolabeService astrolabeService;
    
    @ApiOperation("获取命盘")
    @PostMapping("/get")
    public Result<Astrolabe> getAstrolabe(@Validated @RequestBody AstrolabeQueryDTO queryDTO) {
        return Result.success(astrolabeService.getAstrolabe(queryDTO));
    }
    
    @ApiOperation("解析命盘")
    @PostMapping("/interpret")
    public Result<AstrolabeInterpretation> interpretAstrolabe(@RequestBody Astrolabe astrolabe) {
        return Result.success(astrolabeService.interpretAstrolabe(astrolabe));
    }

    @ApiOperation("获取运限")
    @PostMapping("/horoscope")
    public Result<Map<String, Object>> getHoroscope(@Validated @RequestBody HoroscopeQueryDTO queryDTO) {
        return Result.success(astrolabeService.getHoroscope(queryDTO));
    }
    
    @ApiOperation("批量获取运限")
    @PostMapping("/horoscope/batch")
    public Result<Map<Integer, Map<String, Object>>> getBatchHoroscope(@Validated @RequestBody BatchHoroscopeQueryDTO queryDTO) {
        return Result.success(astrolabeService.getBatchHoroscope(queryDTO));
    }
    
    @ApiOperation("分析星耀组合")
    @PostMapping("/star-combinations")
    public Result<Map<String, List<String>>> analyzeStarCombinations(@Validated @RequestBody StarCombinationQueryDTO queryDTO) {
        return Result.success(astrolabeService.analyzeStarCombinations(queryDTO));
    }
    
    @ApiOperation("分析宫位关系")
    @PostMapping("/palace-relations")
    public Result<Map<String, List<String>>> analyzePalaceRelations(@Validated @RequestBody PalaceRelationQueryDTO queryDTO) {
        return Result.success(astrolabeService.analyzePalaceRelations(queryDTO));
    }
} 