package com.chinese.culture.admin.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("事业财富分析响应")
public class CareerWealthAnalysisResponseDTO {

    @ApiModelProperty("事业分析")
    private String careerAnalysis;

    @ApiModelProperty("财富分析")
    private String wealthAnalysis;

    @ApiModelProperty("吉凶指数")
    private Integer auspiciousness;

    @ApiModelProperty("建议")
    private List<String> suggestions;
} 