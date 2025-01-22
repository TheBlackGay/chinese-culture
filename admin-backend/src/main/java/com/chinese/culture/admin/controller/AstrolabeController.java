//package com.chinese.culture.admin.controller;
//
//import com.chinese.culture.admin.api.dto.request.AstrolabeRequestDTO;
//import com.chinese.culture.admin.api.dto.response.AstrolabeResponseDTO;
//import com.chinese.culture.admin.common.core.iztro.AstroController;
//import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
//import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
//import com.chinese.culture.admin.common.result.Result;
//import com.chinese.culture.admin.convert.AstrolabeConvert;
//import com.chinese.culture.admin.service.AstrolabeBizService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * 命盘查询接口
// */
//@Api(tags = "命盘查询")
//@RestController
//@RequestMapping("/v1/astrolabe")
//@RequiredArgsConstructor
//public class AstrolabeController {
//
//    @Resource
//    private AstrolabeBizService astrolabeBizService;
//
//    @Resource
//    private AstroController astroController;
//
//    @ApiOperation("获取命盘")
//    @PostMapping("/get")
//    public Result<AstrolabeResponseDTO> getAstrolabe(@Validated @RequestBody AstrolabeRequestDTO requestDTO) {
//
////        AstrolabeQueryBO queryDTO = AstrolabeConvert.toAstrolabeQueryBO(requestDTO);
//
//        // 调用新的generateAstrolabe方法
//        List<PalaceBO> palaces = astroController.generateAstrolabe(
//                requestDTO.getBirthYear(),
//                requestDTO.getBirthMonth(),
//                requestDTO.getBirthDay(),
//                requestDTO.getBirthHour() - 1, // 转换为0-11的时辰索引
//                requestDTO.getGender()
//        );
//
//        // 构造Astrolabe对象
//        AstrolabeBO astrolabe = new AstrolabeBO();
//        astrolabe.setPalaces(palaces);
//
//        AstrolabeResponseDTO responseDTO = AstrolabeConvert.convertToResponseDTO(astrolabe);
//
//        return Result.success(responseDTO);
//    }
//
////    @ApiOperation("解析命盘")
////    @PostMapping("/interpret")
////    public Result<AstrolabeInterpretation> interpretAstrolabe(@Validated @RequestBody AstrolabeRequestDTO requestDTO) {
////
////        AstrolabeQueryBO queryDTO = AstrolabeConvert.toAstrolabeQueryBO(requestDTO);
////
////        Astrolabe astrolabe = astrolabeBizService.getAstrolabe(queryDTO);
////
////        return Result.success(astrolabeBizService.interpretAstrolabe(astrolabe));
////    }
////
////    @ApiOperation("获取运限")
////    @PostMapping("/horoscope")
////    public Result<AstrolabeHoroscopeResponseDTO> getHoroscope(@Validated @RequestBody AstrolabeRequestDTO requestDTO) {
////
////        AstrolabeQueryBO queryDTO = AstrolabeConvert.toAstrolabeQueryBO(requestDTO);
////
////        Astrolabe astrolabe = astrolabeBizService.getAstrolabe(queryDTO);
////
////        return Result.success(convertToHoroscopeResponseDTO(astrolabeBizService.getHoroscope(astrolabe)));
////    }
////
////    @ApiOperation("分析星耀组合")
////    @PostMapping("/star-combinations")
////    public Result<AstrolabeStarCombinationResponseDTO> analyzeStarCombinations(
////            @Validated @RequestBody AstrolabeRequestDTO requestDTO) {
////
////        AstrolabeQueryBO queryDTO = AstrolabeConvert.toAstrolabeQueryBO(requestDTO);
////
////        Astrolabe astrolabe = astrolabeBizService.getAstrolabe(queryDTO);
////
////        Map<String, List<String>> combinations = astrolabeBizService.analyzeStarCombinations(astrolabe);
////
////        return Result.success(convertToStarCombinationResponseDTO(combinations));
////    }
////
////    @ApiOperation("分析宫位关系")
////    @PostMapping("/palace-relations")
////    public Result<AstrolabePalaceRelationResponseDTO> analyzePalaceRelations(@Validated @RequestBody AstrolabeRequestDTO requestDTO) {
////
////        Astrolabe astrolabe = astrolabeBizService.getAstrolabe(requestDTO);
////        return Result.success(convertToPalaceRelationResponseDTO(
////            astrolabeBizService.analyzePalaceRelations(astrolabe)));
////    }
////
////    /**
////     * 转换为响应DTO
////     */
////    private AstrolabeResponseDTO convertToResponseDTO(Astrolabe astrolabe) {
////
////        AstrolabeResponseDTO responseDTO = new AstrolabeResponseDTO();
////        responseDTO.setDestinyPalace(astrolabe.getDestinyPalace());
////        responseDTO.setBodyPalace(astrolabe.getBodyPalace().get());
////        responseDTO.setPalaces(astrolabe.getPalaces());
////        responseDTO.setFiveElementsClass(astrolabe.getFiveElementsClass());
////
////        // 获取宫位吉凶分析
////        Map<String, Object> palaceAnalysis = astrolabeBizService.analyzePalaceAuspiciousness(astrolabe);
////        responseDTO.setPalaceAnalysis(palaceAnalysis);
////
////        // 获取星耀组合分析
////        Map<String, List<String>> starCombinations = astrolabeBizService.analyzeStarCombinations(astrolabe);
////        responseDTO.setStarCombinations(starCombinations);
////
////        return responseDTO;
////    }
////
////    /**
////     * 转换为运限响应DTO
////     */
////    private AstrolabeHoroscopeResponseDTO convertToHoroscopeResponseDTO(Map<String, Object> horoscope) {
////        // TODO: 实现转换逻辑
////        return new AstrolabeHoroscopeResponseDTO();
////    }
////
////    /**
////     * 转换为星耀组合响应DTO
////     */
////    private AstrolabeStarCombinationResponseDTO convertToStarCombinationResponseDTO(
////            Map<String, List<String>> combinations) {
////        // TODO: 实现转换逻辑
////        return new AstrolabeStarCombinationResponseDTO();
////    }
////
////    /**
////     * 转换为宫位关系响应DTO
////     */
////    private AstrolabePalaceRelationResponseDTO convertToPalaceRelationResponseDTO(
////            Map<String, List<String>> relations) {
////        // TODO: 实现转换逻辑
////        return new AstrolabePalaceRelationResponseDTO();
////    }
//}
