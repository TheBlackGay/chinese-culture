package com.chinese.culture.admin.common.core.iztro.data.enums;

import lombok.Getter;

/**
 * 生肖枚举（按地支顺序）
 */
@Getter
public enum Zodiac {
    SHU("鼠"),      // 子鼠
    NIU("牛"),      // 丑牛
    HU("虎"),       // 寅虎
    TU("兔"),       // 卯兔
    LONG("龙"),     // 辰龙
    SHE("蛇"),      // 巳蛇
    MA("马"),       // 午马
    YANG("羊"),     // 未羊
    HOU("猴"),      // 申猴
    JI("鸡"),       // 酉鸡
    GOU("狗"),      // 戌狗
    ZHU("猪");      // 亥猪

    private final String chinese;

    Zodiac(String chinese) {
        this.chinese = chinese;
    }
} 