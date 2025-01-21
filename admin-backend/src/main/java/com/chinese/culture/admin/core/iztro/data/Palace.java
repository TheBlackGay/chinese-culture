package com.chinese.culture.admin.core.iztro.data;

import lombok.Data;
import java.util.List;

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
     * 星耀列表
     */
    private List<Star> stars;
    
    /**
     * 四化列表
     */
    private List<String> mutagens;
    
    /**
     * 长生十二神
     */
    private String mutagen12;
    
    /**
     * 是否为命宫
     */
    private boolean isMing;
    
    /**
     * 是否为身宫
     */
    private boolean isBody;

    /**
     * 获取地支
     */
    public String getBranch() {
        return branch;
    }

    /**
     * 设置地支
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * 检查是否包含指定星耀
     */
    public boolean hasStar(String starName) {
        return stars.stream()
                .anyMatch(s -> starName.equals(s.getName()));
    }

    /**
     * 检查是否包含指定四化
     */
    public boolean hasMutagen(String mutagen) {
        return mutagens.contains(mutagen);
    }

    /**
     * 获取环绕宫位
     */
    public List<Palace> surroundedPalaces() {
        // TODO: 实现获取环绕宫位的逻辑
        return null;
    }
} 