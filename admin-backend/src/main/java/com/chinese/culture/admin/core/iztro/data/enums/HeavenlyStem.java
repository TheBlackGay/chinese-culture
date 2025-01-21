package com.chinese.culture.admin.core.iztro.data.enums;

/**
 * 天干枚举
 */
public enum HeavenlyStem {
    JIA("甲", FiveElements.WOOD, StarAttribute.YANG),
    YI("乙", FiveElements.WOOD, StarAttribute.YIN),
    BING("丙", FiveElements.FIRE, StarAttribute.YANG),
    DING("丁", FiveElements.FIRE, StarAttribute.YIN),
    WU("戊", FiveElements.EARTH, StarAttribute.YANG),
    JI("己", FiveElements.EARTH, StarAttribute.YIN),
    GENG("庚", FiveElements.METAL, StarAttribute.YANG),
    XIN("辛", FiveElements.METAL, StarAttribute.YIN),
    REN("壬", FiveElements.WATER, StarAttribute.YANG),
    GUI("癸", FiveElements.WATER, StarAttribute.YIN);

    private final String description;
    private final FiveElements fiveElements;
    private final StarAttribute attribute;

    HeavenlyStem(String description, FiveElements fiveElements, StarAttribute attribute) {
        this.description = description;
        this.fiveElements = fiveElements;
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    public FiveElements getFiveElements() {
        return fiveElements;
    }

    public StarAttribute getAttribute() {
        return attribute;
    }

    public static HeavenlyStem fromDescription(String description) {
        for (HeavenlyStem stem : values()) {
            if (stem.getDescription().equals(description)) {
                return stem;
            }
        }
        throw new IllegalArgumentException("Invalid heavenly stem description: " + description);
    }

    /**
     * 获取下一个天干
     */
    public HeavenlyStem getNext() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }

    /**
     * 获取上一个天干
     */
    public HeavenlyStem getPrevious() {
        int previousOrdinal = (this.ordinal() - 1 + values().length) % values().length;
        return values()[previousOrdinal];
    }

    /**
     * 获取相隔指定数量的天干
     */
    public HeavenlyStem getOffset(int offset) {
        int targetOrdinal = (this.ordinal() + offset + values().length) % values().length;
        return values()[targetOrdinal];
    }
} 