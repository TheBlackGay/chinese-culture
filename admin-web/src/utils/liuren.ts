import { Lunar } from 'lunar-typescript';

// 时辰地支对应表
const SHICHEN_ZHI: Record<string, string> = {
  '子': '子', '丑': '丑', '寅': '寅', '卯': '卯',
  '辰': '辰', '巳': '巳', '午': '午', '未': '未',
  '申': '申', '酉': '酉', '戌': '戌', '亥': '亥'
};

// 月德地支对应表
const YUE_DE_ZHI: Record<number, string> = {
  1: '寅', 2: '卯', 3: '辰', 4: '巳', 5: '午', 6: '未',
  7: '申', 8: '酉', 9: '戌', 10: '亥', 11: '子', 12: '丑'
};

// 天干
export const TIAN_GAN = ['甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸'];

// 地支
export const DI_ZHI = ['子', '丑', '寅', '卯', '辰', '巳', '午', '未', '申', '酉', '戌', '亥'];

// 六合
export const LIU_HE: Record<string, string> = {
  '子': '丑', '丑': '子', '寅': '亥', '亥': '寅',
  '卯': '戌', '戌': '卯', '辰': '酉', '酉': '辰',
  '巳': '申', '申': '巳', '午': '未', '未': '午'
};

// 六仪配天盘
export const LIU_JIA_PAN: Record<string, string[]> = {
  '甲子': ['戊', '己', '庚', '辛', '壬', '癸', '', '', '', ''],
  '甲戌': ['己', '庚', '辛', '壬', '癸', '戊', '', '', '', ''],
  '甲申': ['庚', '辛', '壬', '癸', '戊', '己', '', '', '', ''],
  '甲午': ['辛', '壬', '癸', '戊', '己', '庚', '', '', '', ''],
  '甲辰': ['壬', '癸', '戊', '己', '庚', '辛', '', '', '', ''],
  '甲寅': ['癸', '戊', '己', '庚', '辛', '壬', '', '', '', '']
};

// 五行生克关系
export const WU_XING_RELATION: Record<string, Record<string, string>> = {
  '木': { '木': '比和', '火': '生扶', '土': '克泄', '金': '被克', '水': '生我' },
  '火': { '木': '生我', '火': '比和', '土': '生扶', '金': '克泄', '水': '被克' },
  '土': { '木': '被克', '火': '生我', '土': '比和', '金': '生扶', '水': '克泄' },
  '金': { '木': '克泄', '火': '被克', '土': '生我', '金': '比和', '水': '生扶' },
  '水': { '木': '生扶', '火': '克泄', '土': '被克', '金': '生我', '水': '比和' }
};

// 天干五行
export const GAN_WU_XING: Record<string, string> = {
  '甲': '木', '乙': '木',
  '丙': '火', '丁': '火',
  '戊': '土', '己': '土',
  '庚': '金', '辛': '金',
  '壬': '水', '癸': '水'
};

// 六壬神将
export const SHEN_JIANG = [
  '贵人', '腾蛇', '朱雀', '六合', '勾陈', '青龙', 
  '天空', '白虎', '太常', '玄武', '太阴', '天后'
];

// 天盘遁甲
export const TIAN_PAN_DUN_JIA: Record<string, number> = {
  '甲子': 0, '乙丑': 2, '丙寅': 4, '丁卯': 6, '戊辰': 8, '己巳': 0,
  '庚午': 2, '辛未': 4, '壬申': 6, '癸酉': 8, '甲戌': 0, '乙亥': 2,
  '丙子': 4, '丁丑': 6, '戊寅': 8, '己卯': 0, '庚辰': 2, '辛巳': 4,
  '壬午': 6, '癸未': 8, '甲申': 0, '乙酉': 2, '丙戌': 4, '丁亥': 6,
  '戊子': 8, '己丑': 0, '庚寅': 2, '辛卯': 4, '壬辰': 6, '癸巳': 8,
  '甲午': 0, '乙未': 2, '丙申': 4, '丁酉': 6, '戊戌': 8, '己亥': 0,
  '庚子': 2, '辛丑': 4, '壬寅': 6, '癸卯': 8, '甲辰': 0, '乙巳': 2,
  '丙午': 4, '丁未': 6, '戊申': 8, '己酉': 0, '庚戌': 2, '辛亥': 4,
  '壬子': 6, '癸丑': 8, '甲寅': 0, '乙卯': 2, '丙辰': 4, '丁巳': 6,
  '戊午': 8, '己未': 0, '庚申': 2, '辛酉': 4, '壬戌': 6, '癸亥': 8
};

