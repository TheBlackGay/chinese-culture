package com.chinese.culture.admin.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel("宫位信息")
public class PalaceResponseDTO {

    /**
     * 宫位序号(1-12)
     */
    private int index;

    /**
     * 宫位名称
     */
    @ApiModelProperty("宫位名称")
    private String name;

    /**
     * 主星列表
     */
    @ApiModelProperty("主星列表")
    private List<StarDTO> majorStars;

    /**
     * 辅星列表
     */
    @ApiModelProperty("辅星列表")
    private List<StarDTO> minorStars;

    /**
     * 四化
     */
    @ApiModelProperty("四化")
    private List<String> mutagens;

    /**
     * 天干
     */
    private String heavenlyStem;

    /**
     * 地支
     */
    private String branch;

    /**
     * 杂耀列表
     */
    private List<StarDTO> adjectiveStars;

    /**
     * 长生十二神
     */
    private String mutagen12;

    /**
     * 是否为命宫
     */
    private boolean ming;

    /**
     * 是否为身宫
     */
    private boolean body;

    /**
     * 宫位阴阳枚举：0-阴、1-阳
     */
    private Integer attribute;

    /**
     * 五行枚举:1-金、2-木、3-水、4-火、5-土
     */
    private Integer fiveElements;

    /**
     * 宫位关系枚举:1-原宫,2-对宫,3-财帛位,4-官禄位
     */
    private Map<Integer, PalaceResponseDTO> relatedPalaces;

}
