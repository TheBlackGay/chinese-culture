import type { Star, Palace, ZiWeiResult, HoroscopeItem } from '@/types/iztro';
import { fetchAstroData, fetchHoroscopeData } from './api';
import type { Scope } from '@/types/iztro';
import { MAJOR_STARS, MINOR_STARS, OTHER_STARS } from '@/constants/ziwei-stars';
import { message } from 'antd';

// 将小时转换为时辰序号(0-11)
function getTimeIndex(hour: number): number {
  // 23:00-00:59 子时 (0)
  if (hour >= 0 || hour < 1) return 0;
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
  if (hour >= 21 && hour < 23) return 11;
  // 21:00-22:59 亥时 (11)
  return 12;
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
function getStarInfo(name: string): Partial<Star> | null {
  const starData = MAJOR_STARS[name] || MINOR_STARS[name] || OTHER_STARS[name];
  if (!starData) return null;

  return {
    name,
    type: starData.type,
    description: starData.description
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

// 将API返回的星盘数据转换为前端需要的格式
function transformApiDataToZiWeiResult(apiData: any): ZiWeiResult {

  // 转换宫位数据
  const palaces: Palace[] = apiData.palaces.map((palace: any) => {
    // 合并主星、辅星和杂耀
    const allStars = [
      ...(palace.majorStars || []),
      ...(palace.minorStars || []),
      ...(palace.adjectiveStars || [])
    ];

    return {
      name: palace.name,
      type: palace.name as any, // 转换宫位类型
      position: palace.index,
      heavenlyStem: palace.heavenlyStem,
      earthlyBranch: palace.earthlyBranch,
      isBodyPalace: palace.isBodyPalace,
      isOriginalPalace: palace.isOriginalPalace,
      stars: allStars.map(star => ({
        name: star.name,
        type: star.type,
        brightness: star.brightness || undefined,
        description: getStarInfo(star.name)?.description,
        transformation: star.mutagen || undefined,
        scope: star.scope || 'origin'
      })),
      changsheng12: palace.changsheng12,
      boshi12: palace.boshi12,
      suiqian12: palace.suiqian12,
      jiangqian12: palace.jiangqian12,
      decadal: palace.decadal,
      ages: palace.ages || []
    };
  });

  // 构建返回结果
  return {
    solarDate: apiData.solarDate,
    lunarDate: apiData.lunarDate,
    gender: apiData.gender,
    time: apiData.time,
    timeRange: apiData.timeRange,
    sign: apiData.sign,
    zodiac: apiData.zodiac,
    palaces,
    soul: apiData.soul,
    body: apiData.body,
    fiveElementsClass: apiData.fiveElementsClass,
    centerInfo: {
      birthTime: apiData.time,
      clockTime: apiData.timeRange,
      lunarBirthDay: apiData.lunarDate,
      fate: apiData.soul,
      bodyFate: apiData.body,
      fiveElements: apiData.fiveElementsClass,
      startAge: apiData.startAge?.toString() || "",
      direction: apiData.flowDirection || ""
    }
  };
}

/**
 * 计算紫微斗数
 * @param birthYear 出生年份
 * @param birthMonth 出生月份
 * @param birthDay 出生日期
 * @param birthHour 出生小时(0-23)
 * @param gender 性别 male/female
 * @param horoscopeParams 运限参数
 * @returns 紫微斗数星盘数据
 */
export const calculateZiWei = async (
  birthYear: number,
  birthMonth: number,
  birthDay: number,
  birthHour: number,
  gender: 'male' | 'female',
  horoscopeParams?: {
    decadal?: {
      index: number;
      startYear: number;
      endYear: number;
    };
    year?: number;
    month?: number;
    day?: number;
    hour?: number;
  }
): Promise<ZiWeiResult> => {
  try {
    // 计算时辰
    const timeIndex = getTimeIndex(birthHour);

    // 格式化日期，确保月和日是两位数
    const formattedMonth = String(birthMonth).padStart(2, '0');
    const formattedDay = String(birthDay).padStart(2, '0');
    const birthDate = `${birthYear}-${formattedMonth}-${formattedDay}`;

    // 性别转换
    const genderText = gender === 'male' ? '男' : '女';

    // 调用API获取命盘数据
    const apiData = await fetchAstroData(
      birthDate,
      timeIndex,
      genderText,
      true,
      'zh-CN'
    );

    // 转换API返回的数据为前端需要的格式
    let result = transformApiDataToZiWeiResult(apiData);

    // 如果有运限参数，计算运限
    if (horoscopeParams) {
      try {
        let horoscopeDate = '';
        let horoscopeTimeIndex = 6; // 默认午时

        if (horoscopeParams.hour !== undefined) {
          // 有年月日时
          horoscopeDate = `${horoscopeParams.year}-${String(horoscopeParams.month).padStart(2, '0')}-${String(horoscopeParams.day).padStart(2, '0')}`;
          horoscopeTimeIndex = horoscopeParams.hour;
        } else if (horoscopeParams.day !== undefined) {
          // 有年月日
          horoscopeDate = `${horoscopeParams.year}-${String(horoscopeParams.month).padStart(2, '0')}-${String(horoscopeParams.day).padStart(2, '0')}`;
        } else if (horoscopeParams.month !== undefined) {
          // 有年月
          horoscopeDate = `${horoscopeParams.year}-${String(horoscopeParams.month).padStart(2, '0')}-01`;
        } else if (horoscopeParams.year !== undefined) {
          // 只有年
          horoscopeDate = `${horoscopeParams.year}-06-01`;
        } else if (horoscopeParams.decadal !== undefined) {
          // 有大限
          horoscopeDate = `${horoscopeParams.decadal.startYear}-06-01`;
        }

        if (horoscopeDate) {
          // 调用API获取运限数据
          const horoscopeData = await fetchHoroscopeData(
            birthDate,
            timeIndex,
            genderText,
            horoscopeDate,
            horoscopeTimeIndex
          );

          // 处理运限数据
          if (horoscopeData) {
            result = {
              ...result,
              decadal: processHoroscope(horoscopeData.decadal, 'decadal'),
              yearly: processHoroscope(horoscopeData.yearly, 'yearly'),
              monthly: processHoroscope(horoscopeData.monthly, 'monthly'),
              daily: processHoroscope(horoscopeData.daily, 'daily'),
              hourly: processHoroscope(horoscopeData.hourly, 'hourly'),
            };
          }
        }
      } catch (error) {
        console.error('运限计算出错:', error);
        message.error('运限计算失败，请重试');
      }
    }

    return result;
  } catch (error) {
    console.error('紫微斗数计算出错:', error);
    message.error('紫微斗数计算失败，请重试');
    throw error;
  }
};

// 处理运限数据
function processHoroscope(horoscopeItem: any, scope: Scope): HoroscopeItem | undefined {
  if (!horoscopeItem) return undefined;

  return {
    index: horoscopeItem.position || 0,
    heavenlyStem: horoscopeItem.heavenlyStem || '',
    earthlyBranch: horoscopeItem.earthlyBranch || '',
    age: horoscopeItem.age,
    startAge: horoscopeItem.startAge,
    endAge: horoscopeItem.endAge,
    palaceNames: horoscopeItem.palaceNames || [],
    mutagen: horoscopeItem.mutagen || [],
    stars: horoscopeItem.stars || [],
    position: horoscopeItem.position,
    flowDirection: horoscopeItem.flowDirection,
    flowYear: horoscopeItem.flowYear,
    direction: horoscopeItem.direction
  };
}

// 测试用例
function test() {
  console.log('Testing 1994-12-08 09:05 男');
  const result = calculateZiWei(1994, 12, 8, 9, 'male');
  console.log('Test result:', JSON.stringify(result, null, 2));
}

// 导出测试函数
export { test as testZiWei };
