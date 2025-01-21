package com.chinese.culture.admin.core.tyme.culture.phenology;

import com.chinese.culture.admin.core.tyme.AbstractCultureDay;
import com.chinese.culture.admin.core.tyme.culture.phenology.Phenology;

/**
 * 七十二候
 *
 * @author 6tail
 */
public class PhenologyDay extends AbstractCultureDay {

  public PhenologyDay(com.chinese.culture.admin.core.tyme.culture.phenology.Phenology phenology, int dayIndex) {
    super(phenology, dayIndex);
  }

  /**
   * 候
   *
   * @return 候
   */
  public com.chinese.culture.admin.core.tyme.culture.phenology.Phenology getPhenology() {
    return (Phenology) culture;
  }

}
