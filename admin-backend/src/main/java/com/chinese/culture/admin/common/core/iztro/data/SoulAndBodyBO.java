package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 命宫和身宫数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoulAndBodyBO {
    /**
     * 命宫索引
     */
    private Integer soulIndex;
    
    /**
     * 身宫索引
     */
    private Integer bodyIndex;
    
    /**
     * 命宫天干
     */
    private HeavenlyStem heavenlyStemOfSoul;
    
    /**
     * 命宫地支
     */
    private EarthlyBranch earthlyBranchOfSoul;
} 