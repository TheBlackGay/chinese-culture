import { Hexagram } from '../types';

// 计算某个时间点的卦象
export const calculateHexagram = (date: Date): string => {
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();

  // 使用时间各个部分计算卦象
  const yearValue = year % 8;
  const monthValue = month % 8;
  const dayValue = day % 8;
  const hourValue = hour % 8;
  const minuteValue = minute % 8;

  // 将各个值转换为二进制
  const binary = (yearValue ^ monthValue ^ dayValue ^ hourValue ^ minuteValue).toString(2).padStart(6, '0');
  return binary;
};

// 获取卦象关系
export const getHexagramRelationships = (hexagramKey: string) => {
  // 对卦：上下卦相反
  const opposite = hexagramKey.split('').map(bit => bit === '1' ? '0' : '1').join('');

  // 综卦：上下卦互换
  const inverse = hexagramKey.slice(3) + hexagramKey.slice(0, 3);

  // 互卦：第2、3、4爻为上卦，第3、4、5爻为下卦
  const mutual = hexagramKey.slice(1, 4) + hexagramKey.slice(2, 5);

  // 核卦：去掉初爻和上爻后的六爻
  const nuclear = hexagramKey.slice(1, 4) + hexagramKey.slice(2, 5);

  return {
    opposite,
    inverse,
    mutual,
    nuclear,
  };
};

// 计算手动投掷的结果
export const calculateManualHexagram = (coins: number[][]): string => {
  return coins.map(throwResult => {
    const sum = throwResult.reduce((a, b) => a + b, 0);
    // 根据三枚铜钱的结果计算爻
    // 6: 老阴 (0)
    // 7: 少阳 (1)
    // 8: 少阴 (0)
    // 9: 老阳 (1)
    return sum === 7 || sum === 9 ? '1' : '0';
  }).join('');
};

// 获取变卦
export const getChangedHexagram = (coins: number[][]): string => {
  return coins.map(throwResult => {
    const sum = throwResult.reduce((a, b) => a + b, 0);
    // 只有老阴(6)和老阳(9)会变化
    if (sum === 6) return '1'; // 老阴变阳
    if (sum === 9) return '0'; // 老阳变阴
    return sum === 7 ? '1' : '0'; // 保持不变
  }).join('');
};

// 判断是否为有效的卦象值
export const isValidHexagramValue = (value: number): boolean => {
  return value === 6 || value === 7 || value === 8 || value === 9;
};

// 获取卦象的五行属性
export const getHexagramElement = (hexagram: string): string => {
  const upperTrigram = hexagram.slice(0, 3);
  const lowerTrigram = hexagram.slice(3);
  
  const elementMap: Record<string, string> = {
    '111': '金',
    '000': '土',
    '010': '木',
    '101': '火',
    '011': '金',
    '100': '土',
    '001': '水',
    '110': '木',
  };

  return elementMap[upperTrigram] || elementMap[lowerTrigram] || '未知';
}; 