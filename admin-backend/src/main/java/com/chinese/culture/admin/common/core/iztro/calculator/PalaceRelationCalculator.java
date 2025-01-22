package com.chinese.culture.admin.common.core.iztro.calculator;

import com.chinese.culture.admin.common.core.iztro.data.PalaceBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.common.core.iztro.data.enums.PalaceRelationType;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 三方四正关系判断器
 */
@Slf4j
public class PalaceRelationCalculator {

    // 三合组合
    private static final List<List<String>> TRINE_COMBINATIONS = Arrays.asList(
        Arrays.asList("寅", "午", "戌"),
        Arrays.asList("申", "子", "辰"),
        Arrays.asList("巳", "酉", "丑"),
        Arrays.asList("亥", "卯", "未")
    );

    // 六合组合
    private static final List<List<String>> SEXTILE_COMBINATIONS = Arrays.asList(
        Arrays.asList("子", "丑"),
        Arrays.asList("寅", "亥"),
        Arrays.asList("卯", "戌"),
        Arrays.asList("辰", "酉"),
        Arrays.asList("巳", "申"),
        Arrays.asList("午", "未")
    );

    /**
     * 获取宫位的三方关系宫位
     * 三合局：地支相隔四个位置形成的三个宫位
     *
     * @param palace 目标宫位
     * @param allPalaces 所有宫位列表
     * @return 三合宫位列表
     */
    public static List<PalaceBO> getTrineRelations(PalaceBO palace, List<PalaceBO> allPalaces) {
        List<PalaceBO> trineRelations = new ArrayList<>();
        EarthlyBranch branch = palace.getEarthlyBranch();

        // 获取三合地支
        EarthlyBranch firstTrine = branch.getOffset(4);  // 第一个三合位
        EarthlyBranch secondTrine = branch.getOffset(8); // 第二个三合位

        // 查找对应宫位
        for (PalaceBO p : allPalaces) {
            if (p.getEarthlyBranch() == firstTrine || p.getEarthlyBranch() == secondTrine) {
                trineRelations.add(p);
            }
        }

        return trineRelations;
    }

    /**
     * 获取宫位的四正关系宫位
     * 四正：对宫、三合宫、六合宫
     *
     * @param palace 目标宫位
     * @param allPalaces 所有宫位列表
     * @return 四正宫位列表
     */
    public static List<PalaceBO> getSquareRelations(PalaceBO palace, List<PalaceBO> allPalaces) {
        List<PalaceBO> squareRelations = new ArrayList<>();
        EarthlyBranch branch = palace.getEarthlyBranch();

        // 获取四正地支
        EarthlyBranch opposite = branch.getOffset(6);  // 对宫
        EarthlyBranch trine = branch.getOffset(4);     // 三合宫
        EarthlyBranch harmony = branch.getOffset(2);   // 六合宫

        // 查找对应宫位
        for (PalaceBO p : allPalaces) {
            EarthlyBranch pBranch = p.getEarthlyBranch();
            if (pBranch == opposite || pBranch == trine || pBranch == harmony) {
                squareRelations.add(p);
            }
        }

        return squareRelations;
    }

    /**
     * 获取宫位的对宫
     *
     * @param palace 目标宫位
     * @param allPalaces 所有宫位列表
     * @return 对宫位置
     */
    public static PalaceBO getOppositePalace(PalaceBO palace, List<PalaceBO> allPalaces) {
        EarthlyBranch oppositeBranch = palace.getEarthlyBranch().getOffset(6);

        return allPalaces.stream()
            .filter(p -> p.getEarthlyBranch() == oppositeBranch)
            .findFirst()
            .orElse(null);
    }

    /**
     * 判断两个宫位的关系类型
     *
     * @param palace1 宫位1
     * @param palace2 宫位2
     * @return 关系类型
     */
    public static PalaceRelationType getRelationType(PalaceBO palace1, PalaceBO palace2) {
        EarthlyBranch branch1 = palace1.getEarthlyBranch();
        EarthlyBranch branch2 = palace2.getEarthlyBranch();

        // 获取地支描述
        String branchName1 = branch1.getDescription();
        String branchName2 = branch2.getDescription();

        // 检查是否为三合
        if (TRINE_COMBINATIONS.stream()
                .anyMatch(combo -> combo.contains(branchName1) && combo.contains(branchName2))) {
            return PalaceRelationType.TRINE;
        }

        // 检查是否为六合
        if (SEXTILE_COMBINATIONS.stream()
                .anyMatch(combo -> combo.contains(branchName1) && combo.contains(branchName2))) {
            return PalaceRelationType.SEXTILE;
        }

        if (branch1.equals(branch2.getOpposite())) {
            return PalaceRelationType.OPPOSITE;
        }

        if (branch1.getSixHarmony().equals(branch2)) {
            return PalaceRelationType.SEXTILE;
        }

        return PalaceRelationType.NONE;
    }

