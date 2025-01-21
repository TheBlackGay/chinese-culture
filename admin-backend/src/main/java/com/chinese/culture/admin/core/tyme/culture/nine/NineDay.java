package com.chinese.culture.admin.core.tyme.culture.nine;

import com.chinese.culture.admin.core.tyme.AbstractCultureDay;
import com.chinese.culture.admin.core.tyme.culture.nine.Nine;

/**
 * 数九天
 *
 * @author 6tail
 */
public class NineDay extends AbstractCultureDay {

  public NineDay(com.chinese.culture.admin.core.tyme.culture.nine.Nine nine, int dayIndex) {
    super(nine, dayIndex);
  }

  /**
   * 数九
   *
   * @return 数九
   */
  public com.chinese.culture.admin.core.tyme.culture.nine.Nine getNine() {
    return (Nine) culture;
  }

}
