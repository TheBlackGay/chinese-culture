package com.chinese.culture.admin.core.iztro.calculator;

import com.chinese.culture.admin.core.iztro.data.RawDate;
import com.chinese.culture.admin.core.iztro.data.enums.HeavenlyStem;
import com.chinese.culture.admin.core.iztro.data.enums.EarthlyBranch;
import com.chinese.culture.admin.core.tyme.lunar.LunarDay;
import com.chinese.culture.admin.core.tyme.lunar.LunarHour;
import com.chinese.culture.admin.core.tyme.sixtycycle.SixtyCycle;
import com.chinese.culture.admin.core.tyme.solar.SolarDay;
import com.chinese.culture.admin.core.tyme.solar.SolarTerm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 日期计算器
 */
@Slf4j
@Component
public class DateCalculator {

    /**
     * 将阳历日期转换为阴历日期
     *
     * @param solarDate 阳历日期
     * @return 阴历日期
     */
    public RawDate.LunarDate solar2lunar(RawDate.SolarDate solarDate) {
        try {
            // 将阳历日期转换为LunarDay对象
            SolarDay solarDay = SolarDay.fromYmd(solarDate.getYear(), solarDate.getMonth(), solarDate.getDay());
            LunarDay lunarDay = solarDay.getLunarDay();

            // 创建阴历日期对象
            RawDate.LunarDate lunarDate = new RawDate.LunarDate();
            lunarDate.setYear(lunarDay.getYear());
            lunarDate.setMonth(lunarDay.getMonth());
            lunarDate.setDay(lunarDay.getDay());
            lunarDate.setLeapMonth(lunarDay.getLunarMonth().isLeap());

            return lunarDate;
        } catch (Exception e) {
            log.error("阳历转阴历失败: {}", e.getMessage());
            throw new IllegalArgumentException("无效的阳历日期");
        }
    }

    /**
     * 将阴历日期转换为阳历日期
     *
     * @param lunarDate 阴历日期
     * @return 阳历日期
     */
    public RawDate.SolarDate lunar2solar(RawDate.LunarDate lunarDate) {
        try {
            // 将阴历日期转换为SolarDay对象
            LunarDay lunarDay = LunarDay.fromYmd(lunarDate.getYear(),
                lunarDate.isLeapMonth() ? -lunarDate.getMonth() : lunarDate.getMonth(),
                lunarDate.getDay());
            SolarDay solarDay = lunarDay.getSolarDay();

            // 创建阳历日期对象
            RawDate.SolarDate solarDate = new RawDate.SolarDate();
            solarDate.setYear(solarDay.getYear());
            solarDate.setMonth(solarDay.getMonth());
            solarDate.setDay(solarDay.getDay());

            return solarDate;
        } catch (Exception e) {
            log.error("阴历转阳历失败: {}", e.getMessage());
            throw new IllegalArgumentException("无效的阴历日期");
        }
    }

    /**
     * 获取中国日期（天干地支）
     *
     * @param solarDate 阳历日期
     * @param timeIndex 时辰索引
     * @return 中国日期
     */
    public RawDate.ChineseDate getChineseDate(RawDate.SolarDate solarDate, int timeIndex) {
        try {
            // 将阳历日期转换为SolarDay对象
            SolarDay solarDay = SolarDay.fromYmd(solarDate.getYear(), solarDate.getMonth(), solarDate.getDay());
            LunarDay lunarDay = solarDay.getLunarDay();

            // 创建中国日期对象
            RawDate.ChineseDate chineseDate = new RawDate.ChineseDate();

            // 设置年干支
            SixtyCycle yearCycle = lunarDay.getYearSixtyCycle();
            chineseDate.setYearGan(yearCycle.getHeavenStem().getIndex());
            chineseDate.setYearZhi(yearCycle.getEarthBranch().getIndex());

            // 设置月干支
            SixtyCycle monthCycle = lunarDay.getMonthSixtyCycle();
            chineseDate.setMonthGan(monthCycle.getHeavenStem().getIndex());
            chineseDate.setMonthZhi(monthCycle.getEarthBranch().getIndex());

            // 设置日干支
            SixtyCycle dayCycle = lunarDay.getSixtyCycle();
            chineseDate.setDayGan(dayCycle.getHeavenStem().getIndex());
            chineseDate.setDayZhi(dayCycle.getEarthBranch().getIndex());

            // 设置时辰干支
            List<LunarHour> hours = lunarDay.getHours();
            if (timeIndex >= 0 && timeIndex < hours.size()) {
                LunarHour hour = hours.get(timeIndex);
                chineseDate.setTimeGan(hour.getSixtyCycle().getHeavenStem().getIndex());
                chineseDate.setTimeZhi(hour.getSixtyCycle().getEarthBranch().getIndex());
            }

            return chineseDate;
        } catch (Exception e) {
            log.error("获取中国日期失败: {}", e.getMessage());
            throw new IllegalArgumentException("无效的日期或时辰索引");
        }
    }

