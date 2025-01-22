package com.chinese.culture.admin.common.core.iztro.data;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 大限数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DecadalBO {
    private Integer startAge;    // 大限起始年龄
    private Integer endAge;      // 大限结束年龄
    private String description;  // 大限描述
} 