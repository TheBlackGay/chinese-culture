package com.chinese.culture.admin.common.core.iztro.interpreter;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.calculator.StarCombinationCalculator;
import com.chinese.culture.admin.common.core.iztro.analyzer.PalaceAuspiciousnessAnalyzer;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarName;
import com.chinese.culture.admin.common.core.iztro.data.enums.StarType;
import com.chinese.culture.admin.common.core.iztro.data.enums.Mutagen;
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
    public static String interpretPalaceCombination(PalaceBO palace) {
        StringBuilder interpretation = new StringBuilder();

        // 获取宫位名称
        interpretation.append(palace.getName()).append("宫：\n");

        // 获取宫位星耀
        List<StarBO> allStars = palace.getAllStars();
        if (allStars.isEmpty()) {
            interpretation.append("此宫无主要星耀。\n");
            return interpretation.toString();
        }

        // 列出所有星耀
        interpretation.append("入宫星耀：");
        for (StarBO star : allStars) {
            interpretation.append(star.getFullDescription()).append("、");
        }
        interpretation.setLength(interpretation.length() - 1);
        interpretation.append("\n");

        // 获取星耀组合效果
        String combinationEffect = StarCombinationCalculator.calculateCombinationEffect(palace);
        interpretation.append("组合效果：").append(combinationEffect).append("\n");

        // 获取宫位吉凶
        PalaceAuspiciousnessAnalyzer.PalaceAuspiciousnessResult result = PalaceAuspiciousnessAnalyzer.analyze(palace);
        int auspiciousness = result.getScore();
        String level = result.getLevel();
        interpretation.append("吉凶评分：").append(auspiciousness)
                     .append("（").append(level).append("）\n");

        // 添加宫位解释
        interpretation.append("解释：\n").append(interpretPalaceSignificance(palace));

        return interpretation.toString();
    }

    /**
     * 解释宫位主要含义
     */
    private static String interpretPalaceSignificance(PalaceBO palace) {
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
        List<StarBO> majorStars = palace.getMajorStars();
        if (!majorStars.isEmpty()) {
            significance.append("\n主星解释：");
            for (StarBO star : majorStars) {
                significance.append("\n- ").append(star.getName()).append("：")
                          .append(interpretMajorStar(star, palace.getName()));
            }
        }

        return significance.toString();
    }

    /**
     * 解释主星在宫位中的含义
     */
    private static String interpretMajorStar(StarBO star, String palaceName) {
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
            default:
                return "在" + palaceName + "宫有特殊影响。";
        }
    }

    /**
     * 解释星耀组合
     */
    public static String interpret(StarBO star, PalaceBO palace) {
        StringBuilder interpretation = new StringBuilder();

        // 添加星耀名称
        interpretation.append(star.getName().getDescription());

        // 添加亮度信息
        if (star.getBrightnessInfo().getBrightness() != null) {
            interpretation.append("（").append(star.getBrightnessInfo().getBrightness().getDescription()).append("）");
        }

        // 添加化气信息
        List<Mutagen> mutagens = star.getMutagenInfo().getMutagens();
        if (!mutagens.isEmpty()) {
            interpretation.append("，");
            for (Mutagen mutagen : mutagens) {
                interpretation.append(mutagen.getDescription()).append("、");
            }
            // 移除最后一个顿号
            interpretation.setLength(interpretation.length() - 1);
        }

        // 添加宫位信息
        interpretation.append("落").append(palace.getName()).append("宫");

        return interpretation.toString();
    }

    /**
     * 解释星耀组合
     * @param stars 星耀列表
     * @return 解释结果
     */
    public String interpret(List<StarBO> stars) {
        if (stars == null || stars.isEmpty()) {
            return "无星耀组合";
        }

        StringBuilder result = new StringBuilder();

        // 1. 分析主星组合
        List<StarBO> majorStars = stars.stream()
                .filter(star -> star.getType() == StarType.MAJOR)
                .collect(Collectors.toList());
        if (majorStars.size() >= 2) {
            result.append("主星组合：\n");
            for (int i = 0; i < majorStars.size(); i++) {
                for (int j = i + 1; j < majorStars.size(); j++) {
                    StarBO star1 = majorStars.get(i);
                    StarBO star2 = majorStars.get(j);
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
        List<StarBO> minorStars = stars.stream()
                .filter(star -> star.getType() == StarType.MINOR)
                .collect(Collectors.toList());
        if (!majorStars.isEmpty() && !minorStars.isEmpty()) {
            result.append("\n主辅组合：\n");
            for (StarBO major : majorStars) {
                for (StarBO minor : minorStars) {
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
        Map<Mutagen, List<StarBO>> mutagenGroups = stars.stream()
                .filter(star -> !star.getMutagenInfo().getMutagens().isEmpty())
                .flatMap(star -> star.getMutagenInfo().getMutagens().stream()
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
    private String interpretMajorStarCombination(StarBO star1, StarBO star2) {
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
                case TIANLIANG:
                    return "品德高尚，清正廉明";
                case QISHA:
                    return "权威刚强，决断有力";
                case POJUN:
                    return "变革创新，突破常规";
                case LIANZHEN:
                    return "清廉正直，品行端正";
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
                case TIANLIANG:
                    return "清正廉明，积累丰厚";
                case QISHA:
                    return "权势显赫，财富集聚";
                case POJUN:
                    return "变革创新，财运波动";
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
                case TIANLIANG:
                    return "品学兼优，清正有为";
                case LIANZHEN:
                    return "品行端正，学识优良";
            }
        }

        // 天梁系组合
        if (name1 == StarName.TIANLIANG || name2 == StarName.TIANLIANG) {
            StarName other = name1 == StarName.TIANLIANG ? name2 : name1;
            switch (other) {
                case TIANXIANG:
                    return "仁德双全，清正廉明";
                case QISHA:
                    return "刚正不阿，威严显赫";
                case POJUN:
                    return "正直无私，革故鼎新";
            }
        }

        // 七杀系组合
        if (name1 == StarName.QISHA || name2 == StarName.QISHA) {
            StarName other = name1 == StarName.QISHA ? name2 : name1;
            switch (other) {
                case POJUN:
                    return "刚强果断，破旧立新";
                case TIANXIANG:
                    return "威严仁德，刚柔并济";
            }
        }

        return null;
    }

    /**
     * 解释主星与辅星组合
     */
    private String interpretMajorMinorCombination(StarBO major, StarBO minor) {
        StarName majorName = major.getName();
        StarName minorName = minor.getName();

        // 紫微系辅星组合
        if (majorName == StarName.ZIWEI) {
            switch (minorName) {
                case WENCHANG:
                    return "得文昌之助，文采斐然";
                case WENQU:
                    return "得文曲之助，艺术超群";
                case LUCUN:
                    return "得禄存之助，官运亨通";
                case HUOXING:
                    return "得火星之助，动力十足";
                case LINGXING:
                    return "得铃星之助，变化灵动";
            }
        }

        // 天府系辅星组合
        if (majorName == StarName.TIANFU) {
            switch (minorName) {
                case WENCHANG:
                    return "得文昌相助，文运亨通";
                case WENQU:
                    return "得文曲相助，艺术优秀";
                case LUCUN:
                    return "得禄存相助，财运旺盛";
                case HUOXING:
                    return "得火星相助，事业进取";
                case LINGXING:
                    return "得铃星相助，机遇良多";
            }
        }

        // 天机系辅星组合
        if (majorName == StarName.TIANJI) {
            switch (minorName) {
                case WENCHANG:
                    return "得文昌相助，智慧出众";
                case WENQU:
                    return "得文曲相助，才艺双全";
                case LUCUN:
                    return "得禄存相助，谋略成功";
                case HUOXING:
                    return "得火星相助，决策果断";
                case LINGXING:
                    return "得铃星相助，机智灵活";
            }
        }

        // 太阳系辅星组合
        if (majorName == StarName.TAIYANG) {
            switch (minorName) {
                case WENCHANG:
                    return "得文昌相助，名声显达";
                case WENQU:
                    return "得文曲相助，艺术成就";
                case LUCUN:
                    return "得禄存相助，地位尊崇";
                case HUOXING:
                    return "得火星相助，威望显赫";
                case LINGXING:
                    return "得铃星相助，声名远播";
            }
        }

        // 武曲系辅星组合
        if (majorName == StarName.WUQU) {
            switch (minorName) {
                case WENCHANG:
                    return "得文昌相助，才能出众";
                case WENQU:
                    return "得文曲相助，艺术财运";
                case LUCUN:
                    return "得禄存相助，财运亨通";
                case HUOXING:
                    return "得火星相助，事业进取";
                case LINGXING:
                    return "得铃星相助，财运灵动";
            }
        }

        return null;
    }

    /**
     * 解释四化组合
     */
    private String interpretMutagenCombination(Mutagen mutagen, List<StarBO> stars) {
        StringBuilder effect = new StringBuilder();
        effect.append(stars.stream()
                .map(star -> star.getName().getDescription())
                .collect(Collectors.joining("、")));

        switch (mutagen) {
            case LU:
                effect.append("，吉化加强，福气倍增");
                break;
            case QUAN:
                effect.append("，权化叠加，权力显著");
                break;
            case KE:
                effect.append("，科化并存，才智超群");
                break;
            case JI:
                effect.append("，忌化重叠，需加谨慎");
                break;
        }

        return effect.toString();
    }
}
