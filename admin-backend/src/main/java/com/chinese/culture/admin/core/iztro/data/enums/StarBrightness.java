//package com.chinese.culture.admin.core.iztro.data.enums;
//
///**
// * 星耀亮度枚举
// */
//public enum Brightness {
//    TEMPLE("庙"),
//    STRONG("旺"),
//    GAIN("得"),
//    TRAPPED("陷");
//
//    private final String description;
//
//    Brightness(String description) {
//        this.description = description;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public static Brightness fromDescription(String description) {
//        for (Brightness brightness : values()) {
//            if (brightness.getDescription().equals(description)) {
//                return brightness;
//            }
//        }
//        throw new IllegalArgumentException("Invalid star brightness description: " + description);
//    }
//
//    public static Brightness fromBrightness(Brightness brightness) {
//        switch (brightness) {
//            case TEMPLE:
//                return TEMPLE;
//            case STRONG:
//                return STRONG;
//            case GAIN:
//            case BENEFIT:
//                return GAIN;
//            case TRAPPED:
//                return TRAPPED;
//            default:
//                return STRONG;
//        }
//    }
//
//    public static Brightness fromString(String value) {
//        for (Brightness brightness : values()) {
//            if (brightness.name().equalsIgnoreCase(value) ||
//                brightness.getDescription().equals(value)) {
//                return brightness;
//            }
//        }
//        throw new IllegalArgumentException("Invalid star brightness value: " + value);
//    }
//}