// 十二长生
export const CHANG_SHENG = ['长生', '沐浴', '冠带', '临官', '帝旺', '衰', '病', '死', '墓', '绝', '胎', '养'];

// 日干对应的起始神将索引
export const GAN_SHEN_JIANG_START: Record<string, number> = {
  '甲': 0, '乙': 2, '丙': 4, '丁': 6,
  '戊': 8, '己': 0, '庚': 2, '辛': 4,
  '壬': 6, '癸': 8
};

// 地支对应的神将偏移量
export const ZHI_SHEN_JIANG_OFFSET: Record<string, number> = {
  '子': 0, '丑': 1, '寅': 2, '卯': 3,
  '辰': 4, '巳': 5, '午': 6, '未': 7,
  '申': 8, '酉': 9, '戌': 10, '亥': 11
};

// 地支五行
export const ZHI_WU_XING: Record<string, string> = {
  '子': '水', '丑': '土', '寅': '木', '卯': '木',
  '辰': '土', '巳': '火', '午': '火', '未': '土',
  '申': '金', '酉': '金', '戌': '土', '亥': '水'
};

// 神将吉凶
export const SHEN_JIANG_ATTRIBUTE: Record<string, {
  nature: '吉' | '凶' | '中';
  description: string;
}> = {
  '贵人': { nature: '吉', description: '主贵人相助，谋事顺遂。' },
  '腾蛇': { nature: '凶', description: '主阴谋诡计，小人暗算。' },
  '朱雀': { nature: '凶', description: '主口舌是非，争执不和。' },
  '六合': { nature: '吉', description: '主和合团圆，诸事顺遂。' },
  '勾陈': { nature: '凶', description: '主忧愁烦恼，事多阻滞。' },
  '青龙': { nature: '吉', description: '主吉祥如意，万事亨通。' },
  '天空': { nature: '凶', description: '主虚而不实，难成难就。' },
  '白虎': { nature: '凶', description: '主凶险之事，宜谨慎行事。' },
  '太常': { nature: '吉', description: '主稳定吉祥，诸事平安。' },
  '玄武': { nature: '凶', description: '主暗昧不明，防小人暗算。' },
  '太阴': { nature: '中', description: '主阴柔之象，宜静不宜动。' },
  '天后': { nature: '吉', description: '主贵人相助，喜庆之事。' }
};

// 课体类型
export const KE_TI_TYPE = {
  CHUAN_GONG: '传宫',
  FAN_GONG: '反宫',
  AN_GONG: '安宫',
  KE_GONG: '克宫'
} as const;

// 三传类型
export const SAN_CHUAN_TYPE = {
  FAN_YIN: '反吟',
  FAN_YIN_ZHI_HAI: '反吟之害',
  ZHI_HAI: '昼贵人临日阳刑、夜贵人临日阴刑',
  XIANG_SHENG: '相生',
  XIANG_KE: '相克',
  XIANG_HE: '比和',
  FANG_YIN: '伏吟'
} as const;

// 阳刑地支对应
export const YANG_XING: Record<string, string> = {
  '寅': '巳',
  '巳': '申',
  '申': '寅',
  '丑': '戌',
  '戌': '未',
  '未': '丑',
  '子': '卯',
  '卯': '子',
  '辰': '辰',
  '午': '午',
  '酉': '酉',
  '亥': '亥'
};

