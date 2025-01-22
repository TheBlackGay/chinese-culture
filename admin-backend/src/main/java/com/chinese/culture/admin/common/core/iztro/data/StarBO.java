package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * 星耀
 */
@Data
public class StarBO {

    private StarBaseBO baseInfo;
    private StarBrightnessBO brightnessInfo;
    private StarMutagenBO mutagenInfo;
    private StarRelationBO relationInfo;
    private StarCombinationBO combinationInfo;

    public StarBO() {
        this.baseInfo = new StarBaseBO();
        this.brightnessInfo = new StarBrightnessBO();
        this.mutagenInfo = new StarMutagenBO();
        this.relationInfo = new StarRelationBO();
        this.combinationInfo = new StarCombinationBO();
    }

    public StarBO(StarName name) {
        this();
        this.baseInfo.setName(name);
        this.brightnessInfo.setStarName(name);
        this.mutagenInfo.setStarName(name);
        this.relationInfo.setStarName(name);
        this.combinationInfo.setStarName(name);
    }

    public StarBO(StarName name, StarType type) {
        this(name);
        this.baseInfo.setType(type);
    }

    public StarBO(StarName name, StarType type, Brightness brightness) {
        this(name, type);
        this.brightnessInfo.setBrightness(brightness);
    }

    // 委托方法
    public StarName getName() {
        return baseInfo.getName();
    }

    public void setName(StarName name) {
        baseInfo.setName(name);
    }

    public Integer getPosition() {
        return baseInfo.getPosition();
    }

    public void setPosition(Integer position) {
        baseInfo.setPosition(position);
        combinationInfo.setPosition(position);
    }

    public boolean hasBrightness(Brightness brightness) {
        return brightnessInfo.hasBrightness(brightness);
    }

    public void setBrightness(Brightness brightness) {
        brightnessInfo.setBrightness(brightness);
    }

    public boolean hasMutagen(Mutagen mutagen) {
        return mutagenInfo.hasMutagen(mutagen);
    }

    public void addMutagen(Mutagen mutagen) {
        mutagenInfo.addMutagen(mutagen);
    }

    public Optional<PalaceBO> getPalace() {
        return relationInfo.getPalace();
    }

    public List<StarBO> getOppositeStars() {
        return relationInfo.getOppositeStars();
    }

    public List<StarBO> getSurroundedStars() {
        return relationInfo.getSurroundedStars();
    }

    public boolean isInSamePalace(StarBO other) {
        return combinationInfo.isInSamePalace(other);
    }

    public boolean isOpposite(StarBO other) {
        return combinationInfo.isOpposite(other);
    }

    public boolean isTriple(StarBO other) {
        return combinationInfo.isTriple(other);
    }

    public boolean isSixHarmony(StarBO other) {
        return combinationInfo.isSixHarmony(other);
    }

    public String getCombinationEffect(StarBO other) {
        return combinationInfo.getCombinationEffect(other);
    }

    public void setPalace(PalaceBO palace) {
        relationInfo.setPalace(palace);
        combinationInfo.setPalace(palace);
    }

    /**
     * 获取完整描述（包含亮度和四化）
     */
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder(baseInfo.getName().getDescription());

        // 添加亮度
        String brightnessDesc = brightnessInfo.getBrightnessDescription();
        if (!brightnessDesc.isEmpty()) {
            sb.append("(").append(brightnessDesc).append(")");
        }

        // 添加四化
        String mutagenDesc = mutagenInfo.getMutagenDescription();
        if (!mutagenDesc.isEmpty()) {
            sb.append("[").append(mutagenDesc).append("]");
        }

        return sb.toString();
    }

    /**
     * 获取星耀类型
     */
    public StarType getType() {
        return baseInfo.getType();
    }

    /**
     * 设置星耀类型
     */
    public void setType(StarType type) {
        baseInfo.setType(type);
    }

    /**
     * 获取地支
     */
    public EarthlyBranch getBranch() {
        return baseInfo.getBranch();
    }

    /**
     * 设置地支
     */
    public void setBranch(EarthlyBranch branch) {
        baseInfo.setBranch(branch);
    }

    /**
     * 获取阴阳属性
     */
    public StarAttribute getAttribute() {
        return baseInfo.getAttribute();
    }

    /**
     * 设置阴阳属性
     */
    public void setAttribute(StarAttribute attribute) {
        baseInfo.setAttribute(attribute);
    }

    /**
     * 获取五行属性
     */
    public FiveElements getFiveElements() {
        return baseInfo.getFiveElements();
    }

    /**
     * 设置五行属性
     */
    public void setFiveElements(FiveElements fiveElements) {
        baseInfo.setFiveElements(fiveElements);
    }

    /**
     * 获取四化列表
     */
    public List<Mutagen> getMutagens() {
        return mutagenInfo.getMutagens();
    }

    /**
     * 设置四化列表
     */
    public void setMutagens(List<Mutagen> mutagens) {
        mutagenInfo.setMutagens(mutagens);
    }
}
