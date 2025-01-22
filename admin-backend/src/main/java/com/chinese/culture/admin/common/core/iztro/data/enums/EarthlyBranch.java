package com.chinese.culture.admin.common.core.iztro.data.enums;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * 地支枚举
 */
@Getter
public enum EarthlyBranch {
    ZI("子", FiveElements.WATER, StarAttribute.YANG),
    CHOU("丑", FiveElements.EARTH, StarAttribute.YIN),
    YIN("寅", FiveElements.WOOD, StarAttribute.YANG),
    MAO("卯", FiveElements.WOOD, StarAttribute.YIN),
    CHEN("辰", FiveElements.EARTH, StarAttribute.YANG),
    SI("巳", FiveElements.FIRE, StarAttribute.YIN),
    WU("午", FiveElements.FIRE, StarAttribute.YANG),
    WEI("未", FiveElements.EARTH, StarAttribute.YIN),
    SHEN("申", FiveElements.METAL, StarAttribute.YANG),
    YOU("酉", FiveElements.METAL, StarAttribute.YIN),
    XU("戌", FiveElements.EARTH, StarAttribute.YANG),
    HAI("亥", FiveElements.WATER, StarAttribute.YIN);

    private final String description;
    private final FiveElements fiveElements;
    private final StarAttribute attribute;

    EarthlyBranch(String description, FiveElements fiveElements, StarAttribute attribute) {
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

    public static EarthlyBranch fromDescription(String description) {
        for (EarthlyBranch branch : values()) {
            if (branch.getDescription().equals(description)) {
                return branch;
            }
        }
        throw new IllegalArgumentException("Invalid earthly branch description: " + description);
    }

    public static EarthlyBranch fromIndex(int index) {
        if (index < 0 || index >= values().length) {
            throw new IllegalArgumentException("Invalid earthly branch index: " + index);
        }
        return values()[index];
    }

    /**
     * 获取下一个地支
     */
    public EarthlyBranch getNext() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }

    /**
     * 获取上一个地支
     */
    public EarthlyBranch getPrevious() {
        int previousOrdinal = (this.ordinal() - 1 + values().length) % values().length;
        return values()[previousOrdinal];
    }

    /**
     * 获取相隔指定数量的地支
     */
    public EarthlyBranch getOffset(int offset) {
        int targetOrdinal = (this.ordinal() + offset + values().length) % values().length;
        return values()[targetOrdinal];
    }

    /**
     * 获取三合地支
     * 寅申巳、亥卯未、子辰申、丑巳酉
     */
    public List<EarthlyBranch> getTripleHarmony() {
        switch (this) {
            case YIN:
                return Arrays.asList(YIN, SHEN, SI);
            case SHEN:
                return Arrays.asList(SHEN, YIN, SI);
            case SI:
                return Arrays.asList(SI, YIN, SHEN);
            case HAI:
                return Arrays.asList(HAI, MAO, WEI);
            case MAO:
                return Arrays.asList(MAO, WEI, HAI);
            case WEI:
                return Arrays.asList(WEI, HAI, MAO);
            case ZI:
                return Arrays.asList(ZI, CHEN, SHEN);
            case CHEN:
                return Arrays.asList(CHEN, SHEN, ZI);
            case CHOU:
                return Arrays.asList(CHOU, SI, YOU);
            case YOU:
                return Arrays.asList(YOU, CHOU, SI);
            default:
                return Arrays.asList(this);
        }
    }

    /**
     * 获取六合地支
     * 子丑、寅亥、卯戌、辰酉、巳申、午未
     */
    public EarthlyBranch getSixHarmony() {
        switch (this) {
            case ZI:
                return CHOU;
            case CHOU:
                return ZI;
            case YIN:
                return HAI;
            case HAI:
                return YIN;
            case MAO:
                return XU;
            case XU:
                return MAO;
            case CHEN:
                return YOU;
            case YOU:
                return CHEN;
            case SI:
                return SHEN;
            case SHEN:
                return SI;
            case WU:
                return WEI;
            case WEI:
                return WU;
            default:
                return this;
        }
    }

    /**
     * 获取对冲地支
     * 子午、丑未、寅申、卯酉、辰戌、巳亥
     */
    public EarthlyBranch getOpposite() {
        return getOffset(6);
    }

    public EarthlyBranch getSixHarmonyBranch() {
        switch (this) {
            case ZI: return YOU;
            case CHOU: return XU;
            case YIN: return HAI;
            case MAO: return ZI;
            case CHEN: return CHOU;
            case SI: return YIN;
            case WU: return MAO;
            case WEI: return CHEN;
            case SHEN: return SI;
            case YOU: return WU;
            case XU: return WEI;
            case HAI: return SHEN;
            default: return null;
        }
    }

    public int getOffset(EarthlyBranch base) {
        int offset = this.ordinal() - base.ordinal();
        return offset >= 0 ? offset : offset + 12;
    }
}
