package com.chinese.culture.admin.core.iztro.interpreter;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.calculator.StarCombinationCalculator;
import com.chinese.culture.admin.core.iztro.analyzer.PalaceAuspiciousnessAnalyzer;
import com.chinese.culture.admin.core.iztro.data.enums.StarName;
import com.chinese.culture.admin.core.iztro.data.enums.StarType;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 星耀组合解释系统
 */
@Component
public class StarCombinationInterpreter {

    /**
     * 解释宫位星耀组合
     *
     * @param palace 宫位
     * @return 组合解释
     */
    public static String interpretPalaceCombination(Palace palace) {
        StringBuilder interpretation = new StringBuilder();

        // 获取宫位名称
        interpretation.append(palace.getName()).append("宫：\n");

        // 获取宫位星耀
        List<Star> allStars = palace.getAllStars();
        if (allStars.isEmpty()) {
            interpretation.append("此宫无主要星耀。\n");
            return interpretation.toString();
        }

        // 列出所有星耀
        interpretation.append("入宫星耀：");
        for (Star star : allStars) {
            interpretation.append(star.getFullDescription()).append("、");
        }
        interpretation.setLength(interpretation.length() - 1);
        interpretation.append("\n");

        // 获取星耀组合效果
        String combinationEffect = StarCombinationCalculator.calculateCombinationEffect(palace);
        interpretation.append("组合效果：").append(combinationEffect).append("\n");

        // 获取宫位吉凶
        Map<String, Object> result = PalaceAuspiciousnessAnalyzer.judgeAuspiciousness(palace);
        int auspiciousness = (int) result.get("score");
        String level = (String) result.get("level");
        interpretation.append("吉凶评分：").append(auspiciousness)
                     .append("（").append(level).append("）\n");

        // 添加宫位解释
        interpretation.append("解释：\n").append(interpretPalaceSignificance(palace));

        return interpretation.toString();
    }

    /**
     * 解释宫位主要含义
     */
    private static String interpretPalaceSignificance(Palace palace) {
        StringBuilder significance = new StringBuilder();

        // 根据宫位类型添加基本解释
        switch (palace.getName()) {
            case "命宫":
                significance.append("命宫主人生格局、性格特质、个人发展。");
                break;
            case "兄弟":
                significance.append("兄弟宫主手足关系、同辈交往、竞争能力。");
                break;
            case "夫妻":
                significance.append("夫妻宫主婚姻状况、配偶特质、感情运势。");
                break;
            case "子女":
                significance.append("子女宫主子嗣、创造力、事业成果。");
                break;
            case "财帛":
                significance.append("财帛宫主财运、收入状况、理财能力。");
                break;
            case "疾厄":
                significance.append("疾厄宫主健康状况、困难挑战。");
                break;
            case "迁移":
                significance.append("迁移宫主行动力、变动、旅行。");
                break;
            case "交友":
                significance.append("交友宫主人际关系、社交圈、朋友缘分。");
                break;
            case "官禄":
                significance.append("官禄宫主事业发展、工作状况、成就地位。");
                break;
            case "田宅":
                significance.append("田宅宫主居所、不动产、生活环境。");
                break;
            case "福德":
                significance.append("福德宫主心性修养、福气运势、精神生活。");
                break;
            case "父母":
                significance.append("父母宫主长辈关系、家庭环境、成长背景。");
                break;
        }

        // 根据星耀组合补充具体解释
        List<Star> majorStars = palace.getMajorStars();
        if (!majorStars.isEmpty()) {
            significance.append("\n主星解释：");
            for (Star star : majorStars) {
                significance.append("\n- ").append(star.getName()).append("：")
                          .append(interpretMajorStar(star, palace.getName()));
            }
        }

        return significance.toString();
    }

