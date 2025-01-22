package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.Getter;

/**
 * 宫位名称枚举
 */
@Getter
public enum PalaceName {
    MING_GONG("命宫"),           // 命宫
    XIONG_DI("兄弟"),           // 兄弟宫
    FU_QI("夫妻"),             // 夫妻宫
    ZI_NV("子女"),             // 子女宫
    CAI_BO("财帛"),            // 财帛宫
    JI_E("疾厄"),              // 疾厄宫
    QIAN_YI("迁移"),           // 迁移宫
    JIAO_YOU("交友"),          // 交友宫
    GUAN_LU("官禄"),           // 官禄宫
    TIAN_ZHE("田宅"),          // 田宅宫
    FU_DE("福德"),             // 福德宫
    FU_MU("父母");             // 父母宫

    private final String chinese;

    PalaceName(String chinese) {
        this.chinese = chinese;
    }
} 