// 阴刑地支对应
export const YIN_XING: Record<string, string> = {
  '寅': '申',
  '申': '巳',
  '巳': '寅',
  '丑': '未',
  '未': '戌',
  '戌': '丑',
  '子': '卯',
  '卯': '子',
  '辰': '辰',
  '午': '午',
  '酉': '酉',
  '亥': '亥'
};

// 格局类型
export const GE_JU_TYPE = {
  TIAN_DE: '天德',
  TIAN_YI: '天乙',
  TIAN_RONG: '天荣',
  DI_DE: '地德',
  DI_YI: '地乙',
  DI_RONG: '地荣',
  REN_DE: '人德',
  REN_YI: '人乙',
  REN_RONG: '人荣',
  KONG_WANG: '空亡',
  JIN_YU: '金舆',
  YUE_DE: '月德',
  TIAN_MA: '天马'
} as const;

// 天德贵神对应
export const TIAN_DE_SHEN: Record<string, string> = {
  '甲': '丁',
  '乙': '申',
  '丙': '寅',
  '丁': '亥',
  '戊': '巳',
  '己': '申',
  '庚': '寅',
  '辛': '亥',
  '壬': '巳',
  '癸': '申'
};

// 天乙贵神对应
export const TIAN_YI_SHEN: Record<string, string> = {
  '甲': '丑',
  '乙': '子',
  '丙': '亥',
  '丁': '戌',
  '戊': '酉',
  '己': '申',
  '庚': '未',
  '辛': '午',
  '壬': '巳',
  '癸': '辰'
};

// 三传描述
export const SAN_CHUAN_DESCRIPTION: Record<string, string> = {
  '伏吟': '伏吟之课，主事情原地踏步，需耐心等待。',
  '反吟': '反吟之课，主事情有反复，需谨慎对待。',
  '反吟之害': '反吟之害，主事情有凶险，需特别谨慎。',
  '贼克': '贼克之课，主事情有损害，需防范小人。',
  '六合': '六合之课，主事情和合，可成就好事。',
  '相生': '相生之课，主事情顺遂，可获得助力。',
  '比用': '比用之课，主事情平稳，循序渐进。'
};

// 格局描述
export const GE_JU_DESCRIPTION: Record<string, string> = {
  '天乙贵人': '天乙贵人格，主贵人相助，有贵人提携。',
  '三奇贵人': '三奇贵人格，主智慧超群，能成大事。',
  '天德': '天德格，主吉祥如意，诸事顺遂。',
  '月德': '月德格，主月中吉事，诸事顺遂。',
  '天马': '天马格，主行动迅速，事业远行。',
  '金舆': '金舆格，主财运亨通，富贵吉祥。'
};

// 三奇六仪
export const SAN_QI_LIU_YI: Record<string, string[]> = {
  '甲子': ['戊', '己', '庚', '辛', '壬', '癸', '', '', '', ''],
  '甲戌': ['己', '庚', '辛', '壬', '癸', '戊', '', '', '', ''],
  '甲申': ['庚', '辛', '壬', '癸', '戊', '己', '', '', '', ''],
  '甲午': ['辛', '壬', '癸', '戊', '己', '庚', '', '', '', ''],
  '甲辰': ['壬', '癸', '戊', '己', '庚', '辛', '', '', '', ''],
  '甲寅': ['癸', '戊', '己', '庚', '辛', '壬', '', '', '', '']
};

// 天乙贵人
export const TIAN_YI_GUI_REN: Record<string, string[]> = {
  '甲': ['丑', '未'], '乙': ['子', '申'],
  '丙': ['亥', '酉'], '丁': ['亥', '酉'],
  '戊': ['丑', '未'], '己': ['子', '申'],
  '庚': ['丑', '未'], '辛': ['子', '申'],
  '壬': ['巳', '卯'], '癸': ['巳', '卯']
};

// 三奇贵人
export const SAN_QI_GUI_REN = ['乙', '丙', '丁'];

