package com.chinese.culture.admin.core.iztro.data;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 命盘解释结果
 */
@Data
public class AstrolabeInterpretation {
    
    /**
     * 宫位关系
     */
    private PalaceRelations palaceRelations;
    
    /**
     * 星耀组合
     */
    private StarCombinations starCombinations;
    
    /**
     * 四化关系
     */
    private MutagenRelations mutagenRelations;
    
    /**
     * 重要格局
     */
    private List<String> majorPatterns;
    
    /**
     * 宫位关系数据
     */
    @Data
    public static class PalaceRelations {
        // 相刑关系
        private Map<String, List<String>> punishments;
        // 相合关系
        private Map<String, List<String>> harmonies;
        // 三合局
        private Map<String, List<String>> trineFormations;
    }
    
    /**
     * 星耀组合数据
     */
    @Data
    public static class StarCombinations {
        // 三方四正
        private Map<String, List<String>> trineAndOpposition;
        // 星耀会合
        private Map<String, List<String>> convergence;
    }
    
    /**
     * 四化关系数据
     */
    @Data
    public static class MutagenRelations {
        // 四化关系
        private Map<String, List<String>> mutagenRelations;
        // 星耀冲突
        private Map<String, List<String>> starConflicts;
    }
} 