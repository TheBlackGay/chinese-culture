// 单个爻的属性
export interface Yao {
  value: number;      // 6:老阴, 7:少阳, 8:少阴, 9:老阳
  isChanging: boolean;
  text: string;       // 爻辞
  image: string;      // 象辞
  position: number;   // 爻位
}

// 卦象的五行属性
export type WuXing = '金' | '木' | '水' | '火' | '土';

// 卦象的八宫属性
export type BaGong = '乾' | '兑' | '离' | '震' | '巽' | '坎' | '艮' | '坤';

// 完整卦象的结构
export interface Hexagram {
  key: string;        // 二进制表示，如："111111" 表示乾卦
  name: string;       // 卦名
  description: string;// 卦辞
  yaoTexts: string[]; // 爻辞
  lines: Yao[];       // 爻
  image: string;      // 象辞
  sequence: number;   // 序号（1-64）
  nature: string;     // 性质（如：刚健、柔顺等）
  element: WuXing;    // 五行属性
  palace: BaGong;     // 八宫属性
  meaning: {
    general: string;  // 总体含义
    love: string;     // 感情
    career: string;   // 事业
    health: string;   // 健康
    wealth: string;   // 财运
  };
  relationships: {    // 卦与卦的关系
    opposite: string; // 对宫卦
    inverse: string;  // 综卦
    mutual: string;   // 互卦
    nuclear: string;  // 互卦
  };
}

// 历史记录的结构
export interface HistoryRecord {
  id: string;         // 唯一标识
  timestamp: number;  // 时间戳
  hexagram: Hexagram; // 本卦
  changed?: Hexagram; // 变卦
  question: string;   // 问题
  notes?: string;     // 笔记
  tags?: string[];    // 标签
}

// 卦象解释的结构
export interface HexagramInterpretation {
  text: string;       // 解释文本
  source: string;     // 来源（如：《周易》、《易传》等）
  author?: string;    // 作者/注释者
  dynasty?: string;   // 朝代
  notes?: string;     // 注释
}

// 卦象预测的类型
export type DivinationType = 
  | '事业' 
  | '财运' 
  | '感情' 
  | '婚姻' 
  | '学业' 
  | '健康' 
  | '出行' 
  | '官司' 
  | '寻物' 
  | '其他'; 