// 大六壬核心计算类
export class LiuRen {
  private lunar: any;
  private shiChen: string;
  private questionType: string;
  private tianPan: string[];
  private diPan: string[];
  private siKe: {
    tianKe: string;
    diKe: string;
    renKe: string;
    diZhi: string;
  };
  private sanChuan: {
    type: string;
    chu: string;
    zhong: string;
    mo: string;
  };

  constructor(date: Date, shiChen: string, questionType: string) {
    this.lunar = Lunar.fromDate(date);
    this.shiChen = shiChen;
    this.questionType = questionType;
    this.diPan = this.calculateDiPan();
    this.tianPan = this.calculateTianPan();
    this.siKe = this.calculateSiKe();
    this.sanChuan = this.calculateSanChuan();
  }

  // 获取日干支
  getDayGanZhi(): { gan: string; zhi: string } {
    const dayGanZhi = this.lunar.getDayInGanZhi();
    return {
      gan: dayGanZhi.substring(0, 1),
      zhi: dayGanZhi.substring(1, 2)
    };
  }

  // 获取时辰地支
  getTimeZhi(): string {
    const timeZhi = SHICHEN_ZHI[this.shiChen];
    if (!timeZhi) {
      throw new Error(`无效的时辰：${this.shiChen}`);
    }
    return timeZhi;
  }

  // 计算地盘
  private calculateDiPan(): string[] {
    // 地盘固定为十二地支，从子开始顺排
    return [...DI_ZHI];
  }

  // 计算天盘
  private calculateTianPan(): string[] {
    const dayGanZhi = this.getDayGanZhi();
    const dayGan = dayGanZhi.gan;
    const dayZhi = dayGanZhi.zhi;
    console.log('日干支：', dayGan + dayZhi);
    
    // 1. 获取遁甲数
    const dunJiaNum = TIAN_PAN_DUN_JIA[dayGan + dayZhi];
    if (typeof dunJiaNum !== 'number') {
      throw new Error(`无法找到遁甲数：${dayGan + dayZhi}`);
    }
    console.log('遁甲数：', dunJiaNum);
    
    // 2. 确定六甲日
    const liuJiaZhi = ['子', '戌', '申', '午', '辰', '寅'];
    const liuJiaIndex = Math.floor(dunJiaNum / 2);
    const liuJiaKey = `甲${liuJiaZhi[liuJiaIndex]}`;
    console.log('六甲配天盘键：', liuJiaKey);
    
    // 3. 获取天盘天干序列
    const baseSequence = ['甲', '乙', '丙', '丁', '戊', '己', '庚', '辛', '壬', '癸'];
    const ganIndex = baseSequence.indexOf(dayGan);
    if (ganIndex === -1) {
      throw new Error(`无法找到日干${dayGan}在天干序列中的位置`);
    }
    
    // 4. 根据日干调整天干序列
    const adjustedSequence = [
      ...baseSequence.slice(ganIndex),
      ...baseSequence.slice(0, ganIndex)
    ];
    
    // 5. 填充天盘
    const tianPan = new Array(12).fill('');
    for (let i = 0; i < 10; i++) {
      const index = (i + dunJiaNum) % 12;
      tianPan[index] = adjustedSequence[i];
    }
    
    // 6. 填充剩余位置
    for (let i = 0; i < 12; i++) {
      if (tianPan[i] === '') {
        tianPan[i] = '亡';
      }
    }
    
    console.log('最终天盘：', tianPan);
    
    return tianPan;
  }

