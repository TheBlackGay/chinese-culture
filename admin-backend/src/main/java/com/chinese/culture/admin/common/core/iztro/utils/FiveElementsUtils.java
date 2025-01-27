package com.chinese.culture.admin.common.core.iztro.utils;

import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElements;
import com.chinese.culture.admin.common.core.iztro.data.enums.FiveElementsClass;
import com.chinese.culture.admin.common.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import lombok.experimental.UtilityClass;

/**
 * 五行计算工具类
 */
@UtilityClass
public class FiveElementsUtils {

    /**
     * 获取五行局
     *
     * 纳音五行计算取数口诀：
     * - 甲乙丙丁一到五，子丑午未一来数，
     * - 寅卯申酉二上走，辰巳戌亥三为足。
     * - 干支相加多减五，五行木金水火土。
     *
     * 注解：
     * 1、五行取数：木1 金2 水3 火4 土5
     *
     * 天干取数：
     * - 甲乙 ——> 1
     * - 丙丁 ——> 2
     * - 戊己 ——> 3
     * - 庚辛 ——> 4
     * - 壬癸 ——> 5
     *
     * 地支取数：
     * - 子午丑未 ——> 1
     * - 寅申卯酉 ——> 2
     * - 辰戌巳亥 ——> 3
     *
     * 计算方法：
     * 干支数相加，超过5者减去5，以差论之。
     * - 若差为1则五行属木(三局)
     * - 若差为2则五行属金(四局)
     * - 若差为3则五行属水(二局)
     * - 若差为4则五行属火(六局)
     * - 若差为5则五行属土(五局)
     *
     * @param heavenlyStem 天干
     * @param earthlyBranch 地支
     * @return 五行局
     */
    public FiveElementsClass getFiveElementsClass(String heavenlyStem, String earthlyBranch) {
        // 计算天干数
        int stemNumber;
        switch (heavenlyStem) {
            case "甲":
            case "乙":
                stemNumber = 1;
                break;
            case "丙":
            case "丁":
                stemNumber = 2;
                break;
            case "戊":
            case "己":
                stemNumber = 3;
                break;
            case "庚":
            case "辛":
                stemNumber = 4;
                break;
            case "壬":
            case "癸":
                stemNumber = 5;
                break;
            default:
                throw new IllegalArgumentException("无效的天干：" + heavenlyStem);
        }

        // 计算地支数
        int branchNumber;
        switch (earthlyBranch) {
            case "子":
            case "午":
            case "丑":
            case "未":
                branchNumber = 1;
                break;
            case "寅":
            case "申":
            case "卯":
            case "酉":
                branchNumber = 2;
                break;
            case "辰":
            case "戌":
            case "巳":
            case "亥":
                branchNumber = 3;
                break;
            default:
                throw new IllegalArgumentException("无效的地支：" + earthlyBranch);
        }

        // 计算和数
        int sum = stemNumber + branchNumber;
        // 如果和数大于5，则减去5
        while (sum > 5) {
            sum -= 5;
        }

        // 根据差数确定五行局
        switch (sum) {
            case 1:
                return FiveElementsClass.WOOD;   // 木三局
            case 2:
                return FiveElementsClass.METAL;  // 金四局
            case 3:
                return FiveElementsClass.WATER;  // 水二局
            case 4:
                return FiveElementsClass.FIRE;   // 火六局
            case 5:
                return FiveElementsClass.EARTH;  // 土五局
            default:
                throw new IllegalStateException("无效的五行局计算结果：" + sum);
        }
    }

