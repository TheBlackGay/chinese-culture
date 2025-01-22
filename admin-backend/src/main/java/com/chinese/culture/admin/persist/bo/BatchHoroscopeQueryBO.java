package com.chinese.culture.admin.persist.bo;

import com.chinese.culture.admin.common.core.iztro.data.AstrolabeBO;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 批量运限查询DTO
 */
@Data
public class BatchHoroscopeQueryBO {

    /**
     * 命盘数据
     */
    @NotNull(message = "命盘数据不能为空")
    private AstrolabeBO astrolabe;

    /**
     * 年龄列表
     */
    @NotNull(message = "年龄列表不能为空")
    @Size(min = 1, max = 100, message = "年龄列表大小必须在1-100之间")
    private List<Integer> ages;
}