  // 计算四课
  private calculateSiKe(): {
    tianKe: string;
    diKe: string;
    renKe: string;
    diZhi: string;
  } {
    const dayGan = this.getDayGanZhi().gan;
    const timeZhi = this.getTimeZhi();
    
    // 1. 天课：以日干寄宫所临之地支
    const tianKeIndex = this.tianPan.indexOf(dayGan);
    if (tianKeIndex === -1) {
      console.error('无法找到日干在天盘中的位置：', {
        dayGan,
        tianPan: this.tianPan
      });
      throw new Error(`无法找到日干${dayGan}在天盘中的位置`);
    }
    const tianKe = this.diPan[tianKeIndex];
    console.log('天课计算：', { dayGan, tianKeIndex, tianKe });
    
    // 2. 地课：天课所临之地支
    const diKeIndex = this.tianPan.findIndex(gan => gan !== '' && this.diPan[this.tianPan.indexOf(gan)] === tianKe);
    if (diKeIndex === -1) {
      console.error('无法找到地课：', {
        tianKe,
        tianPan: this.tianPan,
        diPan: this.diPan
      });
      throw new Error(`无法找到地课，天课为${tianKe}`);
    }
    const diKe = this.diPan[diKeIndex];
    console.log('地课计算：', { tianKe, diKeIndex, diKe });
    
    // 3. 人课：时辰地支所临之天干
    const renKeIndex = this.diPan.indexOf(timeZhi);
    if (renKeIndex === -1) {
      console.error('无法找到时辰地支在地盘中的位置：', {
        timeZhi,
        diPan: this.diPan
      });
      throw new Error(`无法找到时辰地支${timeZhi}在地盘中的位置`);
    }
    const renKe = this.tianPan[renKeIndex] || '';
    console.log('人课计算：', { timeZhi, renKeIndex, renKe });
    
    return {
      tianKe,
      diKe,
      renKe,
      diZhi: timeZhi
    };
  }

  // 计算三传
  private calculateSanChuan(): {
    type: string;
    chu: string;
    zhong: string;
    mo: string;
  } {
    const { tianKe, diKe, renKe, diZhi } = this.siKe;
    const dayGanZhi = this.getDayGanZhi();
    const isDay = this.isDay();

    // 1. 检查伏吟
    if (tianKe === diKe && diKe === diZhi) {
      return {
        type: '伏吟',
        chu: tianKe,
        zhong: this.findNextShengZhi(tianKe),
        mo: this.findNextShengZhi(this.findNextShengZhi(tianKe))
      };
    }

    // 2. 检查反吟
    const zhiIndex = DI_ZHI.indexOf(diZhi);
    const fanYinIndex = (zhiIndex + 6) % 12;
    if (diKe === DI_ZHI[fanYinIndex]) {
      const xingZhi = isDay ? this.getYangXing(diZhi) : this.getYinXing(diZhi);
      if (diKe === xingZhi) {
        return {
          type: '反吟之害',
          chu: diKe,
          zhong: DI_ZHI[fanYinIndex],
          mo: xingZhi
        };
      }
      return {
        type: '反吟',
        chu: diKe,
        zhong: DI_ZHI[fanYinIndex],
        mo: DI_ZHI[(zhiIndex + 3) % 12]
      };
    }

    // 3. 检查贼克
    const diKeWuXing = ZHI_WU_XING[diKe];
    const diZhiWuXing = ZHI_WU_XING[diZhi];
    
    if (!diKeWuXing || !diZhiWuXing) {
      console.error('无法获取五行属性：', { diKe, diZhi, diKeWuXing, diZhiWuXing });
      return {
        type: '比用',
        chu: diKe,
        zhong: diZhi,
        mo: renKe
      };
    }

    const wuXingRelation = WU_XING_RELATION[diKeWuXing]?.[diZhiWuXing];
    if (!wuXingRelation) {
      console.error('无法获取五行关系：', { diKeWuXing, diZhiWuXing });
      return {
        type: '比用',
        chu: diKe,
        zhong: diZhi,
        mo: renKe
      };
    }

    if (wuXingRelation === '克泄') {
      return {
        type: '贼克',
        chu: diKe,
        zhong: this.findNextKeZhi(diKe),
        mo: this.findNextKeZhi(this.findNextKeZhi(diKe))
      };
    }

    // 4. 检查六合
    if (LIU_HE[diKe] === diZhi || LIU_HE[diZhi] === diKe) {
      return {
        type: '六合',
        chu: diKe,
        zhong: diZhi,
        mo: renKe
      };
    }

    // 5. 检查相生
    if (wuXingRelation === '生扶') {
      return {
        type: '相生',
        chu: diKe,
        zhong: this.findNextShengZhi(diKe),
        mo: this.findNextShengZhi(this.findNextShengZhi(diKe))
      };
    }

    // 默认为比用
    return {
      type: '比用',
      chu: diKe,
      zhong: diZhi,
      mo: renKe
    };
  }

