package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import lombok.Data;

/**
 * 星耀组合信息
 */
@Data
public class StarCombinationBO {
    /**
     * 星耀名称
     */
    private StarName starName;

    /**
     * 所在宫位引用
     */
    private PalaceBO palace;

    /**
     * 所在位置
     */
    private int position;

    /**
     * 检查是否与目标星耀同宫
     */
    public boolean isInSamePalace(StarBO other) {
        if (palace == null) {
            return false;
        }
        return palace.equals(other.getPalace().orElse(null));
    }

    /**
     * 检查是否与目标星耀对宫
     */
    public boolean isOpposite(StarBO other) {
        if (palace == null) {
            return false;
        }
        return palace.getOppositePalace()
                .map(p -> p.hasStar(other.getName().getDescription()))
                .orElse(false);
    }

    /**
     * 检查是否与目标星耀三合
     * TODO: 实现三合逻辑
     */
    public boolean isTriple(StarBO other) {
        return false;
    }

    /**
     * 检查是否与目标星耀六合
     * TODO: 实现六合逻辑
     */
    public boolean isSixHarmony(StarBO other) {
        return false;
    }

    /**
     * 获取星耀组合效果
     * TODO: 实现组合效果判断逻辑
     */
    public String getCombinationEffect(StarBO other) {
        return "";
    }
}
