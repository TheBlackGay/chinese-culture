package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 命盘格局类型
 */
public enum HoroscopePattern {

    /**
     * 紫微命格：紫微星在命宫
     */
    ZIWEI_MING("紫微命格"),

    /**
     * 天府命格：天府星在命宫
     */
    TIANFU_MING("天府命格"),

    /**
     * 禄存命格：禄存星在命宫
     */
    LUCUN_MING("禄存命格"),

    /**
     * 文昌命格：文昌星在命宫
     */
    WENCHANG_MING("文昌命格"),

    /**
     * 科权禄全格：科、权、禄三颗星在重要宫位
     */
    FULL_MUTAGEN("科权禄全格"),

    /**
     * 三奇格：文昌、文曲、左辅、右弼中三颗以上同宫
     */
    THREE_NOBLE("三奇格"),

    /**
     * 四煞格：火星、铃星、地空、地劫同宫
     */
    FOUR_EVIL("四煞格"),

    /**
     * 红艳格：太阳、太阴、天同、天机同宫
     */
    RED_BEAUTY("红艳格"),

    /**
     * 富贵格：紫微、天府、武曲、贪狼同宫
     */
    WEALTH_NOBLE("富贵格"),

    /**
     * 破格：命宫无主星
     */
    BROKEN("破格"),

    /**
     * 普通格局
     */
    NORMAL("普通格局");

    private final String description;

    HoroscopePattern(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static HoroscopePattern fromDescription(String description) {
        for (HoroscopePattern pattern : values()) {
            if (pattern.description.equals(description)) {
                return pattern;
            }
        }
        throw new IllegalArgumentException("Unknown horoscope pattern: " + description);
    }
}
