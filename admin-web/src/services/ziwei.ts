import { astro } from 'iztro';

/**
 * 计算紫微斗数
 * @param birthYear 出生年份
 * @param birthMonth 出生月份
 * @param birthDay 出生日期
 * @param birthHour 出生小时(0-23)
 * @param gender 性别 male/female
 * @returns 紫微斗数星盘数据
 */
export const calculateZiWei = (
  birthYear: number,
  birthMonth: number,
  birthDay: number,
  birthHour: number,
  gender: 'male' | 'female'
) => {
  try {
    // 格式化月份和日期为两位数
    const formattedMonth = birthMonth.toString().padStart(2, '0');
    const formattedDay = birthDay.toString().padStart(2, '0');
    
    // 将小时转换为时辰序号(0-11)
    // 23:00-01:00 子时 (0)
    // 01:00-03:00 丑时 (1)
    // 03:00-05:00 寅时 (2)
    // ...以此类推
    let timeIndex = Math.floor((birthHour + 1) / 2);
    if (birthHour === 23) timeIndex = 0;
    
    // 使用阳历计算紫微斗数
    const astrolabe = astro.bySolar(
      `${birthYear}-${formattedMonth}-${formattedDay}`, 
      timeIndex, 
      gender === 'male' ? '男' : '女',
      true, // 使用经过修正的真太阳时
      'zh-CN' // 使用简体中文
    );

    // 获取当前运限信息
    const horoscope = astrolabe.horoscope(new Date());
    
    return {
      ...astrolabe,
      horoscope,
      // 基本信息
      solarDate: astrolabe.solarDate,        // 阳历日期
      lunarDate: astrolabe.lunarDate,        // 农历日期
      chineseDate: astrolabe.chineseDate,    // 四柱
      time: astrolabe.time,                  // 时辰
      timeRange: astrolabe.timeRange,        // 时辰对应的时间段
      sign: astrolabe.sign,                  // 星座
      zodiac: astrolabe.zodiac,              // 生肖
      soul: astrolabe.soul,                  // 命主
      body: astrolabe.body,                  // 身主
      fiveElementsClass: astrolabe.fiveElementsClass,  // 五行局
      
      // 宫位信息
      palaces: astrolabe.palaces.map(palace => ({
        name: palace.name,                   // 宫名
        isBodyPalace: palace.isBodyPalace,   // 是否身宫
        isOriginalPalace: palace.isOriginalPalace, // 是否来因宫
        heavenlyStem: palace.heavenlyStem,   // 宫位天干
        earthlyBranch: palace.earthlyBranch, // 宫位地支
        majorStars: palace.majorStars,       // 主星
        minorStars: palace.minorStars,       // 辅星
        adjectiveStars: palace.adjectiveStars // 杂耀
      }))
    };
    
  } catch (error) {
    console.error('计算紫微斗数出错:', error);
    throw error;
  }
}; 