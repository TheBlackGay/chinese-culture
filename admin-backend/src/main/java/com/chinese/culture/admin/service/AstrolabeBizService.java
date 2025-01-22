//package com.chinese.culture.admin.service;
//
//import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
//import com.chinese.culture.admin.common.core.iztro.data.AstrolabeInterpretationBO;
//import com.chinese.culture.admin.persist.bo.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 命盘服务接口
// */
//public interface AstrolabeBizService {
//
//    /**
//     * 获取命盘
//     *
//     * @param queryDTO 查询参数
//     * @return 命盘数据
//     */
//    AstrolabeBO getAstrolabe(AstrolabeQueryBO queryDTO);
//
//    /**
//     * 解析命盘
//     *
//     * @param astrolabe 命盘数据
//     * @return 命盘解析结果
//     */
//    AstrolabeInterpretationBO interpretAstrolabe(AstrolabeBO astrolabe);
//
//    /**
//     * 获取运限
//     *
//     * @param queryDTO 查询参数
//     * @return 运限数据
//     */
//    Map<String, Object> getHoroscope(HoroscopeQueryBO queryDTO);
//
//    /**
//     * 批量获取运限
//     *
//     * @param queryDTO 查询参数
//     * @return 多个年龄的运限数据
//     */
//    Map<Integer, Map<String, Object>> getBatchHoroscope(BatchHoroscopeQueryBO queryDTO);
//
//    /**
//     * 分析星耀组合
//     *
//     * @param queryDTO 查询参数
//     * @return 星耀组合数据
//     */
//    Map<String, List<String>> analyzeStarCombinations(StarCombinationQueryBO queryDTO);
//
//    /**
//     * 分析宫位关系
//     *
//     * @param queryDTO 查询参数
//     * @return 宫位关系数据
//     */
//    Map<String, List<String>> analyzePalaceRelations(PalaceRelationQueryBO queryDTO);
//}