  // 判断昼夜
  private isDay(): boolean {
    const hour = parseInt(this.lunar.getTimeZhi());
    return hour >= 3 && hour < 15; // 寅时至申时为昼
  }

  // 查找下一个相克地支
  private findNextKeZhi(currentZhi: string): string {
    const currentWuXing = ZHI_WU_XING[currentZhi];
    for (const zhi of DI_ZHI) {
      const wuXing = ZHI_WU_XING[zhi];
      if (WU_XING_RELATION[currentWuXing][wuXing] === '克泄') {
        return zhi;
      }
    }
    return currentZhi;
  }

  // 查找下一个相生地支
  private findNextShengZhi(currentZhi: string): string {
    const currentWuXing = ZHI_WU_XING[currentZhi];
    for (const zhi of DI_ZHI) {
      const wuXing = ZHI_WU_XING[zhi];
      if (WU_XING_RELATION[currentWuXing][wuXing] === '生扶') {
        return zhi;
      }
    }
    return currentZhi;
  }

  // 计算神将
  calculateShenJiang(): string {
    const dayGan = this.getDayGanZhi().gan;
    const timeZhi = this.getTimeZhi();
    
    // 获取日干对应的起始神将位置
    const startIndex = GAN_SHEN_JIANG_START[dayGan];
    
    // 获取时支对应的偏移量
    const offset = ZHI_SHEN_JIANG_OFFSET[timeZhi];
    
    // 计算最终神将位置（注意要考虑逆行）
    const shenJiangIndex = (startIndex - offset + 12) % 12;
    
    return SHEN_JIANG[shenJiangIndex];
  }

  // 判断课体
  determineKeTi(): {
    type: typeof KE_TI_TYPE[keyof typeof KE_TI_TYPE];
    description: string;
  } {
    const dayGanWuXing = GAN_WU_XING[this.getDayGanZhi().gan];
    const timeZhiWuXing = ZHI_WU_XING[this.getTimeZhi()];
    const relation = WU_XING_RELATION[dayGanWuXing][timeZhiWuXing];

    if (relation === '生扶') {
      return {
        type: KE_TI_TYPE.CHUAN_GONG,
        description: '传宫课：日干生扶时支，为传宫课，主事情顺利进展。'
      };
    } else if (relation === '被克') {
      return {
        type: KE_TI_TYPE.FAN_GONG,
        description: '反宫课：日干被时支克制，为反宫课，主事情有波折。'
      };
    } else if (relation === '比和') {
      return {
        type: KE_TI_TYPE.AN_GONG,
        description: '安宫课：日干与时支比和，为安宫课，主事情平稳。'
      };
    } else {
      return {
        type: KE_TI_TYPE.KE_GONG,
        description: '克宫课：日干克制时支，为克宫课，主事情有阻力。'
      };
    }
  }

  // 判断格局
  determineGeJu(): string[] {
    const geJu: string[] = [];
    
    // 1. 检查天乙贵人
    const dayGan = this.getDayGanZhi().gan;
    const guiRenZhi = TIAN_YI_GUI_REN[dayGan] || [];
    if (guiRenZhi.some(zhi => this.siKe.tianKe === zhi || this.siKe.diKe === zhi)) {
      geJu.push('天乙贵人');
    }
    
    // 2. 检查三奇贵人
    if (SAN_QI_GUI_REN.some(gan => this.tianPan.includes(gan))) {
      geJu.push('三奇贵人');
    }
    
    // 3. 检查天德
    if (this.checkTianDe()) geJu.push('天德');
    
    // 4. 检查月德
    if (this.checkYueDe()) geJu.push('月德');
    
    // 5. 检查天马
    if (this.checkTianMa()) geJu.push('天马');
    
    // 6. 检查金舆
    if (this.checkJinYu()) geJu.push('金舆');
    
    return geJu;
  }

