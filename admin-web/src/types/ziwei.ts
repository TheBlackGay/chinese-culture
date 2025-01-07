// 星耀类型
export interface Star {
  name: string;
  type: '主星' | '辅星' | '杂耀';
  category: '吉星' | '凶星' | '中性';
  wuxing: '金' | '木' | '水' | '火' | '土';
  color: string;
  description: string;
  transformation?: {
    type: '禄' | '权' | '科' | '忌';
    source: string;
  };
}

// 宫位类型
export interface Palace {
  name: string;
  position: number;
  stars: Star[];
  combinations: string[];
}

// 紫微斗数结果类型
export interface ZiWeiResult {
  palaces: Palace[];
  birthYear: number;
  birthMonth: number;
  birthDay: number;
  birthHour: number;
  gender: '男' | '女';
  solarDate: string;
  lunarDate: string;
  mingGong: number;
  shenGong: number;
  wuxing: string;
}

// 星耀强度类型
export interface StarStrength {
  庙: string[];
  旺: string[];
  平: string[];
  闲: string[];
  陷: string[];
}

// 星耀组合类型
export interface StarCombination {
  stars: string[];
  name: string;
  type: '吉格' | '凶格';
  description: string;
  effect: string;
}

// 四化规则类型
export interface Transformation {
  禄: string;
  权: string;
  科: string;
  忌: string;
} 