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

// 纳音五行对应表
const naYinWuXing: { [key: string]: string } = {
  '甲子': '海中金', '乙丑': '海中金',
  '丙寅': '炉中火', '丁卯': '炉中火',
  '戊辰': '大林木', '己巳': '大林木',
  '庚午': '路旁土', '辛未': '路旁土',
  '壬申': '剑锋金', '癸酉': '剑锋金',
  '甲戌': '山头火', '乙亥': '山头火',
  '丙子': '涧下水', '丁丑': '涧下水',
  '戊寅': '城头土', '己卯': '城头土',
  '庚辰': '白蜡金', '辛巳': '白蜡金',
  '壬午': '杨柳木', '癸未': '杨柳木',
  '甲申': '泉中水', '乙酉': '泉中水',
  '丙戌': '屋上土', '丁亥': '屋上土',
  '戊子': '霹雳火', '己丑': '霹雳火',
  '庚寅': '松柏木', '辛卯': '松柏木',
  '壬辰': '长流水', '癸巳': '长流水',
  '甲午': '沙中金', '乙未': '沙中金',
  '丙申': '山下火', '丁酉': '山下火',
  '戊戌': '平地木', '己亥': '平地木',
  '庚子': '壁上土', '辛丑': '壁上土',
  '壬寅': '金箔金', '癸卯': '金箔金',
  '甲辰': '覆灯火', '乙巳': '覆灯火',
  '丙午': '天河水', '丁未': '天河水',
  '戊申': '大驿土', '己酉': '大驿土',
  '庚戌': '钗钏金', '辛亥': '钗钏金',
  '壬子': '桑柘木', '癸丑': '桑柘木',
  '甲寅': '大溪水', '乙卯': '大溪水',
  '丙辰': '沙中土', '丁巳': '沙中土',
  '戊午': '天上火', '己未': '天上火',
  '庚申': '石榴木', '辛酉': '石榴木',
  '壬戌': '大海水', '癸亥': '大海水'
};

// 六亲映射表（以日干五行为主）
const liuQinMap: { [key: string]: { [key: string]: string } } = {
  '木': { '木': '兄弟', '火': '子孙', '土': '妻财', '金': '官鬼', '水': '父母' },
  '火': { '木': '父母', '火': '兄弟', '土': '子孙', '金': '妻财', '水': '官鬼' },
  '土': { '木': '官鬼', '火': '父母', '土': '兄弟', '金': '子孙', '水': '妻财' },
  '金': { '木': '妻财', '火': '官鬼', '土': '父母', '金': '兄弟', '水': '子孙' },
  '水': { '木': '子孙', '火': '妻财', '土': '官鬼', '金': '父母', '水': '兄弟' }
};

