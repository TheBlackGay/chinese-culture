import { Solar, Lunar } from 'lunar-typescript';

export interface LunarInfo {
  year: number;
  month: number;
  day: number;
  yearInGanZhi: string;
  monthInGanZhi: string;
  dayInGanZhi: string;
  yearInChinese: string;
  monthInChinese: string;
  dayInChinese: string;
  zodiac: string;
  term: string | null;
  festivals: string[];
  constellation: string;
  wuXing: {
    [key: string]: number;  // 五行统计
  };
  ganZhi: {
    year: { gan: string; zhi: string; wuXing: string };
    month: { gan: string; zhi: string; wuXing: string };
    day: { gan: string; zhi: string; wuXing: string };
    hour: { gan: string; zhi: string; wuXing: string };
  };
}

// 天干五行对应
const ganWuXing: { [key: string]: string } = {
  '甲': '木', '乙': '木',
  '丙': '火', '丁': '火',
  '戊': '土', '己': '土',
  '庚': '金', '辛': '金',
  '壬': '水', '癸': '水'
};

// 地支五行对应
const zhiWuXing: { [key: string]: string } = {
  '子': '水', '丑': '土', '寅': '木', '卯': '木',
  '辰': '土', '巳': '火', '午': '火', '未': '土',
  '申': '金', '酉': '金', '戌': '土', '亥': '水'
};

// 分解天干地支
const splitGanZhi = (ganZhi: string) => {
  const gan = ganZhi[0];
  const zhi = ganZhi[1];
  return {
    gan,
    zhi,
    wuXing: ganWuXing[gan] + zhiWuXing[zhi]
  };
};

export const getLunarInfo = (date: Date = new Date()): LunarInfo => {
  const solar = Solar.fromDate(date);
  const lunar = solar.getLunar();
  const baZi = lunar.getBaZi();

  // 解析八字
  const [yearGZ, monthGZ, dayGZ, hourGZ] = baZi;
  const ganZhi = {
    year: splitGanZhi(yearGZ),
    month: splitGanZhi(monthGZ),
    day: splitGanZhi(dayGZ),
    hour: splitGanZhi(hourGZ)
  };

  // 统计五行 - 使用农历时间的干支来计算
  const wuXing: { [key: string]: number } = {
    '木': 0, '火': 0, '土': 0, '金': 0, '水': 0
  };

  // 年干支五行
  const yearGan = ganWuXing[lunar.getYearGan()];
  const yearZhi = zhiWuXing[lunar.getYearZhi()];
  wuXing[yearGan]++;
  wuXing[yearZhi]++;

  // 月干支五行
  const monthGan = ganWuXing[lunar.getMonthGan()];
  const monthZhi = zhiWuXing[lunar.getMonthZhi()];
  wuXing[monthGan]++;
  wuXing[monthZhi]++;

  // 日干支五行
  const dayGan = ganWuXing[lunar.getDayGan()];
  const dayZhi = zhiWuXing[lunar.getDayZhi()];
  wuXing[dayGan]++;
  wuXing[dayZhi]++;

  // 时干支五行
  const hourGan = ganWuXing[lunar.getTimeGan()];
  const hourZhi = zhiWuXing[lunar.getTimeZhi()];
  wuXing[hourGan]++;
  wuXing[hourZhi]++;

  return {
    year: lunar.getYear(),
    month: lunar.getMonth(),
    day: lunar.getDay(),
    yearInGanZhi: yearGZ,
    monthInGanZhi: monthGZ,
    dayInGanZhi: dayGZ,
    yearInChinese: lunar.getYearInChinese(),
    monthInChinese: lunar.getMonthInChinese(),
    dayInChinese: lunar.getDayInChinese(),
    zodiac: lunar.getYearShengXiao(),
    term: lunar.getJieQi(),
    festivals: [...lunar.getFestivals(), ...solar.getFestivals()],
    constellation: solar.getXingZuo(),
    wuXing,
    ganZhi
  };
};

export const getNextTerm = (date: Date = new Date()): { name: string; date: Date } => {
  const solar = Solar.fromDate(date);
  const lunar = solar.getLunar();
  const nextJieQi = lunar.getNextJieQi();
  return {
    name: nextJieQi.getName(),
    date: nextJieQi.getSolar().toDate(),
  };
};

export const getBaZi = (date: Date = new Date(), hour: number = 0): string[] => {
  const lunar = Lunar.fromDate(date);
  return lunar.getBaZi();
};

// 获取星座
export const getZodiacSign = (date: Date): string => {
  const month = date.getMonth() + 1;
  const day = date.getDate();

  const zodiacDates = [
    { name: '摩羯座', startMonth: 12, startDay: 22, endMonth: 1, endDay: 19 },
    { name: '水瓶座', startMonth: 1, startDay: 20, endMonth: 2, endDay: 18 },
    { name: '双鱼座', startMonth: 2, startDay: 19, endMonth: 3, endDay: 20 },
    { name: '白羊座', startMonth: 3, startDay: 21, endMonth: 4, endDay: 19 },
    { name: '金牛座', startMonth: 4, startDay: 20, endMonth: 5, endDay: 20 },
    { name: '双子座', startMonth: 5, startDay: 21, endMonth: 6, endDay: 21 },
    { name: '巨蟹座', startMonth: 6, startDay: 22, endMonth: 7, endDay: 22 },
    { name: '狮子座', startMonth: 7, startDay: 23, endMonth: 8, endDay: 22 },
    { name: '处女座', startMonth: 8, startDay: 23, endMonth: 9, endDay: 22 },
    { name: '天秤座', startMonth: 9, startDay: 23, endMonth: 10, endDay: 23 },
    { name: '天蝎座', startMonth: 10, startDay: 24, endMonth: 11, endDay: 22 },
    { name: '射手座', startMonth: 11, startDay: 23, endMonth: 12, endDay: 21 }
  ];

  // 处理跨年的情况（摩羯座）
  if (month === 12 && day >= 22 || month === 1 && day <= 19) {
    return '摩羯座';
  }

  // 其他星座
  const zodiac = zodiacDates.find(z => {
    if (month === z.startMonth) {
      return day >= z.startDay;
    }
    if (month === z.endMonth) {
      return day <= z.endDay;
    }
    return false;
  });

  return zodiac?.name || '未知';
}; 