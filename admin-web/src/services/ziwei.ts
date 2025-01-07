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
import type { Star, Palace, ZiWeiResult, PalaceType, Scope, HoroscopeItem } from '@/types/iztro';
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
    return {
      ...starInfo,
      type,
      brightness: star.brightness,
      scope: star.scope || 'origin'
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
    console.log('horoscope:', JSON.stringify(horoscope, null, 2));

    // 获取宫位数据
    const palaces: Palace[] = [];
    
    // 遍历宫位
    for (let i = 0; i < 12; i++) {
      // 使用 palace() 方法获取宫位数据
      const palace = horoscope.palace(i);
      
      // 创建一个不包含循环引用的宫位对象
      const simplePalace = {
        index: palace.index,
        name: palace.name,
        isBodyPalace: palace.isBodyPalace || false,
        isOriginalPalace: palace.isOriginalPalace || false,
        heavenlyStem: palace.heavenlyStem,
        earthlyBranch: palace.earthlyBranch,
        majorStars: palace.majorStars || [],
        minorStars: palace.minorStars || [],
        adjectiveStars: palace.adjectiveStars || [],
        changsheng12: palace.changsheng12,
        boshi12: palace.boshi12,
        mutagen: palace.mutagen || []
      };

      if (!palace) {
        console.warn(`Missing palace data for index ${i}`);
        continue;
      }

      // 处理各类星耀
      const majorStars = processStars(simplePalace.majorStars, '主星');
      const minorStars = processStars(simplePalace.minorStars, '辅星');
      const adjectiveStars = processStars(simplePalace.adjectiveStars, '杂耀');

      // 合并所有星耀
      const stars = [...majorStars, ...minorStars, ...adjectiveStars];

      // 构建宫位数据
      palaces.push({
        name: simplePalace.name,
        type: simplePalace.name,
        position: i + 1,
        heavenlyStem: simplePalace.heavenlyStem,
        earthlyBranch: simplePalace.earthlyBranch,
        isBodyPalace: simplePalace.isBodyPalace,
        isOriginalPalace: simplePalace.isOriginalPalace,
        stars,
        transformations: simplePalace.mutagen,
        changsheng12: simplePalace.changsheng12,
        boshi12: simplePalace.boshi12
      });
    }

    console.log('final palaces:', palaces); // 添加调试信息

    // 获取当前运限信息
    const horoscopeInfo = horoscope.horoscope();

    // 处理运限信息
    function processHoroscope(horoscopeItem: any, scope: Scope): HoroscopeItem | undefined {
      if (!horoscopeItem) return undefined;

      return {
        index: horoscopeItem.index,
        heavenlyStem: horoscopeItem.heavenlyStem,
        earthlyBranch: horoscopeItem.earthlyBranch,
        age: scope === 'decadal' 
          ? Math.floor((new Date().getFullYear() - birthYear) / 10) * 10
          : scope === 'yearly' 
            ? new Date().getFullYear() - birthYear 
            : undefined,
        palaceNames: horoscopeItem.palaceNames || [],
        mutagen: horoscopeItem.mutagen || [],
        stars: (horoscopeItem.stars || []).map((starGroup: any[]) =>
          starGroup.map(star => ({
            name: star.name,
            type: star.type,
            category: star.category,
            wuxing: star.wuxing,
            description: star.description,
            brightness: star.brightness,
            scope
          }))
        )
      };
    }

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
        startAge: '未知', // TODO: 计算起运年龄
        direction: '未知', // TODO: 计算流年方向
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