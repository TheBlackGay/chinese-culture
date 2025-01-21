package com.chinese.culture.admin.model.ziwei;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "紫微斗数请求参数")
public class ZiweiRequest {
    @ApiModelProperty(value = "出生年", example = "1994", required = true)
    private Integer birthYear;

    @ApiModelProperty(value = "出生月", example = "12", required = true)
    private Integer birthMonth;

    @ApiModelProperty(value = "出生日", example = "8", required = true)
    private Integer birthDay;

    @ApiModelProperty(value = "出生时", example = "9", required = true)
    private Integer birthHour;

    @ApiModelProperty(value = "性别", example = "male", required = true, allowableValues = "male,female")
    private String gender;
    
    @ApiModelProperty(value = "运限参数")
    private HoroscopeParams horoscopeParams;
    
    @Data
    @ApiModel(description = "运限参数")
    public static class HoroscopeParams {
        @ApiModelProperty(value = "大限参数")
        private DecadalParams decadal;

        @ApiModelProperty(value = "流年", example = "2024")
        private Integer year;

        @ApiModelProperty(value = "流月", example = "1")
        private Integer month;

        @ApiModelProperty(value = "流日", example = "1")
        private Integer day;

        @ApiModelProperty(value = "流时", example = "0")
        private Integer hour;
    }
    
    @Data
    @ApiModel(description = "大限参数")
    public static class DecadalParams {
        @ApiModelProperty(value = "大限索引", example = "1")
        private Integer index;

        @ApiModelProperty(value = "大限开始年", example = "2024")
        private Integer startYear;

        @ApiModelProperty(value = "大限结束年", example = "2033")
        private Integer endYear;
    }
} 