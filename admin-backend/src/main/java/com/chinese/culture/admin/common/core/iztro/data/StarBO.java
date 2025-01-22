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
 * 星耀数据类
 */
@Data
@Builder
@AllArgsConstructor
public class StarBO {
    /**
     * 基础信息
     */
    private StarName name;            // 星耀名称
    private StarType type;            // 星耀类型
    private Scope scope;              // 作用范围（本命盘|大限盘|流年盘）
    private Integer position;         // 所在位置
    private EarthlyBranch branch;     // 所属地支
    private FiveElements element;     // 五行属性
    private StarAttribute attribute;  // 阴阳属性

    /**
     * 亮度信息
     */
    private Brightness brightness;    // 星耀亮度

    /**
     * 四化信息
     */
    @Builder.Default
    private List<Mutagen> mutagens = new ArrayList<>();  // 四化列表

    /**
     * 关系信息
     */
    private PalaceBO palace;         // 所在宫位
    @Builder.Default
    private List<StarBO> oppositeStars = new ArrayList<>();    // 对宫星耀
    @Builder.Default
    private List<StarBO> surroundingStars = new ArrayList<>(); // 同宫星耀

    public StarBO() {
        this.mutagens = new ArrayList<>();
        this.oppositeStars = new ArrayList<>();
        this.surroundingStars = new ArrayList<>();
    }

    public StarBO(StarName name) {
        this();
        this.name = name;
    }

    public StarBO(StarName name, StarType type) {
        this(name);
        this.type = type;
    }

    /**
     * 检查是否具有特定亮度
     */
    public boolean hasBrightness(Brightness brightness) {
        return this.brightness == brightness;
    }

    /**
     * 检查是否具有特定四化
     */
    public boolean hasMutagen(Mutagen mutagen) {
        return mutagens.contains(mutagen);
    }

    /**
     * 添加四化
     */
    public void addMutagen(Mutagen mutagen) {
        if (!hasMutagen(mutagen)) {
            mutagens.add(mutagen);
        }
    }

    /**
     * 获取所在宫位
     */
    public Optional<PalaceBO> getPalace() {
        return Optional.ofNullable(palace);
    }

    /**
     * 检查是否与另一个星耀同宫
     */
    public boolean isInSamePalace(StarBO other) {
        return this.position != null && this.position.equals(other.getPosition());
    }

    /**
     * 检查是否与另一个星耀对宫
     */
    public boolean isOpposite(StarBO other) {
        return oppositeStars.contains(other);
    }

    /**
     * 获取完整描述（包含亮度和四化）
     */
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder(name.getChinese());

        // 添加亮度
        if (brightness != null) {
            sb.append("(").append(brightness.getChinese()).append(")");
        }

        // 添加四化
        if (!mutagens.isEmpty()) {
            sb.append("[");
            for (int i = 0; i < mutagens.size(); i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(mutagens.get(i).getChinese());
            }
            sb.append("]");
        }

        return sb.toString();
    }

    /**
     * 创建星耀的简单构造方法
     */
    public static StarBO of(StarName name, StarType type, Scope scope) {
        return StarBO.builder()
                .name(name)
                .type(type)
                .scope(scope)
                .build();
    }

    /**
     * 创建带亮度的星耀构造方法
     */
    public static StarBO of(StarName name, StarType type, Scope scope, Brightness brightness) {
        return StarBO.builder()
                .name(name)
                .type(type)
                .scope(scope)
                .brightness(brightness)
                .build();
    }

    /**
     * 创建带四化的星耀构造方法
     */
    public static StarBO of(StarName name, StarType type, Scope scope, Mutagen mutagen) {
        StarBO star = StarBO.of(name, type, scope);
        star.addMutagen(mutagen);
        return star;
    }
}