  // 检查天德
  private checkTianDe(): boolean {
    const dayGan = this.getDayGanZhi().gan;
    const tianDeZhi = {
      '甲': '丁', '乙': '申', '丙': '寅', '丁': '亥',
      '戊': '丑', '己': '未', '庚': '巳', '辛': '午',
      '壬': '戌', '癸': '酉'
    }[dayGan];
    return tianDeZhi === this.siKe.tianKe || tianDeZhi === this.siKe.diKe;
  }

  // 检查月德
  private checkYueDe(): boolean {
    const month = this.lunar.getMonth();
    const yueDeZhi = YUE_DE_ZHI[month];
    return yueDeZhi === this.siKe.tianKe || yueDeZhi === this.siKe.diKe;
  }

  // 检查天马
  private checkTianMa(): boolean {
    const dayZhi = this.getDayGanZhi().zhi;
    const tianMaZhi = {
      '申': '寅', '子': '寅', '辰': '寅',
      '巳': '亥', '酉': '亥', '丑': '亥',
      '寅': '申', '午': '申', '戌': '申',
      '亥': '巳', '卯': '巳', '未': '巳'
    }[dayZhi];
    return tianMaZhi === this.siKe.tianKe || tianMaZhi === this.siKe.diKe;
  }

  // 检查金舆
  private checkJinYu(): boolean {
    const dayGan = this.getDayGanZhi().gan;
    return (dayGan === '庚' || dayGan === '辛') && 
      (['申', '酉'].includes(this.siKe.tianKe) || ['申', '酉'].includes(this.siKe.diKe));
  }

  // 获取阳刑
  private getYangXing(zhi: string): string {
    const yangXing: Record<string, string> = {
      '子': '卯', '丑': '戌', '寅': '巳', '卯': '子',
      '辰': '未', '巳': '申', '午': '酉', '未': '辰',
      '申': '寅', '酉': '午', '戌': '丑', '亥': '亥'
    };
    return yangXing[zhi] || zhi;
  }

  // 获取阴刑
  private getYinXing(zhi: string): string {
    const yinXing: Record<string, string> = {
      '子': '未', '丑': '未', '寅': '戌', '卯': '戌',
      '辰': '丑', '巳': '申', '午': '巳', '未': '子',
      '申': '寅', '酉': '卯', '戌': '午', '亥': '酉'
    };
    return yinXing[zhi] || zhi;
  }

