package com.chinese.culture.admin.api.dto.response;

import lombok.Data;
import java.util.List;

/**
 * 星盘响应 DTO
 */
@Data
public class AstrolabeResponseDTO {
    /**
     * 宫位列表
     */
    private List<PalaceResponseDTO> palaces;
} 