    /**
     * 获取宫位关系的效果描述
     *
     * @param palace1 宫位1
     * @param palace2 宫位2
     * @return 关系效果描述
     */
    public static String getRelationEffect(PalaceBO palace1, PalaceBO palace2) {
        PalaceRelationType relationType = getRelationType(palace1, palace2);

        switch (relationType) {
            case OPPOSITE:
                return String.format("%s宫与%s宫相对，主对立制衡、相互影响。",
                    palace1.getName(), palace2.getName());
            case TRINE:
                return String.format("%s宫与%s宫三合，主相生相助、配合默契。",
                    palace1.getName(), palace2.getName());
            case SEXTILE:
                return String.format("%s宫与%s宫六合，主和谐共生、互相扶持。",
                    palace1.getName(), palace2.getName());
            case SQUARE:
                return String.format("%s宫与%s宫相刑，主冲突矛盾、阻碍不顺。",
                    palace1.getName(), palace2.getName());
            default:
                return String.format("%s宫与%s宫无特殊关系。",
                    palace1.getName(), palace2.getName());
        }
    }

    /**
     * 分析宫位的所有关系
     *
     * @param palace 目标宫位
     * @param allPalaces 所有宫位列表
     * @return 关系分析结果
     */
    public static Map<String, Object> analyzeAllRelations(PalaceBO palace, List<PalaceBO> allPalaces) {
        Map<String, Object> result = new HashMap<>();

        // 获取三方关系
        List<PalaceBO> trineRelations = getTrineRelations(palace, allPalaces);
        List<String> trineEffects = new ArrayList<>();
        for (PalaceBO p : trineRelations) {
            trineEffects.add(getRelationEffect(palace, p));
        }

        // 获取四正关系
        List<PalaceBO> squareRelations = getSquareRelations(palace, allPalaces);
        List<String> squareEffects = new ArrayList<>();
        for (PalaceBO p : squareRelations) {
            squareEffects.add(getRelationEffect(palace, p));
        }

        // 获取对宫关系
        PalaceBO oppositePalace = getOppositePalace(palace, allPalaces);
        String oppositeEffect = oppositePalace != null ?
            getRelationEffect(palace, oppositePalace) : "无对宫关系";

        result.put("palace", palace.getName());
        result.put("trineRelations", trineEffects);
        result.put("squareRelations", squareEffects);
        result.put("oppositeRelation", oppositeEffect);

        return result;
    }

    private static boolean isOpposite(PalaceBO palace1, PalaceBO palace2) {
        int index1 = palace1.getIndex();
        int index2 = palace2.getIndex();
        return Math.abs(index1 - index2) == 6;
    }

    private static boolean isTrine(PalaceBO palace1, PalaceBO palace2) {
        int index1 = palace1.getIndex();
        int index2 = palace2.getIndex();
        int diff = Math.abs(index1 - index2);
        return diff == 4 || diff == 8;
    }

    private static boolean isSextile(PalaceBO palace1, PalaceBO palace2) {
        int index1 = palace1.getIndex();
        int index2 = palace2.getIndex();
        int diff = Math.abs(index1 - index2);
        return diff == 2 || diff == 10;
    }

    private static boolean isSquare(PalaceBO palace1, PalaceBO palace2) {
        int index1 = palace1.getIndex();
        int index2 = palace2.getIndex();
        int diff = Math.abs(index1 - index2);
        return diff == 3 || diff == 9;
    }

    public static PalaceRelationType calculateRelation(PalaceBO palace1, PalaceBO palace2) {
        if (palace1 == null || palace2 == null) {
            return PalaceRelationType.NONE;
        }

        if (isOpposite(palace1, palace2)) {
            return PalaceRelationType.OPPOSITE;
        }

        if (isTrine(palace1, palace2)) {
            return PalaceRelationType.TRINE;
        }

        if (isSextile(palace1, palace2)) {
            return PalaceRelationType.SEXTILE;
        }

        if (isSquare(palace1, palace2)) {
            return PalaceRelationType.SQUARE;
        }

        return PalaceRelationType.NONE;
    }
}
