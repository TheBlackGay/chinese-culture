package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 星耀类型枚举
 */
public enum StarType {
    MAJOR("主星"),       // 紫微、天机等十四主星
    SOFT("辅星"),        // 文昌、文曲等吉星
    TOUGH("煞星"),       // 火星、铃星等凶星
    ADJECTIVE("杂耀"),   // 其他杂星
    FLOWER("桃花"),      // 红鸾、天喜等桃花星
    HELPER("解神"),      // 解神
    LUCUN("禄存"),       // 禄存
    TIANMA("天马");      // 天马

    private final String chinese;

    StarType(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }
} 