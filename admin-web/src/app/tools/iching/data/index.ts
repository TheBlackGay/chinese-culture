export * from './base';
export * from './generator';

// 重新导出主要的数据和工具函数
export {
  HEXAGRAM_DATA,
  TRIGRAMS,
  getTrigramNames,
  getFullHexagramName,
} from './generator'; 