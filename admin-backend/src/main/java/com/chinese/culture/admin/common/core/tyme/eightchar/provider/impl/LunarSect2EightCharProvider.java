package com.chinese.culture.admin.common.core.tyme.eightchar.provider.impl;

import com.chinese.culture.admin.common.core.tyme.eightchar.EightChar;
import com.chinese.culture.admin.common.core.tyme.eightchar.provider.EightCharProvider;
import com.chinese.culture.admin.common.core.tyme.lunar.LunarHour;

/**
 * Lunar流派2的八字计算（晚子时日柱算当天）
 *
 * @author 6tail
 */
public class LunarSect2EightCharProvider implements EightCharProvider {

  @Override
  public EightChar getEightChar(LunarHour hour) {
    return new EightChar(hour.getYearSixtyCycle(), hour.getMonthSixtyCycle(), hour.getLunarDay().getSixtyCycle(), hour.getSixtyCycle());
  }
}
