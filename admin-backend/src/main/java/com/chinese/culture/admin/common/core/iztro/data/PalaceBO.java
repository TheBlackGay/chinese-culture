package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 宫位数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PalaceBO {
    /**
     * 基础信息
     */
    private Integer index;                  // 宫位索引
    private PalaceName name;                // 宫位名称
    private boolean isBodyPalace;           // 是否身宫
    private boolean isOriginalPalace;       // 是否来因宫
    private HeavenlyStem heavenlyStem;      // 宫位天干
    private EarthlyBranch earthlyBranch;    // 宫位地支

    /**
     * 星耀信息
     */
    @Builder.Default
    private List<StarBO> majorStars = new ArrayList<>();      // 主星
    @Builder.Default
    private List<StarBO> minorStars = new ArrayList<>();      // 辅星
    @Builder.Default
    private List<StarBO> adjectiveStars = new ArrayList<>();  // 杂耀

    /**
     * 十二神煞
     */
    private StarName changsheng12;     // 长生12神
    private StarName boshi12;          // 博士12神
    private StarName jiangqian12;      // 将前12神
    private StarName suiqian12;        // 岁前12神

    /**
     * 大小限
     */
    private DecadalBO decadal;         // 大限
    @Builder.Default
    private List<Integer> ages = new ArrayList<>();  // 小限

    /**
     * 判断宫位是否包含指定星耀
     */
    public boolean hasStars(List<StarName> stars) {
        List<StarBO> allStars = new ArrayList<>();
        allStars.addAll(majorStars);
        allStars.addAll(minorStars);
        allStars.addAll(adjectiveStars);
        
        return stars.stream().allMatch(star -> 
            allStars.stream().anyMatch(s -> s.getName() == star)
        );
    }

    /**
     * 判断宫位是否不包含指定星耀
     */
    public boolean notHaveStars(List<StarName> stars) {
        return !hasStars(stars);
    }

    /**
     * 判断宫位是否包含指定星耀中的任意一个
     */
    public boolean hasOneOfStars(List<StarName> stars) {
        List<StarBO> allStars = new ArrayList<>();
        allStars.addAll(majorStars);
        allStars.addAll(minorStars);
        allStars.addAll(adjectiveStars);
        
        return stars.stream().anyMatch(star -> 
            allStars.stream().anyMatch(s -> s.getName() == star)
        );
    }

    /**
     * 判断宫位是否包含指定四化
     */
    public boolean hasMutagen(Mutagen mutagen) {
        List<StarBO> allStars = new ArrayList<>();
        allStars.addAll(majorStars);
        allStars.addAll(minorStars);
        allStars.addAll(adjectiveStars);
        
        return allStars.stream().anyMatch(star -> star.hasMutagen(mutagen));
    }

    /**
     * 判断宫位是否不包含指定四化
     */
    public boolean notHaveMutagen(Mutagen mutagen) {
        return !hasMutagen(mutagen);
    }
} 