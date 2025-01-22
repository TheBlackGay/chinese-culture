package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 三方四正宫位关系
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurroundedPalacesBO {
    private PalaceBO target;    // 本宫
    private PalaceBO opposite;  // 对宫
    private PalaceBO wealth;    // 财帛位（三方）
    private PalaceBO career;    // 官禄位（三方）

    /**
     * 判断三方四正宫位是否包含指定星耀
     */
    public boolean hasStars(StarName... stars) {
        return target.hasStars(List.of(stars)) ||
               opposite.hasStars(List.of(stars)) ||
               wealth.hasStars(List.of(stars)) ||
               career.hasStars(List.of(stars));
    }

    /**
     * 判断三方四正宫位是否不包含指定星耀
     */
    public boolean notHaveStars(StarName... stars) {
        return !hasStars(stars);
    }

    /**
     * 判断三方四正宫位是否包含指定四化
     */
    public boolean hasMutagen(Mutagen mutagen) {
        return target.hasMutagen(mutagen) ||
               opposite.hasMutagen(mutagen) ||
               wealth.hasMutagen(mutagen) ||
               career.hasMutagen(mutagen);
    }

    /**
     * 判断三方四正宫位是否不包含指定四化
     */
    public boolean notHaveMutagen(Mutagen mutagen) {
        return !hasMutagen(mutagen);
    }
} 