    /**
     * 解释主星在宫位中的含义
     */
    private static String interpretMajorStar(Star star, String palaceName) {
        StarName starName = star.getName();
        switch (starName) {
            case ZIWEI:
                return "为帝星，主权威地位，在" + palaceName + "宫主掌权贵、尊荣。";
            case TIANJI:
                return "为谋星，主智慧机巧，在" + palaceName + "宫主谋略、才智。";
            case TAIYANG:
                return "为阳星，主光明显达，在" + palaceName + "宫主名声、地位。";
            case WUQU:
                return "为财星，主财富权力，在" + palaceName + "宫主财运、能力。";
            case TIANTONG:
                return "为福星，主和谐福德，在" + palaceName + "宫主和气、福分。";
            case TIANFU:
                return "为富星，主积累储藏，在" + palaceName + "宫主财库、储蓄。";
            case TAIYIN:
                return "为阴星，主阴柔内在，在" + palaceName + "宫主情感、直觉。";
            case TANLANG:
                return "为欲星，主进取开拓，在" + palaceName + "宫主欲望、冲劲。";
            case JUMEN:
                return "为口星，主言语交际，在" + palaceName + "宫主口才、人际。";
            case TIANXIANG:
                return "为相星，主仁慈福德，在" + palaceName + "宫主仁爱、声望。";
            case TIANLIANG:
                return "为梁星，主正直清高，在" + palaceName + "宫主品德、清誉。";
            case QISHA:
                return "为杀星，主权威刚强，在" + palaceName + "宫主权力、决断。";
            case POJUN:
                return "为破星，主变革创新，在" + palaceName + "宫主改革、突破。";
            case LIANZHEN:
                return "为廉星，主清廉正直，在" + palaceName + "宫主操守、品行。";
            case WENCHANG:
                return "为文星，主文章学问，在" + palaceName + "宫主学识、才华。";
            case WENQU:
                return "为艺星，主艺术才能，在" + palaceName + "宫主艺术、创作。";
            case ZUOFU:
                return "为辅星，主辅助扶持，在" + palaceName + "宫主助力、支持。";
            case YOUBI:
                return "为弼星，主襄助成就，在" + palaceName + "宫主协助、成全。";
            case HUAGAI:
                return "为盖星，主遮护庇荫，在" + palaceName + "宫主保护、遮盖。";
            case TIANYUE:
                return "为钺星，主权威威严，在" + palaceName + "宫主威望、权势。";
            case TIANXING:
                return "为刑星，主刚强果断，在" + palaceName + "宫主决断、变动。";
            case TIANRUI:
                return "为瑞星，主吉祥福瑞，在" + palaceName + "宫主福气、喜庆。";
            case TIANYING:
                return "为英星，主英明果断，在" + palaceName + "宫主才智、决策。";
            case TIANMA:
                return "为马星，主奔驰腾达，在" + palaceName + "宫主动力、进取。";
            case DIANJIN:
                return "为禄星，主官禄财富，在" + palaceName + "宫主禄位、财运。";
            case TIANKONG:
                return "为空星，主虚无空寂，在" + palaceName + "宫主清净、超脱。";
            case TIANJI:
                return "为机星，主机巧灵动，在" + palaceName + "宫主智慧、变通。";
            case DIANXIU:
                return "为秀星，主文秀雅致，在" + palaceName + "宫主才学、优雅。";
            default:
                return "在" + palaceName + "宫有特殊影响。";
        }
    }

