package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElements;
import com.chinese.culture.admin.common.core.iztro.data.enums.PalaceAttribute;
import com.chinese.culture.admin.common.core.iztro.data.enums.PalaceRelation;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 宫位
 */
@Data
public class PalaceBO {

    /**
     * 宫位名称
     */
    private String name;

    /**
     * 天干
     */
    private String heavenlyStem;

    /**
     * 宫位序号(1-12)
     */
    private int index;

    /**
     * 地支
     */
    private String branch;

    /**
     * 主星列表
     */
    private List<StarBO> majorStars = new ArrayList<>();

    /**
     * 辅星列表
     */
    private List<StarBO> minorStars = new ArrayList<>();

    /**
     * 杂耀列表
     */
    private List<StarBO> adjectiveStars = new ArrayList<>();

    /**
     * 四化列表
     */
    private List<String> mutagens = new ArrayList<>();

    /**
     * 长生十二神
     */
    private String mutagen12;

    /**
     * 是否为命宫
     */
    private boolean mingGong;

    /**
     * 是否为身宫
     */
    private boolean shenGong;

    /**
     * 宫位阴阳属性
     */
    private PalaceAttribute attribute;

    /**
     * 五行属性
     */
    private FiveElements fiveElements;

    /**
     * 宫位关系映射
     */
    private Map<PalaceRelation, PalaceBO> relatedPalaces;

    private int yearlyFlow;
    private int majorLimit;
    private int minorLimit;

    private List<StarBO> stars = new ArrayList<>();

    private boolean currentMajorLimit;
    private boolean currentMinorLimit;
    private boolean currentYearlyFlow;

    public PalaceBO() {
        this.majorStars = new ArrayList<>();
        this.minorStars = new ArrayList<>();
        this.adjectiveStars = new ArrayList<>();
        this.mutagens = new ArrayList<>();
        this.stars = new ArrayList<>();
        this.relatedPalaces = new HashMap<>();
    }

    /**
     * 获取所有星耀
     */
    public List<StarBO> getAllStars() {
        List<StarBO> allStars = new ArrayList<>();
        allStars.addAll(majorStars);
        allStars.addAll(minorStars);
        allStars.addAll(adjectiveStars);
        return allStars;
    }

    /**
     * 获取地支
     */
    public EarthlyBranch getEarthlyBranch() {
        return EarthlyBranch.fromDescription(branch);
    }

    /**
     * 根据星耀类型获取星耀列表
     */
    public List<StarBO> getStarsByType(StarType type) {
        switch (type) {
            case MAJOR:
                return majorStars;
            case MINOR:
                return minorStars;
            case ADJECTIVE:
                return adjectiveStars;
            default:
                throw new IllegalArgumentException("Invalid star type: " + type);
        }
    }

    /**
     * 检查是否包含指定星耀
     */
    public boolean hasStar(String starName) {
        return getAllStars().stream()
                .anyMatch(star -> star.getName().equals(starName));
    }

    /**
     * 检查是否包含指定四化
     */
    public boolean hasMutagen(String mutagen) {
        return mutagens.contains(mutagen);
    }

    /**
     * 获取对宫
     */
    public Optional<PalaceBO> getOppositePalace() {
        if (relatedPalaces == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(relatedPalaces.get(PalaceRelation.OPPOSITE));
    }

    /**
     * 获取财帛位
     */
    public Optional<PalaceBO> getWealthPalace() {
        return Optional.ofNullable(relatedPalaces.get(PalaceRelation.WEALTH));
    }

    /**
     * 获取官禄位
     */
    public Optional<PalaceBO> getCareerPalace() {
        return Optional.ofNullable(relatedPalaces.get(PalaceRelation.CAREER));
    }

    /**
     * 获取三方四正宫位
     */
    public List<PalaceBO> getSurroundedPalaces() {
        return relatedPalaces.values().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 检查三方四正是否包含指定星耀
     */
    public boolean surroundedHasStar(String starName) {
        return getSurroundedPalaces().stream()
                .anyMatch(palace -> palace.hasStar(starName));
    }

    /**
     * 检查三方四正是否包含指定四化
     */
    public boolean surroundedHasMutagen(String mutagen) {
        return getSurroundedPalaces().stream()
                .anyMatch(palace -> palace.hasMutagen(mutagen));
    }

    /**
     * 设置所有星耀
     */
    public void setStars(List<StarBO> stars) {
        if (stars == null) {
            return;
        }

        majorStars.clear();
        minorStars.clear();
        adjectiveStars.clear();

        for (StarBO star : stars) {
            switch (star.getType()) {
                case MAJOR:
                    majorStars.add(star);
                    break;
                case MINOR:
                    minorStars.add(star);
                    break;
                case ADJECTIVE:
                    adjectiveStars.add(star);
                    break;
            }
        }
    }

    public List<PalaceBO> surroundedPalaces() {
        List<PalaceBO> palaces = new ArrayList<>();
        // TODO: 实现获取周围宫位的逻辑
        return palaces;
    }

    public boolean isCurrentYearlyFlow(int age) {
        return this.yearlyFlow != 0 && this.yearlyFlow == age;
    }

    public boolean isCurrentMajorLimit(int age) {
        return this.majorLimit != 0 && this.majorLimit <= age && age <= this.majorLimit;
    }

    public boolean isCurrentMinorLimit(int age) {
        return this.minorLimit != 0 && this.minorLimit <= age && age <= this.minorLimit;
    }


    public void addMajorStar(StarBO star) {
        stars.add(star);
        star.setPalace(this);
    }

    public void addMinorStar(StarBO star) {
        minorStars.add(star);
        star.setPalace(this);
    }

    public void addAdjectiveStar(StarBO star) {
        adjectiveStars.add(star);
        star.setPalace(this);
    }

    public void setEarthlyBranch(EarthlyBranch branch) {
        this.branch = branch.getDescription();
    }
}
