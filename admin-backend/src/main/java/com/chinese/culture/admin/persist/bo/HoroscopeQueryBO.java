package com.chinese.culture.admin.persist.bo;

import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 运限查询DTO
 */
@Data
public class HoroscopeQueryBO {

    /**
     * 命盘数据
     */
    @NotNull(message = "命盘数据不能为空")
    private AstrolabeBO astrolabe;
}
