import type { Star } from '@/types/iztro';

// 主星
export const MAJOR_STARS: Record<string, Omit<Star, 'name' | 'transformation'>> = {
  紫微: {
    type: '主星',
    category: '吉星',
    wuxing: '土',
    description: '君星，主权威、地位、名声',
    scope: 'origin'
  },
  天机: {
    type: '主星',
    category: '吉星',
    wuxing: '木',
    description: '主智慧、谋略、文书',
    scope: 'origin'
  },
  太阳: {
    type: '主星',
    category: '吉星',
    wuxing: '火',
    description: '主光明、名誉、父亲',
    scope: 'origin'
  },
  武曲: {
    type: '主星',
    category: '中性',
    wuxing: '金',
    description: '主财富、武职、行政',
    scope: 'origin'
  },
  天同: {
    type: '主星',
    category: '吉星',
    wuxing: '水',
    description: '主感情、艺术、慈悲',
    scope: 'origin'
  },
  廉贞: {
    type: '主星',
    category: '凶星',
    wuxing: '火',
    description: '主正直、固执、孤傲',
    scope: 'origin'
  },
  天府: {
    type: '主星',
    category: '吉星',
    wuxing: '土',
    description: '主福德、财富、官禄',
    scope: 'origin'
  },
  太阴: {
    type: '主星',
    category: '吉星',
    wuxing: '水',
    description: '主阴柔、文艺、母亲',
    scope: 'origin'
  },
  贪狼: {
    type: '主星',
    category: '凶星',
    wuxing: '水',
    description: '主欲望、权力、异性',
    scope: 'origin'
  },
  巨门: {
    type: '主星',
    category: '凶星',
    wuxing: '水',
    description: '主口舌、是非、诉讼',
    scope: 'origin'
  },
  天相: {
    type: '主星',
    category: '吉星',
    wuxing: '土',
    description: '主仁慈、德行、信用',
    scope: 'origin'
  },
  天梁: {
    type: '主星',
    category: '吉星',
    wuxing: '火',
    description: '主官禄、声望、领导',
    scope: 'origin'
  },
  七杀: {
    type: '主星',
    category: '凶星',
    wuxing: '金',
    description: '主权威、刚强、竞争',
    scope: 'origin'
  },
  破军: {
    type: '主星',
    category: '凶星',
    wuxing: '水',
    description: '主变动、冲击、独立',
    scope: 'origin'
  },
};

// 辅星
export const MINOR_STARS: Record<string, Omit<Star, 'name' | 'transformation'>> = {
  文昌: {
    type: '辅星',
    category: '吉星',
    wuxing: '金',
    description: '主文学、才华、考试',
    scope: 'origin'
  },
  文曲: {
    type: '辅星',
    category: '吉星',
    wuxing: '水',
    description: '主艺术、技巧、创作',
    scope: 'origin'
  },
  左辅: {
    type: '辅星',
    category: '吉星',
    wuxing: '土',
    description: '主助力、支持、贵人',
    scope: 'origin'
  },
  右弼: {
    type: '辅星',
    category: '吉星',
    wuxing: '土',
    description: '主辅助、合作、帮助',
    scope: 'origin'
  },
};

