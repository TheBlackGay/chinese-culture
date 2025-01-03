import { Hexagram, Yao, WuXing, BaGong } from './types';
import { HEXAGRAM_DATA } from './data';

// 计算某个时间点的卦象
export function calculateHexagramByTime(timestamp: number): string {
  const date = new Date(timestamp);
  const hours = date.getHours();
  const minutes = date.getMinutes();
  
  // 使用时间生成六爻
  const lines = [];
  for (let i = 0; i < 6; i++) {
    const value = ((hours + minutes + i) * (i + 1)) % 4 + 6; // 6-9
    lines.push(value);
  }
  
  return getHexagramKey(lines);
}

// 计算两个卦象是否有关系
export function getHexagramRelationship(hex1: string, hex2: string): string | null {
  if (hex1 === getInverseHexagram(hex2)) return 'inverse';
  if (hex1 === getOppositeHexagram(hex2)) return 'opposite';
  if (hex1 === getMutualHexagram(hex2)) return 'mutual';
  if (hex1 === getNuclearHexagram(hex2)) return 'nuclear';
  return null;
}

// 获取综卦（上下颠倒）
export function getInverseHexagram(hex: string): string {
  return hex.split('').reverse().join('');
}

// 获取对宫卦（阴阳互换）
export function getOppositeHexagram(hex: string): string {
  return hex.split('').map(bit => bit === '1' ? '0' : '1').join('');
}

// 获取互卦
export function getMutualHexagram(hex: string): string {
  const lines = hex.split('');
  // 互卦取第2、3、4爻作为下卦，第3、4、5爻作为上卦
  return [lines[2], lines[3], lines[4], lines[3], lines[4], lines[5]].join('');
}

// 获取互卦
export function getNuclearHexagram(hex: string): string {
  const lines = hex.split('');
  // 互卦取第2、3、4爻作为下卦，第3、4、5爻作为上卦
  return [lines[1], lines[2], lines[3], lines[2], lines[3], lines[4]].join('');
}

// 获取卦的五行属性
export function getHexagramElement(hex: string): WuXing {
  const elements: Record<string, WuXing> = {
    '111111': '金', // 乾
    '000000': '土', // 坤
    '010101': '木', // 震
    '101010': '火', // 离
    // ... 其他卦象的五行属性
  };
  return elements[hex] || '土';
}

// 获取卦的八宫属性
export function getHexagramPalace(hex: string): BaGong {
  const palaces: Record<string, BaGong> = {
    '111111': '乾',
    '000000': '坤',
    '010101': '震',
    '101010': '离',
    '001111': '兑',
    '111100': '艮',
    '110011': '坎',
    '001100': '巽',
  };
  return palaces[hex] || '坤';
}

// 将爻值转换为二进制字符串
export function getHexagramKey(lines: number[]): string {
  return lines.map(line => line % 2 === 1 ? '1' : '0').join('');
}

// 获取变卦
export function getChangedHexagram(lines: number[]): string {
  return lines.map(line => {
    if (line === 6) return '1'; // 老阴变阳
    if (line === 9) return '0'; // 老阳变阴
    return line % 2 === 1 ? '1' : '0'; // 少阳少阴保持不变
  }).join('');
}

// 获取卦象的吉凶
export function getHexagramLuck(hex: string): '大吉' | '中吉' | '小吉' | '凶' {
  const luckMap: Record<string, '大吉' | '中吉' | '小吉' | '凶'> = {
    '111111': '大吉', // 乾
    '000000': '中吉', // 坤
    '010101': '小吉', // 震
    '101010': '大吉', // 离
    // ... 其他卦象的吉凶
  };
  return luckMap[hex] || '凶';
}

// 根据干支计算卦象
export function calculateHexagramByGanZhi(
  year: string,
  month: string,
  day: string,
  hour: string
): string {
  // 这里需要实现干支计算逻辑
  // 临时返回一个示例卦象
  return '111111';
}

// 获取卦象的详细解释
export function getHexagramInterpretation(hex: string, type: string): string {
  const hexagram = HEXAGRAM_DATA[hex];
  if (!hexagram) return '未知卦象';
  
  switch (type) {
    case '事业':
      return hexagram.meaning.career;
    case '财运':
      return hexagram.meaning.wealth;
    case '感情':
      return hexagram.meaning.love;
    case '健康':
      return hexagram.meaning.health;
    default:
      return hexagram.meaning.general;
  }
} 