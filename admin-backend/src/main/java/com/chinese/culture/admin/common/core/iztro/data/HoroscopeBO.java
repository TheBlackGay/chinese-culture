package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
import com.chinese.culture.admin.common.core.iztro.data.enums.PalaceName;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 运限信息数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoroscopeBO {
    private Integer index;                     // 索引
    private HeavenlyStem heavenlyStem;        // 天干
    private EarthlyBranch earthlyBranch;      // 地支
    private Integer age;                       // 年龄
    private Integer startAge;                  // 起始年龄
    private Integer endAge;                    // 结束年龄
    private List<PalaceName> palaceNames;     // 宫位名称列表
    private List<Mutagen> mutagens;           // 四化列表
    private List<List<StarBO>> stars;         // 星耀列表
    private Integer position;                  // 所在位置
    private String flowDirection;              // 流向
    private Integer flowYear;                  // 流年
    private String direction;                  // 方向
} 