import { Lunar, Solar } from 'lunar-typescript';

export interface LunarInfo {
  year: number;
  month: number;
  day: number;
  yearInGanZhi: string;
  monthInGanZhi: string;
  dayInGanZhi: string;
  hourInGanZhi: string;
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
  naYin: {
    year: string;
    month: string;
    day: string;
    hour: string;
  };
  taiYuan: string;
  mingGong: string;
  shenGong: string;
  
  // 长生十二神
  changSheng: {
    year: string;   // 年柱长生
    month: string;  // 月柱长生
    day: string;    // 日柱长生
    hour: string;   // 时柱长生
  };
  
  // 十神
  shiShen: {
    year: string;
    month: string;
    day: string;
    hour: string;
  };
  
  // 节令
  jieQi: {
    current: string;
    next: string;
    nextDate: string;
  };
  
  // 吉神方位
  jiShen: string[];
  
  // 胎息
  taiXi: string;
  
  // 命卦
  mingGua: string;
  
  // 六亲
  liuQin: {
    year: string;
    month: string;
    day: string;
    hour: string;
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

export function getLunarInfo(date: Date): LunarInfo {
  // 先转换为阳历对象，再获取阴历对象
  const solar = Solar.fromDate(date);
  const lunar = solar.getLunar();
  
  // 获取八字
  const bazi = lunar.getEightChar();
  
  // 获取纳音
  const naYin = {
    year: bazi.getYearNaYin(),
    month: bazi.getMonthNaYin(),
    day: bazi.getDayNaYin(),
    hour: bazi.getTimeNaYin(),
  };

  // 获取节气信息
  const jieQi = {
    current: lunar.getJieQi() || '',
    next: lunar.getNextJieQi()?.getName() || '',
    nextDate: lunar.getNextJieQi()?.getSolar().toYmd() || '',
  };

  // 获取十神
  const shiShen = {
    year: bazi.getYearShiShenGan(),
    month: bazi.getMonthShiShenGan(),
    day: bazi.getDayShiShenGan(),
    hour: bazi.getTimeShiShenGan(),
  };

  // 获取六亲
  const liuQin = {
    year: bazi.getYearXun(),
    month: bazi.getMonthXun(),
    day: bazi.getDayXun(),
    hour: bazi.getTimeXun(),
  };

  // 统计五行 - 使用八字的干支来计算
  const wuXing: { [key: string]: number } = {
    '木': 0, '火': 0, '土': 0, '金': 0, '水': 0
  };

  // 年干支五行
  const yearGan = ganWuXing[bazi.getYear()[0]];
  const yearZhi = zhiWuXing[bazi.getYear()[1]];
  wuXing[yearGan]++;
  wuXing[yearZhi]++;

  // 月干支五行
  const monthGan = ganWuXing[bazi.getMonth()[0]];
  const monthZhi = zhiWuXing[bazi.getMonth()[1]];
  wuXing[monthGan]++;
  wuXing[monthZhi]++;

  // 日干支五行
  const dayGan = ganWuXing[bazi.getDay()[0]];
  const dayZhi = zhiWuXing[bazi.getDay()[1]];
  wuXing[dayGan]++;
  wuXing[dayZhi]++;

  // 时干支五行
  const hourGan = ganWuXing[bazi.getTime()[0]];
  const hourZhi = zhiWuXing[bazi.getTime()[1]];
  wuXing[hourGan]++;
  wuXing[hourZhi]++;

  // 解析干支
  const ganZhi = {
    year: splitGanZhi(bazi.getYear()),
    month: splitGanZhi(bazi.getMonth()),
    day: splitGanZhi(bazi.getDay()),
    hour: splitGanZhi(bazi.getTime()),
  };

  return {
    year: lunar.getYear(),
    month: lunar.getMonth(),
    day: lunar.getDay(),
    yearInGanZhi: bazi.getYear(),
    monthInGanZhi: bazi.getMonth(),
    dayInGanZhi: bazi.getDay(),
    hourInGanZhi: bazi.getTime(),
    yearInChinese: lunar.getYearInChinese(),
    monthInChinese: lunar.getMonthInChinese(),
    dayInChinese: lunar.getDayInChinese(),
    zodiac: lunar.getYearShengXiao(),
    term: lunar.getJieQi(),
    festivals: lunar.getFestivals(),
    constellation: solar.getXingZuo(),
    wuXing,
    ganZhi,
    naYin,
    taiYuan: '',  // 暂时返回空字符串
    mingGong: '', // 暂时返回空字符串
    shenGong: '', // 暂时返回空字符串
    changSheng: {
      year: '',  // 暂时返回空字符串
      month: '', // 暂时返回空字符串
      day: '',   // 暂时返回空字符串
      hour: '',  // 暂时返回空字符串
    },
    shiShen,
    jieQi,
    jiShen: [], // 暂时返回空数组
    taiXi: '',  // 暂时返回空字符串
    mingGua: '', // 暂时返回空字符串
    liuQin,
  };
}

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

// 计算真太阳时
export const getTrueSolarTime = (date: Date, longitude: number = 120): string => {
  // 获取时区偏移（以分钟为单位）
  const timezoneOffset = -date.getTimezoneOffset();
  
  // 经度修正（每偏离基准经度1度，对应4分钟的时差）
  const longitudeCorrection = (longitude - 120) * 4;
  
  // 计算真太阳时（分钟）
  const trueSolarMinutes = date.getHours() * 60 + date.getMinutes() + 
    timezoneOffset + longitudeCorrection;
  
  // 转换为时:分格式
  const hours = Math.floor(trueSolarMinutes / 60) % 24;
  const minutes = Math.floor(trueSolarMinutes % 60);
  
  return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
}; 