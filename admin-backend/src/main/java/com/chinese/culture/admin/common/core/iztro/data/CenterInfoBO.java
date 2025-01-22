package com.chinese.culture.admin.common.core.iztro.data;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 中宫信息数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterInfoBO {
    private String birthTime;      // 出生时辰
    private String clockTime;      // 时钟时间
    private String lunarBirthDay;  // 农历生日
    private String fate;           // 命宫
    private String bodyFate;       // 身宫
    private String fiveElements;   // 五行局
    private String startAge;       // 起运年龄
    private String direction;      // 行运方向
} 