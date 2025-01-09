import { astro } from 'iztro';
import type {
  HeavenlyStemName,
  EarthlyBranchName,
  StarName,
  Brightness,
  FiveElementsClassName,
  GenderName,
  Mutagen
} from 'iztro/lib/i18n';
import type { Star, Palace, ZiWeiResult, PalaceType, Scope, HoroscopeItem, IFunctionalHoroscope } from '@/types/iztro';
import { MAJOR_STARS, MINOR_STARS, OTHER_STARS } from '@/constants/ziwei-stars';

// 将小时转换为时辰序号(0-11)
function getTimeIndex(hour: number): number {
  // 23:00-00:59 子时 (0)
  if (hour >= 23 || hour < 1) return 0;
  // 01:00-02:59 丑时 (1)
  if (hour >= 1 && hour < 3) return 1;
  // 03:00-04:59 寅时 (2)
  if (hour >= 3 && hour < 5) return 2;
  // 05:00-06:59 卯时 (3)
  if (hour >= 5 && hour < 7) return 3;
  // 07:00-08:59 辰时 (4)
  if (hour >= 7 && hour < 9) return 4;
  // 09:00-10:59 巳时 (5)
  if (hour >= 9 && hour < 11) return 5;
  // 11:00-12:59 午时 (6)
  if (hour >= 11 && hour < 13) return 6;
  // 13:00-14:59 未时 (7)
  if (hour >= 13 && hour < 15) return 7;
  // 15:00-16:59 申时 (8)
  if (hour >= 15 && hour < 17) return 8;
  // 17:00-18:59 酉时 (9)
  if (hour >= 17 && hour < 19) return 9;
  // 19:00-20:59 戌时 (10)
  if (hour >= 19 && hour < 21) return 10;
  // 21:00-22:59 亥时 (11)
  return 11;
}

// 获取宫位类型
function getPalaceType(index: number): string {
  const palaceTypes = [
    '命宫', '兄弟', '夫妻', '子女',
    '财帛', '田宅', '官禄', '迁移',
    '疾厄', '福德', '田宅', '父母'
  ];
  return palaceTypes[index];
}

// 获取星耀信息
function getStarInfo(name: string): Star | null {
  const starData = MAJOR_STARS[name] || MINOR_STARS[name] || OTHER_STARS[name];
  if (!starData) return null;

  return {
    name,
    type: starData.type,
    category: starData.category,
    wuxing: starData.wuxing,
    description: starData.description,
    scope: 'origin' // 默认为本命星耀
  };
}

