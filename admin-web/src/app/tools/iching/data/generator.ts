import { Hexagram, Yao } from '../types';
import { BASE_HEXAGRAMS, TRIGRAMS } from './base';
import {
  getInverseHexagram,
  getOppositeHexagram,
  getMutualHexagram,
  getNuclearHexagram,
} from '../utils';

// 生成单个爻的数据
function generateYao(value: number, position: number, text: string): Yao {
  return {
    value,
    position,
    text,
    image: '', // 需要补充象辞
    isChanging: value === 6 || value === 9,
  };
}

// 生成完整卦象数据
function generateHexagram(key: string): Hexagram {
  const base = BASE_HEXAGRAMS[key];
  if (!base) throw new Error(`未找到卦象数据：${key}`);

  // 生成六爻数据
  const lines: Yao[] = base.yaoTexts.map((text, index) => {
    // 根据二进制位确定爻的性质（阴阳）
    const isYang = key[5 - index] === '1';
    // 6: 老阴, 7: 少阳, 8: 少阴, 9: 老阳
    const value = isYang ? 7 : 8;
    return generateYao(value, index + 1, text);
  });

  // 计算关系卦
  const relationships = {
    opposite: getOppositeHexagram(key),
    inverse: getInverseHexagram(key),
    mutual: getMutualHexagram(key),
    nuclear: getNuclearHexagram(key),
  };

  return {
    ...base,
    lines,
    relationships,
  };
}

// 生成所有卦象数据
export function generateAllHexagrams(): Record<string, Hexagram> {
  const hexagrams: Record<string, Hexagram> = {};

  // 遍历所有可能的六位二进制组合
  for (let i = 0; i < 64; i++) {
    const key = i.toString(2).padStart(6, '0');
    try {
      hexagrams[key] = generateHexagram(key);
    } catch (error) {
      console.warn(`生成卦象数据失败：${key}`, error);
    }
  }

  return hexagrams;
}

// 获取上下卦的名称
export function getTrigramNames(hex: string): { upper: string; lower: string } {
  const upper = hex.slice(0, 3);
  const lower = hex.slice(3);
  return {
    upper: TRIGRAMS[upper]?.name || '未知',
    lower: TRIGRAMS[lower]?.name || '未知',
  };
}

// 获取卦象的完整名称（包含上下卦）
export function getFullHexagramName(hex: string): string {
  const { upper, lower } = getTrigramNames(hex);
  const base = BASE_HEXAGRAMS[hex];
  if (!base) return `${upper}${lower}`;
  return `${base.name}（${upper}${lower}）`;
}

// 导出完整的卦象数据
export const HEXAGRAM_DATA = generateAllHexagrams(); 