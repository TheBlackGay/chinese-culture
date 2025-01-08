import {Decadal} from "iztro/lib/data/types/astro";

const { astro } = require('iztro');

interface Palace {
  heavenlyStem: string;
  earthlyBranch: string;
  name: string;
  index: number;
  isBodyPalace: boolean;
  isOriginalPalace: boolean;
  majorStars: any[];
  minorStars: any[];
  adjectiveStars: any[];
  changsheng12: string;
  boshi12: string;
  mutagen: string[];
  // 大限
  decadal: Decadal;
  // 小限
  ages: number[];
}

interface Horoscope {
  palaces: Palace[];
  [key: string]: any;
}

// 在计算紫微斗数时调用此函数
function processHoroscope(horoscope: Horoscope): Horoscope {
  if (!horoscope || !horoscope.palaces) {
    console.error('Invalid horoscope data');
    return horoscope;
  }

  // 打印命盘的所有可用属性
  console.log('=== 紫微斗数命盘数据 ===');
  console.log('1. 基本信息：', {
    solarDate: horoscope.solarDate,         // 阳历日期
    lunarDate: horoscope.lunarDate,         // 农历日期
    time: horoscope.time,                   // 时辰
    timeRange: horoscope.timeRange,         // 时辰范围
    sign: horoscope.sign,                   // 星座
    zodiac: horoscope.zodiac,               // 生肖
    gender: horoscope.gender,               // 性别
  });

  console.log('2. 命主身主：', {
    soul: horoscope.soul,                   // 命主
    body: horoscope.body,                   // 身主
  });

  console.log('3. 五行局：', {
    fiveElementsClass: horoscope.fiveElementsClass, // 五行局
  });

  // 打印第一个宫位的详细信息作为示例
  if (horoscope.palaces?.[0]) {
    const samplePalace = horoscope.palaces[0];
    console.log('4. 宫位示例（第一个宫位）：', {
      name: samplePalace.name,              // 宫位名称
      index: samplePalace.index,            // 宫位索引
      heavenlyStem: samplePalace.heavenlyStem,    // 天干
      earthlyBranch: samplePalace.earthlyBranch,  // 地支
      isBodyPalace: samplePalace.isBodyPalace,    // 是否身宫
      isOriginalPalace: samplePalace.isOriginalPalace,  // 是否命宫
      majorStars: samplePalace.majorStars,        // 主星
      minorStars: samplePalace.minorStars,        // 辅星
      adjectiveStars: samplePalace.adjectiveStars,  // 杂耀
      changsheng12: samplePalace.changsheng12,    // 长生十二神
      boshi12: samplePalace.boshi12,              // 博士十二神
      mutagen: samplePalace.mutagen,              // 四化
    });
  }

  // 尝试获取其他可能的属性
  console.log('5. 其他可能的属性：');
  const otherProps = Object.keys(horoscope).filter(key =>
    !['palaces', 'solarDate', 'lunarDate', 'time', 'timeRange', 'sign',
      'zodiac', 'gender', 'soul', 'body', 'fiveElementsClass'].includes(key)
  );
  otherProps.forEach(prop => {
    try {
      console.log(`${prop}:`, horoscope[prop]);
    } catch (error) {
      console.log(`${prop}: [无法访问]`);
    }
  });

  // 尝试调用可能存在的方法
  console.log('6. 可能的方法调用结果：');
  const methodsToTry = [
    'getAge',
    'getStartAge',
    'getFlowYear',
    'getFlowDirection',
    'getDecadalAge',
    'getDecadalDirection',
    'horoscope'
  ];

  methodsToTry.forEach(method => {
    try {
      if (typeof horoscope[method] === 'function') {
        console.log(`${method}:`, horoscope[method]());
      }
    } catch (error) {
      console.log(`${method}: [调用失败]`);
    }
  });

  // 处理宫位信息时，只提取需要的属性
  const processedPalaces = horoscope.palaces.map(palace => ({
    heavenlyStem: palace.heavenlyStem,
    earthlyBranch: palace.earthlyBranch,
    name: palace.name,
    index: palace.index,
    isBodyPalace: palace.isBodyPalace || false,
    isOriginalPalace: palace.isOriginalPalace || false,
    majorStars: palace.majorStars || [],
    minorStars: palace.minorStars || [],
    adjectiveStars: palace.adjectiveStars || [],
    changsheng12: palace.changsheng12,
    boshi12: palace.boshi12,
    mutagen: palace.mutagen || []
  }));

  return {
    ...horoscope,
    palaces: processedPalaces
  };
}

module.exports = {
  processHoroscope
};
