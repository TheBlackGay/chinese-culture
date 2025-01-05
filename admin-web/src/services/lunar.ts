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

  // 统计五行
  const wuXing: { [key: string]: number } = {
    '木': 0, '火': 0, '土': 0, '金': 0, '水': 0
  };

  [ganZhi.year, ganZhi.month, ganZhi.day, ganZhi.hour].forEach(gz => {
    const gan = ganWuXing[gz.gan];
    const zhi = zhiWuXing[gz.zhi];
    wuXing[gan]++;
    wuXing[zhi]++;
  });

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