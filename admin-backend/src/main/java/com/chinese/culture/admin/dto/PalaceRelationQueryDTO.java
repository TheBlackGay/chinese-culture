package com.chinese.culture.admin.dto;

import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 宫位关系查询DTO
 */
@Data
public class PalaceRelationQueryDTO {
    
    /**
     * 命盘数据
     */
    @NotNull(message = "命盘数据不能为空")
    private Astrolabe astrolabe;
    
    /**
     * 是否包含冲克关系
     */
    private Boolean includePunishments = true;
    
    /**
     * 是否包含合化关系
     */
    private Boolean includeHarmonies = true;
    
    /**
     * 是否包含三合关系
     */
    private Boolean includeTrineFormations = true;
} 