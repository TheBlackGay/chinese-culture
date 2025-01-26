public static int getStartIndex(int day, int timeIndex, int fiveElementsClass) {
    // 如果是晚子时,需要加一天
    if (timeIndex == 12) {
        day++;
    }

    // 如果日期超过当月最大天数,说明跨月了,需要处理为合法日期
    // 这里简化处理,实际应该根据具体月份的天数来处理
    if (day > 30) {
        day -= 30;
    }

    int offset = -1;
    int quotient;
    int remainder;

    do {
        offset++;
        int divisor = day + offset;
        quotient = divisor / fiveElementsClass;
        remainder = divisor % fiveElementsClass;
    } while (remainder != 0);

    // 商数对12取余
    quotient = quotient % 12;
    
    // 从商数减1开始计算
    int ziweiIndex = quotient - 1;

    // 根据偏移量的奇偶性决定加减方向
    if (offset % 2 == 0) {
        // 偶数偏移量,顺时针方向
        ziweiIndex += offset;
    } else {
        // 奇数偏移量,逆时针方向
        ziweiIndex -= offset;
    }

    // 确保索引在0-11范围内
    return fixIndex(ziweiIndex);
}

private static int fixIndex(int index) {
    if (index < 0) {
        return fixIndex(index + 12);
    }
    if (index > 11) {
        return fixIndex(index - 12);
    }
    return index;
} 