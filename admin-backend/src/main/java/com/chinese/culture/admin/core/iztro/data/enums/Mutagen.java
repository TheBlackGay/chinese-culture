package com.chinese.culture.admin.core.iztro.data.enums;

/**
 * 四化枚举
 */
public enum Mutagen {
    LUCKY("化禄"),
    POWER("化权"),
    SKILL("化科"),
    WEAK("化忌");

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
