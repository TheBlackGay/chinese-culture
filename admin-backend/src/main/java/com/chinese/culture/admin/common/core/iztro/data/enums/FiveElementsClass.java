package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 五行局枚举
 * 几局就从几岁（虚岁）开始起运
 * 比如 木三局 就从3岁开始起运
 */
@Getter
@AllArgsConstructor
public enum FiveElementsClass {

    SHUI_ER("水二局", 2),    // 水二局
    MU_SAN("木三局", 3),     // 木三局
    JIN_SI("金四局", 4),     // 金四局
    TU_WU("土五局", 5),      // 土五局
    HUO_LIU("火六局", 6);    // 火六局

    private final String chinese;

    private final int startAge;  // 起运年龄

}
