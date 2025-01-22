package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀名称枚举
 */
public enum StarName {
    // 紫微系主星
    ZIWEI("紫微"),
    TIANJI("天机"),
    TAIYANG("太阳"),
    WUQU("武曲"),
    TIANTONG("天同"),
    LIANZHEN("廉贞"),

    // 天府系主星
    TIANFU("天府"),
    TAIYIN("太阴"),
    TANLANG("贪狼"),
    JUMEN("巨门"),
    TIANXIANG("天相"),
    TIANLIANG("天梁"),
    QISHA("七杀"),
    POJUN("破军"),

    // 十四辅星
    ZUOFU("左辅"),
    YOUBI("右弼"),
    TIANKUI("天魁"),
    TIANYUE("天钺"),
    WENCHANG("文昌"),
    WENQU("文曲"),
    DIKONG("地空"),
    DIJIE("地劫"),
    HUOXING("火星"),
    LINGXING("铃星"),
    TUOLUO("陀罗"),
    QINGYANG("擎羊"),
    TIANMA("天马"),
    LUCUN("禄存"),

    // 杂耀 - 交际类：增加社交属性，增加表达情感能力
    HONGLUAN("红鸾"),    // 阴水：增加亲切感，含蓄，端庄温柔
    TIANXI("天喜"),      // 阳水：增加亲和力，活泼好动，笑脸迎人
    TIANYAO("天姚"),     // 阴水：增加社交能力，古灵精怪，喜欢成为焦点
    XIANCHI("咸池"),     // 增加异性吸引力，爱干净，皮肤好

    // 杂耀 - 孤傲类：削弱社交能力，不易接近
    GUCHEN("孤辰"),      // 阳火：增加孤独感，喜欢独处，高冷难接近
    GUAXIU("寡宿"),      // 阴火：降低表达欲望，沉默寡言，多疑
    TIANXING("天刑"),    // 阳火：降低亲和力，严肃冷酷，不苟言笑

    // 杂耀 - 才艺类：增加才艺方面的天赋
    LONGCHI("龙池"),     // 阳水：增加硬核技能天赋，如结构学、建造学、书法
    FENGGE("凤阁"),      // 阳土：增加柔和技能天赋，如音乐、舞蹈、艺术设计
    TIANCAI("天才"),     // 阴木：增加智慧，聪明领悟力高，小天机
    TIANCHU("天厨"),     // 增加美食鉴赏能力，难以抵抗美食诱惑

    // 杂耀 - 矜贵类：增加贵气
    SANTAI("三台"),      // 阳土：喜欢排场，奢华，重视名声地位
    BAZUO("八座"),       // 阴土：与三台相配，喜欢享受生活
    ENGUANG("恩光"),     // 阳火：重义气，容易投桃报李，不喜欢欠人情
    TIANGUI("天贵"),     // 阳土：小贵人星，乐于助人，略带傲气
    TIANGUAN("天官"),    // 阳土：追求权力，有架子

    // 杂耀 - 精神类：影响内在气质
    TIANKU("天哭"),      // 阳金：增加悲情气质，容易消极
    TIANXU("天虚"),      // 阴土：容易精神内耗，忧郁，纠结
    POSUI("破碎"),       // 阴火：博而不精，爱好广泛但不深入
    FEILIAN("蜚廉"),     // 阳火：充满好奇心，表达欲强，小巨门
    HUAGAI("华盖"),      // 对神秘事物感兴趣，有哲学天赋
    YINSHA("阴煞"),      // 忧郁阴沉，不善表达感情

    // 杂耀 - 幸运类：代表好运
    JIEXING("解神"),     // 解决问题能力强，乐天，善于开导他人
    TIANDE("天德"),      // 稳重，处事得体，悲天悯人
    YUEDE("月德"),       // 善良温和，包容性强，有奉献精神
    TIANFUYI("天福"),      // 阳土：乐天积极，小天同，喜欢平顺

    // 杂耀 - 健康类：影响健康状况
    TIANSHOU("天寿"),    // 阳土：沉稳，处事不惊，情绪稳定
    TIANSHANG("天伤"),   // 阳水：固定出现在仆役宫
    TIANSHI("天使"),     // 阴水：固定出现在疾厄宫
    TIANYUEER("天月"),     // 病秧秧感觉，虚弱感

    // 杂耀 - 封赏类：意外得到
    TIANWU("天巫"),      // 增加第六感，容易接触神秘学
    TAIFU("台辅"),       // 阳土：小左辅，提供精神支持
    FENGGAO("封诰"),     // 阴土：小右弼，提供物质帮助

    // 杂耀 - 思想类：注重精神追求
    TIANKONG("天空"),    // 代表突发转折和变化
    XUNKONG("旬空"),     // 代表潜在挑战和未完成
    JIEKONG("截空"),     // 代表需要克服的阻碍
    KONGWANG("空亡"),    // 意志消沉，易产生虚无感

    // 四化
    KE("科"),
    QUAN("权"),
    JI("忌"),
    LU("禄");

    private final String description;

    StarName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StarName fromDescription(String description) {
        for (StarName starName : values()) {
            if (starName.description.equals(description)) {
                return starName;
            }
        }
        throw new IllegalArgumentException("Invalid star name description: " + description);
    }
}
