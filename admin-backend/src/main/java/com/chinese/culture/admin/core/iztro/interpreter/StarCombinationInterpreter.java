package com.chinese.culture.admin.core.iztro.interpreter;

import com.chinese.culture.admin.core.iztro.data.Palace;
import com.chinese.culture.admin.core.iztro.data.Star;
import com.chinese.culture.admin.core.iztro.calculator.StarCombinationCalculator;
import com.chinese.culture.admin.core.iztro.calculator.PalaceAuspiciousnessCalculator;
import org.springframework.stereotype.Component;

import java.util.*;

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
        int auspiciousness = PalaceAuspiciousnessCalculator.calculateAuspiciousness(palace);
        String level = PalaceAuspiciousnessCalculator.getAuspiciousnessLevel(auspiciousness);
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
        switch (star.getName()) {
            case "紫微":
                return "为帝星，主权威地位，在" + palaceName + "宫主掌权贵、尊荣。";
            case "天机":
                return "为谋星，主智慧机巧，在" + palaceName + "宫主谋略、才智。";
            case "太阳":
                return "为阳星，主光明显达，在" + palaceName + "宫主名声、地位。";
            case "武曲":
                return "为财星，主财富权力，在" + palaceName + "宫主财运、能力。";
            case "天同":
                return "为福星，主和谐福德，在" + palaceName + "宫主和气、福分。";
            case "天府":
                return "为富星，主积累储藏，在" + palaceName + "宫主财库、储蓄。";
            case "太阴":
                return "为阴星，主阴柔内在，在" + palaceName + "宫主情感、直觉。";
            case "贪狼":
                return "为欲星，主进取开拓，在" + palaceName + "宫主欲望、冲劲。";
            case "巨门":
                return "为口星，主言语交际，在" + palaceName + "宫主口才、人际。";
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
        // TODO: 实现星耀组合的解释逻辑
        return result.toString();
    }
} 