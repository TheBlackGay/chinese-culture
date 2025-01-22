package com.chinese.culture.admin.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 中国传统历法配置
 */
@Configuration
@ConfigurationProperties(prefix = "chinese.culture")
@Data
public class ChineseCalendarConfig {
    // 紫微斗数配置
    private ZiweiConfig ziwei = new ZiweiConfig();

    // 农历配置
    private LunarConfig lunar = new LunarConfig();

    @Data
    public static class ZiweiConfig {
        // 默认语言
        private String defaultLanguage = "zh-CN";
        // 是否启用四化
        private boolean enableMutagen = true;
        // 是否启用长生十二神
        private boolean enableMutagen12 = true;
        // 是否启用运限
        private boolean enableHoroscope = true;
        // 运限计算方式：0-传统，1-年龄
        private int horoscopeMode = 0;
    }

    @Data
    public static class LunarConfig {
        // 是否启用节气
        private boolean enableSolarTerms = true;
        // 是否启用节日
        private boolean enableFestivals = true;
        // 是否启用吉凶
        private boolean enableLuck = true;
        // 是否启用八字
        private boolean enableBaZi = true;
        // 默认时区
        private String timezone = "Asia/Shanghai";
    }
}
