package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀名称枚举
 */
public enum StarName {
    // 主星
    ZI_WEI_MAJ("紫微"),
    TIAN_JI_MAJ("天机"),
    TAI_YANG_MAJ("太阳"),
    WU_QU_MAJ("武曲"),
    TIAN_TONG_MAJ("天同"),
    LIAN_ZHEN_MAJ("廉贞"),
    TIAN_FU_MAJ("天府"),
    TAI_YIN_MAJ("太阴"),
    TAN_LANG_MAJ("贪狼"),
    JU_MEN_MAJ("巨门"),
    TIAN_XIANG_MAJ("天相"),
    TIAN_LIANG_MAJ("天梁"),
    QI_SHA_MAJ("七杀"),
    PO_JUN_MAJ("破军"),

    // 辅星
    ZUO_FU_MIN("左辅"),
    YOU_BI_MIN("右弼"),
    WEN_CHANG_MIN("文昌"),
    WEN_QU_MIN("文曲"),
    LU_CUN_MIN("禄存"),
    TIAN_MA_MIN("天马"),
    QING_YANG_MIN("擎羊"),
    TUO_LUO_MIN("陀罗"),
    HUO_XING_MIN("火星"),
    LING_XING_MIN("铃星"),
    TIAN_KUI_MIN("天魁"),
    TIAN_YUE_MIN("天钺"),
    DI_KONG_MIN("地空"),
    DI_JIE_MIN("地劫"),

    // 杂耀
    TIAN_KONG("天空"),
    TIAN_XING("天刑"),
    TIAN_YAO("天姚"),
    JIE_SHEN("解神"),
    YIN_SHA("阴煞"),
    TIAN_XI("天喜"),
    TIAN_GUAN("天官"),
    TIAN_FU("天福"),
    TIAN_KU("天哭"),
    TIAN_XU("天虚"),
    LONG_CHI("龙池"),
    FENG_GE("凤阁"),
    HONG_LUAN("红鸾"),
    GU_CHEN("孤辰"),
    GUA_SU("寡宿"),
    FEI_LIAN("飞廉"),
    PO_SUI("破碎"),
    TAI_FU("台辅"),
    FENG_GAO("封诰"),
    TIAN_WU("天巫"),
    TIAN_YUE("天月"),
    SAN_TAI("三台"),
    BA_ZUO("八座"),
    EN_GUANG("恩光"),
    TIAN_GUI("天贵"),
    TIAN_CAI("天才"),
    TIAN_SHOU("天寿"),
    JIE_KONG("截空"),
    XUN_ZHONG("旬中"),
    XUN_KONG("旬空"),
    KONG_WANG("空亡"),
    JIE_LU("截路"),
    YUE_DE("月德"),
    TIAN_SHANG("天伤"),
    TIAN_SHI("天使"),
    TIAN_CHU("天厨"),

    // 长生十二神
    CHANG_SHENG("长生"),
    MU_YU("沐浴"),
    GUAN_DAI("冠带"),
    LIN_GUAN("临官"),
    DI_WANG("帝旺"),
    SHUAI("衰"),
    BING("病"),
    SI("死"),
    MU("墓"),
    JUE("绝"),
    TAI("胎"),
    YANG("养"),

    // 博士十二神
    BO_SHI("博士"),
    LI_SHI("力士"),
    QING_LONG("青龙"),
    XIAO_HAO("小耗"),
    JIANG_JUN("将军"),
    ZHOU_SHU("奏书"),
    FA_LIAN("飞廉"),
    XI_SHEN("喜神"),
    BING_FU("病符"),
    DA_HAO("大耗"),
    FU_BING("伏兵"),
    GUAN_FU("官府"),

    // 流年神煞
    SUI_JIAN("岁建"),
    HUI_QI("晦气"),
    SANG_MEN("丧门"),
    GUAN_SUO("贯索"),
    GUAN_FU2("官符"),
    LONG_DE("龙德"),
    BAI_HU("白虎"),
    TIAN_DE("天德"),
    DIAO_KE("吊客"),
    JIANG_XING("将星"),
    PAN_AN("攀鞍"),
    SUI_YI("岁驿"),
    XI_SHEN2("息神"),
    HUA_GAI("华盖"),
    JIE_SHA("劫煞"),
    ZHAI_SHA("灾煞"),
    TIAN_SHA("天煞"),
    ZHI_BEI("指背"),
    XIAN_CHI("咸池"),
    YUE_SHA("月煞"),
    WANG_SHEN("亡神"),

    // 大限星耀
    YUN_KUI("运魁"),
    YUN_YUE("运钺"),
    YUN_CHANG("运昌"),
    YUN_QU("运曲"),
    YUN_LUAN("运鸾"),
    YUN_XI("运喜"),
    YUN_LU("运禄"),
    YUN_YANG("运羊"),
    YUN_TUO("运陀"),
    YUN_MA("运马"),

    // 流年星耀
    LIU_KUI("流魁"),
    LIU_YUE("流钺"),
    LIU_CHANG("流昌"),
    LIU_QU("流曲"),
    LIU_LUAN("流鸾"),
    LIU_XI("流喜"),
    LIU_LU("流禄"),
    LIU_YANG("流羊"),
    LIU_TUO("流陀"),
    LIU_MA("流马"),
    NIAN_JIE("年解");

    private final String chinese;

    StarName(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 