    /**
     * 获取时辰范围
     *
     * @param timeIndex 时辰索引
     * @return 时间范围
     */
    public List<String> getTimeRange(int timeIndex) {
        if (timeIndex < 0 || timeIndex >= 12) {
            throw new IllegalArgumentException("无效的时辰索引");
        }

        List<String> result = new ArrayList<>();
        int startHour = timeIndex * 2;
        int endHour = startHour + 2;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour == 24 ? 0 : endHour, 0);

        result.add(startTime.format(formatter));
        result.add(endTime.format(formatter));

        return result;
    }

    /**
     * 计算年龄
     *
     * @param birthDate 出生日期
     * @param targetDate 目标日期
     * @return 年龄
     */
    public int calculateAge(LocalDateTime birthDate, LocalDateTime targetDate) {
        if (birthDate == null || targetDate == null) {
            throw new IllegalArgumentException("日期不能为空");
        }

        LocalDate birth = birthDate.toLocalDate();
        LocalDate target = targetDate.toLocalDate();

        if (birth.isAfter(target)) {
            throw new IllegalArgumentException("出生日期不能晚于目标日期");
        }

        int age = target.getYear() - birth.getYear();
        if (target.getMonthValue() < birth.getMonthValue() ||
            (target.getMonthValue() == birth.getMonthValue() && target.getDayOfMonth() < birth.getDayOfMonth())) {
            age--;
        }

        return age;
    }

    /**
     * 获取节气
     * @param solarDate 阳历日期字符串 (格式：yyyy-MM-dd)
     * @return 节气名称，如果不是节气日期则返回null
     */
    public static String getSolarTerm(String solarDate) {
        try {
            validateDateFormat(solarDate);
            String[] parts = solarDate.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            SolarDay solarDay = SolarDay.fromYmd(year, month, day);
            SolarTerm solarTerm = solarDay.getTerm();

            return solarTerm != null ? solarTerm.getName() : null;
        } catch (Exception e) {
            log.error("Solar term calculation failed: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid solar date format. Expected: yyyy-MM-dd");
        }
    }

    /**
     * 判断是否在年份分界点之前
     * 分界点可以是立春(exact)或正月初一(normal)
     * @param solarDate 阳历日期字符串 (格式：yyyy-MM-dd)
     * @param divideRule 分界规则 ("exact" 或 "normal")
     * @return true如果在分界点之前，false如果在分界点之后
     */
    public static boolean isBeforeYearDivide(String solarDate, String divideRule) {
        try {
            validateDateFormat(solarDate);
            String[] parts = solarDate.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            SolarDay targetDay = SolarDay.fromYmd(year, month, day);

            if ("exact".equals(divideRule)) {
                // 使用立春作为分界点
                SolarDay springDay = SolarDay.fromYmd(year, 2, 4); // 立春一般在2月4日前后
                while (springDay.getTerm() == null || !"立春".equals(springDay.getTerm().getName())) {
                    springDay = springDay.next(1);
                }
                return targetDay.isBefore(springDay);
            } else {
                // 使用正月初一作为分界点
                LunarDay lunarDay = targetDay.getLunarDay();
                return lunarDay.getMonth() == 12 || (lunarDay.getMonth() == 1 && lunarDay.getDay() == 1);
            }
        } catch (Exception e) {
            log.error("Year divide check failed: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid date format or divide rule");
        }
    }

    /**
     * 获取年柱
     * 需要考虑年份分界点，分界点之前算上一年
     * @param solarDate 阳历日期字符串 (格式：yyyy-MM-dd)
     * @param divideRule 分界规则 ("exact" 或 "normal")
     * @return 年柱 (干支组合)
     */
    public static String getYearPillar(String solarDate, String divideRule) {
        try {
            validateDateFormat(solarDate);
            String[] parts = solarDate.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            SolarDay solarDay = SolarDay.fromYmd(year, month, day);

            // 如果在分界点之前，使用上一年
            if (isBeforeYearDivide(solarDate, divideRule)) {
                solarDay = SolarDay.fromYmd(year - 1, month, day);
            }

            LunarDay lunarDay = solarDay.getLunarDay();

            return lunarDay.getYearSixtyCycle().toString();
        } catch (Exception e) {
            log.error("Year pillar calculation failed: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid date format or divide rule");
        }
    }

    /**
     * 获取月柱
     * 需要考虑节气
     * @param solarDate 阳历日期字符串 (格式：yyyy-MM-dd)
     * @return 月柱 (干支组合)
     */
    public static String getMonthPillar(String solarDate) {
        try {
            validateDateFormat(solarDate);
            String[] parts = solarDate.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            SolarDay solarDay = SolarDay.fromYmd(year, month, day);
            LunarDay lunarDay = solarDay.getLunarDay();

            return lunarDay.getMonthSixtyCycle().toString();
        } catch (Exception e) {
            log.error("Month pillar calculation failed: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    /**
     * 获取日柱
     * @param solarDate 阳历日期字符串 (格式：yyyy-MM-dd)
     * @return 日柱 (干支组合)
     */
    public static String getDayPillar(String solarDate) {
        try {
            validateDateFormat(solarDate);
            String[] parts = solarDate.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            SolarDay solarDay = SolarDay.fromYmd(year, month, day);
            LunarDay lunarDay = solarDay.getLunarDay();

            return lunarDay.getSixtyCycle().toString();
        } catch (Exception e) {
            log.error("Day pillar calculation failed: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid date format");
        }
    }

    /**
     * 获取时柱
     * @param solarDate 阳历日期字符串 (格式：yyyy-MM-dd)
     * @param timeIndex 时辰索引 (0-11)
     * @return 时柱 (干支组合)
     */
    public static String getHourPillar(String solarDate, int timeIndex) {
        try {
            validateDateFormat(solarDate);
            if (timeIndex < 0 || timeIndex > 11) {
                throw new IllegalArgumentException("Time index must be between 0 and 11");
            }

            // 先获取日干，用于推算时干
            String dayPillar = getDayPillar(solarDate);
            String dayHeavenlyStem = dayPillar.substring(0, 1);  // 获取日干

            // 计算时干和时支
            String hourHeavenlyStem = calculateHourHeavenlyStem(dayHeavenlyStem, timeIndex);
            String hourEarthlyBranch = calculateHourEarthlyBranch(timeIndex);

            return hourHeavenlyStem + hourEarthlyBranch;
        } catch (Exception e) {
            log.error("Hour pillar calculation failed: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid date format or time index");
        }
    }

    /**
     * 计算时干
     */
    private static String calculateHourHeavenlyStem(String dayHeavenlyStem, int timeIndex) {
        HeavenlyStem dayStem = HeavenlyStem.fromDescription(dayHeavenlyStem);
        int baseIndex = (dayStem.ordinal() % 5) * 2;
        int hourStemIndex = (baseIndex + timeIndex) % 10;
        return HeavenlyStem.values()[hourStemIndex].getDescription();
    }

    /**
     * 计算时支
     */
    private static String calculateHourEarthlyBranch(int timeIndex) {
        return EarthlyBranch.values()[timeIndex % 12].getDescription();
    }

    /**
     * 验证日期格式是否正确
     */
    private static void validateDateFormat(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Expected: yyyy-MM-dd");
        }
    }
}
