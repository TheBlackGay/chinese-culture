// 星耀类型
export interface Star {
  name: string;
  type: '主星' | '辅星' | '杂耀';
  category: '吉星' | '凶星' | '中性';
  wuxing: '金' | '木' | '水' | '火' | '土';
  description: string;
}

// 宫位类型
export type PalaceType = 
  | '命宫' | '兄弟' | '夫妻' | '子女'
  | '财帛' | '疾厄' | '迁移' | '交友'
  | '官禄' | '田宅' | '福德' | '父母';

// 宫位
export interface Palace {
  name: string;
  type: PalaceType;
  position: number;
  heavenlyStem: string;
  earthlyBranch: string;
  stars: Star[];
  transformations?: string[];
}

// 紫微斗数结果
export interface ZiWeiResult {
  // 基本信息
  solarDate: string;
  lunarDate: string;
  gender: '男' | '女';
  time: string;
  timeRange: string;
  sign: string;
  zodiac: string;
  
  // 命盘信息
  palaces: Palace[];
  
  // 命主身主
  soul: string;
  body: string;
  
  // 五行局
  fiveElementsClass: string;
  
  // 中宫信息
  centerInfo: {
    birthTime: string;
    clockTime: string;
    lunarBirthDay: string;
    fate: string;
    bodyFate: string;
    fiveElements: string;
    startAge: string;
    direction: string;
  };
} 