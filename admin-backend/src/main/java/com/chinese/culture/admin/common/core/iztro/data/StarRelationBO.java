package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 星耀位置关系信息
 */
@Data
public class StarRelationBO {
    /**
     * 星耀名称
     */
    private StarName starName;

    /**
     * 所在宫位引用
     */
    private PalaceBO palace;

    /**
     * 获取所在宫位
     */
    public Optional<PalaceBO> getPalace() {
        return Optional.ofNullable(palace);
    }

    /**
     * 获取对宫星耀
     */
    public List<StarBO> getOppositeStars() {
        if (palace == null) {
            return new ArrayList<>();
        }
        return palace.getOppositePalace()
                .map(PalaceBO::getAllStars)
                .orElse(new ArrayList<>());
    }

    /**
     * 获取三方四正星耀
     */
    public List<StarBO> getSurroundedStars() {
        if (palace == null) {
            return new ArrayList<>();
        }
        List<StarBO> stars = new ArrayList<>();
        palace.getSurroundedPalaces().forEach(p -> stars.addAll(p.getAllStars()));
        return stars;
    }
}