// 处理星耀信息
function processStars(stars: any[], type: '主星' | '辅星' | '杂耀'): Star[] {
  return stars.map(star => {
    const starInfo = getStarInfo(star.name);
    if (!starInfo) {
      console.warn(`Unknown star: ${star.name}`);
      return null;
    }

    // 打印星耀的原始数据
    console.log(`Processing star:`, JSON.stringify(star));

    let transformation = star.transformation || undefined;
    let transformationType = undefined;
    if (transformation) {
      transformationType = transformation.sihuaLu || transformation.sihuaQuan || transformation.sihuaKe || transformation.sihuaJi
    }

    return {
      ...starInfo,
      type,
      brightness: star.brightness,
      scope: star.scope || 'origin',
      transformation: star.transformation || undefined,
      transformationType: transformationType
    } as Star;
  }).filter((star): star is Star => star !== null);
}

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
): ZiWeiResult => {
  try {
    // 计算时辰
    const timeIndex = getTimeIndex(birthHour);

    // 格式化日期，确保月和日是两位数
    const formattedMonth = String(birthMonth).padStart(2, '0');
    const formattedDay = String(birthDay).padStart(2, '0');

    // 使用 iztro 的 bySolar 方法计算命盘
    const horoscope = astro.bySolar(
      `${birthYear}-${formattedMonth}-${formattedDay}`,
      timeIndex,
      gender === 'male' ? '男' : '女',
      true
    );

    console.log('Input params:', {
      date: `${birthYear}-${formattedMonth}-${formattedDay}`,
      timeIndex,
      gender: gender === 'male' ? '男' : '女'
    });

    // 打印 horoscope 对象的方法和属性
    console.log('horoscope methods:', Object.getOwnPropertyNames(Object.getPrototypeOf(horoscope)));
    console.log('horoscope properties:', Object.keys(horoscope));

    // 尝试调用所有可能的方法
    const methodsToTry = [
      'getStartAge',
      'getFlowDirection',
      'getAge',
      'getDirection',
      'getStartYear',
      'getFlowYear',
      'getDecadalAge',
      'getDecadalDirection'
    ];

    methodsToTry.forEach(method => {
      try {
        if (typeof (horoscope as any)[method] === 'function') {
          console.log(`${method} result:`, (horoscope as any)[method]());
        }
      } catch (error) {
        console.warn(`调用 ${method} 失败:`, error);
      }
    });

    // 尝试直接获取起运年龄和流年方向
    try {
      console.log('getStartAge:', typeof horoscope.getStartAge, horoscope.getStartAge?.());
      console.log('getFlowDirection:', typeof horoscope.getFlowDirection, horoscope.getFlowDirection?.());
    } catch (error) {
      console.warn('获取起运年龄和流年方向失败:', error);
    }

    // 获取宫位数据
    let tempPalaces: Palace[] = new Array(12);
    let palaces: Palace[] = new Array(12);

    // 1. 先获取所有宫位数据
    for (let i = 0; i < 12; i++) {
      const palace = horoscope.palace(i);

      if (!palace) {
        console.warn(`Missing palace data for index ${i}`);
        continue;
      }

      // 打印原始宫位数据，特别关注四化信息
      console.log(`Palace ${i} raw data:`, palace);
      console.log(`Palace ${i} stars:`, palace.majorStars);
      console.log(`Palace ${i} suiqian12:`, palace.suiqian12);
      console.log(`Palace ${i} jiangqian12:`, palace.jiangqian12);

      // 创建宫位对象
      const simplePalace = {
        index: palace.index,
        name: palace.name,
        isBodyPalace: palace.isBodyPalace || false,
        isOriginalPalace: palace.isOriginalPalace || false,
        heavenlyStem: palace.heavenlyStem,
        earthlyBranch: palace.earthlyBranch,
        majorStars: palace.majorStars?.map(star => ({
          ...star,
          transformation: star.mutagen
        })) || [],
        minorStars: palace.minorStars?.map(star => ({
          ...star,
          transformation: star.mutagen
        })) || [],
        adjectiveStars: palace.adjectiveStars?.map(star => ({
          ...star,
          transformation: star.mutagen
        })) || [],
        changsheng12: palace.changsheng12,
        boshi12: palace.boshi12,
        suiqian12: palace.suiqian12,  // 岁前十二神
        jiangqian12: palace.jiangqian12,  // 将前十二神
        decadal: palace.decadal,
        ages: palace.ages
      };

      // 处理各类星耀
      const majorStars = processStars(simplePalace.majorStars, '主星');
      const minorStars = processStars(simplePalace.minorStars, '辅星');
      const adjectiveStars = processStars(simplePalace.adjectiveStars, '杂耀');

      // 打印处理后的星耀数据
      console.log(`Palace ${i} processed stars:`, { majorStars, minorStars, adjectiveStars });

      // 合并所有星耀
      const stars = [...majorStars, ...minorStars, ...adjectiveStars];

      // 先把宫位数据存储到临时数组
      tempPalaces[i] = {
        name: simplePalace.name,
        type: simplePalace.name as PalaceType,
        position: i + 1,
        heavenlyStem: simplePalace.heavenlyStem,
        earthlyBranch: simplePalace.earthlyBranch,
        isBodyPalace: simplePalace.isBodyPalace,
        isOriginalPalace: simplePalace.isOriginalPalace,
        stars,
        // transformations: Object.entries(simplePalace.transformations).map(([star, trans]) =>
        //   `${star}${(trans as any).type}化xxx`
        // ),
        changsheng12: simplePalace.changsheng12,
        boshi12: simplePalace.boshi12,
        suiqian12: palace.suiqian12,  // 岁前十二神
        jiangqian12: palace.jiangqian12,  // 将前十二神
        decadal: simplePalace.decadal,
        ages: simplePalace.ages
      };
    }

    // 2. 定义地支的显示位置（4x4布局）
    const branchToDisplayPosition: { [key: string]: { row: number; col: number; position: number } } = {
      '寅': { row: 3, col: 0, position: 3 },  // 左下角
      '卯': { row: 2, col: 0, position: 4 },
      '辰': { row: 1, col: 0, position: 5 },
      '巳': { row: 0, col: 0, position: 6 },  // 左上角
      '午': { row: 0, col: 1, position: 7 },
      '未': { row: 0, col: 2, position: 8 },
      '申': { row: 0, col: 3, position: 9 },  // 右上角
      '酉': { row: 1, col: 3, position: 10 },
      '戌': { row: 2, col: 3, position: 11 },
      '亥': { row: 3, col: 3, position: 12 }, // 右下角
      '子': { row: 3, col: 2, position: 1 },
      '丑': { row: 3, col: 1, position: 2 }
    };

    // 3. 根据地支重新排列宫位
    for (let i = 0; i < 12; i++) {
      const palace = tempPalaces[i];
      if (!palace) continue;

      // 获取显示位置和序号
      const displayInfo = branchToDisplayPosition[palace.earthlyBranch];

      // 放到对应位置
      palaces[i] = {
        ...palace,
        position: displayInfo.position,  // 使用地支对应的序号
        displayPosition: {
          row: displayInfo.row,
          col: displayInfo.col
        }
      };
    }

    // 4. 按照位置序号排序
    palaces.sort((a, b) => {
      if (!a || !b) return 0;
      return a.position - b.position;
    });

    console.log('final palaces:', palaces);

    // 获取当前运限信息
    const horoscopeInfo = horoscope.horoscope();

    // 尝试从 horoscope 对象获取
    console.log('Raw horoscope data:', horoscopeInfo);

    // 获取起运年龄和流年方向
    let startAge = horoscope.getStartAge?.() || '未知';
    let direction = horoscope.getFlowDirection?.() || '顺行';

    // 获取大限数据
    const decadalInfo = {
      index: horoscopeInfo.decadal?.index,
      heavenlyStem: horoscopeInfo.decadal?.heavenlyStem,
      earthlyBranch: horoscopeInfo.decadal?.earthlyBranch,
      age: horoscopeInfo.decadal?.age,
      // 根据五行局数计算起运年龄
      startAge: (() => {
        const fiveElementsToAge: { [key: string]: number } = {
          '水二局': 2,
          '水六局': 6,
          '金四局': 4,
          '金九局': 9,
          '火六局': 6,
          '火七局': 7,
          '木三局': 3,
          '木八局': 8,
          '土五局': 5,
          '土十局': 10
        };
        const baseAge = fiveElementsToAge[horoscope.fiveElementsClass] || 0;
        return baseAge + (horoscopeInfo.decadal?.index || 0) * 10;
      })(),
      endAge: (() => {
        const fiveElementsToAge: { [key: string]: number } = {
          '水二局': 2,
          '水六局': 6,
          '金四局': 4,
          '金九局': 9,
          '火六局': 6,
          '火七局': 7,
          '木三局': 3,
          '木八局': 8,
          '土五局': 5,
          '土十局': 10
        };
        const baseAge = fiveElementsToAge[horoscope.fiveElementsClass] || 0;
        return baseAge + (horoscopeInfo.decadal?.index || 0) * 10 + 9;
      })(),
      palaceNames: horoscopeInfo.decadal?.palaceNames || [],
      mutagen: horoscopeInfo.decadal?.mutagen || [],
      stars: horoscopeInfo.decadal?.stars || [],
      position: horoscopeInfo.decadal?.position,
      flowDirection: direction,
      flowYear: horoscopeInfo.decadal?.flowYear,
      direction: direction
    };

    console.log('Decadal info:', decadalInfo);

    // 处理运限信息
    function processHoroscope(horoscopeItem: any, scope: Scope): HoroscopeItem | undefined {
      if (!horoscopeItem) return undefined;

      // 如果是大限数据，使用我们之前处理好的数据
      if (scope === 'decadal') {
        return decadalInfo;
      }

      return {
        index: horoscopeItem.index || 0,
        heavenlyStem: horoscopeItem.heavenlyStem || '',
        earthlyBranch: horoscopeItem.earthlyBranch || '',
        age: horoscopeItem.age,
        startAge: horoscopeItem.startAge,
        endAge: horoscopeItem.endAge,
        palaceNames: horoscopeItem.palaceNames || [],
        mutagen: horoscopeItem.mutagen || [],
        stars: Array.isArray(horoscopeItem.stars)
          ? horoscopeItem.stars.map((starGroup: any[]) =>
              Array.isArray(starGroup)
                ? starGroup.map(star => ({
                    name: star.name || '',
                    type: star.type || '杂耀',
                    category: star.category || '中性',
                    wuxing: star.wuxing || '土',
                    description: star.description || '',
                    brightness: star.brightness,
                    scope
                  }))
                : []
            )
          : [],
        position: horoscopeItem.position,
        flowDirection: horoscopeItem.flowDirection,
        flowYear: horoscopeItem.flowYear,
        direction: horoscopeItem.direction
      };
    }

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
      suiqian12: palace.suiqian12 || '',  // 岁前十二神
      jiangqian12: palace.jiangqian12 || '',  // 将前十二神
      decadal: palace.decadal,
      ages: palace.ages
    }));

    return {
      // 基本信息
      solarDate: horoscope.solarDate,
      lunarDate: horoscope.lunarDate,
      gender: gender === 'male' ? '男' : '女',
      time: horoscope.time,
      timeRange: horoscope.timeRange,
      sign: horoscope.sign,
      zodiac: horoscope.zodiac,

      // 命盘信息
      palaces,

      // 命主身主
      soul: horoscope.soul,
      body: horoscope.body,

      // 五行局
      fiveElementsClass: horoscope.fiveElementsClass,

      // 中宫信息
      centerInfo: {
        birthTime: `${birthYear}-${String(birthMonth).padStart(2, '0')}-${String(birthDay).padStart(2, '0')} ${birthHour}:00`,
        clockTime: `${birthHour}:00`,
        lunarBirthDay: horoscope.lunarDate,
        fate: horoscope.soul,
        bodyFate: horoscope.body,
        fiveElements: horoscope.fiveElementsClass,
        startAge: startAge === '未知' ? '未知' : `${startAge}岁`,
        direction: direction === '顺行' ? '顺行' : '逆行',
      },

      // 运限信息
      decadal: processHoroscope(horoscopeInfo.decadal, 'decadal'),
      yearly: processHoroscope(horoscopeInfo.yearly, 'yearly'),
      monthly: processHoroscope(horoscopeInfo.monthly, 'monthly'),
      daily: processHoroscope(horoscopeInfo.daily, 'daily'),
      hourly: processHoroscope(horoscopeInfo.hourly, 'hourly')
    };
  } catch (error) {
    console.error('计算紫微斗数出错:', error);
    throw error;
  }
};

// 测试用例
function test() {
  console.log('Testing 1994-12-08 09:05 男');
  const result = calculateZiWei(1994, 12, 8, 9, 'male');
  console.log('Test result:', JSON.stringify(result, null, 2));
}

// 导出测试函数
export { test as testZiWei };
