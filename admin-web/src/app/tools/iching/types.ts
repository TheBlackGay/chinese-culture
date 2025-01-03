// 单个爻的属性
export interface Yao {
  value: number;      // 6:老阴, 7:少阳, 8:少阴, 9:老阳
  isChanging: boolean;
}

// 完整卦象的结构
export interface Hexagram {
  name: string;
  description: string;
  yaoTexts: string[];
  lines: Yao[];
}

// 历史记录的结构
export interface HistoryRecord {
  timestamp: number;
  hexagram: Hexagram;
  notes?: string;
} 