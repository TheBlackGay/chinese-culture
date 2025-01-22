package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.Brightness;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;
import lombok.Data;

/**
 * 星耀亮度信息
 */
@Data
public class StarBrightnessBO {

    /**
     * 星耀名称
     */
    private StarName starName;

    /**
     * 亮度(地支)
     */
    private Brightness brightness;

    /**
     * 检查是否具有指定亮度
     */
    public boolean hasBrightness(Brightness brightness) {

        return this.brightness == brightness;
    }

    /**
     * 获取亮度描述
     */
    public String getBrightnessDescription() {

        return brightness != null ? brightness.getDescription() : "";
    }

}
