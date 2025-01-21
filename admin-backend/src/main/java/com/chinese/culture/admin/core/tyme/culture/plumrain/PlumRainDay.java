package com.chinese.culture.admin.core.tyme.culture.plumrain;

import com.chinese.culture.admin.core.tyme.AbstractCultureDay;
import com.chinese.culture.admin.core.tyme.culture.plumrain.PlumRain;

/**
 * 梅雨天
 *
 * @author 6tail
 */
public class PlumRainDay extends AbstractCultureDay {

  public PlumRainDay(com.chinese.culture.admin.core.tyme.culture.plumrain.PlumRain plumRain, int dayIndex) {
    super(plumRain, dayIndex);
  }

  /**
   * 梅雨
   *
   * @return 梅雨
   */
  public com.chinese.culture.admin.core.tyme.culture.plumrain.PlumRain getPlumRain() {
    return (PlumRain) culture;
  }

  @Override
  public String toString() {
    return getPlumRain().getIndex() == 0 ? super.toString() : culture.getName();
  }

}