// 杂耀
export const OTHER_STARS: Record<string, Omit<Star, 'name' | 'transformation'>> = {
  天魁: {
    type: '杂耀',
    category: '吉星',
    wuxing: '水',
    description: '主贵人、学术、名望',
    scope: 'origin'
  },
  天钺: {
    type: '杂耀',
    category: '吉星',
    wuxing: '火',
    description: '主权力、地位、声誉',
    scope: 'origin'
  },
  禄存: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主财禄、福分、富贵',
    scope: 'origin'
  },
  天马: {
    type: '杂耀',
    category: '吉星',
    wuxing: '火',
    description: '主迁移、活动、奔波',
    scope: 'origin'
  },
  擎羊: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主冲动、固执、倔强',
    scope: 'origin'
  },
  陀罗: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主阻碍、延迟、保守',
    scope: 'origin'
  },
  火星: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主冲动、暴躁、意外',
    scope: 'origin'
  },
  铃星: {
    type: '杂耀',
    category: '凶星',
    wuxing: '金',
    description: '主口舌、是非、诉讼',
    scope: 'origin'
  },
  地空: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主虚无、空想、损失',
    scope: 'origin'
  },
  地劫: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主劫难、变故、损失',
    scope: 'origin'
  },
  红鸾: {
    type: '杂耀',
    category: '吉星',
    wuxing: '木',
    description: '主婚姻、感情、艺术',
    scope: 'origin'
  },
  天喜: {
    type: '杂耀',
    category: '吉星',
    wuxing: '木',
    description: '主婚姻、喜庆、艺术',
    scope: 'origin'
  },
  天巫: {
    type: '杂耀',
    category: '中性',
    wuxing: '水',
    description: '主信仰、神秘、医药',
    scope: 'origin'
  },
  天使: {
    type: '杂耀',
    category: '凶星',
    wuxing: '金',
    description: '主疾病、灾祸、忧郁',
    scope: 'origin'
  },
  咸池: {
    type: '杂耀',
    category: '凶星',
    wuxing: '水',
    description: '主感情、娱乐、享受',
    scope: 'origin'
  },
  天寿: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主长寿、健康、平安',
    scope: 'origin'
  },
  月德: {
    type: '杂耀',
    category: '吉星',
    wuxing: '水',
    description: '主德行、声望、和气',
    scope: 'origin'
  },
  天才: {
    type: '杂耀',
    category: '吉星',
    wuxing: '木',
    description: '主才智、学习、技能',
    scope: 'origin'
  },
  天厨: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主饮食、享受、福禄',
    scope: 'origin'
  },
  解神: {
    type: '杂耀',
    category: '吉星',
    wuxing: '木',
    description: '主解难、化解、救助',
    scope: 'origin'
  },
  阴煞: {
    type: '杂耀',
    category: '凶星',
    wuxing: '水',
    description: '主阴谋、暗算、隐患',
    scope: 'origin'
  },
  三台: {
    type: '杂耀',
    category: '吉星',
    wuxing: '木',
    description: '主升迁、进步、发展',
    scope: 'origin'
  },
  八座: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主地位、名望、稳固',
    scope: 'origin'
  },
  天官: {
    type: '杂耀',
    category: '吉星',
    wuxing: '金',
    description: '主官禄、财富、名誉',
    scope: 'origin'
  },
  天德: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主德行、贵人、和谐',
    scope: 'origin'
  },
  寡宿: {
    type: '杂耀',
    category: '凶星',
    wuxing: '水',
    description: '主孤独、寂寞、分离',
    scope: 'origin'
  },
  天刑: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主刑伤、官非、灾祸',
    scope: 'origin'
  },
  旬空: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主虚空、延迟、无果',
    scope: 'origin'
  },
  截空: {
    type: '杂耀',
    category: '凶星',
    wuxing: '金',
    description: '主阻碍、中断、损失',
    scope: 'origin'
  },
  恩光: {
    type: '杂耀',
    category: '吉星',
    wuxing: '火',
    description: '主恩惠、提拔、援助',
    scope: 'origin'
  },
  天福: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主福德、吉祥、幸福',
    scope: 'origin'
  },
  空亡: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主虚无、损失、无果',
    scope: 'origin'
  },
  华盖: {
    type: '杂耀',
    category: '中性',
    wuxing: '水',
    description: '主独特、清高、孤傲',
    scope: 'origin'
  },
  天月: {
    type: '杂耀',
    category: '吉星',
    wuxing: '水',
    description: '主文艺、才华、美好',
    scope: 'origin'
  },
  天姚: {
    type: '杂耀',
    category: '中性',
    wuxing: '火',
    description: '主感情、艺术、美貌',
    scope: 'origin'
  },
  天空: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主虚空、损失、无果',
    scope: 'origin'
  },
  孤辰: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主孤独、寂寞、分离',
    scope: 'origin'
  },
  蜚廉: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主口舌、是非、诽谤',
    scope: 'origin'
  },
  天伤: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主伤害、意外、灾祸',
    scope: 'origin'
  },
  天贵: {
    type: '杂耀',
    category: '吉星',
    wuxing: '金',
    description: '主贵人、名誉、地位',
    scope: 'origin'
  },
  龙池: {
    type: '杂耀',
    category: '吉星',
    wuxing: '水',
    description: '主文艺、才华、声誉',
    scope: 'origin'
  },
  天虚: {
    type: '杂耀',
    category: '凶星',
    wuxing: '土',
    description: '主虚幻、欺诈、损失',
    scope: 'origin'
  },
  封诰: {
    type: '杂耀',
    category: '吉星',
    wuxing: '金',
    description: '主名誉、职位、荣耀',
    scope: 'origin'
  },
  天哭: {
    type: '杂耀',
    category: '凶星',
    wuxing: '水',
    description: '主忧愁、悲伤、疾病',
    scope: 'origin'
  },
  台辅: {
    type: '杂耀',
    category: '吉星',
    wuxing: '土',
    description: '主官禄、辅助、名声',
    scope: 'origin'
  },
  凤阁: {
    type: '杂耀',
    category: '吉星',
    wuxing: '火',
    description: '主才艺、名声、婚姻',
    scope: 'origin'
  },
  破碎: {
    type: '杂耀',
    category: '凶星',
    wuxing: '火',
    description: '主破坏、损失、意外',
    scope: 'origin'
  }
}; 