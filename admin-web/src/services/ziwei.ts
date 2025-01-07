import { astro } from 'iztro';
import type { Star, Palace, ZiWeiResult, PalaceType } from '@/types/iztro';
import { MAJOR_STARS, MINOR_STARS, OTHER_STARS } from '@/constants/ziwei-stars';

// 将小时转换为时辰序号(0-11)
function getTimeIndex(hour: number): number {
  // 23:00-01:00 子时 (0)
  if (hour >= 23 || hour < 1) return 0;
  // 01:00-03:00 丑时 (1)
  if (hour >= 1 && hour < 3) return 1;
  // 03:00-05:00 寅时 (2)
  if (hour >= 3 && hour < 5) return 2;
  // 05:00-07:00 卯时 (3)
  if (hour >= 5 && hour < 7) return 3;
  // 07:00-09:00 辰时 (4)
  if (hour >= 7 && hour < 9) return 4;
  // 09:00-11:00 巳时 (5)
  if (hour >= 9 && hour < 11) return 5;
  // 11:00-13:00 午时 (6)
  if (hour >= 11 && hour < 13) return 6;
  // 13:00-15:00 未时 (7)
  if (hour >= 13 && hour < 15) return 7;
  // 15:00-17:00 申时 (8)
  if (hour >= 15 && hour < 17) return 8;
  // 17:00-19:00 酉时 (9)
  if (hour >= 17 && hour < 19) return 9;
  // 19:00-21:00 戌时 (10)
  if (hour >= 19 && hour < 21) return 10;
  // 21:00-23:00 亥时 (11)
  return 11;
}

// 获取宫位类型
function getPalaceType(index: number): PalaceType {
  const types: PalaceType[] = [
    '命宫', '父母', '福德', '田宅',
    '官禄', '交友', '迁移', '疾厄',
    '财帛', '子女', '夫妻', '兄弟'
  ];
  return types[index];
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
    description: starData.description
  };
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
    // 格式化日期
    const formattedMonth = String(birthMonth).padStart(2, '0');
    const formattedDay = String(birthDay).padStart(2, '0');
    
    // 计算时辰
    const timeIndex = getTimeIndex(birthHour);
    
    // 使用 iztro 计算命盘
    const horoscope = astro.bySolar(
      `${birthYear}-${formattedMonth}-${formattedDay}`,
      timeIndex,
      gender === 'male' ? '男' : '女',
      true,
      'zh-CN'
    );

    console.log('horoscope:', horoscope); // 添加调试信息

    // 获取宫位数据
    const palaces: Palace[] = [];
    
    // 遍历宫位
    const palaceData = horoscope.palace;
    console.log('palaceData:', palaceData); // 添加调试信息

    if (palaceData) {
      // 确保我们有12个宫位
      for (let i = 0; i < 12; i++) {
        // 获取宫位数据
        const palace = typeof palaceData === 'function' ? palaceData(i) : palaceData[i];
        console.log(`palace ${i}:`, palace); // 添加调试信息

        if (!palace) {
          console.warn(`Missing palace data for index ${i}`);
          continue;
        }

        // 获取星耀信息
        const stars = (palace.stars || [])
          .map((name: string) => {
            const star = getStarInfo(name);
            if (!star) {
              console.warn(`Unknown star: ${name}`);
            }
            return star;
          })
          .filter((star): star is Star => star !== null);

        // 构建宫位数据
        palaces.push({
          name: palace.name || `宫位${i + 1}`,
          type: getPalaceType(i),
          position: i + 1,
          heavenlyStem: palace.heavenlyStem || '',
          earthlyBranch: palace.earthlyBranch || '',
          stars,
          transformations: palace.transformations || [],
        });
      }
    } else {
      console.error('No palace data available');
    }

    console.log('final palaces:', palaces); // 添加调试信息

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
        birthTime: `${birthYear}-${formattedMonth}-${formattedDay} ${birthHour}:00`,
        clockTime: `${birthHour}:00`,
        lunarBirthDay: horoscope.lunarDate,
        fate: horoscope.soul,
        bodyFate: horoscope.body,
        fiveElements: horoscope.fiveElementsClass,
        startAge: '未知', // TODO: 计算起运年龄
        direction: '未知', // TODO: 计算流年方向
      }
    };
  } catch (error) {
    console.error('计算紫微斗数出错:', error);
    throw error;
  }
}; 