// 天干十神对应表（以日干为主）
const shiShenMap: { [key: string]: { [key: string]: string } } = {
  '甲': { '甲': '比肩', '乙': '劫财', '丙': '食神', '丁': '伤官', '戊': '偏财', '己': '正财', '庚': '七杀', '辛': '正官', '壬': '偏印', '癸': '正印' },
  '乙': { '甲': '劫财', '乙': '比肩', '丙': '食神', '丁': '伤官', '戊': '偏财', '己': '正财', '庚': '七杀', '辛': '正官', '壬': '偏印', '癸': '正印' },
  '丙': { '甲': '偏印', '乙': '正印', '丙': '比肩', '丁': '劫财', '戊': '食神', '己': '伤官', '庚': '偏财', '辛': '正财', '壬': '七杀', '癸': '正官' },
  '丁': { '甲': '偏印', '乙': '正印', '丙': '劫财', '丁': '比肩', '戊': '食神', '己': '伤官', '庚': '偏财', '辛': '正财', '壬': '七杀', '癸': '正官' },
  '戊': { '甲': '七杀', '乙': '正官', '丙': '偏印', '丁': '正印', '戊': '比肩', '己': '劫财', '庚': '食神', '辛': '伤官', '壬': '偏财', '癸': '正财' },
  '己': { '甲': '七杀', '乙': '正官', '丙': '偏印', '丁': '正印', '戊': '劫财', '己': '比肩', '庚': '食神', '辛': '伤官', '壬': '偏财', '癸': '正财' },
  '庚': { '甲': '正财', '乙': '偏财', '丙': '七杀', '丁': '正官', '戊': '偏印', '己': '正印', '庚': '比肩', '辛': '劫财', '壬': '食神', '癸': '伤官' },
  '辛': { '甲': '正财', '乙': '偏财', '丙': '七杀', '丁': '正官', '戊': '偏印', '己': '正印', '庚': '劫财', '辛': '比肩', '壬': '食神', '癸': '伤官' },
  '壬': { '甲': '伤官', '乙': '食神', '丙': '正财', '丁': '偏财', '戊': '七杀', '己': '正官', '庚': '偏印', '辛': '正印', '壬': '比肩', '癸': '劫财' },
  '癸': { '甲': '伤官', '乙': '食神', '丙': '正财', '丁': '偏财', '戊': '七杀', '己': '正官', '庚': '偏印', '辛': '正印', '壬': '劫财', '癸': '比肩' }
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

  // 计算八字
  const yearGanZhi = splitGanZhi(lunar.getYearInGanZhi());
  const monthGanZhi = splitGanZhi(lunar.getMonthInGanZhi());
  const dayGanZhi = splitGanZhi(lunar.getDayInGanZhi());
  const hourGanZhi = splitGanZhi(hourInGanZhi);

  // 计算五行统计
  const wuXing = {
    '木': 0, '火': 0, '土': 0, '金': 0, '水': 0
  };

  // 统计天干五行
  [yearGanZhi.gan, monthGanZhi.gan, dayGanZhi.gan, hourGanZhi.gan].forEach(gan => {
    const element = ganWuXing[gan];
    if (element) wuXing[element]++;
  });

  // 统计地支五行
  [yearGanZhi.zhi, monthGanZhi.zhi, dayGanZhi.zhi, hourGanZhi.zhi].forEach(zhi => {
    const element = zhiWuXing[zhi];
    if (element) wuXing[element]++;
  });

  // 计算纳音五行
  const naYin = {
    year: naYinWuXing[lunar.getYearInGanZhi()] || '',
    month: naYinWuXing[lunar.getMonthInGanZhi()] || '',
    day: naYinWuXing[lunar.getDayInGanZhi()] || '',
    hour: naYinWuXing[hourInGanZhi] || ''
  };

  // 计算十神和六亲
  const yearTiangan = lunar.getYearGan();
  const monthTiangan = lunar.getMonthGan();
  const dayTiangan = lunar.getDayGan();
  const hourTiangan = lunar.getTimeGan();

  const yearDizhi = lunar.getYearZhi();
  const monthDizhi = lunar.getMonthZhi();
  const dayDizhi = lunar.getDayZhi();
  const hourDizhi = lunar.getTimeZhi();

  // 计算十神（以日干为主）
  const shiShen = {
    year: shiShenMap[dayTiangan]?.[yearTiangan] || '',
    month: shiShenMap[dayTiangan]?.[monthTiangan] || '',
    day: '比肩', // 日柱天干与日主相同，固定为比肩
    hour: shiShenMap[dayTiangan]?.[hourTiangan] || ''
  };

  // 计算六亲（以日干五行为主）
  const dayGanWuXing = ganWuXing[dayTiangan];
  const liuQin = {
    year: liuQinMap[dayGanWuXing][zhiWuXing[yearDizhi]] || '',
    month: liuQinMap[dayGanWuXing][zhiWuXing[monthDizhi]] || '',
    day: liuQinMap[dayGanWuXing][zhiWuXing[dayDizhi]] || '',
    hour: liuQinMap[dayGanWuXing][zhiWuXing[hourDizhi]] || ''
  };

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
    wuXing,
    ganZhi: {
      year: yearGanZhi,
      month: monthGanZhi,
      day: dayGanZhi,
      hour: hourGanZhi
    },
    naYin,
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
    shiShen,
    liuQin,
    jieQi: {
      current: currentJieQi.getName(),
      currentDate: currentJieQiDate,
      currentDays: currentDays,
      currentHours: currentHours,
      next: nextJieQi.getName(),
      nextDate: nextJieQiDate,
      nextDays: nextDays,
      nextHours: nextHours
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