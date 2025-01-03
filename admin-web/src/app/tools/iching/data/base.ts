import { Hexagram } from '../types';

// 八卦基础数据
export const TRIGRAMS = {
  '111': {
    name: '乾',
    nature: '刚健',
    element: '金',
    symbol: '天',
    attribute: '健',
  },
  '000': {
    name: '坤',
    nature: '柔顺',
    element: '土',
    symbol: '地',
    attribute: '顺',
  },
  '010': {
    name: '震',
    nature: '动',
    element: '木',
    symbol: '雷',
    attribute: '动',
  },
  '101': {
    name: '离',
    nature: '丽',
    element: '火',
    symbol: '火',
    attribute: '明',
  },
  '011': {
    name: '兑',
    nature: '悦',
    element: '金',
    symbol: '泽',
    attribute: '说',
  },
  '100': {
    name: '乾',
    nature: '止',
    element: '土',
    symbol: '山',
    attribute: '止',
  },
  '001': {
    name: '坎',
    nature: '陷',
    element: '水',
    symbol: '水',
    attribute: '陷',
  },
  '110': {
    name: '巽',
    nature: '入',
    element: '木',
    symbol: '风',
    attribute: '入',
  },
} as const;

// 基础卦象数据
export const BASE_HEXAGRAMS: Record<string, Omit<Hexagram, 'lines'>> = {
  '111111': {
    key: '111111',
    name: '乾',
    sequence: 1,
    nature: '刚健',
    element: '金',
    palace: '乾',
    description: '元亨利贞。',
    image: '天行健，君子以自强不息。',
    yaoTexts: [
      '潜龙勿用。',
      '见龙在田，利见大人。',
      '君子终日乾乾，夕惕若厉，无咎。',
      '或跃在渊，无咎。',
      '飞龙在天，利见大人。',
      '亢龙有悔。',
    ],
    meaning: {
      general: '象征天，代表刚健、纯阳、积极向上的特质。提醒人要像天一样运行不息，保持进取心。',
      love: '感情上主动积极，但需要注意不要过于强势。单身者有机会遇到贵人相助。',
      career: '事业发展顺利，但需循序渐进，不可操之过急。领导者要以身作则，团队共同进步。',
      health: '精力充沛，但要注意不要过度劳累。保持运动习惯，注意作息规律。',
      wealth: '财运亨通，但要注意量力而行，不可贪多务得。适合稳健的投资方式。',
    },
    relationships: {
      opposite: '000000',
      inverse: '111111',
      mutual: '111111',
      nuclear: '111111',
    },
  },
  '000000': {
    key: '000000',
    name: '坤',
    sequence: 2,
    nature: '柔顺',
    element: '土',
    palace: '坤',
    description: '元亨，利牝马之贞。',
    image: '地势坤，君子以厚德载物。',
    yaoTexts: [
      '履霜，坚冰至。',
      '直方大，不习无不利。',
      '含章可贞，或从王事，无成有终。',
      '括囊，无咎无誉。',
      '黄裳，元吉。',
      '龙战于野，其血玄黄。',
    ],
    meaning: {
      general: '象征地，代表包容、承载、滋养的特质。提醒人要像大地一样厚德载物，包容万物。',
      love: '感情稳定发展，以包容和体贴为主。已婚者家庭和睦，未婚者应保持耐心。',
      career: '工作踏实稳健，积累经验为主。团队合作中发挥支持作用，得到认可。',
      health: '体质较弱，需要调养。注意保暖，适当运动，以柔和为主。',
      wealth: '财运平稳，适合稳健理财。不适合冒险投资，积少成多是关键。',
    },
    relationships: {
      opposite: '111111',
      inverse: '000000',
      mutual: '000000',
      nuclear: '000000',
    },
  },
  // ... 其他卦象数据
} as const; 