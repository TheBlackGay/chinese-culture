package com.chinese.culture.admin.dto;

import com.chinese.culture.admin.core.iztro.data.Astrolabe;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 运限查询DTO
 */
@Data
public class HoroscopeQueryDTO {
    
    /**
     * 命盘数据
     */
    @NotNull(message = "命盘数据不能为空")
    private Astrolabe astrolabe;
} 