package com.chinese.culture.admin.core.tyme.eightchar.provider;

import com.chinese.culture.admin.core.tyme.eightchar.EightChar;
import com.chinese.culture.admin.core.tyme.lunar.LunarHour;

/**
 * 八字计算接口
 *
 * @author 6tail
 */
public interface EightCharProvider {

  /**
   * 八字
   *
   * @param hour 农历时辰
   * @return 八字
   */
  EightChar getEightChar(LunarHour hour);
}
