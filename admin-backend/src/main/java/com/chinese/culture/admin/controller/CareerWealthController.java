//package com.chinese.culture.admin.controller;
//
//import com.chinese.culture.admin.common.result.Result;
//import com.chinese.culture.admin.common.core.iztro.analyzer.CareerWealthAnalyzer;
//import com.chinese.culture.admin.api.dto.request.CareerWealthRequestDTO;
//import com.chinese.culture.admin.api.dto.response.CareerWealthResponseDTO;
//import com.chinese.culture.admin.api.dto.request.CareerWealthAnalysisRequestDTO;
//import com.chinese.culture.admin.api.dto.request.CareerWealthAnalysisResponseDTO;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 事业财运分析控制器
// */
//@Api(tags = "事业财运分析")
//@RestController
//@RequestMapping("/api/v1/career-wealth")
//@RequiredArgsConstructor
//public class CareerWealthController {
//
//    @ApiOperation("分析事业财运")
//    @PostMapping("/analyze")
//    public Result<CareerWealthResponseDTO> analyzeCareerAndWealth(
//            @Validated @RequestBody CareerWealthRequestDTO requestDTO) {
//
//        // 转换为分析器需要的请求DTO
//        CareerWealthAnalysisRequestDTO analysisRequest = new CareerWealthAnalysisRequestDTO();
//        analysisRequest.setPalaces(requestDTO.getPalaces());
//        analysisRequest.setAge(requestDTO.getAge());
//
//        // 调用分析器进行分析
//        CareerWealthAnalysisResponseDTO analysisResult =
//            CareerWealthAnalyzer.analyzeCareerAndWealth(analysisRequest);
//
//        // 转换为API响应DTO
//        CareerWealthResponseDTO response = new CareerWealthResponseDTO();
//        response.setCareer(convertCareerAnalysis(analysisResult.getCareer()));
//        response.setWealth(convertWealthAnalysis(analysisResult.getWealth()));
//        response.setTiming(convertTimingAnalysis(analysisResult.getTiming()));
//        response.setSuggestion(analysisResult.getSuggestion());
//
//        return Result.success(response);
//    }
//
//    private CareerWealthResponseDTO.CareerDTO convertCareerAnalysis(
//            CareerWealthAnalysisResponseDTO.CareerAnalysisDTO careerAnalysis) {
//        CareerWealthResponseDTO.CareerDTO careerDTO = new CareerWealthResponseDTO.CareerDTO();
//        careerDTO.setScore(careerAnalysis.getScore());
//        careerDTO.setSuitableIndustries(careerAnalysis.getSuitableIndustries());
//        careerDTO.setDescription(careerAnalysis.getDescription());
//        return careerDTO;
//    }
//
//    private CareerWealthResponseDTO.WealthDTO convertWealthAnalysis(
//            CareerWealthAnalysisResponseDTO.WealthAnalysisDTO wealthAnalysis) {
//        CareerWealthResponseDTO.WealthDTO wealthDTO = new CareerWealthResponseDTO.WealthDTO();
//        wealthDTO.setScore(wealthAnalysis.getScore());
//        wealthDTO.setSources(wealthAnalysis.getSources());
//        wealthDTO.setDescription(wealthAnalysis.getDescription());
//        return wealthDTO;
//    }
//
//    private CareerWealthResponseDTO.TimingDTO convertTimingAnalysis(
//            CareerWealthAnalysisResponseDTO.TimingAnalysisDTO timingAnalysis) {
//        CareerWealthResponseDTO.TimingDTO timingDTO = new CareerWealthResponseDTO.TimingDTO();
//        timingDTO.setPeakAge(timingAnalysis.getPeakAge());
//        timingDTO.setSuggestion(timingAnalysis.getSuggestion());
//        return timingDTO;
//    }
//}
