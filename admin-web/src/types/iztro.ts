// 运限范围类型
export type Scope = 'origin' | 'decadal' | 'yearly' | 'monthly' | 'daily' | 'hourly';

// 运限信息类型
export interface HoroscopeItem {
  index: number;
  heavenlyStem: string;
  earthlyBranch: string;
  age?: number;
  palaceNames: string[];
  mutagen: string[];
  stars: Star[][];
}

// iztro 运限信息接口
export interface IFunctionalHoroscope {
  decadal?: {
    age?: number;
    direction?: string;
    heavenlyStem?: string;
    earthlyBranch?: string;
    palaceNames?: string[];
    stars?: Star[][];
  };
  yearly?: {
    age?: number;
    heavenlyStem?: string;
    earthlyBranch?: string;
    palaceNames?: string[];
    stars?: Star[][];
  };
  monthly?: {
    age?: number;
    heavenlyStem?: string;
    earthlyBranch?: string;
    palaceNames?: string[];
    stars?: Star[][];
  };
  daily?: {
    age?: number;
    heavenlyStem?: string;
    earthlyBranch?: string;
    palaceNames?: string[];
    stars?: Star[][];
  };
  hourly?: {
    age?: number;
    heavenlyStem?: string;
    earthlyBranch?: string;
    palaceNames?: string[];
    stars?: Star[][];
  };
}

// 星耀类型
export interface Star {
  name: string;
  type: '主星' | '辅星' | '杂耀';
  category: '吉星' | '凶星' | '中性';
  wuxing: '金' | '木' | '水' | '火' | '土';
  description: string;
  brightness?: '庙' | '旺' | '得' | '利' | '平' | '不' | '陷';
  scope: Scope;
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
  isBodyPalace: boolean;
  isOriginalPalace: boolean;
  stars: Star[];
  transformations: string[];
  changsheng12?: string;
  boshi12?: string;
  displayPosition?: {
    row: number;
    col: number;
  };
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

  // 运限信息
  decadal?: HoroscopeItem;
  yearly?: HoroscopeItem;
  monthly?: HoroscopeItem;
  daily?: HoroscopeItem;
  hourly?: HoroscopeItem;
} 