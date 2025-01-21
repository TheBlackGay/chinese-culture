package com.chinese.culture.admin.core.iztro.data;

import lombok.Data;
import java.util.List;

/**
 * 星耀
 */
@Data
public class Star {
    /**
     * 星耀名称
     */
    private String name;
    
    /**
     * 所在宫位(1-12)
     */
    private int position;
    
    /**
     * 地支
     */
    private String branch;
    
    /**
     * 亮度(地支)
     */
    private String brightness;
    
    /**
     * 四化
     */
    private List<String> mutagens;

    /**
     * 设置位置
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 设置地支
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * 获取位置
     */
    public Integer getPosition() {
        return position;
    }
} 