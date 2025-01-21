package com.chinese.culture.admin.dto;

import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 星耀组合查询DTO
 */
@Data
public class StarCombinationQueryDTO {
    
    /**
     * 命盘数据
     */
    @NotNull(message = "命盘数据不能为空")
    private Astrolabe astrolabe;
    
    /**
     * 是否包含三方四正
     */
    private Boolean includeTrineAndOpposition = true;
    
    /**
     * 是否包含星耀会合
     */
    private Boolean includeConvergence = true;
} 