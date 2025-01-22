package com.chinese.culture.admin.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("星耀信息")
public class StarDTO {

    /**
     * 星耀名称
     */
    @ApiModelProperty("星耀名称")
    private String name;

    /**
     * 亮度
     */
    @ApiModelProperty("亮度")
    private String brightness;

    /**
     * 四化
     */
    @ApiModelProperty("四化")
    private List<String> mutagens;

}