    /**
     * 获取地支五行
     * 寅、卯：属木。寅为阳木，代表东北方，亦代表春季，藏有甲木、丙火、戊土，在时辰上指凌晨三点至五点，对应生肖虎，象征勇气和力量；卯为阴木，代表正东方，亦代表春季，属纯阳木，在时辰上指早晨五点至七点，对应生肖兔，代表着清新和活力。
     * 巳、午：属火。巳为阴火，代表东南方，亦代表夏季，藏有阴火、阴土、阳金，在时辰上指上午九点至十一点，对应生肖蛇，代表着智慧和变化；午为阳火，代表南方，亦代表夏季，属纯阳火，在时辰上指中午十一点至下午一点，对应生肖马，象征热情和自由。
     * 申、酉：属金。申为阳金，代表西南方，亦代表秋季，藏有阳金、阳水、阴土，在时辰上指下午三点至五点，对应生肖猴，象征机智和灵动；酉为阴金，代表正西方，亦代表秋季，属纯阴金，在时辰上指下午五点至七点，对应生肖鸡，代表着美丽和敏锐。
     * 亥、子：属水。亥为阴水，代表西北方，亦代表冬季，在时辰上指晚上九点至十一点，对应生肖猪，代表着诚实和宽容；子为阳水，代表正北方，亦代表冬季，藏有癸水，在时辰上指夜间十一至凌晨一点，对应生肖鼠，象征智慧和机敏。
     * 辰、戌、丑、未：属土。辰为阳土，代表东南方，亦代表四季或中央，藏有阳土、阳木、阳水，在时辰上指早上七点至九点，对应生肖龙，象征威严和神秘；戌为阳土，代表西北方，亦代表四季或中央，藏有阳土、阳火、阴金，在时辰上指晚上七点至九点，对应生肖狗，象征忠诚和警觉；丑为阴土，代表东北方，亦代表四季或中央，藏有癸水、己土、辛金，在时辰上指凌晨一点至三点，对应生肖牛，代表着勤劳和耐力；未为阴土，代表西南方，亦代表四季或中央，藏有阴土、阴火、阳木，在时辰上指下午一点至三点，对应生肖羊，代表着平和与慈爱。
     *
     * @param earthlyBranch 地支
     * @return 五行
     */
    public static FiveElements getEarthlyBranchFiveElements(EarthlyBranch earthlyBranch) {
        switch (earthlyBranch) {
            case ZI:
            case HAI:
                return FiveElements.WATER;
            case YIN:
            case MAO:
                return FiveElements.WOOD;
            case SI:
            case WU:
                return FiveElements.FIRE;
            case SHEN:
            case YOU:
                return FiveElements.METAL;
            case CHOU:
            case CHEN:
            case XU:
            case WEI:
                return FiveElements.EARTH;
            default:
                throw new IllegalArgumentException("Invalid earthly branch");
        }
    }

    /**
     * 获取天干五行
     * @param heavenlyStem 天干
     * @return 五行
     */
    public static FiveElements getHeavenlyStemFiveElements(HeavenlyStem heavenlyStem) {
        switch (heavenlyStem) {
            case JIA:
            case YI:
                return FiveElements.WOOD;
            case BING:
            case DING:
                return FiveElements.FIRE;
            case WU:
            case JI:
                return FiveElements.EARTH;
            case GENG:
            case XIN:
                return FiveElements.METAL;
            case REN:
            case GUI:
                return FiveElements.WATER;
            default:
                throw new IllegalArgumentException("Invalid heavenly stem");
        }
    }

    /**
     * 判断两个五行是否相生
     * 木生火，火生土，土生金，金生水，水生木
     * @param source 源五行
     * @param target 目标五行
     * @return 是否相生
     */
    public static boolean isReinforce(FiveElements source, FiveElements target) {
        switch (source) {
            case WOOD:
                return target == FiveElements.FIRE;
            case FIRE:
                return target == FiveElements.EARTH;
            case EARTH:
                return target == FiveElements.METAL;
            case METAL:
                return target == FiveElements.WATER;
            case WATER:
                return target == FiveElements.WOOD;
            default:
                return false;
        }
    }

    /**
     * 判断两个五行是否相克
     * 木克土，土克水，水克火，火克金，金克木
     * @param source 源五行
     * @param target 目标五行
     * @return 是否相克
     */
    public static boolean isRestrain(FiveElements source, FiveElements target) {
        switch (source) {
            case WOOD:
                return target == FiveElements.EARTH;
            case EARTH:
                return target == FiveElements.WATER;
            case WATER:
                return target == FiveElements.FIRE;
            case FIRE:
                return target == FiveElements.METAL;
            case METAL:
                return target == FiveElements.WOOD;
            default:
                return false;
        }
    }
}