  // 获取结果解释
  getResult(): {
    tianPan: string[];
    diPan: string[];
    shenJiang: string;
    shenJiangAttribute: {
      nature: '吉' | '凶' | '中';
      description: string;
    };
    keTi: {
      type: string;
      description: string;
    };
    siKe: {
      tianKe: string;
      diKe: string;
      renKe: string;
      diZhi: string;
    };
    sanChuan: {
      type: string;
      chu: string;
      zhong: string;
      mo: string;
    };
    geJu: string[];
    analysis: string;
  } {
    const shenJiang = this.calculateShenJiang();
    const keTi = this.determineKeTi();
    const geJu = this.determineGeJu();
    const dayGanZhi = this.getDayGanZhi();

    // 根据神将和课体综合分析
    const shenJiangNature = SHEN_JIANG_ATTRIBUTE[shenJiang].nature;
    const keTiType = keTi.type;
    
    let analysis = `此课${dayGanZhi.gan}日${this.shiChen}时，遇${shenJiang}${shenJiangNature}神，为${keTiType}。\n`;
    analysis += SHEN_JIANG_ATTRIBUTE[shenJiang].description + '\n';
    analysis += keTi.description + '\n';
    analysis += `三传为${this.sanChuan.type}：初传${this.sanChuan.chu}，中传${this.sanChuan.zhong}，末传${this.sanChuan.mo}。\n`;
    analysis += SAN_CHUAN_DESCRIPTION[this.sanChuan.type] + '\n';
    
    if (geJu.length > 0) {
      analysis += `格局：${geJu.join('、')}。\n`;
      geJu.forEach(ju => {
        analysis += GE_JU_DESCRIPTION[ju] + '\n';
      });
    }

    // 根据问题类型给出具体建议
    analysis += '综合判断：';
    const positiveCount = (shenJiangNature === '吉' ? 1 : 0) + 
      (keTiType === KE_TI_TYPE.CHUAN_GONG ? 1 : 0) + 
      (this.sanChuan.type === SAN_CHUAN_TYPE.XIANG_SHENG ? 1 : 0) +
      geJu.length;

    switch (this.questionType) {
      case 'career':
        if (positiveCount >= 3) {
          analysis += '事业发展顺利，可大胆进取，贵人相助，必有所成。';
        } else if (positiveCount >= 1) {
          analysis += '事业发展有所阻碍，需稳扎稳打，循序渐进。';
        } else {
          analysis += '事业发展不利，需谨慎行事，暂时观望为宜。';
        }
        break;
      case 'love':
        if (positiveCount >= 3) {
          analysis += '感情发展顺利，可以主动表达，有望开花结果。';
        } else if (positiveCount >= 1) {
          analysis += '感情发展需要耐心，保持平和心态，顺其自然。';
        } else {
          analysis += '感情发展有波折，需要谨慎对待，避免冲动。';
        }
        break;
      case 'wealth':
        if (positiveCount >= 3) {
          analysis += '财运亨通，可以适度投资，有望获得收益。';
        } else if (positiveCount >= 1) {
          analysis += '财运平平，宜守不宜进，稳妥为上。';
        } else {
          analysis += '财运不佳，需要谨慎理财，避免冒险。';
        }
        break;
      case 'health':
        if (positiveCount >= 3) {
          analysis += '身体状况良好，可以适度锻炼，保持规律作息。';
        } else if (positiveCount >= 1) {
          analysis += '身体状况一般，需要注意休息，适当保养。';
        } else {
          analysis += '身体状况欠佳，需要及时调养，避免过度劳累。';
        }
        break;
      case 'action':
        if (positiveCount >= 3) {
          analysis += '时机良好，可以采取行动，把握机会。';
        } else if (positiveCount >= 1) {
          analysis += '时机一般，可以准备，但不宜急进。';
        } else {
          analysis += '时机不佳，不宜轻举妄动，需要再等待。';
        }
        break;
      default:
        if (positiveCount >= 3) {
          analysis += '整体形势大好，可以按计划进行，必有所成。';
        } else if (positiveCount >= 1) {
          analysis += '整体形势平稳，需要谨慎对待，循序渐进。';
        } else {
          analysis += '整体形势不佳，需要谨慎行事，暂时观望。';
        }
    }

    return {
      tianPan: this.tianPan,
      diPan: this.diPan,
      shenJiang,
      shenJiangAttribute: SHEN_JIANG_ATTRIBUTE[shenJiang],
      keTi,
      siKe: this.siKe,
      sanChuan: this.sanChuan,
      geJu,
      analysis
    };
  }
}

// 计算六壬函数
export function calculateLiuRen(date: Date, shiChen: string, questionType: string) {
  try {
    console.log('计算参数：', { date, shiChen, questionType });
    const liuren = new LiuRen(date, shiChen, questionType);
    const result = liuren.getResult();
    console.log('计算结果：', result);
    return result;
  } catch (error) {
    console.error('六壬计算出错：', error);
    throw error;
  }
} 