package com.chinese.culture.admin.common.core.iztro.star;

import com.chinese.culture.admin.common.core.iztro.data.StarBO;
import com.chinese.culture.admin.common.core.iztro.data.SoulAndBodyBO;
import com.chinese.culture.admin.common.core.iztro.data.enums.*;
import com.chinese.culture.admin.common.core.iztro.utils.PalaceUtils;
import com.chinese.culture.admin.common.core.iztro.utils.LunarUtils;
import com.chinese.culture.admin.common.core.iztro.utils.FiveElementsUtils;
import com.chinese.culture.admin.common.core.tyme.lunar.LunarDay;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * 紫微星定位
 * 六五四三二，酉午亥辰丑，
 * 局数除日数，商数宫前走；
 * 若见数无余，便要起虎口，
 * 日数小於局，还直宫中守。
 *
 * 举例：
 * - 例一：27日出生木三局，以三除27，循环0次就可以整除，27➗3=9，从寅进9格，在戍安紫微。
 * - 例二：13日出生火六局，以六除13，最少需要加5才能整除，18➗6=3，从寅进3格为辰，添加数为5（奇数），故要逆回五宫，在亥安紫微。
 * - 例三：6日出生土五局，以五除6，最少需要加4才能整除，10➗5=2，从寅进2格为卯，添加数为4（偶数），顺行4格为未，在未安紫微。
 */
@Data
@Slf4j
public class ZiweiStarLocator {

    /**
     * 修正索引值，使其在指定范围内循环
     * @param index 需要修正的索引值
     * @param max 最大循环数，默认为12【因为宫位索引的范围是0-11】
     * @return 修正后的索引值
     */
    private int fixIndex(int index, int max) {
        while (index < 0) {
            index += max;
        }
        while (index >= max) {
            index -= max;
        }
        return index;
    }

    private int fixIndex(int index) {
        return fixIndex(index, 12);
    }

    /**
     * 这个方法是获取紫微星的信息
     * @param solarDateStr
     * @param timeIndex
     * @param fixLeap
     * @return
     */
    public StarBO getZiweiStarPosition(String solarDateStr, int timeIndex, boolean fixLeap) {

        // 获取命主身主
        LocalDateTime dateTime = LocalDateTime.parse(solarDateStr + "T00:00:00");
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(dateTime, fixLeap);

        // 获取五行局
        FiveElementsClass fiveElements = FiveElementsUtils.getFiveElementsClass(
            soulAndBody.getHeavenlyStemOfSoul().getChinese(),
            soulAndBody.getEarthlyBranchOfSoul().getChinese()
        );
        log.info("五行局: {}, 值: {}", fiveElements.name(), fiveElements.getValue());

        // 获取农历日期
        LunarDay lunarDay = LunarUtils.fromSolar(LocalDate.parse(solarDateStr));
        int _day = lunarDay.getDay();
        log.info("农历日期: {}月{}日", lunarDay.getMonth(), _day);

        // 如果timeIndex等于12说明是晚子时，需要加一天
        if (timeIndex == 12) {
            _day++;
            // 处理月末的情况
            int maxDays = LunarUtils.getMonthDays(lunarDay.getYear(), lunarDay.getMonth(), lunarDay.getLunarMonth().isLeap());
            if (_day > maxDays) {
                _day = 1;
            }
            log.info("晚子时处理后的日期: {}", _day);
        }

        int remainder = -1; // 余数
        int quotient = 0; // 商
        int offset = -1; // 循环次数

        do {
            // 农历出生日（初一为1，以此类推）加上偏移量作为除数，以这个数处以五行局的数向下取整
            // 需要一直运算到余数为0为止
            offset++;
            int divisor = _day + offset;
            quotient = divisor / fiveElements.getValue();
            remainder = divisor % fiveElements.getValue();
        } while (remainder != 0);

        // 计算紫微星位置
        int ziweiIndex = 0; // 从寅宫(0)开始

        // 根据商数顺时针数
        ziweiIndex = fixIndex(ziweiIndex + quotient - 1);

        // 根据偏移量调整
        if (offset % 2 == 0) {
            // 偶数顺时针
            ziweiIndex = fixIndex(ziweiIndex + offset);
        } else {
            // 奇数逆时针
            ziweiIndex = fixIndex(ziweiIndex - offset);
        }

        // 计算紫微星亮度
        // 紫微星在各宫位的亮度,从寅宫开始顺序排列
        Brightness[] brightnessArray = new Brightness[] {
            Brightness.WANG,  // 寅
            Brightness.WANG,  // 卯
            Brightness.DE,    // 辰
            Brightness.WANG,  // 巳
            Brightness.MIAO,  // 午
            Brightness.MIAO,  // 未
            Brightness.WANG,  // 申
            Brightness.WANG,  // 酉
            Brightness.DE,    // 戌
            Brightness.WANG,  // 亥
            Brightness.PING,  // 子
            Brightness.MIAO   // 丑
        };
        
        // 直接根据宫位索引获取亮度
        Brightness brightness = brightnessArray[ziweiIndex];

        return StarBO.builder()
                .name(StarName.ZI_WEI_MAJ)
                .type(StarType.MAJOR)
                .scope(Scope.ORIGIN)
                .position(ziweiIndex)
                .element(FiveElements.valueOf(fiveElements.name()))
                .brightness(brightness)
                .build();
    }

    public int getZiweiStarPosition(LocalDateTime solarDate, int timeIndex) {
        // 获取命宫和身宫
        SoulAndBodyBO soulAndBody = PalaceUtils.getSoulAndBody(solarDate, false);

        // 获取五行局
        FiveElementsClass fiveElements = FiveElementsUtils.getFiveElementsClass(
            soulAndBody.getHeavenlyStemOfSoul().getChinese(),
            soulAndBody.getEarthlyBranchOfSoul().getChinese()
        );

        // 获取农历日期
        LunarDay lunarDay = LunarUtils.fromSolar(solarDate.toLocalDate());
        int day = lunarDay.getDay();

        // 如果timeIndex等于12说明是晚子时，需要加一天
        if (timeIndex == 12) {
            day++;
            int maxDays = LunarUtils.getMonthDays(lunarDay.getYear(), lunarDay.getMonth(), lunarDay.getLunarMonth().isLeap());
            if (day > maxDays) {
                day = 1;
            }
            log.info("晚子时处理后的日期: {}", day);
        }

        int remainder = -1; // 余数
        int quotient = 0; // 商
        int offset = -1; // 循环次数

        do {
            // 农历出生日（初一为1，以此类推）加上偏移量作为除数，以这个数处以五行局的数向下取整
            // 需要一直运算到余数为0为止
            offset++;
            int divisor = day + offset;
            quotient = divisor / fiveElements.getValue();
            remainder = divisor % fiveElements.getValue();
        } while (remainder != 0);

        // 将商除以12取余数
        quotient = quotient % 12;

        // 以商减一（因为需要从0开始）作为起始位置
        int ziweiIndex = quotient - 1;

        if (offset % 2 == 0) {
            // 若循环次数为偶数，则索引顺时针数到循环数
            ziweiIndex += offset;
        } else {
            // 若循环次数为奇数，则索引逆时针数到循环数
            ziweiIndex -= offset;
        }

        // 修正索引范围
        return fixIndex(ziweiIndex);
    }
}
