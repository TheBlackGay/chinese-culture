import { Star } from './ziwei';
import { Decadal } from 'iztro/lib/data/types/astro';
import { HeavenlyStemName, EarthlyBranchName, StarName, Brightness, FiveElementsClassName, GenderName, Mutagen } from 'iztro/lib/i18n';

export type Scope = 'origin' | 'decadal' | 'yearly' | 'monthly' | 'daily' | 'hourly';

// 运限参数类型
export interface HoroscopeParams {
  decadal?: {
    index: number;
    solarDate: string;
  };
  year?: {
    index: number;
    solarDate: string;
  };
  month?: {
    index: number;
    solarDate: string;
  };
  day?: {
    index: number;
    solarDate: string;
  };
  hour?: {
    index: number;
    solarDate: string;
  };
}

// 运限信息类型
export interface HoroscopeItem {
  heavenlyStem: HeavenlyStemName;
  earthlyBranch: EarthlyBranchName;
  stars: Star[][];
  startAge?: number;
  endAge?: number;
  direction?: string;
}

// 宫位类型
export type PalaceType =
  | '命宫' | '兄弟' | '夫妻' | '子女'
  | '财帛' | '疾厄' | '迁移' | '交友'
  | '官禄' | '田宅' | '福德' | '父母';

// 宫位接口
export interface Palace {
  name: string;
  type: PalaceType;
  position: number;
  heavenlyStem: HeavenlyStemName;
  earthlyBranch: EarthlyBranchName;
  isBodyPalace: boolean;
  isOriginalPalace: boolean;
  stars: Star[];
  changsheng12?: string;
  boshi12?: string;
  suiqian12?: string;  // 岁前十二神
  jiangqian12?: string;  // 将前十二神
  displayPosition?: {
    row: number;
    col: number;
  };
  decadal?: {
    heavenlyStem: HeavenlyStemName;
    earthlyBranch: EarthlyBranchName;
    range: [number, number];
  };
  ages?: number[];
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
  fiveElementsClass: FiveElementsClassName;

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