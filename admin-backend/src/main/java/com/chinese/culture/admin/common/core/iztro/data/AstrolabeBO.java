package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElementsClass;
import com.chinese.culture.admin.common.core.iztro.data.enums.PalaceName;
import com.chinese.culture.admin.common.core.iztro.data.enums.Zodiac;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 命盘数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AstrolabeBO {
    /**
     * 基本信息
     */
    private String solarDate;          // 阳历日期
    private String lunarDate;          // 农历日期
    private String time;               // 时辰
    private String timeRange;          // 时辰范围
    private String sign;               // 星座
    private Zodiac zodiac;            // 生肖
    private String gender;             // 性别（男/女）

    /**
     * 命盘信息
     */
    private List<PalaceBO> palaces;   // 十二宫位
    private String soul;               // 命主
    private String body;               // 身主
    private FiveElementsClass fiveElementsClass;  // 五行局

    /**
     * 中宫信息
     */
    private CenterInfoBO centerInfo;   // 中宫信息

    /**
     * 运限信息
     */
    private HoroscopeBO decadal;      // 大限
    private HoroscopeBO yearly;        // 流年
    private HoroscopeBO monthly;       // 流月
    private HoroscopeBO daily;         // 流日
    private HoroscopeBO hourly;        // 流时

    /**
     * 获取指定宫位
     */
    public PalaceBO getPalace(int index) {
        if (index < 0 || index >= palaces.size()) {
            return null;
        }
        return palaces.get(index);
    }

    /**
     * 获取指定宫位
     */
    public PalaceBO getPalace(PalaceName name) {
        return palaces.stream()
                .filter(p -> p.getName() == name)
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取命宫
     */
    public PalaceBO getMingGong() {
        return palaces.stream()
                .filter(PalaceBO::isOriginalPalace)
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取身宫
     */
    public PalaceBO getShenGong() {
        return palaces.stream()
                .filter(PalaceBO::isBodyPalace)
                .findFirst()
                .orElse(null);
    }
} 