import { Hexagram } from './types';
import { ALL_HEXAGRAMS } from './hexagrams';

export const HEXAGRAM_DATA = ALL_HEXAGRAMS;

// 辅助函数：根据卦象值获取卦象名称
export function getHexagramName(value: string): string {
  return HEXAGRAM_DATA[value]?.name || '未知卦象';
}

// 辅助函数：检查是否是有效的卦象值
export function isValidHexagram(value: string): boolean {
  return value in HEXAGRAM_DATA;
}

// 辅助函数：将爻值转换为二进制字符串
export function getHexagramKey(lines: number[]): string {
  // 注意：爻是从下往上数的，所以需要反转数组
  return lines.map(line => line % 2 === 1 ? '1' : '0').join('');
}

// 辅助函数：获取变卦
export function getChangedHexagram(lines: number[]): string {
  return lines.map(line => {
    if (line === 6) return '1'; // 老阴变阳
    if (line === 9) return '0'; // 老阳变阴
    return line % 2 === 1 ? '1' : '0'; // 少阳少阴保持不变
  }).join('');
} 