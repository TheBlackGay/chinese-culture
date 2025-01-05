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
  hourInChinese: string;
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
    year: string;   // 年柱纳音
    month: string;  // 月柱纳音
    day: string;    // 日柱纳音
    hour: string;   // 时柱纳音
  };
  taiYuan: string;  // 胎元
  mingGong: string; // 命宫
  shenGong: string; // 身宫
  taiXi: string;    // 胎息
  mingGua: string;  // 命卦
  
  // 神煞
  jiShen: string[];   // 吉神
  xiongSha: string[]; // 凶煞
  
  // 长生十二神
  changSheng: {
    year: string;   // 年柱长生
    month: string;  // 月柱长生
    day: string;    // 日柱长生
    hour: string;   // 时柱长生
  };
  
  // 十神
  shiShen: {
    year: string;   // 年柱十神
    month: string;  // 月柱十神
    day: string;    // 日柱十神
    hour: string;   // 时柱十神
  };
  
  // 节令
  jieQi: {
    current: string;      // 当前节气
    currentDate: string;  // 当前节气日期
    currentDays: number;  // 距离当前节气的天数
    currentHours: number; // 距离当前节气的小时数
    next: string;         // 下一节气
    nextDate: string;     // 下一节气日期
    nextDays: number;     // 距离下一节气的天数
    nextHours: number;    // 距离下一节气的小时数
  };
  
  // 六亲
  liuQin: {
    year: string;   // 年柱六亲
    month: string;  // 月柱六亲
    day: string;    // 日柱六亲
    hour: string;   // 时柱六亲
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

// 时辰对应表
const zhiToShichen: Record<string, string> = {
  '子': '子时', '丑': '丑时', '寅': '寅时', '卯': '卯时',
  '辰': '辰时', '巳': '巳时', '午': '午时', '未': '未时',
  '申': '申时', '酉': '酉时', '戌': '戌时', '亥': '亥时'
};

export function getLunarInfo(date: Date): LunarInfo {
  const lunar = Lunar.fromDate(date);
  const currentJieQi = lunar.getPrevJieQi(true);  // 获取当前节气
  const nextJieQi = lunar.getNextJieQi(true);     // 获取下一个节气
  
  // 计算与当前节气的间隔
  const currentJieQiDate = currentJieQi.getSolar().toYmd();
  const currentJieQiDateTime = new Date(currentJieQiDate);
  const diffFromCurrent = Math.abs(date.getTime() - currentJieQiDateTime.getTime());
  const currentDays = Math.floor(diffFromCurrent / (24 * 60 * 60 * 1000));
  const currentHours = Math.floor((diffFromCurrent % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000));

  // 计算与下一个节气的间隔
  const nextJieQiDate = nextJieQi.getSolar().toYmd();
  const nextJieQiDateTime = new Date(nextJieQiDate);
  const diffToNext = Math.abs(nextJieQiDateTime.getTime() - date.getTime());
  const nextDays = Math.floor(diffToNext / (24 * 60 * 60 * 1000));
  const nextHours = Math.floor((diffToNext % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000));

  // 获取时辰
  const hourGan = lunar.getTimeGan();
  const hourZhi = lunar.getTimeZhi();
  const hourInGanZhi = hourGan + hourZhi;
  const hourInChinese = zhiToShichen[hourZhi] || '';

  return {
    year: lunar.getYear(),
    month: lunar.getMonth(),
    day: lunar.getDay(),
    yearInGanZhi: lunar.getYearInGanZhi(),
    monthInGanZhi: lunar.getMonthInGanZhi(),
    dayInGanZhi: lunar.getDayInGanZhi(),
    hourInGanZhi: hourInGanZhi,
    yearInChinese: lunar.getYearInChinese(),
    monthInChinese: lunar.getMonthInChinese(),
    dayInChinese: lunar.getDayInChinese(),
    hourInChinese: hourInChinese,
    zodiac: lunar.getYearShengXiao(),
    constellation: getZodiacSign(date),
    term: lunar.getJieQi(),
    festivals: lunar.getFestivals(),
    wuXing: {
      '木': 0, '火': 0, '土': 0, '金': 0, '水': 0
    },
    ganZhi: {
      year: splitGanZhi(lunar.getYearInGanZhi()),
      month: splitGanZhi(lunar.getMonthInGanZhi()),
      day: splitGanZhi(lunar.getDayInGanZhi()),
      hour: splitGanZhi(hourInGanZhi)
    },
    naYin: {
      year: '',
      month: '',
      day: '',
      hour: ''
    },
    taiYuan: '',
    mingGong: '',
    shenGong: '',
    taiXi: '',
    mingGua: '',
    jiShen: [],
    xiongSha: [],
    changSheng: {
      year: '',
      month: '',
      day: '',
      hour: ''
    },
    shiShen: {
      year: '',
      month: '',
      day: '',
      hour: ''
    },
    jieQi: {
      current: currentJieQi.getName(),
      currentDate: currentJieQiDate,
      currentDays: currentDays,
      currentHours: currentHours,
      next: nextJieQi.getName(),
      nextDate: nextJieQiDate,
      nextDays: nextDays,
      nextHours: nextHours
    },
    liuQin: {
      year: '',
      month: '',
      day: '',
      hour: ''
    }
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