    /**
     * 解释星耀组合
     * @param stars 星耀列表
     * @return 解释结果
     */
    public String interpret(List<Star> stars) {
        if (stars == null || stars.isEmpty()) {
            return "无星耀组合";
        }

        StringBuilder result = new StringBuilder();

        // 1. 分析主星组合
        List<Star> majorStars = stars.stream()
                .filter(star -> star.getType() == StarType.MAJOR)
                .collect(Collectors.toList());
        if (majorStars.size() >= 2) {
            result.append("主星组合：\n");
            for (int i = 0; i < majorStars.size(); i++) {
                for (int j = i + 1; j < majorStars.size(); j++) {
                    Star star1 = majorStars.get(i);
                    Star star2 = majorStars.get(j);
                    String effect = interpretMajorStarCombination(star1, star2);
                    if (effect != null) {
                        result.append(star1.getName().getDescription())
                              .append("与")
                              .append(star2.getName().getDescription())
                              .append("：")
                              .append(effect)
                              .append("\n");
                    }
                }
            }
        }

        // 2. 分析主星与辅星组合
        List<Star> minorStars = stars.stream()
                .filter(star -> star.getType() == StarType.MINOR)
                .collect(Collectors.toList());
        if (!majorStars.isEmpty() && !minorStars.isEmpty()) {
            result.append("\n主辅组合：\n");
            for (Star major : majorStars) {
                for (Star minor : minorStars) {
                    String effect = interpretMajorMinorCombination(major, minor);
                    if (effect != null) {
                        result.append(major.getName().getDescription())
                              .append("与")
                              .append(minor.getName().getDescription())
                              .append("：")
                              .append(effect)
                              .append("\n");
                    }
                }
            }
        }

        // 3. 分析四化组合
        Map<Mutagen, List<Star>> mutagenGroups = stars.stream()
                .filter(star -> !star.getMutagens().isEmpty())
                .flatMap(star -> star.getMutagens().stream()
                        .map(mutagen -> new AbstractMap.SimpleEntry<>(mutagen, star)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        if (!mutagenGroups.isEmpty()) {
            result.append("\n四化组合：\n");
            mutagenGroups.forEach((mutagen, mutatedStars) -> {
                if (mutatedStars.size() >= 2) {
                    result.append(mutagen.getDescription())
                          .append("化星组合：")
                          .append(interpretMutagenCombination(mutagen, mutatedStars))
                          .append("\n");
                }
            });
        }

        return result.toString();
    }

    /**
     * 解释主星组合
     */
    private String interpretMajorStarCombination(Star star1, Star star2) {
        StarName name1 = star1.getName();
        StarName name2 = star2.getName();

        // 紫微系组合
        if (name1 == StarName.ZIWEI || name2 == StarName.ZIWEI) {
            StarName other = name1 == StarName.ZIWEI ? name2 : name1;
            switch (other) {
                case TIANJI:
                    return "智慧超群，谋略出众";
                case TAIYANG:
                    return "光明正大，名声显赫";
                case WUQU:
                    return "权力财富，双重收获";
                case TIANFU:
                    return "富贵双全，储藏丰厚";
                case TAIYIN:
                    return "阴阳调和，内外兼修";
            }
        }

        // 天府系组合
        if (name1 == StarName.TIANFU || name2 == StarName.TIANFU) {
            StarName other = name1 == StarName.TIANFU ? name2 : name1;
            switch (other) {
                case TAIYIN:
                    return "富贵荣华，享受优渥";
                case TANLANG:
                    return "积累财富，开拓进取";
                case TIANXIANG:
                    return "仁德兼备，声望显著";
            }
        }

        // 文昌系组合
        if (name1 == StarName.WENCHANG || name2 == StarName.WENCHANG) {
            StarName other = name1 == StarName.WENCHANG ? name2 : name1;
            switch (other) {
                case WENQU:
                    return "才华横溢，文艺双全";
                case TIANJI:
                    return "学识渊博，智慧超群";
                case TAIYANG:
                    return "文采光华，名声显达";
            }
        }

        return null;
    }

    /**
     * 解释主星与辅星组合
     */
    private String interpretMajorMinorCombination(Star major, Star minor) {
        StarName majorName = major.getName();
        StarName minorName = minor.getName();

        // 紫微系辅星组合
        if (majorName == StarName.ZIWEI) {
            switch (minorName) {
                case ZUOFU:
                    return "得左辅之助，谋略得当";
                case YOUBI:
                    return "得右弼之助，决策明智";
                case HUAGAI:
                    return "得华盖庇护，品格高尚";
            }
        }

        // 天府系辅星组合
        if (majorName == StarName.TIANFU) {
            switch (minorName) {
                case TIANYUE:
                    return "得天钺相助，威望提升";
                case TIANXING:
                    return "得天刑相助，决断力强";
                case TIANMA:
                    return "得天马相助，进取有力";
            }
        }

        // 文昌系辅星组合
        if (majorName == StarName.WENCHANG) {
            switch (minorName) {
                case DIANXIU:
                    return "得点秀相助，才学优雅";
                case DIANJI:
                    return "得点机相助，智慧灵动";
            }
        }

        return null;
    }

    /**
     * 解释四化组合
     */
    private String interpretMutagenCombination(Mutagen mutagen, List<Star> stars) {
        StringBuilder effect = new StringBuilder();
        effect.append(stars.stream()
                .map(star -> star.getName().getDescription())
                .collect(Collectors.joining("、")));

        switch (mutagen) {
            case LUCKY:
                effect.append("，吉化加强，福气倍增");
                break;
            case POWER:
                effect.append("，权化叠加，权力显著");
                break;
            case SKILL:
                effect.append("，科化并存，才智超群");
                break;
            case WEAK:
                effect.append("，忌化重叠，需加谨慎");
                break;
        }

        return effect.toString();
    }
}
