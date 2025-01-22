package com.chinese.culture.admin.common.core.tyme.eightchar.provider;

import com.chinese.culture.admin.common.core.tyme.eightchar.ChildLimitInfo;
import com.chinese.culture.admin.common.core.tyme.solar.SolarTerm;
import com.chinese.culture.admin.common.core.tyme.solar.SolarTime;

/**
 * 童限计算接口
 *
 * @author 6tail
 */
public interface ChildLimitProvider {

  /**
   * 童限信息
   *
   * @param birthTime 出生公历时刻
   * @param term      节令
   * @return 童限信息
   */
  ChildLimitInfo getInfo(SolarTime birthTime, SolarTerm term);
}
