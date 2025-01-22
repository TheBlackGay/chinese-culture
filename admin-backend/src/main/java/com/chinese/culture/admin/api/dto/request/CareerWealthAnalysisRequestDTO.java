package com.chinese.culture.admin.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("事业财富分析请求")
public class CareerWealthAnalysisRequestDTO {

    @ApiModelProperty("农历年")
    private Integer lunarYear;

    @ApiModelProperty("农历月")
    private Integer lunarMonth;

    @ApiModelProperty("农历日")
    private Integer lunarDay;

    @ApiModelProperty("出生时辰（1-12）")
    private Integer birthHour;

    @ApiModelProperty("性别（男/女）")
    private String gender;
} 