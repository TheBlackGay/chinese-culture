package com.chinese.culture.admin.common.constant;

/**
 * 中国传统历法常量
 */
public class ChineseCalendarConstants {
    // 天干
    public static final String[] HEAVENLY_STEMS = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    
    // 地支
    public static final String[] EARTHLY_BRANCHES = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    
    // 生肖
    public static final String[] ZODIAC = {"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    
    // 二十四节气
    public static final String[] SOLAR_TERMS = {
        "小寒", "大寒", "立春", "雨水", "惊蛰", "春分",
        "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
        "小暑", "大暑", "立秋", "处暑", "白露", "秋分",
        "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"
    };
    
    // 宫位
    public static final String[] PALACES = {
        "命宫", "兄弟", "夫妻", "子女",
        "财帛", "疾厄", "迁移", "交友",
        "官禄", "田宅", "福德", "父母"
    };
    
    // 主星
    public static final String[] MAJOR_STARS = {
        "紫微", "天机", "太阳", "武曲",
        "天同", "廉贞", "天府", "太阴",
        "贪狼", "巨门", "天相", "天梁",
        "七杀", "破军"
    };

    // 四化
    public static final String[] MUTAGENS = {"化禄", "化权", "化科", "化忌"};

    // 星耀亮度
    public static final String[] BRIGHTNESS = {"庙", "旺", "得地", "平", "陷"};

    private ChineseCalendarConstants() {}
} 