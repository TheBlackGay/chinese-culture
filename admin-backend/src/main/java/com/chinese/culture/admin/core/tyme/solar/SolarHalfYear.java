package com.chinese.culture.admin.core.tyme.solar;

import com.chinese.culture.admin.core.tyme.AbstractTyme;
import com.chinese.culture.admin.core.tyme.solar.SolarMonth;
import com.chinese.culture.admin.core.tyme.solar.SolarSeason;
import com.chinese.culture.admin.core.tyme.solar.SolarYear;

import java.util.ArrayList;
import java.util.List;

/**
 * 公历半年
 *
 * @author 6tail
 */
public class SolarHalfYear extends AbstractTyme {

  public static final String[] NAMES = {"上半年", "下半年"};

  /**
   * 年
   */
  protected com.chinese.culture.admin.core.tyme.solar.SolarYear year;

  /**
   * 索引，0-1
   */
  protected int index;

  /**
   * 初始化
   *
   * @param year  年
   * @param index 索引，0-1
   */
  public SolarHalfYear(int year, int index) {
    if (index < 0 || index > 1) {
      throw new IllegalArgumentException(String.format("illegal solar half year index: %d", index));
    }
    this.year = com.chinese.culture.admin.core.tyme.solar.SolarYear.fromYear(year);
    this.index = index;
  }

  public static SolarHalfYear fromIndex(int year, int index) {
    return new SolarHalfYear(year, index);
  }

  /**
   * 公历年
   *
   * @return 公历年
   */
  public SolarYear getSolarYear() {
    return year;
  }

  /**
   * 年
   *
   * @return 年
   */
  public int getYear() {
    return year.getYear();
  }

  /**
   * 索引
   *
   * @return 索引，0-1
   */
  public int getIndex() {
    return index;
  }

  public String getName() {
    return NAMES[index];
  }

  @Override
  public String toString() {
    return year + getName();
  }

  public SolarHalfYear next(int n) {
    int i = index;
    int y = getYear();
    if (n != 0) {
      i += n;
      y += i / 2;
      i %= 2;
      if (i < 0) {
        i += 2;
        y -= 1;
      }
    }
    return fromIndex(y, i);
  }

  /**
   * 月份列表
   *
   * @return 月份列表，半年有6个月。
   */
  public List<com.chinese.culture.admin.core.tyme.solar.SolarMonth> getMonths() {
    List<com.chinese.culture.admin.core.tyme.solar.SolarMonth> l = new ArrayList<>(6);
    int y = getYear();
    for (int i = 1; i < 7; i++) {
      l.add(SolarMonth.fromYm(y, index * 6 + i));
    }
    return l;
  }

  /**
   * 季度列表
   *
   * @return 季度列表，半年有2个季度。
   */
  public List<com.chinese.culture.admin.core.tyme.solar.SolarSeason> getSeasons() {
    List<com.chinese.culture.admin.core.tyme.solar.SolarSeason> l = new ArrayList<>(2);
    int y = getYear();
    for (int i = 0; i < 2; i++) {
      l.add(SolarSeason.fromIndex(y, index * 2 + i));
    }
    return l;
  }
}
