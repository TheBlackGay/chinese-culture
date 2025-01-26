public StarPosition calculatePosition(String solarDate, int timeIndex, String fiveElementsClass, int lunarDay, int maxDaysInMonth) {
    // 解析五行局数
    int classValue;
    switch (fiveElementsClass) {
        case "水二局":
            classValue = FiveElementsClass.WATER.getValue();
            break;
        case "木三局":
            classValue = FiveElementsClass.WOOD.getValue();
            break;
        case "金四局":
            classValue = FiveElementsClass.METAL.getValue();
            break;
        case "土五局":
            classValue = FiveElementsClass.EARTH.getValue();
            break;
        case "火六局":
            classValue = FiveElementsClass.FIRE.getValue();
            break;
        default:
            throw new IllegalArgumentException("无效的五行局数: " + fiveElementsClass);
    }

    // 如果timeIndex等于12说明是晚子时，需要加一天
    int day = timeIndex == 12 ? lunarDay + 1 : lunarDay;
    
    // 假如日期超过当月最大天数，说明跨月了，需要处理为合法日期
    if (day > maxDaysInMonth) {
        day -= maxDaysInMonth;
    }

    int remainder = -1; // 余数
    int quotient = 0; // 商
    int offset = -1; // 循环次数

    do {
        // 农历出生日（初一为1，以此类推）加上偏移量作为除数，以这个数处以五行局的数向下取整
        // 需要一直运算到余数为0为止
        offset++;
        int divisor = day + offset;
        quotient = divisor / classValue;
        remainder = divisor % classValue;
    } while (remainder != 0);

    // 将商除以12取余数
    quotient = quotient % 12;

    // 以商减一（因为需要从0开始）作为起始位置
    int ziweiIndex = quotient - 1;

    if (offset % 2 == 0) {
        // 若循环次数为偶数，则索引顺时针数到循环数
        ziweiIndex = (ziweiIndex + offset) % 12;
    } else {
        // 若循环次数为奇数，则索引逆时针数到循环数
        ziweiIndex = (ziweiIndex - offset + 12) % 12;
    }

    // 天府星位置与紫微星相对
    int tianfuIndex = (12 - ziweiIndex) % 12;

    System.out.println("紫微星位置: " + ziweiIndex + " (" + PALACE_NAMES[ziweiIndex] + ")");
    System.out.println("天府星位置: " + tianfuIndex + " (" + PALACE_NAMES[tianfuIndex] + ")");

    return new StarPosition(ziweiIndex, tianfuIndex);
} 