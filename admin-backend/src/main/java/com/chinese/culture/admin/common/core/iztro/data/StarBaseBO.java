package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import lombok.Data;

/**
 * 星耀基础信息
 */
@Data
public class StarBaseBO {
    /**
     * 星耀名称
     */
    private StarName name;

    /**
     * 所在宫位(1-12)
     */
    private int position;

    /**
     * 地支
     */
    private EarthlyBranch branch;

    /**
     * 星耀类型
     */
    private StarType type;

    /**
     * 阴阳属性
     */
    private StarAttribute attribute;

    /**
     * 五行属性
     */
    private FiveElements fiveElements;
} 