package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.Gender;
import lombok.Data;
import java.util.List;
import java.util.Optional;

/**
 * 命盘
 */
@Data
public class AstrolabeBO {

    /**
     * 阳历生日
     */
    private String solarDate;

    /**
     * 农历生日
     */
    private String lunarDate;

    /**
     * 原始日期数据
     */
    private RawDateBO rawDates;

    /**
     * 时辰范围
     */
    private String timeRange;

    /**
     * 出生时辰（1-12）
     */
    private int birthHour;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 年龄
     */
    private int age;

    /**
     * 星座
     */
    private String sign;

    /**
     * 生肖
     */
    private String zodiac;

    /**
     * 宫位列表
     */
    private List<PalaceBO> palaces;

    /**
     * 星耀列表
     */
    private List<StarBO> stars;

    /**
     * 年干
     */
    private String yearStem;

    /**
     * 命主
     */
    private String soul;

    /**
     * 身主
     */
    private String body;

    /**
     * 五行局
     */
    private String fiveElements;

    /**
     * 运限数据
     */
    private HoroscopeBO horoscope;

    /**
     * 根据宫位名称获取宫位
     */
    public Optional<PalaceBO> getPalace(String name) {
        return palaces.stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst();
    }

    /**
     * 根据星耀名称获取星耀
     */
    public Optional<StarBO> getStar(String name) {
        return stars.stream()
                .filter(s -> name.equals(s.getName()))
                .findFirst();
    }

    /**
     * 获取农历年份
     */
    public int getLunarYear() {
        return rawDates.getLunarDate().getYear();
    }

    /**
     * 设置四化
     */
    public void setTransformations(List<StarBO> stars) {
        this.stars = stars;
    }

}
