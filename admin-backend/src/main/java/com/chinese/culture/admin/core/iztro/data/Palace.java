package com.chinese.culture.admin.core.iztro.data;

import com.chinese.culture.admin.core.iztro.data.enums.FiveElements;
import com.chinese.culture.admin.core.iztro.data.enums.PalaceAttribute;
import com.chinese.culture.admin.core.iztro.data.enums.PalaceRelation;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

/**
 * 宫位
 */
@Data
public class Palace {
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
    private List<Star> majorStars = new ArrayList<>();
    
    /**
     * 辅星列表
     */
    private List<Star> minorStars = new ArrayList<>();
    
    /**
     * 杂耀列表
     */
    private List<Star> adjectiveStars = new ArrayList<>();
    
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
    private boolean ming;
    
    /**
     * 是否为身宫
     */
    private boolean body;
    
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
    private Map<PalaceRelation, Palace> relatedPalaces;

    private int yearlyFlow;
    private int majorLimit;
    private int minorLimit;

    private List<Star> stars = new ArrayList<>();

    private boolean currentMajorLimit;
    private boolean currentMinorLimit;
    private boolean currentYearlyFlow;

    /**
     * 获取所有星耀
     */
    public List<Star> getAllStars() {
        List<Star> allStars = new ArrayList<>();
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
    public List<Star> getStarsByType(StarType type) {
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
    public Optional<Palace> getOppositePalace() {
        return Optional.ofNullable(relatedPalaces.get(PalaceRelation.OPPOSITE));
    }

    /**
     * 获取财帛位
     */
    public Optional<Palace> getWealthPalace() {
        return Optional.ofNullable(relatedPalaces.get(PalaceRelation.WEALTH));
    }

    /**
     * 获取官禄位
     */
    public Optional<Palace> getCareerPalace() {
        return Optional.ofNullable(relatedPalaces.get(PalaceRelation.CAREER));
    }

    /**
     * 获取三方四正宫位
     */
    public List<Palace> getSurroundedPalaces() {
        return relatedPalaces.values().stream()
                .filter(palace -> palace != null)
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
    public void setStars(List<Star> stars) {
        if (stars == null) {
            return;
        }
        
        majorStars.clear();
        minorStars.clear();
        adjectiveStars.clear();
        
        for (Star star : stars) {
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

    public List<Palace> surroundedPalaces() {
        List<Palace> palaces = new ArrayList<>();
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

    public boolean isMing() {
        return ming;
    }

    public boolean isBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    public List<String> getMutagens() {
        return mutagens;
    }

    public List<Star> getMajorStars() {
        return majorStars;
    }

    public List<Star> getMinorStars() {
        return minorStars;
    }

    public List<Star> getAdjectiveStars() {
        return adjectiveStars;
    }

    public List<Star> getStars() {
        return stars;
    }

    public void addMajorStar(Star star) {
        stars.add(star);
        star.setPalace(this);
    }

    public void addMinorStar(Star star) {
        minorStars.add(star);
        star.setPalace(this);
    }

    public void addAdjectiveStar(Star star) {
        adjectiveStars.add(star);
        star.setPalace(this);
    }

    public void setCurrentMajorLimit(boolean currentMajorLimit) {
        this.currentMajorLimit = currentMajorLimit;
    }

    public void setCurrentMinorLimit(boolean currentMinorLimit) {
        this.currentMinorLimit = currentMinorLimit;
    }

    public void setCurrentYearlyFlow(boolean currentYearlyFlow) {
        this.currentYearlyFlow = currentYearlyFlow;
    }

    public void setEarthlyBranch(EarthlyBranch branch) {
        this.branch = branch.toString();
    }
} 