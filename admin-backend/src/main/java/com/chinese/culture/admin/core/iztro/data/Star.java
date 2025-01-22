package com.chinese.culture.admin.core.iztro.data;

import com.chinese.culture.admin.core.iztro.data.enums.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 星耀
 */
@Data
public class Star {

    /**
     * 星耀名称
     */
    private StarName name;

    /**
     * 所在宫位(1-12)
     */
    private int position;

    /**
     * 地支
     */
    private EarthlyBranch branch;

    /**
     * 亮度(地支)
     */
    private Brightness brightness;

    /**
     * 四化
     */
    private List<Mutagen> mutagens = new ArrayList<>();

    /**
     * 星耀类型
     */
    private StarType type;

    /**
     * 阴阳属性
     */
    private StarAttribute attribute;

    /**
     * 五行属性
     */
    private FiveElements fiveElements;

    /**
     * 所在宫位引用
     */
    private Palace palace;

    public Star() {
        this.mutagens = new ArrayList<>();
    }

    public Star(StarName name) {
        this.name = name;
        this.mutagens = new ArrayList<>();
    }

    public Star(StarName name, StarType type) {
        this.name = name;
        this.type = type;
        this.mutagens = new ArrayList<>();
    }

    public Star(StarName name, StarType type, Brightness brightness) {
        this.name = name;
        this.type = type;
        this.brightness = brightness;
        this.mutagens = new ArrayList<>();
    }

    /**
     * 检查是否具有指定亮度
     */
    public boolean hasBrightness(Brightness brightness) {
        return this.brightness == brightness;
    }

    /**
     * 检查是否具有指定四化
     */
    public boolean hasMutagen(Mutagen mutagen) {
        return mutagens.contains(mutagen);
    }

    /**
     * 获取所在宫位
     */
    public Optional<Palace> getPalace() {
        return Optional.ofNullable(palace);
    }

    /**
     * 获取对宫星耀
     */
    public List<Star> getOppositeStars() {
        if (palace == null) {
            return new ArrayList<>();
        }
        return palace.getOppositePalace()
                .map(Palace::getAllStars)
                .orElse(new ArrayList<>());
    }

    /**
     * 获取三方四正星耀
     */
    public List<Star> getSurroundedStars() {
        List<Star> stars = new ArrayList<>();
        palace.getSurroundedPalaces().forEach(p -> stars.addAll(p.getAllStars()));
        return stars;
    }

    /**
     * 检查是否与目标星耀同宫
     */
    public boolean isInSamePalace(Star other) {
        return this.position == other.position;
    }

    /**
     * 检查是否与目标星耀对宫
     */
    public boolean isOpposite(Star other) {
        return palace.getOppositePalace()
                .map(p -> p.hasStar(other.getName().getDescription()))
                .orElse(false);
    }

    /**
     * 检查是否与目标星耀三合
     * TODO: 实现三合逻辑
     */
    public boolean isTriple(Star other) {
        return false;
    }

    /**
     * 检查是否与目标星耀六合
     * TODO: 实现六合逻辑
     */
    public boolean isSixHarmony(Star other) {
        return false;
    }

    /**
     * 获取星耀组合效果
     * TODO: 实现组合效果判断逻辑
     */
    public String getCombinationEffect(Star other) {
        return "";
    }

    /**
     * 获取位置
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * 设置位置
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 添加四化
     */
    public void addMutagen(Mutagen mutagen) {
        if (!mutagens.contains(mutagen)) {
            mutagens.add(mutagen);
        }
    }

    /**
     * 获取四化描述
     */
    public String getMutagenDescription() {
        if (mutagens.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Mutagen mutagen : mutagens) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(mutagen.getDescription());
        }
        return sb.toString();
    }

    /**
     * 获取完整描述（包含亮度和四化）
     */
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder(name.getDescription());

        // 添加亮度
        if (brightness != null) {
            sb.append("(").append(brightness.getDescription()).append(")");
        }

        // 添加四化
        String mutagenDesc = getMutagenDescription();
        if (!mutagenDesc.isEmpty()) {
            sb.append("[").append(mutagenDesc).append("]");
        }

        return sb.toString();
    }
}
