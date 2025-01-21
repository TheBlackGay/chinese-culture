package com.chinese.culture.admin.core.tyme.culture.dog;

import com.chinese.culture.admin.core.tyme.AbstractCultureDay;
import com.chinese.culture.admin.core.tyme.culture.dog.Dog;

/**
 * 三伏天
 *
 * @author 6tail
 */
public class DogDay extends AbstractCultureDay {

  public DogDay(com.chinese.culture.admin.core.tyme.culture.dog.Dog dog, int dayIndex) {
    super(dog, dayIndex);
  }

  /**
   * 三伏
   *
   * @return 三伏
   */
  public com.chinese.culture.admin.core.tyme.culture.dog.Dog getDog() {
    return (Dog)culture;
  }

}
