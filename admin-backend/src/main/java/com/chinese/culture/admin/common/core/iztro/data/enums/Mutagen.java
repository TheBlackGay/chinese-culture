package com.chinese.culture.admin.common.core.iztro.data.enums;

/**
 * 四化枚举
 */
public enum Mutagen {
    LU("化禄"),
    QUAN("化权"),
    KE("化科"),
    JI("化忌");

    private final String description;

    Mutagen(String description) {
        this.description = description;
    }

    public static Mutagen fromString(String text) {
        for (Mutagen m : Mutagen.values()) {
            if (m.description.equals(text)) {
                return m;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }

    public String getDescription() {
        return description;
    }
}
