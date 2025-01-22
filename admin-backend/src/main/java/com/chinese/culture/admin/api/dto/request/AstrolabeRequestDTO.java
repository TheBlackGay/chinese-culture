package com.chinese.culture.admin.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 命盘查询请求DTO
 */
@Data
@ApiModel("命盘查询请求")
public class AstrolabeRequestDTO {

    /**
     * 出生年
     */
    @NotNull(message = "出生年不能为空")
    private Integer birthYear;

    /**
     * 出生月
     */
    @NotNull(message = "出生月不能为空")
    @Min(value = 1, message = "出生月必须在1-12之间")
    @Max(value = 12, message = "出生月必须在1-12之间")
    private Integer birthMonth;

    /**
     * 出生日
     */
    @NotNull(message = "出生日不能为空")
    @Min(value = 1, message = "出生日必须在1-31之间")
    @Max(value = 31, message = "出生日必须在1-31之间")
    private Integer birthDay;

    /**
     * 出生时辰(1-12)
     */
    @NotNull(message = "出生时辰不能为空")
    @Min(value = 1, message = "出生时辰必须在1-12之间")
    @Max(value = 12, message = "出生时辰必须在1-12之间")
    private Integer birthHour;

    /**
     * 性别（1-男，2-女）
     */
    @NotNull(message = "性别不能为空")
    @Min(value = 1, message = "性别只能是1或2")
    @Max(value = 2, message = "性别只能是1或2")
    private Integer gender;

    /**
     * 是否使用农历
     */
    private Boolean isLunar = false;

}
