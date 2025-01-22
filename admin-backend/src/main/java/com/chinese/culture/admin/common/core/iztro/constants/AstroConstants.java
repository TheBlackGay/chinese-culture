package com.chinese.culture.admin.common.core.iztro.constants;

import java.util.*;

/**
 * 紫微斗数常量类
 */
public class AstroConstants {

    // 主星
    public static final List<String> MAJOR_STARS = Arrays.asList(
        "紫微", "天机", "太阳", "武曲", "天同", "天府", "太阴", "贪狼",
        "巨门", "天相", "天梁", "七杀", "破军"
    );

    // 辅星
    public static final List<String> MINOR_STARS = Arrays.asList(
        "文昌", "文曲", "左辅", "右弼", "天魁", "天钺", "禄存", "天马",
        "擎羊", "陀罗"
    );

    // 杂耀
    public static final List<String> ADJECTIVE_STARS = Arrays.asList(
        "火星", "铃星", "地空", "地劫", "天刑", "天姚", "天虚", "天哭",
        "天空", "孤辰", "寡宿", "红鸾", "天喜"
    );

    // 吉星
    public static final List<String> LUCKY_STARS = Arrays.asList(
        "紫微", "天机", "太阳", "武曲", "天同", "天府", "太阴", "贪狼",
        "巨门", "天相", "天梁", "七杀", "破军", "文昌", "文曲", "左辅",
        "右弼", "天魁", "天钺", "禄存", "天马"
    );

    // 凶星
    public static final List<String> UNLUCKY_STARS = Arrays.asList(
        "擎羊", "陀罗", "火星", "铃星", "地空", "地劫", "天刑", "天姚",
        "天虚", "天哭", "天空", "孤辰", "寡宿"
    );

    // 权重
    public static final int MAJOR_STAR_WEIGHT = 3;
    public static final int MINOR_STAR_WEIGHT = 2;
    public static final int ADJECTIVE_STAR_WEIGHT = 1;

    // 分数
    public static final int BASE_SCORE = 60;
    public static final int MIN_SCORE = 0;
    public static final int MAX_SCORE = 100;

    // 加分项
    public static final int BRIGHT_BONUS = 15;
    public static final int NORMAL_BONUS = 10;
    public static final int WEAK_BONUS = 5;
    public static final int LUCKY_MUTAGEN_BONUS = 15;
    public static final int POWER_SKILL_MUTAGEN_BONUS = 10;

    // 减分项
    public static final int DEAD_PENALTY = -10;
    public static final int WEAK_MUTAGEN_PENALTY = -10;

    // 放大系数
    public static final int SCORE_MULTIPLIER = 5;

    // 格局等级分数线
    public static final int GREAT_AUSPICIOUS_THRESHOLD = 90;
    public static final int AUSPICIOUS_THRESHOLD = 80;
    public static final int MEDIUM_AUSPICIOUS_THRESHOLD = 70;
    public static final int MEDIUM_THRESHOLD = 60;
    public static final int MEDIUM_INAUSPICIOUS_THRESHOLD = 50;
    public static final int INAUSPICIOUS_THRESHOLD = 40;

    private AstroConstants() {
        // 私有构造函数防止实例化
    }
}
