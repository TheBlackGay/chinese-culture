import type { Star } from '@/types/iztro';

// 主星
export const MAJOR_STARS: Record<string, Omit<Star, 'name' | 'transformation'>> = {
  紫微: {
    type: '主星',
    category: '吉星',
    wuxing: '土',
    description: '君星，主权威、地位、名声',
  },
  天机: {
    type: '主星',
    category: '吉星',
    wuxing: '木',
    description: '主智慧、谋略、文书',
  },
  太阳: {
    type: '主星',
    category: '吉星',
    wuxing: '火',
    description: '主光明、名誉、父亲',
  },
  武曲: {
    type: '主星',
    category: '中性',
    wuxing: '金',
    description: '主财富、武职、行政',
  },
  天同: {
    type: '主星',
    category: '吉星',
    wuxing: '水',
    description: '主感情、艺术、慈悲',
  },
  廉贞: {
    type: '主星',
    category: '凶星',
    wuxing: '火',
    description: '主正直、固执、孤傲',
  },
  天府: {
    type: '主星',
    category: '吉星',
    wuxing: '土',
    description: '主福德、财富、官禄',
  },
  太阴: {
    type: '主星',
    category: '吉星',
    wuxing: '水',
    description: '主阴柔、文艺、母亲',
  },
  贪狼: {
    type: '主星',
    category: '凶星',
    wuxing: '水',
    description: '主欲望、权力、异性',
  },
  巨门: {
    type: '主星',
    category: '凶星',
    wuxing: '水',
    description: '主口舌、是非、诉讼',
  },
  天相: {
    type: '主星',
    category: '吉星',
    wuxing: '土',
    description: '主仁慈、德行、信用',
  },
  天梁: {
    type: '主星',
    category: '吉星',
    wuxing: '火',
    description: '主官禄、声望、领导',
  },
  七杀: {
    type: '主星',
    category: '凶星',
    wuxing: '金',
    description: '主权威、刚强、竞争',
  },
  破军: {
    type: '主星',
    category: '凶星',
    wuxing: '水',
    description: '主变动、冲击、独立',
  },
};

// 辅星
export const MINOR_STARS: Record<string, Omit<Star, 'name' | 'transformation'>> = {
  文昌: {
    type: '辅星',
    category: '吉星',
    wuxing: '金',
    description: '主文学、才华、考试',
  },
  文曲: {
    type: '辅星',
    category: '吉星',
    wuxing: '水',
    description: '主艺术、技巧、创作',
  },
  左辅: {
    type: '辅星',
    category: '吉星',
    wuxing: '土',
    description: '主助力、支持、贵人',
  },
  右弼: {
    type: '辅星',
    category: '吉星',
    wuxing: '土',
    description: '主辅助、合作、帮助',
  },
};

// 杂耀
export const OTHER_STARS: Record<string, Omit<Star, 'name' | 'transformation'>> = {
  天魁: {
    type: '杂耀',
    category: '吉星',
    wuxing: '水',
    description: '主贵人、学术、名望',
  },
  天钺: {
    type: '杂耀',
    category: '吉星',
    wuxing: '火',
    description: '主权力、地位、声誉',
  },
  禄存: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主财禄、福分、富贵',
  },
  天马: {
    type: '杂耀',
    category: '吉星',
    wuxing: '火',
    description: '主迁移、活动、奔波',
  },
  擎羊: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主冲动、固执、倔强',
  },
  陀罗: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主阻碍、延迟、保守',
  },
  火星: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主冲动、暴躁、意外',
  },
  铃星: {
    type: '杂耀',
    category: '凶星',
    wuxing: '金',
    description: '主口舌、是非、诉讼',
  },
}; 