package com.chinese.culture.admin.core.tyme.solar;

import com.chinese.culture.admin.core.tyme.AbstractCultureDay;
import com.chinese.culture.admin.core.tyme.solar.SolarTerm;

/**
 * 节气第几天
 *
 * @author 6tail
 */
public class SolarTermDay extends AbstractCultureDay {

  public SolarTermDay(com.chinese.culture.admin.core.tyme.solar.SolarTerm solarTerm, int dayIndex) {
    super(solarTerm, dayIndex);
  }

  /**
   * 节气
   *
   * @return 节气
   */
  public com.chinese.culture.admin.core.tyme.solar.SolarTerm getSolarTerm() {
    return (SolarTerm)culture;
  }

}
