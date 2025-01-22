package com.chinese.culture.admin.common.core.iztro.data;

import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 星耀四化信息
 */
@Data
public class StarMutagenBO {
    /**
     * 星耀名称
     */
    private StarName starName;

    /**
     * 四化
     */
    private List<Mutagen> mutagens = new ArrayList<>();

    /**
     * 检查是否具有指定四化
     */
    public boolean hasMutagen(Mutagen mutagen) {
        return mutagens.contains(mutagen);
    }

    /**
     * 添加四化
     */
    public void addMutagen(Mutagen mutagen) {
        if (!mutagens.contains(mutagen)) {
            mutagens.add(mutagen);
        }
    }

    /**
     * 获取四化描述
     */
    public String getMutagenDescription() {
        if (mutagens.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Mutagen mutagen : mutagens) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(mutagen.getDescription());
        }
        return sb.toString();
    }
} 